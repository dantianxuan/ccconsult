/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.PageList;
import com.ccconsult.dao.ArticleDAO;
import com.ccconsult.enums.ArticleTypeEnum;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.pojo.Article;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ArticleController extends BaseController {

    @Autowired
    private ArticleDAO articleDAO;

    @RequestMapping(value = "backstage/articleEdit.htm", method = RequestMethod.GET)
    public ModelAndView toPage(HttpServletRequest request, ModelMap modelMap) {
        String articleId = request.getParameter("articleId");
        if (!StringUtils.isBlank(articleId)) {
            modelMap.put("result", new CcResult(articleDAO.findById(NumberUtils.toInt(articleId))));
        }
        ModelAndView view = new ModelAndView("backstage/articleEdit");
        return view;
    }

    @RequestMapping(value = "backstage/articleList.htm", method = RequestMethod.GET)
    public ModelAndView manageArticles(HttpServletRequest request, ModelMap modelMap) {
        int pageSize = NumberUtils.toInt(request.getParameter("pageSize"));
        int pageNo = NumberUtils.toInt(request.getParameter("pageNo"));
        int type = NumberUtils.toInt(request.getParameter("type"));
        PageList<Article> articles = articleDAO.queryPage(pageNo, pageSize == 0 ? 20 : pageSize,
            type == 0 ? ArticleTypeEnum.WEB_NEWS.getValue() : type);
        modelMap.put("articles", articles);
        return new ModelAndView("backstage/articleList");
    }

    @RequestMapping(value = "backstage/articleEdit.htm", params = "action=save", method = RequestMethod.POST)
    public ModelAndView saveArtcile(HttpServletRequest request, final Article article,
                                    ModelMap modelMap) {
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                if (article.getId() != null && article.getId() > 0) {
                    Article localArticle = articleDAO.findById(article.getId());
                    AssertUtil.state(localArticle != null, "文章不存在");
                    localArticle.setContent(article.getContent());
                    localArticle.setGmtModified(new Date());
                    localArticle.setTitle(article.getTitle());
                    localArticle.setTopTag(article.getTopPhoto());
                    localArticle.setType(article.getType());
                    localArticle.setTopPhoto(article.getTopPhoto());
                    return new CcResult(localArticle);
                }
                article.setGmtCreate(new Date());
                article.setGmtModified(new Date());
                article.setState(DataStateEnum.NORMAL.getValue());
                articleDAO.save(article);
                return new CcResult(article);
            }
        });
        modelMap.put("result", result);
        return new ModelAndView("content/article");
    }

    @RequestMapping(value = "backstage/deleteArticle.json")
    public @ResponseBody
    ModelMap deleteArticle(HttpServletRequest request, final String articleId, ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                Article article = articleDAO.findById(NumberUtils.toInt(articleId));
                AssertUtil.state(article != null, "文章不存在");
                articleDAO.delete(article);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    //~~~~~~~~~~~~~~~后台管理~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @RequestMapping(value = "/article.htm", method = RequestMethod.GET)
    public ModelAndView articlePage(HttpServletRequest request, ModelMap modelMap) {
        int articleId = NumberUtils.toInt(request.getParameter("articleId"));
        if (articleId != 0) {
            modelMap.put("result", new CcResult(articleDAO.findById(articleId)));
            return new ModelAndView("content/article");
        }
        modelMap.put("result", new CcResult("非法请求"));
        return new ModelAndView("error", modelMap);
    }

    @RequestMapping(value = "/articles.htm", method = RequestMethod.GET)
    public ModelAndView articlesPage(HttpServletRequest request, ModelMap modelMap) {
        int pageSize = NumberUtils.toInt(request.getParameter("pageSize"));
        int pageNo = NumberUtils.toInt(request.getParameter("pageNo"));
        int type = NumberUtils.toInt(request.getParameter("type"));
        PageList<Article> articles = articleDAO.queryPage(pageNo, pageSize == 0 ? 20 : pageSize,
            type == 0 ? ArticleTypeEnum.WEB_NEWS.getValue() : type);
        modelMap.put("articles", articles);
        return new ModelAndView("content/articles");
    }
}

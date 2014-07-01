/**
 * 
 */
package com.ccconsult.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ArticleDAO;
import com.ccconsult.pojo.Article;
import com.ccconsult.service.ArticleService;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ArticleController {

    private final static int TOPX = 100;
    @Autowired
    private ArticleService   articleService;
    @Autowired
    private ArticleDAO       articleDAO;

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
        List<Article> articles = articleDAO.findRecentList(TOPX);
        modelMap.put("articles", articles);
        return new ModelAndView("backstage/articleList");
    }

    @RequestMapping(value = "backstage/articleEdit.htm", params = "action=save", method = RequestMethod.POST)
    public ModelAndView saveArtcile(HttpServletRequest request, Article article, ModelMap modelMap) {
        if (article.getId() != null && article.getId() > 0) {
            modelMap.put("result", articleService.updateArticle(article));
        } else {
            modelMap.put("result", articleService.saveArticle(article));
        }
        return new ModelAndView("content/article");
    }

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
        modelMap.put("result", new CcResult(articleDAO.findRecentList(TOPX)));
        return new ModelAndView("content/articles");
    }

}

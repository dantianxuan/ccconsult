/**
 * 
 */
package com.ccconsult.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.PageList;
import com.ccconsult.core.ConsultComponent;
import com.ccconsult.dao.ArticleDAO;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.enums.ArticleTypeEnum;
import com.ccconsult.enums.ConsultStepEnum;
import com.ccconsult.pojo.Article;
import com.ccconsult.pojo.Company;
import com.ccconsult.view.ConsultBase;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class IndexController {

    @Autowired
    private ArticleDAO       articleDAO;
    @Autowired
    private CompanyDAO       companyDAO;
    @Autowired
    private ConsultComponent consultComponent;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView toIndex(HttpServletRequest request, ModelMap modelMap) {
        String theme = request.getParameter(CcConstrant.THEME);
        if (theme != null) {
            request.getSession().setAttribute(CcConstrant.THEME, theme);
        }
        List<Article> articles = articleDAO.queryList(1, 10, ArticleTypeEnum.WEB_NEWS.getValue());
        List<Company> companys = companyDAO.queryTopList(10);
        modelMap.put("articles", articles);
        modelMap.put("companys", companys);
        PageList<ConsultBase> consultBases = consultComponent.queryPaged(2,
            ConsultStepEnum.FIHSHED.getValue(), 0, 0, 0, 10, 1);
        modelMap.put("consultBases", consultBases);
        ModelAndView view = new ModelAndView("content/index");
        return view;
    }

    @RequestMapping(value = "/mySelf.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, ModelMap modelMap) {
        Object jobseeker = request.getSession().getAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT);
        if (jobseeker != null) {
            return new ModelAndView("redirect:/consultant/consultantSelf.htm");
        }
        Object interviewer = request.getSession()
            .getAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT);
        if (interviewer != null) {
            return new ModelAndView("redirect:/counselor/counselorSelf.htm");
        }
        ModelAndView view = new ModelAndView("content/login");
        return view;
    }

}

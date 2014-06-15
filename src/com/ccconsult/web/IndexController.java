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
import com.ccconsult.dao.ArticleDAO;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.pojo.Article;
import com.ccconsult.pojo.Company;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class IndexController {

    private final static int TOPX = 16;

    @Autowired
    private ArticleDAO       articleDAO;
    @Autowired
    private CompanyDAO       companyDAO;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public ModelAndView toIndex(HttpServletRequest request, ModelMap modelMap) {
        List<Article> articles = articleDAO.findRecentList(TOPX);
        List<Company> companys = companyDAO.findAll();
        modelMap.put("articles", articles);
        modelMap.put("companys", companys);
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

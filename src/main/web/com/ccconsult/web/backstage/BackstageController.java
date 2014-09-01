/**
 * 
 */
package com.ccconsult.web.backstage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.core.file.FileComponent;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.web.BaseController;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class BackstageController extends BaseController {

    @Autowired
    private CompanyDAO    companyDAO;
    @Autowired
    private FileComponent fileComponent;

    @RequestMapping(value = "backstage/index.htm", method = RequestMethod.GET)
    public ModelAndView toPage(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("backstage/index");
        return view;
    }

}

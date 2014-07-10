/**
 * 
 */
package com.ccconsult.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class BackstageController extends BaseController {

    @RequestMapping(value = "backstage/index.htm", method = RequestMethod.GET)
    public ModelAndView toPage(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("backstage/index");
        return view;
    }

}

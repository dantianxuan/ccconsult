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
@RequestMapping("/error.htm")
public class ErrorController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest httpservletrequest, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("error");
        return view;
    }
}

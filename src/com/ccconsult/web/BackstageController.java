/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.core.FileComponent;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.enums.FileTypeEnum;
import com.ccconsult.pojo.Company;
import com.ccconsult.util.StringUtil;

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

/**
 * 
 */
package com.ccconsult.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.CcResult;
import com.ccconsult.base.PageList;
import com.ccconsult.base.util.StringUtil;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.pojo.Company;
import com.ccconsult.pojo.Counselor;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class SearchController {

    @Autowired
    private CompanyDAO   companyDAO;
    @Autowired
    private CounselorDAO counselorDAO;

    @RequestMapping(value = "/searchList.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, String type, ModelMap modelMap) {
        String keyword = request.getParameter("keyword");
        CcResult result = new CcResult(true);
        int pageSize = request.getParameter("pageSize") == null ? 20 : NumberUtils.toInt(request
            .getParameter("pageSize"));
        int pageNo = request.getParameter("pageNo") == null ? 1 : NumberUtils.toInt(request
            .getParameter("pageNo"));
        if (!StringUtil.equals("COUNSELOR", type)) {
            type = "COMPANY";
            PageList<Company> companys = companyDAO.queryByName(pageNo, pageSize, keyword);
            result.setObject(companys);
        } else {
            PageList<Counselor> counselors = counselorDAO.queryByName(pageNo, pageSize, keyword);
            result.setObject(counselors);
        }
        modelMap.put("keyword", keyword);
        modelMap.put("type", type);
        modelMap.put("result", result);
        ModelAndView view = new ModelAndView("content/searchList");
        return view;
    }

}

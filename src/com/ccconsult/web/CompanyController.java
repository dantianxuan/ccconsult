/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.PageList;
import com.ccconsult.core.FileComponent;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.enums.FileTypeEnum;
import com.ccconsult.pojo.Company;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.util.StringUtil;
import com.ccconsult.view.CompanyBriefVO;

/**
 * 
 * @author jingyu.dan
 * @version $Id: CompanyController.java, v 0.1 2014-5-31 上午10:19:52 jingyu.dan Exp $
 */
@Controller
public class CompanyController extends BaseController {
    @Autowired
    private CompanyDAO    companyDAO;
    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private FileComponent fileComponent;

    @RequestMapping(value = "/company.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/company");
        int companyId = NumberUtils.toInt(request.getParameter("companyId"));
        //查询公司信息
        Company company = companyDAO.findById(companyId);
        //查询公司注册员工
        List<Counselor> counselors = counselorDAO.findByCompanyId(companyId);
        modelMap.put("company", company);
        modelMap.put("counselors", counselors);
        return view;
    }

    @RequestMapping(value = "backstage/companyEdit.htm", method = RequestMethod.GET)
    public ModelAndView toPage(HttpServletRequest request, ModelMap modelMap) {
        String companyId = request.getParameter("companyId");
        if (!StringUtils.isBlank(companyId)) {
            modelMap.put("company", companyDAO.findById(NumberUtils.toInt(companyId)));
        }
        ModelAndView view = new ModelAndView("backstage/companyEdit");
        return view;
    }

    @RequestMapping(value = "backstage/companyList.htm", method = RequestMethod.GET)
    public ModelAndView manageArticles(HttpServletRequest request, ModelMap modelMap) {
        int pageSize = NumberUtils.toInt(request.getParameter("pageSize"));
        int pageNo = NumberUtils.toInt(request.getParameter("pageNo"));
        String name = request.getParameter("name");
        PageList<CompanyBriefVO> companyBriefVOs = companyDAO.queryByName(pageNo,
            pageSize == 0 ? 20 : pageSize, name);
        modelMap.put("companyBriefVOs", companyBriefVOs);
        return new ModelAndView("backstage/companyList");
    }

    @RequestMapping(value = "backstage/companyEdit.htm", params = "action=save", method = RequestMethod.POST)
    public ModelAndView saveCompany(final HttpServletRequest request, final Company company,
                                    @RequestParam final MultipartFile[] localPhoto,
                                    ModelMap modelMap) {
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                String fileName = "";
                String contextPath = request.getSession().getServletContext().getRealPath("/");
                for (MultipartFile myfile : localPhoto) {
                    if (myfile == null || myfile.isEmpty()) {
                        continue;
                    }
                    fileName = fileComponent.uploadFile(myfile, FileTypeEnum.COMPANY_PHOTO,
                        contextPath, CcConstrant.FILE_2M_SIZE, "jpg|jpeg|png|gif");
                }
                if (!StringUtil.isBlank(fileName)) {
                    company.setPhoto(fileName);
                }

                if (company.getId() != null && company.getId() > 0) {
                    Company localCompany = companyDAO.findById(company.getId());
                    AssertUtil.state(localCompany != null, "公司信息不存在");
                    localCompany.setDescription(company.getDescription());
                    localCompany.setGmtModified(new Date());
                    localCompany.setLink(company.getLink());
                    localCompany.setMailSuffix(company.getMailSuffix());
                    localCompany.setName(company.getName());
                    localCompany.setTopTag(company.getTopTag());
                    if (company.getPhoto() != null) {
                        localCompany.setPhoto(company.getPhoto());
                    }
                    companyDAO.update(localCompany);
                    return new CcResult(localCompany);
                }
                company.setGmtCreate(new Date());
                company.setGmtModified(new Date());
                companyDAO.save(company);
                return new CcResult(company);
            }
        });
        if (result.isSuccess()) {
            return new ModelAndView("redirect:/backstage/companyEdit.htm?companyId="
                                    + company.getId());
        }
        modelMap.put("result", result);
        return new ModelAndView("backstage/companyEdit");
    }

    @RequestMapping(value = "backstage/deleteCompany.json")
    public @ResponseBody
    ModelMap deleteArticle(HttpServletRequest request, final String articleId, ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                //                Article article = articleDAO.findById(NumberUtils.toInt(articleId));
                //                AssertUtil.state(article != null, "文章不存在");
                //                articleDAO.delete(article);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

}

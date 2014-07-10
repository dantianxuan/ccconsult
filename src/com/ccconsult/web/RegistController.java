/**
 * 
 */
package com.ccconsult.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.CompanyDAO;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Counselor;
import com.ccconsult.pojo.RegMail;
import com.ccconsult.service.RegistService;
import com.ccconsult.util.LogUtil;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class RegistController {

    /**日志 */
    private static final Logger logger = Logger.getLogger(RegistController.class);

    @Autowired
    private RegistService       registService;

    @Autowired
    private CompanyDAO          companyDAO;

    /**
     * 注册面试官init页面
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselorInit.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest httpservletrequest, ModelMap modelMap) {
        modelMap.put("companys", companyDAO.findAll());
        ModelAndView view = new ModelAndView("regist/regCounselorInit");
        return view;
    }

    /**
     * 使用公司邮箱进行注册
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselorMail.htm", method = RequestMethod.GET)
    public ModelAndView registMail(HttpServletRequest httpservletrequest, String email,
                                   String subPrefix, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("regist/regCounselorInit");
        RegMail regMail = new RegMail();
        regMail.setMail(email + subPrefix);
        regMail.setGmtCreate(new Date());
        regMail.setToken(UUID.randomUUID().toString());
        CcResult result = registService.regMail(regMail);
        modelMap.put("result", result);
        return view;
    }

    /**
     * 公司邮箱链接注册
     * 
     * @return
     */
    @RequestMapping(value = "regist/regCounselor.htm", method = RequestMethod.GET)
    public ModelAndView initRegInterviewer(String token, ModelMap modelMap) {
        CcResult result = registService.getRegMainInfo(token);
        if (!result.isSuccess()) {
            modelMap.put("result", result);
            return new ModelAndView("redirect:regist/regCounselorInit.htm");
        }
        modelMap.put("result", result);
        Counselor counselor = new Counselor();
        counselor.setEmail(((RegMail) result.getObject()).getMail());
        modelMap.put("counselor", counselor);
        modelMap.put("regMailId", ((RegMail) result.getObject()).getId());
        return new ModelAndView("regist/regConsultant");
    }

    /**
     * 公司邮箱链接注册
     * @return
     */
    @RequestMapping(value = "regist/regCounselor.htm", params = "action=regist")
    public ModelAndView submitRegInterviewer(HttpServletRequest request, Counselor counselor,
                                             String repasswd,
                                             @RequestParam MultipartFile[] localPhoto,
                                             String regMailId, ModelMap modelMap) {
        CcResult result = null;
        String fileName = "";
        try {
            for (MultipartFile myfile : localPhoto) {
                if (myfile.isEmpty()) {
                    System.out.println("文件未上传");
                } else {
                    System.out.println("文件长度: " + myfile.getSize() + "文件类型: "
                                       + myfile.getContentType() + "文件名称: " + myfile.getName()
                                       + "文件原名: " + myfile.getOriginalFilename());
                    String path = request.getSession().getServletContext().getRealPath("/")
                                  + "UPLOAD";
                    File parentFile = new File(path);
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    fileName = UUID.randomUUID().toString() + myfile.getOriginalFilename();
                    FileCopyUtils.copy(myfile.getBytes(), new File(path, fileName));
                }
            }
            counselor.setGmtCreate(new Date());
            counselor.setPhoto(fileName);
            counselor.setGmtModified(new Date());
            if (StringUtils.equals(counselor.getPasswd(), repasswd)) {
                result = registService.regCounselor(counselor, NumberUtils.toInt(regMailId));
            } else {
                result = new CcResult("重复密码输入不一致");
            }

        } catch (Exception e) {
            LogUtil.error(logger, e, "文件上传失败");
            result = new CcResult("文件上传失败");
        }
        if (result.isSuccess()) {
            request.getSession().setAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT,
                result.getObject());
            return new ModelAndView("redirect:/counselor/counselorSelf.htm");
        }

        modelMap.put("result", result);
        modelMap.put("regMailId", regMailId);
        modelMap.put("counselor", counselor);
        return new ModelAndView("regist/regCounselor");
    }

    /**
     * @return
     */
    @RequestMapping(value = "regist/regConsultant.htm", method = RequestMethod.GET)
    public ModelAndView submitRegJobseeker(HttpServletRequest request, ModelMap modelMap) {
        return new ModelAndView("regist/regConsultant");
    }

    /**
     * 公司邮箱链接注册
     * 
     * @return
     * @throws IOException 
     * @throws Exception
     */
    @RequestMapping(value = "regist/regConsultant.htm", params = "action=regist")
    public ModelAndView submitRegConsultant(HttpServletRequest request, Consultant consultant,
                                            String repasswd, ModelMap modelMap) {
        CcResult result = null;
        consultant.setGmtCreate(new Date());
        consultant.setGmtModified(new Date());
        if (StringUtils.equals(consultant.getPasswd(), repasswd)) {
            result = registService.regConsultant(consultant);
        } else {
            result = CcResult.retFailure("重复密码输入不一致");
        }
        if (result.isSuccess()) {
            request.getSession().setAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT,
                result.getObject());
            return new ModelAndView("redirect:/consultant/consultantSelf.htm?consultantId="
                                    + consultant.getId());
        }

        modelMap.put("result", result);
        modelMap.put("consultant", consultant);
        return new ModelAndView("regist/regConsultant");
    }
}

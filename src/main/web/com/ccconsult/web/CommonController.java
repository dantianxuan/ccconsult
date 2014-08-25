/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.enums.FileTypeEnum;
import com.ccconsult.base.util.FileUtil;
import com.ccconsult.base.util.LogUtil;
import com.ccconsult.core.FileComponent;

/**
 * 
 * controller的基类
 * @author jingyu.dan
 * @version $Id: BaseController.java, v 0.1 2014-5-29 下午11:17:50 jingyu.dan Exp $
 */
@Controller
public class CommonController extends BaseController {

    /**日志 */
    private static final Logger logger = Logger.getLogger(CommonController.class);
    @Autowired
    private FileComponent       fileComponent;

    @RequestMapping(value = "/uploadFile.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap saveCompany(final HttpServletRequest request, final String fileType,
                         @RequestParam final MultipartFile[] uploadFiles, final String prefixReg,
                         ModelMap modelMap) {
        modelMap.clear();
        final Map<String, String> filePaths = new HashMap<String, String>();
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                try {
                    FileTypeEnum fileTypeEnum = FileTypeEnum.getByCode(fileType);
                    AssertUtil.notNull(fileTypeEnum, "上传文件类别（非类型）不存在");
                    String contextPath = request.getSession().getServletContext().getRealPath("/");
                    for (MultipartFile myfile : uploadFiles) {
                        if (!myfile.isEmpty()) {
                            String file = fileComponent.uploadFile(myfile, fileTypeEnum,
                                contextPath, CcConstrant.FILE_2M_SIZE, prefixReg);
                            filePaths.put(file, FileUtil.getPath(file));
                        }
                    }
                } catch (Exception e) {
                    LogUtil.error(logger, e, "文件上传失败");
                    throw new CcException("文件上传失败");
                }
                return new CcResult(filePaths);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

}

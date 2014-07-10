/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core;

import java.io.File;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.enums.FileTypeEnum;
import com.ccconsult.util.LogUtil;
import com.ccconsult.web.ConsultantController;

/**
 * 
 * @author jingyudan
 * @version $Id: FileComponentImpl.java, v 0.1 2014-7-9 下午8:21:49 jingyudan Exp $
 */
public class FileComponentImpl implements FileComponent {
    /**日志 */
    private static final Logger logger = Logger.getLogger(FileComponentImpl.class);

    @Override
    public File uploadFile(MultipartFile myfile, FileTypeEnum fileTypeEnm, String contextPath) {
        File file = null;
        try {
            LogUtil.info(logger,
                "文件长度: " + myfile.getSize() + "文件类型: " + myfile.getContentType() + "文件名称: "
                        + myfile.getName() + "文件原名: " + myfile.getOriginalFilename());
            String path = contextPath + "UPLOAD/" + fileTypeEnm.getCode() + "/";
            File parentFile = new File(path);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            //文件名称 文件原始名称+文件类型+uuid
            String fileName = fileTypeEnm.getCode() + CcConstrant.ALT_SEPARATOR
                              + System.currentTimeMillis() + CcConstrant.ALT_SEPARATOR
                              + myfile.getOriginalFilename().replace(CcConstrant.ALT_SEPARATOR, "");
            file = new File(path, fileName);
            FileCopyUtils.copy(myfile.getBytes(), file);
        } catch (Exception e) {
            LogUtil.error(logger, "文件上传失败");
        }
        return file;
    }
}

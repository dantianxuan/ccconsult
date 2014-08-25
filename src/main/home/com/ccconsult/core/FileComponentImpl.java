/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.enums.FileTypeEnum;
import com.ccconsult.util.DateUtil;
import com.ccconsult.util.LogUtil;
import com.ccconsult.util.StringUtil;

/**
 * 文件命名规则：uuid.prefix （文件保存中）
 * 文件命名规则：文件类型枚举code@上传时间@原始文件名@uuid.prefix （该部分保存在数据库字段中）
 * 
 * @author jingyudan
 * @version $Id: FileComponentImpl.java, v 0.1 2014-7-9 下午8:21:49 jingyudan Exp $
 */
public class FileComponentImpl implements FileComponent {
    /**日志 */
    private static final Logger logger = Logger.getLogger(FileComponentImpl.class);

    @Override
    public String uploadFile(MultipartFile myfile, FileTypeEnum fileTypeEnm, String contextPath,
                             int maxByteSize, String prefixReg) {
        File file = null;
        String uuid = UUID.randomUUID().toString().replace(CcConstrant.ALT_SEPARATOR, "");
        AssertUtil.state(myfile.getSize() <= maxByteSize, "文件太大，不能上传");
        String prefix = myfile.getOriginalFilename().substring(
            myfile.getOriginalFilename().lastIndexOf(".") + 1);
        if (!StringUtil.isBlank(prefixReg)) {
            String prefixLower = StringUtil.toLowerCase(prefix);
            AssertUtil.state(prefixLower.matches(prefixReg), "不允许的文件类型");
        }
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
            String fileName = uuid + "." + prefix;
            file = new File(path, fileName);
            FileCopyUtils.copy(myfile.getBytes(), file);
        } catch (Exception e) {
            LogUtil.error(logger, "文件上传失败");
        }
        return fileTypeEnm.getCode() + CcConstrant.ALT_SEPARATOR
               + DateUtil.format(new Date(), DateUtil.longFormat) + CcConstrant.ALT_SEPARATOR
               + myfile.getOriginalFilename().replace(CcConstrant.ALT_SEPARATOR, "")
               + CcConstrant.ALT_SEPARATOR + uuid + "." + prefix;
    }
}

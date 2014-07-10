/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.ccconsult.enums.FileTypeEnum;

/**
 * 
 * @author jingyudan
 * @version $Id: FileComponent.java, v 0.1 2014-7-9 下午7:59:47 jingyudan Exp $
 */
public interface FileComponent {

    /**
     * 上传文件到指定目录
     * 
     * @param myfile
     * @param path
     * @return
     */
    public File uploadFile(MultipartFile myfile, FileTypeEnum fileTypeEnm, String contextPath);

}

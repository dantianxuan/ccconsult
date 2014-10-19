/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import org.springframework.web.multipart.MultipartFile;

import com.ccconsult.base.CcResult;
import com.ccconsult.pojo.Consult;

/**
 * 
 * @author jingyudan
 * @version $Id: ConsultResumeService.java, v 0.1 2014年10月19日 上午8:00:44 jingyudan Exp $
 */
public interface ConsultResumeService {

    /**
     * 创建一笔咨询请求
     * 
     * @return
     */
    public CcResult create(final Consult consult, final MultipartFile[] localFile,
                           final String contextPath);

}

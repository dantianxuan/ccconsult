/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象类
 * 
 * @author jingyu.dan
 * @version $Id: AbstractService.java, v 0.1 2014-5-30 下午8:00:21 jingyu.dan Exp $
 */
public class AbstractService {

    @Autowired
    protected ServiceTemplate serviceTemplate;

}

/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import com.ccconsult.base.CcResult;
import com.ccconsult.pojo.Counselor;

/**
 * 
 * @author jingyu.dan
 * @version $Id: CounselorService.java, v 0.1 2014-5-28 下午8:36:13 jingyu.dan Exp $
 */
public interface CounselorService {

    /**
     * 修改面试官信息
     * 
     * @param userInfo
     * @return
     */
    public CcResult updateCounselor(Counselor counselor);

    /**
     * 删除面试官信息
     * 
     * @param id
     * @return
     */
    public CcResult deleteCounselor(int id);

}

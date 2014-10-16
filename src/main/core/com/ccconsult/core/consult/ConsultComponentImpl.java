/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.core.consult;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccconsult.base.CcResult;
import com.ccconsult.core.notify.NotifySender;
import com.ccconsult.core.order.OrderComponent;
import com.ccconsult.dao.ArticleDAO;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.MessageDAO;

/**
 * 
 * @author jingyudan
 * @version $Id: ConsultComponentImpl.java, v 0.1 2014-10-3 下午4:14:12 jingyudan Exp $
 */
public class ConsultComponentImpl implements ConsultComponent {

    @Autowired
    private ConsultDAO            consultDAO;
    @Autowired
    private MessageDAO            messageDAO;
    @Autowired
    private ConsultQueryComponent consultComponent;
    @Autowired
    private ArticleDAO            articleDAO;
    @Autowired
    private NotifySender          notifySender;
    @Autowired
    private OrderComponent        orderComponent;

    /** 
     * @see com.ccconsult.core.consult.ConsultComponent#rejectConsult(int, java.lang.String)
     */
    @Override
    public CcResult rejectConsult(int consultId, String reason) {
        return null;
    }

    /** 
     * @see com.ccconsult.core.consult.ConsultComponent#deleteConsult(int)
     */
    @Override
    public CcResult deleteConsult(int consultId) {
        return null;
    }

}

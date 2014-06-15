/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.view;

import com.ccconsult.base.ToString;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Message;

/**
 * 
 * @author jingyu.dan
 * @version $Id: MessageVO.java, v 0.1 2014-6-12 下午2:27:41 jingyu.dan Exp $
 */
public class MessageVO extends ToString {
    private static final long serialVersionUID = 1L;

    private CounselorVO       counselorVO;

    private Consultant        consultant;

    private Message           message;

    public CounselorVO getCounselorVO() {
        return counselorVO;
    }

    public void setCounselorVO(CounselorVO counselorVO) {
        this.counselorVO = counselorVO;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}

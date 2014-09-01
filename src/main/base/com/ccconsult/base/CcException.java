/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base;

/**
 * 异常
 * 
 * @author jingyu.dan
 * @version $Id: CcException.java, v 0.1 2014-5-30 下午6:15:11 jingyu.dan Exp $
 */
public class CcException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 结果码 */
    private String            code             = "UNKNOWN";

    /** 异常信息 */
    private String            message          = "未知异常";

    /**
     * 构造方法
     *
     */
    public CcException() {
    }

    /**
     * 构造方法
     * 
     * @param resultCode 错误码
     */
    public CcException(String message, String code) {
        this.setMessage(message);
        this.code = code;
    }

    /**
     * 构造方法
     * 
     * @param message   错误信息
     */
    public CcException(String message) {
        this.setMessage(message);
    }

    /**
     * 构造方法
     *
     * @param cause
     */
    public CcException(Throwable cause, String message) {
        super(cause);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

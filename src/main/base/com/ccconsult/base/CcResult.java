/**
 * 
 */
package com.ccconsult.base;

import java.util.Map;

/**
 * 同意处理结果
 * 
 * @author jingyu.dan
 * 
 */
public class CcResult extends ToString {

    /** serialVersionUID */
    private static final long     serialVersionUID = 4887277624451526447L;

    /** 成功标识 */
    private boolean               success          = false;

    /** 结果码 */
    private String                code             = "UNKNOWN";

    /** 返回信息 */
    private String                message          = "未知异常";

    /** 单次服务请求唯一标识 */
    protected String              token;

    /**包含对象*/
    protected Object              object;

    /**包含对象*/
    protected Map<String, Object> relObject;

    /**
     * 同意处理结果
     */
    public CcResult() {

    }

    public CcResult(boolean isSuccess) {
        if (isSuccess) {
            this.success = true;
            this.message = "处理成功";
            this.code = "SUCCESS";
        }
    }

    public static CcResult retFailure(String message) {
        CcResult result = new CcResult();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    public CcResult(Object object) {
        this.success = true;
        this.message = "处理成功";
        this.code = "SUCCESS";
        this.object = object;
    }

    public CcResult(Map<String, Object> relObject) {
        this.success = true;
        this.message = "处理成功";
        this.code = "SUCCESS";
        this.relObject = relObject;
    }

    public static CcResult retSuccess() {
        CcResult result = new CcResult();
        result.success = true;
        result.message = "处理成功";
        result.code = "SUCCESS";
        return result;
    }

    /**
     * 取得是否成功标识
     * 
     * @return
     */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Setter method for property <tt>token</tt>.
     * 
     * @param token
     *            value to be assigned to property token
     */
    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Map<String, Object> getRelObject() {
        return relObject;
    }

    public void setRelObject(Map<String, Object> relObject) {
        this.relObject = relObject;
    }

}

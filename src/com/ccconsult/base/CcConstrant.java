/**
 * 
 */
package com.ccconsult.base;

/**
 * 
 * @author jingyu.dan
 * 
 */
public class CcConstrant {

    /** 用于统计处理时间的时间key */
    public final static String TIME_TOKEN                = "TIME_TOKEN";

    /** counselor对象 */
    public final static String SESSION_COUNSELOR_OBJECT  = "SESSION_COUNSELOR_OBJECT";

    /** CONSULTANT 对象 */
    public final static String SESSION_CONSULTANT_OBJECT = "SESSION_CONSULTANT_OBJECT";

    /** UPLOAD_FOLDER */
    public final static String UPLOAD_FOLDER             = "UPLOAD_FOLDER";

    /**短消息字符长度*/
    public final static int    SHOT_MESSAGE_LENGTH       = 2048;

    /**默认页大小*/
    public final static int    DEFAULT_PAGE_SIZE         = 20;

    public static String getUploadFolder() {
        return UPLOAD_FOLDER;
    }

}

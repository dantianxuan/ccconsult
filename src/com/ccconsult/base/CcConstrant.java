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

    /** CONSULTANT 对象 */
    public final static String ALL_SERVICES              = "ALL_SERVICES";

    /** ADMIN 对象 */
    public final static String SESSION_ADMIN_OBJECT      = "SESSION_ADMIN_OBJECT";

    /** ADMIN 对象 */
    public final static String ADMIN                     = "dantianxuan@qq.com";

    /** UPLOAD_FOLDER */
    public final static String UPLOAD_FOLDER             = "UPLOAD_FOLDER";

    public final static String CONSULTANT_PHOTO          = "consultant.jpg";
    public final static String COUNSELOR_PHOTO           = "counselor.jpg";

    /**短消息字符长度*/
    public final static int    COMMON_128_LENGTH         = 128;

    /**短消息字符长度*/
    public final static int    COMMON_256_LENGTH         = 256;

    /**短消息字符长度*/
    public final static int    COMMON_4096_LENGTH        = 4096;

    /**默认页大小*/
    public final static int    DEFAULT_PAGE_SIZE         = 20;

    /**默认页大小*/
    public final static String ALT_SEPARATOR             = "@";

    public static String getUploadFolder() {
        return UPLOAD_FOLDER;
    }

}

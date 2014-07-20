/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.util;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.enums.FileTypeEnum;

/**
 * 文件助手
 * 
 * @author jingyudan
 * @version $Id: FileUti.java, v 0.1 2014-7-9 下午8:43:47 jingyudan Exp $
 */
public class FileUtil {

    /**
     * 获取文件的实际路径
     * 默认路径在default中，是个问号。
     * 
     * @param file
     * @return
     */
    public static String getPath(String file) {
        String path = "/STATIC/image/default.png";
        if (StringUtil.isBlank(file)) {
            return path;
        }
        String[] metas = file.split(CcConstrant.ALT_SEPARATOR);
        if (metas.length != 4) {
            return path;
        }
        FileTypeEnum fileType = FileTypeEnum.getByCode(metas[0]);
        if (fileType == null) {
            return path;
        }
        return "/" + fileType.getBasePath() + "/" + metas[3];
    }

    /**
     * 获取文件的实际路径
     * 
     * @param file
     * @return
     */
    public static String getFileName(String file) {
        if (StringUtil.isBlank(file)) {
            return "";
        }
        String[] metas = file.split(CcConstrant.ALT_SEPARATOR);
        return metas[2];
    }

}

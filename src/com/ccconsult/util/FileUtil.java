/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.util;

import org.apache.commons.lang.math.NumberUtils;

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
        if (metas.length != 3) {
            return path;
        }
        FileTypeEnum fileType = FileTypeEnum.getByValue(NumberUtils.toInt(metas[1]));
        return "/" + fileType.getBasePath() + "/" + file;
    }

    /**
     * 获取文件的实际路径
     * 
     * @param file
     * @return
     */
    public static String getFileName(String file) {
        String[] metas = file.split(CcConstrant.ALT_SEPARATOR);
        return metas[1];
    }

}

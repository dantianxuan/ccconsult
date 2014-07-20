/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * 
 * @author jingyudan
 * @version $Id: ResourceUtil.java, v 0.1 2014-7-20 下午5:45:19 jingyudan Exp $
 */
public class ResourceUtil {

    private static Properties props = new Properties();
    static {
        FileInputStream fis = null;
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                .replace("classes/", "");
            fis = new FileInputStream(new File(path + "resource.properties"));
            props.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getByKey(String key) {
        return (String) props.get(key);
    }

}

/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约时间
 * 
 * @author jingyudan
 * @version $Id: ScheduleTimeUtil.java, v 0.1 2014-8-10 上午10:02:21 jingyudan Exp $
 */
public class ScheduleTimeUtil {

    /**服务时间*/
    public static List<String>        scheduleList = new ArrayList<String>();

    /**服务时间*/
    public static Map<String, String> scheduleMap  = new HashMap<String, String>();

    static {
        scheduleList.add("00:00-00:30");
        scheduleList.add("00:30-01:00");
        scheduleList.add("01:00-01:30");
        scheduleList.add("01:30-02:00");
        scheduleList.add("02:00-02:30");
        scheduleList.add("02:30-03:00");
        scheduleList.add("03:00-03:30");
        scheduleList.add("03:30-04:00");
        scheduleList.add("04:00-04:30");
        scheduleList.add("04:30-05:00");
        scheduleList.add("05:00-05:30");
        scheduleList.add("05:30-06:00");
        scheduleList.add("06:00-06:30");
        scheduleList.add("06:30-07:00");
        scheduleList.add("07:00-07:30");
        scheduleList.add("07:30-08:00");
        scheduleList.add("08:00-08:30");
        scheduleList.add("08:30-09:00");
        scheduleList.add("09:00-09:30");
        scheduleList.add("09:30-10:00");
        scheduleList.add("10:00-10:30");
        scheduleList.add("10:30-11:00");
        scheduleList.add("11:00-11:30");
        scheduleList.add("11:30-12:00");
        scheduleList.add("12:00-12:30");
        scheduleList.add("12:30-13:00");
        scheduleList.add("13:00-13:30");
        scheduleList.add("13:30-14:00");
        scheduleList.add("14:00-14:30");
        scheduleList.add("14:30-15:00");
        scheduleList.add("15:00-15:30");
        scheduleList.add("15:30-16:00");
        scheduleList.add("16:00-16:30");
        scheduleList.add("16:30-17:00");
        scheduleList.add("17:00-17:30");
        scheduleList.add("17:30-18:00");
        scheduleList.add("18:00-18:30");
        scheduleList.add("18:30-19:00");
        scheduleList.add("19:00-19:30");
        scheduleList.add("19:30-20:00");
        scheduleList.add("20:00-20:30");
        scheduleList.add("20:30-21:00");
        scheduleList.add("21:00-21:30");
        scheduleList.add("21:30-22:00");
        scheduleList.add("22:00-22:30");
        scheduleList.add("22:30-23:00");
        scheduleList.add("23:00-23:30");
        scheduleList.add("23:30-00:00");
        for (int index = 0; index < scheduleList.size(); index++) {
            scheduleMap.put(String.valueOf(index + 1), scheduleList.get(index));
        }
    }

    public static List<String> getSchuleTime(String time) {
        List<String> newList = new ArrayList<String>();
        if (time == null) {
            return newList;
        }
        String[] list = time.split(",");
        for (int index = 0; index < list.length; index++) {
            newList.add(list[index]);
        }
        return newList;
    }

    public static String getBegin(String time) {
        return time.split("-")[0];
    }

    public static String getEnd(String time) {
        return time.split("-")[1];
    }

    public static List<String> getScheduleList() {
        return scheduleList;
    }

    public static void setScheduleList(List<String> scheduleList) {
        ScheduleTimeUtil.scheduleList = scheduleList;
    }

    public static Map<String, String> getScheduleMap() {
        return scheduleMap;
    }

    public static void setScheduleMap(Map<String, String> scheduleMap) {
        ScheduleTimeUtil.scheduleMap = scheduleMap;
    }

}

///**
// * ccinterviewer.com Inc.
// * Copyright (c) 2014-2014 All Rights Reserved.
// */
//package com.ccconsult.schedule;
//
//import java.util.HashMap;
//import java.util.Set;
//
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//
//import com.ccconsult.dao.ConsultDAO;
//import com.ccconsult.dao.CounselorDAO;
//
///**
// * 日常通知调度器
// * 
// * @author jingyudan
// * @version $Id: DailyNotifyScheduler.java, v 0.1 2014-8-15 下午4:19:55 jingyudan Exp $
// */
//public class DailyNotifyJob implements Job {
//
//    private CounselorDAO counselorDAO;
//
//    private ConsultDAO   consultDAO;
//    
//    
//
//    @Override
//    public void execute(JobExecutionContext arg0) throws JobExecutionException {
//    }
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        HashMap<String, Object> result = null;
//        CCPRestSDK restAPI = new CCPRestSDK();
//        restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
//        restAPI.setAccount("8a48b5514767145d01477fcf5f2907d4", "72a27e3d8d314aaab7f495ffb5cde4e5");// 初始化主帐号名称和主帐号令牌
//        restAPI.setAppId("8a48b55147d7c67d0147d910cf290285");// 初始化应用ID
//        result = restAPI.sendTemplateSMS("18142011154", "1", new String[] { "模板内容1", "模板内容2" });
//        System.out.println("SDKTestGetSubAccounts result=" + result);
//        if ("000000".equals(result.get("statusCode"))) {
//            //正常返回输出data包体信息（map）
//            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
//            Set<String> keySet = data.keySet();
//            for (String key : keySet) {
//                Object object = data.get(key);
//                System.out.println(key + " = " + object);
//            }
//        } else {
//            //异常返回输出错误码和错误信息
//            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= "
//                               + result.get("statusMsg"));
//        }
//    }
//
//}

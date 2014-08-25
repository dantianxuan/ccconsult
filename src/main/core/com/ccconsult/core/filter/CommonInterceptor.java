/**
 * 
 */
package com.ccconsult.core.filter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.util.LogUtil;

/**
 * 用于系统打印日志
 * 
 * @author jingyu.dan
 * 
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = Logger.getLogger(CounselorAuthorityInterceptor.class);

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        LogUtil.info(logger, "接受请求");
        request.getParameterMap().put(CcConstrant.TIME_TOKEN, new Date());
        return true;
    }

}

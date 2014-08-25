/**
 * 
 */
package com.ccconsult.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.util.LogUtil;

/**
 * @author jingyu.dan
 * 
 */
public class CounselorAuthorityInterceptor extends HandlerInterceptorAdapter {
    private Logger log = Logger.getLogger(CounselorAuthorityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        Object counselorVO = request.getSession()
            .getAttribute(CcConstrant.SESSION_COUNSELOR_OBJECT);
        HttpSession session = request.getSession();
        if (counselorVO == null) {
            if (null != request.getQueryString()) {
                session
                    .setAttribute("redirectUrl",
                        request.getRequestURL().append("?").append(request.getQueryString())
                            .toString());
            } else {
                session.setAttribute("redirectUrl", request.getRequestURL().toString());
            }
            LogUtil.info(log, "无权限的请求", request.getLocalAddr());
            response.sendRedirect("/ccconsult/login.htm");
            return false;
        }
        return true;
    }

}

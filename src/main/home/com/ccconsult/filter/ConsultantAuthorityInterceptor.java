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
public class ConsultantAuthorityInterceptor extends HandlerInterceptorAdapter {
    private Logger log = Logger.getLogger(CounselorAuthorityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Object consultant = request.getSession()
            .getAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT);
        if (consultant == null) {
            HttpSession session = request.getSession();
            if (null != request.getQueryString()
                && !request.getRequestURL().toString().contains("&")) {
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

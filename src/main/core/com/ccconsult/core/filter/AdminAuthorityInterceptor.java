/**
 * 
 */
package com.ccconsult.core.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.util.LogUtil;
import com.ccconsult.pojo.Consultant;

/**
 * @author jingyu.dan
 * 
 */
public class AdminAuthorityInterceptor extends HandlerInterceptorAdapter {
    private Logger log = Logger.getLogger(AdminAuthorityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        Object consultant = request.getSession()
            .getAttribute(CcConstrant.SESSION_CONSULTANT_OBJECT);
        if (consultant == null || !((Consultant) consultant).getEmail().equals(CcConstrant.ADMIN)) {
            HttpSession session = request.getSession();
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

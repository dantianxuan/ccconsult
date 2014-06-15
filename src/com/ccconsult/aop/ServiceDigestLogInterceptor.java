/**
 * 
 */
package com.ccconsult.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.ccconsult.util.LogUtil;

/**
 * @author jinsaichen
 * 
 */
public class ServiceDigestLogInterceptor implements MethodInterceptor {
    /** 服务摘要 */
    public Logger serviceDigest = Logger.getLogger("SERVICE-DIGEST");

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept
     * .MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String method = invocation.getMethod().getDeclaringClass().getSimpleName() + "."
                        + invocation.getMethod().getName();

        Object[] args = invocation.getArguments();
        long startTime = System.currentTimeMillis();
        Object result = new Object();

        // 是否有异常
        String code = ",Y,";

        // 执行方法并根据执行结果设置执行结果
        try {

            LogUtil.info(serviceDigest, "调用服务", method, "[args=",
                ToStringBuilder.reflectionToString(args), "]");
            result = invocation.proceed();

            return result;
        } catch (Throwable ex) {
            LogUtil.error(serviceDigest, ex, "调用服务[method=", method, ", args=",
                ToStringBuilder.reflectionToString(args), "]异常！");
            code = ",N,";
            throw ex;

        } finally {

            // 1. 打印摘要日志
            long elapseTime = System.currentTimeMillis() - startTime;
            LogUtil.info(serviceDigest, "[(", method, code, elapseTime, "ms)]");

            // 2. 打印入参和出参日志
            LogUtil.info(serviceDigest, "调用服务", method, "[args=",
                ToStringBuilder.reflectionToString(args), "],返回值为：", result);
        }
    }

}

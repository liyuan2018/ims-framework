package com.spark.ims.web.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.GenericFilterBean;
import com.spark.ims.web.session.ImsSessionManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.spark.ims.auth.constants.ITokenConstants.APP_ID_KEY;

/**
 * 过滤器配置类
 *
 * Created by liyuan on 2018/4/26.
 */
@Configurable
public class FilterConfig {

    /** 注册设置app id的过滤器 */
    @Bean
    public FilterRegistrationBean registeretAppIdFilter() {
        return generateFilterRegistrationBean(new GenericFilterBean() {

            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
                    throw new ServletException("GenericFilterBean just supports HTTP requests");
                }
                HttpServletRequest httpRequest = (HttpServletRequest) request;

                String appId = StringUtils.isNotEmpty(httpRequest.getParameter(APP_ID_KEY)) ? httpRequest.getParameter(APP_ID_KEY) : httpRequest.getHeader(APP_ID_KEY);
                if (StringUtils.isNotEmpty(appId)) {
                    ImsSessionManager.getInstance().setAppId(appId);
                }
                chain.doFilter(request, response);
            }
        }, 0, "/sysUsers/login");
    }

    /**
     * 生成过滤器注册Bean
     * @param filter    过滤器
     * @return          过滤器注册Bean
     */
    private FilterRegistrationBean generateFilterRegistrationBean(Filter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        return filterRegistrationBean;
    }

    /**
     * 生成过滤器注册Bean
     * @param filter    过滤器
     * @param order     过滤器在过滤链中的顺序
     * @return          过滤器注册Bean
     */
    private FilterRegistrationBean generateFilterRegistrationBean(Filter filter, int order) {
        FilterRegistrationBean filterRegistrationBean = generateFilterRegistrationBean(filter);
        filterRegistrationBean.setOrder(order);
        return filterRegistrationBean;
    }

    /**
     * 生成过滤器注册Bean
     * @param filter        过滤器
     * @param order         过滤器在过滤链中的顺序
     * @param urlPatterns   待过滤url模式变长数组
     * @return              过滤器注册Bean
     */
    private FilterRegistrationBean generateFilterRegistrationBean(Filter filter, int order, String... urlPatterns) {
        FilterRegistrationBean filterRegistrationBean = generateFilterRegistrationBean(filter, order);
        if (urlPatterns == null || urlPatterns.length == 0) {
            return filterRegistrationBean;
        }
        filterRegistrationBean.addUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }

}

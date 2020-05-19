package com.ding.cas;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看下面这个链接和开放平台
 * https://www.cnblogs.com/whm-blog/p/11248304.html
 */
@Configuration
public class CasConfigure {

    @Autowired
    private CasClientProperties casClientProperties;

    /**
     * 用于实现单点登出功能
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setEnabled(true);
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new SingleSignOutFilter());
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns(casClientProperties.getFilterUrl ());
        filterRegistration.addInitParameter("casServerUrlPrefix", casClientProperties.getCasServerLoginUrl());
        filterRegistration.addInitParameter("serverName", casClientProperties.getServerName());
        filterRegistration.setOrder(3);
        return filterRegistration;
    }

    /**
     * 该过滤器负责用户的认证工作
     * @return
     */
    @Bean
    public FilterRegistrationBean authenticationFilterRegistrationBean() {
        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
        authenticationFilter.setFilter(new AuthenticationFilter());
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("casServerLoginUrl", casClientProperties.getCasServerLoginUrl());
        initParameters.put("ignorePattern", "/openApi/|/recall/|/test/|/health|/open/|/error*|/webjars/|/swagger*|/Mei*|/assets*|/v2/api*");
        initParameters.put("serverName", casClientProperties.getServerName());
        authenticationFilter.setInitParameters(initParameters);
        authenticationFilter.setOrder(4);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add(casClientProperties.getFilterUrl());
        authenticationFilter.setUrlPatterns(urlPatterns);
        return authenticationFilter;
    }

    /**
     * 该过滤器负责对Ticket的校验工作
     * @return
     */
    @Bean
    public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new Cas30ProxyReceivingTicketValidationFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("casServerUrlPrefix", casClientProperties.getCasServerUrlPrefix());
        registrationBean.addInitParameter("serverName", casClientProperties.getServerName());
        registrationBean.addInitParameter("useSession", String.valueOf(true));
        registrationBean.addInitParameter("exceptionOnValidationFailure", String.valueOf(false));
        registrationBean.addInitParameter("redirectAfterValidation", String.valueOf(true));
        registrationBean.setEnabled(casClientProperties.isEnable());
        registrationBean.setOrder(4);
        return registrationBean;
    }

    /**
     * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名
     * @return
     */
    @Bean
    public FilterRegistrationBean casHttpServletRequestWrapperFilter(){
        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
        authenticationFilter.setFilter(new HttpServletRequestWrapperFilter());
        authenticationFilter.setOrder(6);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add(casClientProperties.getFilterUrl());
        authenticationFilter.setUrlPatterns(urlPatterns);
        return authenticationFilter;
    }

    /**
     * 该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。
     * 比如AssertionHolder.getAssertion().getPrincipal().getName()。
     * 这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息
     * @return
     */
    @Bean
    public FilterRegistrationBean casAssertionThreadLocalFilter(){
        FilterRegistrationBean authenticationFilter = new FilterRegistrationBean();
        authenticationFilter.setFilter(new AssertionThreadLocalFilter());
        authenticationFilter.setOrder(7);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add(casClientProperties.getFilterUrl());
        authenticationFilter.setUrlPatterns(urlPatterns);
        return authenticationFilter;
    }
}

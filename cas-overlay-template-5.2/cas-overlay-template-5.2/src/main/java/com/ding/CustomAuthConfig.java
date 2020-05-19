package com.ding;

import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 還需要在下面这个问题配置  不然spring不会管理这个文件
 * O:\cas\cas-overlay-template-5.2\cas-overlay-template-5.2\src\main\resource\META-INF\spring.factories
 */
@Configuration("CustomAuthConfig")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomAuthConfig implements AuthenticationEventExecutionPlanConfigurer {
    @Autowired
    private CasConfigurationProperties casProperties;

    @Autowired
    @Qualifier("servicesManager")
    private ServicesManager servicesManager;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Bean
    public AuthenticationHandler myAuthenticationHandler() {
        final Login handler = new Login(Login.class.getSimpleName(), servicesManager, new DefaultPrincipalFactory(), 10,sysUserMapper);
        return handler;
    }

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandler(myAuthenticationHandler());
    }
}

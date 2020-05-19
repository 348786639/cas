package com.ding.cas;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cas")
@Configuration
public class CasClientProperties {
    /**
     * 是否开启单点登录
     */
    private boolean enable = true;
    /**
     * 单点登录需要访问的CAS SERVER URL入口
     */
    private String casServerLoginUrl;
    /**
     * 托管此应用的服务器名称，例如本机：http://localhost:8080
     */
    private String serverName;

    /**
     * cas服务器的开头  例如 http://localhost:8443/cas
     */
    private String casServerUrlPrefix;

    /**
     * 验证白名单，当请求路径匹配此表达式时，自动通过验证
     */
    private String ignorePattern;

    /**
     * 白名单表达式的类型
     * REGEX 正则表达式 默认的
     * CONTAINS  包含匹配
     * EXACT 精确匹配
     */
    private String ignoreUrlPatternType;

    private String filterUrl;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCasServerLoginUrl() {
        return casServerLoginUrl;
    }

    public void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public String getIgnorePattern() {
        return ignorePattern;
    }

    public void setIgnorePattern(String ignorePattern) {
        this.ignorePattern = ignorePattern;
    }

    public String getIgnoreUrlPatternType() {
        return ignoreUrlPatternType;
    }

    public void setIgnoreUrlPatternType(String ignoreUrlPatternType) {
        this.ignoreUrlPatternType = ignoreUrlPatternType;
    }

    public String getFilterUrl() {
        return filterUrl;
    }

    public void setFilterUrl(String filterUrl) {
        this.filterUrl = filterUrl;
    }
}

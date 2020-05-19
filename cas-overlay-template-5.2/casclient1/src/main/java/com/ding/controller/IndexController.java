package com.ding.controller;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String sayHello(){
        //方案一：获取其他属性和名字
        Map<String,Object> map = AssertionHolder.getAssertion().getPrincipal().getAttributes();
        AssertionHolder.getAssertion().getPrincipal().getName();
        //方案二：获取其他属性和名字
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Principal principal = request.getUserPrincipal();
        Map<String, Object> attributes = ((AttributePrincipal) principal).getAttributes();
        return "index";
    }

    /**
     * 验证会不会被拦截
     * @return
     */
    @RequestMapping("/openApi/openApi")
    public String openApi(){
        return "openApi";
    }
}

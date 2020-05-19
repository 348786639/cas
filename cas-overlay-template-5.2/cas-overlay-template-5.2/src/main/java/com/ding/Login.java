package com.ding;

import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;


/**
 * 自定义登陆逻辑参考和页面
 * https://blog.csdn.net/u010588262/article/details/80014083
 * https://blog.csdn.net/weixin_37548740/article/details/104053834
 */
public class Login extends AbstractUsernamePasswordAuthenticationHandler {

    private SysUserMapper sysUserMapper;

    public Login(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order,SysUserMapper sysUserMapper) {
        super(name, servicesManager, principalFactory, order);
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential usernamePasswordCredential, String s) throws GeneralSecurityException, PreventedException {
        //使用jdbc试一下 和使用mybatis试试
        DriverManagerDataSource d=new DriverManagerDataSource();
        d.setDriverClassName("com.mysql.jdbc.Driver");
        d.setUrl("jdbc:mysql://127.0.0.1:3306/blog");
        d.setUsername("root");
        d.setPassword("123456");
        JdbcTemplate template=new JdbcTemplate();
        template.setDataSource(d);

        String username=usernamePasswordCredential.getUsername();
        String pd=usernamePasswordCredential.getPassword();
        Map<String,Object> user = template.queryForMap("SELECT `password` FROM t_sys_user WHERE user_name = ?", username);
        String pad = sysUserMapper.findUserName(username);
        System.out.printf(pad);
        //查询数据库加密的的密码
        if(username==null || username.equals("admin1111")){
            throw new FailedLoginException("没有该用户");
        }

        //返回多属性
        Map<String, Object> map=new HashMap<>();
        map.put("email", "34865666@qq.com");
        map.put("phone", "18850588888");
        if(username.equals("admin") && pd.equals("123456")){
            return createHandlerResult(usernamePasswordCredential, principalFactory.createPrincipal(username, map), null);
        }
        throw new FailedLoginException("Sorry, login attemp failed.");
    }
}

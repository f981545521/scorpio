package cn.acyou.scorpio.conf.shrio;

import cn.acyou.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-20 下午 08:59]
 **/
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        log.info("———— shiro [ 权限认证：roles、permissions] ———— token:" + token);
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        // 设置用户拥有的角色集合，比如“admin,auditor,developer”
        Set<String> userRoles = new HashSet<>();
        userRoles.add("developer");
        authInfo.setRoles(userRoles);
        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
        Set<String> userPerms = new HashSet<>();
        userPerms.add("student:list");
        //stringSet.add("student:get");
        authInfo.setStringPermissions(userPerms);
        return authInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        log.info("———————————— shiro [身份认证] ————————————" + token);
        //token已经过期
        String userName = (String) authenticationToken.getPrincipal();
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}

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

import java.util.HashSet;
import java.util.Set;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-20 下午 08:59]
 **/
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        log.info("———— shiro [ 权限认证：roles、permissions] ———— username:" + username);
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

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码
     * private UserService userService;
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("———————————— shiro [身份认证] ————————————");
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        //根据用户名从数据库获取密码
        String password = "123";
        if (userName == null) {
            throw new ServiceException("用户名不正确");
        } else if (!userPwd.equals(password)) {
            throw new ServiceException("密码不正确");
        }
        return new SimpleAuthenticationInfo(userName, password, getName());
    }
}

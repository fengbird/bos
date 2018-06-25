package com.itheima.bos.service.realm;

import com.itheima.bos.dao.UserRepository;
import com.itheima.bos.domain.system.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * BOS权限控制器
 *
 * @author tengchao
 * @create 2018-01-13-21:32.
 */
@Component
public class BOSRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //为用户授权，只需把用户需要的权限添加到info中就可以了
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //为用户添加area权限
        info.addStringPermission("area");
        info.addStringPermission("courier");
        info.addRole("admin");
        return info;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken passwordToken = (UsernamePasswordToken) authenticationToken;
        String username = passwordToken.getUsername();
        if (StringUtils.isNotBlank(username)) {
            //根据页面提交的username查询数据库中的密码
            List<User> list = userRepository.findByUsername(username);
            if (list != null && list.size() > 0) {
                User user = list.get(0);
                /**
                 * 找到用户,创建认证信息,至于密码是否正确,交由框架去处理
                 *
                 * @param principal(身份) : the 'primary' principal associated with the specified realm.
                 *        调用subject.getPrincipal()时返回的对象
                 *        通常登录成功后,我们会将获取的User存入Session,所以这里使用user
                 * @param credentials(凭证) : the credentials that verify the given principal.
                 *        和第一个参数相关的密码
                 * @param realmName : the realm from where the principal and credentials were acquired.
                 *        从那个realm获取前两个参数,通常都是getName()
                 */
                AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
                return info;
            } else {
                return null;
            }
        }
        return null;
    }
}

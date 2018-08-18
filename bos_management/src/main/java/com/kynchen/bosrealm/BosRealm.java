package com.kynchen.bosrealm;/*
* @author kynchen
* 
* @date 2018/8/12 16:18
*
* @version idea
*/

import bos.domain.system.Permission;
import bos.domain.system.Role;
import bos.domain.system.User;
import com.kynchen.service.system.RoleService;
import com.kynchen.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//@Service("bosRealm")
public class BosRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        //获取当前用户
        User user= (User) subject.getPrincipal();
        //根据用户查询角色和权限
        List<Role> roleList=roleService.findByUserId(user.getId());
        for(Role role:roleList){
            authorizationInfo.addRole(role.getKeyword());
            for(Permission permission:role.getPermissions()){
                authorizationInfo.addStringPermission(permission.getKeyword());
            }
        }

        return authorizationInfo;
    }

    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        User user=userService.findByUsername(usernamePasswordToken.getUsername());
        if(user==null){
            return null;
        }else{
            //第一个参数，将登录信息存入subject中
            //第二个参数，返回密码，securityManage会自动和用户输入的密码比较
            //返回realm名称
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }

    }
}

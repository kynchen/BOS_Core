package com.kynchen.action.system;/*
 * @author kynchen
 *
 * @date 2018/8/12 16:10
 *
 * @version idea
 */

import bos.domain.system.User;
import com.alibaba.fastjson.JSONArray;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.system.UserService;
import com.kynchen.utils.JsonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

    @Action(value = "user_login", results = {@Result(name = "success", type = "redirect", location = "index.html"),
            @Result(name = "login", type = "redirect", location = "login.html")})
    public String user_login() {
        //Shiro权限验证登录
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UsernamePasswordToken(model.getUsername(), model.getPassword());
        try {
            subject.login(authenticationToken);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return "login";
        }

    }

    @Action(value = "user_logout", results = {@Result(name = "success", type = "redirect", location = "login.html")})
    public String user_logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return SUCCESS;
    }

    @Autowired
    private UserService userService;

    @Action(value = "user_list")
    public String user_list(){
        List<User> users = userService.findAll();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(users);
        JsonUtils.write(jsonArray);
        return NONE;
    }

    private String roleId;

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Action(value = "user_save",results = {@Result(name = "success",type = "redirect",location = "/pages/system/userlist.html")})
    public String user_save(){

        userService.save(model,roleId);

        return SUCCESS;
    }
}

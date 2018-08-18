package com.kynchen.action.system;/*
* @author kynchen
* 
* @date 2018/8/13 16:00
*
* @version idea
*/

import bos.domain.system.Role;
import com.alibaba.fastjson.JSONArray;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.system.RoleService;
import com.kynchen.utils.JsonUtils;
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
public class RoleAction extends BaseAction<Role> {

    @Autowired
    private RoleService roleService;

    @Action(value = "role_list")
    public String role_list(){
        List<Role> roleList = roleService.findAll();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(roleList);
        JsonUtils.write(jsonArray);
        return NONE;
    }

    private String[] permissionIds;
    private String menuIds;

    public void setPermissionIds(String[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    /** 级联保存角色
     * @return
     */
    @Action(value = "role_save",results = {@Result(name = "success",type = "redirect",location = "/pages/system/role.html")})
    public String role_save(){

        roleService.saveRole(model,permissionIds,menuIds);
        return SUCCESS;
    }
}


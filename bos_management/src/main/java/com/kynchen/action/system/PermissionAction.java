package com.kynchen.action.system;/*
* @author kynchen
* 
* @date 2018/8/13 15:10
*
* @version idea
*/

import bos.domain.system.Permission;
import com.alibaba.fastjson.JSONArray;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.system.PermissionService;
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
public class PermissionAction extends BaseAction<Permission> {

    @Autowired
    private PermissionService permissionService;

    @Action(value = "permission_list")
    public String permission_list(){

        List<Permission> list=permissionService.findAll();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        JsonUtils.write(jsonArray);
        return NONE;
    }

    @Action(value = "permission_save",results = {@Result(name = "success",type = "redirect",location = "/pages/system/permission.html")})
    public String permission_save(){
        permissionService.save(model);
        return SUCCESS;
    }
}

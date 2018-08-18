package com.kynchen.action.system;/*
* @author kynchen
* 
* @date 2018/8/13 10:26
*
* @version idea
*/

import bos.domain.system.Menu;
import bos.domain.system.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.system.MenuService;
import com.kynchen.utils.JsonUtils;
import org.apache.shiro.SecurityUtils;
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
public class MenuAction extends BaseAction<Menu> {

    @Autowired
    private MenuService menuService;

    @Action(value = "menu_list")
    public String menu_list(){
        List<Menu> list=menuService.findAll();
        PropertyFilter filter = new PropertyFilter() {
            @Override
            public boolean apply(Object o, String s, Object o1) {
                return true;
            }
        };
        String jsonString= JSONArray.toJSONString(list,filter,SerializerFeature.DisableCircularReferenceDetect);
        JSONArray jsonArray = JSON.parseArray(jsonString);
        JSONArray jsonArray1= new JSONArray();
        jsonArray1.addAll(jsonArray);
        JsonUtils.write(jsonArray1);
        System.out.println(jsonArray1);
        return NONE;
    }

    @Action(value = "menu_save",results = {@Result(name = "success",type = "redirect",location = "/pages/system/menu.html")})
    public String menu_save(){

        menuService.save(model);
        return SUCCESS;
    }

    @Action(value = "menu_showMenu")
    public String menu_showMenu(){

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> menus=menuService.showMenu(user);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(menus);
        JsonUtils.write(jsonArray);
        return NONE;
    }
}

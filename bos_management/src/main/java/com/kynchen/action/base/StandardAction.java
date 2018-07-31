package com.kynchen.action.base;/*
* @author kynchen
* 
* @date 2018/7/24 19:07
*
* @version idea
*/

import com.alibaba.fastjson.JSONArray;
import com.kynchen.action.common.BaseAction;
import com.kynchen.domain.base.Standard;
import com.kynchen.service.base.StandardService;
import com.kynchen.utils.JsonUtils;
import com.kynchen.utils.PageForJson;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Actions
@Controller
@Scope("prototype")
public class StandardAction extends BaseAction<Standard> {


    @Autowired
    private StandardService standardService;

    /** 添加收派标准
     * @return
     */
    @Action(value = "standard_save",results = {@Result(name = "success",location = "./pages/base/standard.html",type = "redirect")})
    public String standard_save(){
        standardService.standardSave(model);
        return SUCCESS;
    }


    /** 分页
     * @return
     */
    @Action(value = "standard_queryPage")
    public String standard_queryPage(){

        Pageable pageable = new PageRequest(page-1, rows);
        Page<Standard> standardPage = standardService.findAllByPage(pageable);
        PageForJson.write(standardPage);
        return NONE;
    }

    @Action(value = "standard_findAll")
    public String standard_findAll(){
        List<Standard> list =standardService.findAll();
//        JSONArray js = JSONArray.parseArray(JSON.toJSONString(list));
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        JsonUtils.write(jsonArray);
        return NONE;
    }


}

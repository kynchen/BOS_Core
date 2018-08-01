package com.kynchen.action.base;/*
 * @author kynchen
 *
 * @date 2018/8/1 9:03
 *
 * @version idea
 */

import com.alibaba.fastjson.JSONArray;
import com.kynchen.action.common.BaseAction;
import com.kynchen.domain.base.TakeTime;
import com.kynchen.service.base.TakeTimeService;
import com.kynchen.utils.JsonUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime> {

    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value="taketime_findAll")
    public String taketime_findAll(){
        List<TakeTime> list = takeTimeService.findAll();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        JsonUtils.write(jsonArray);
        return NONE;
    }
}

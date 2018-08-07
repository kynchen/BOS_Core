package com.kynchen.action.base;/*
 * @author kynchen
 *
 * @date 2018/8/1 9:03
 *
 * @version idea
 */

import bos.domain.base.TakeTime;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.base.TakeTimeService;
import com.kynchen.utils.PageForJson;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime> {

    @Autowired
    private TakeTimeService takeTimeService;

    @Action(value="taketime_findAll")
    public String taketime_findAll(){
        Pageable pageable = new PageRequest(page-1,rows);
        Page<TakeTime> page = takeTimeService.findAll(pageable);
        PageForJson.write(page);
        return NONE;
    }

    @Action(value = "takeTime_save",results = {@Result(name = "success",location = "./pages/base/sub_area.html",type = "redirect")})
    public String takeTime_save(){

        takeTimeService.save(model);

        return SUCCESS;
    }
}

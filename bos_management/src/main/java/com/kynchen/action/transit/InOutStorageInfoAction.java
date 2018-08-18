package com.kynchen.action.transit;/*
* @author kynchen
* 
* @date 2018/8/15 20:59
*
* @version idea
*/

import bos.domain.transit.InOutStorageInfo;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.transit.InOutStorageInfoService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class InOutStorageInfoAction extends BaseAction<InOutStorageInfo> {

    @Autowired
    private InOutStorageInfoService inOutStorageInfoService;

    private String transitInfoId;

    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    @Action(value = "inOutStorageInfo_save",results = {@Result(name = "success",type="redirect",location = "/pages/transit/transitinfo.html")})
    public String inOutStorageInfo_save(){
        inOutStorageInfoService.save(transitInfoId,model);
        return SUCCESS;
    }
}

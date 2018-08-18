package com.kynchen.action.transit;/*
* @author kynchen
* 
* @date 2018/8/16 11:20
*
* @version idea
*/

import bos.domain.transit.SignInfo;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.transit.SignInfoService;
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
public class SignInfoAction extends BaseAction<SignInfo> {

    @Autowired
    private SignInfoService signInfoService;

    private String transitInfoId;

    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    @Action(value = "signInfo_save",results = {@Result(name = "success",type="redirect",location = "/pages/transit/transitinfo.html")})
    public String signInfo_save(){

        signInfoService.save(transitInfoId,model);

        return SUCCESS;
    }
}

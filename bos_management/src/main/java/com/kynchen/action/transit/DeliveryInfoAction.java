package com.kynchen.action.transit;/*
* @author kynchen
* 
* @date 2018/8/15 21:49
*
* @version idea
*/

import bos.domain.transit.DeliveryInfo;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.transit.DeliveryInfoService;
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
public class DeliveryInfoAction extends BaseAction<DeliveryInfo> {

    @Autowired
    private DeliveryInfoService deliveryInfoService;

    private String transitInfoId;

    public void setTransitInfoId(String transitInfoId) {
        this.transitInfoId = transitInfoId;
    }

    @Action(value = "deliveryInfo_save",results = {@Result(name = "success",type = "redirect",location = "/pages/transit/transitinfo.html")})
    public String deliveryInfo_save(){

        deliveryInfoService.save(transitInfoId,model);

        return SUCCESS;
    }
}

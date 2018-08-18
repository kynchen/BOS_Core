package com.kynchen.action.take_delivery;/*
* @author kynchen
* 
* @date 2018/8/9 18:38
*
* @version idea
*/

import bos.domain.take_delivery.Order;
import com.alibaba.fastjson.JSONObject;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.take_delivery.OrderService;
import com.kynchen.utils.JsonUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

    @Autowired
    private OrderService OrderService;

    @Action(value = "findOrderByOrderNum")
    public String findOrderByOrderNum(){

        Order order=OrderService.findOrderByOrderNum(model.getOrderNum());
        JSONObject jsonObject = new JSONObject();
        if(order!=null){
            jsonObject.put("success",true);
            jsonObject.put("orderData",order);
        }else{
            jsonObject.put("success",false);
        }
        JsonUtils.write(jsonObject);
        return NONE;
    }
}

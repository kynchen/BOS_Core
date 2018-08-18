package com.kynchen.action.transit;/*
* @author kynchen
* 
* @date 2018/8/15 10:26
*
* @version idea
*/

import bos.domain.transit.TransitInfo;
import com.alibaba.fastjson.JSONObject;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.transit.TransitInfoService;
import com.kynchen.utils.JsonUtils;
import com.kynchen.utils.PageForJson;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
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
public class TransitInfoAction extends BaseAction<TransitInfo> {

    @Autowired
    private TransitInfoService transitInfoService;

    //选中的运单号
    private String wayBillIds;

    public void setWayBillIds(String wayBillIds) {
        this.wayBillIds = wayBillIds;
    }

    @Action(value = "transit_create")
    public String transit_create(){
        JSONObject jsonObject = new JSONObject();
        try{
            transitInfoService.create(wayBillIds);
            jsonObject.put("success",true);
            jsonObject.put("msg","操作成功");
            JsonUtils.write(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg","操作成功");
            JsonUtils.write(jsonObject);
        }
        return NONE;
    }

    @Action("transitInfo_pageQuery")
    public String transitInfo_pageQuery(){
        Pageable pageable= new PageRequest(page-1,rows);
        Page<TransitInfo> page =transitInfoService.page_query(pageable);
        PageForJson.write(page);
        return NONE;
    }
}

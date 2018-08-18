package com.kynchen.action.take_delivery;/*
* @author kynchen
* 
* @date 2018/8/9 15:28
*
* @version idea
*/

import bos.domain.take_delivery.WayBill;
import com.alibaba.fastjson.JSONObject;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.take_delivery.WayBillService;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

@ParentPackage("struts-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {

    @Autowired
    private WayBillService wayBillService;

    //运单号隐藏域id
    private String wayBillId;

    public void setWayBillId(String wayBillId) {
        this.wayBillId = wayBillId;
    }

    /** 快速录入运单
     * @return
     */
    @Action(value = "waybill_save")
    public String waybill_save(){
        JSONObject jsonObject = new JSONObject();
        try {
            if(model.getOrder()!=null&&(model.getOrder().getId()==null||model.getOrder().getId()==0)){
                model.setOrder(null);
            }
            if(wayBillId!=null&&!"".equals(wayBillId)){
                model.setId(Integer.parseInt(wayBillId));
            }
            if(model.getSignStatus()==null||model.getSignStatus()==0) {
                model.setSignStatus(1);
                wayBillService.save(model);
                jsonObject.put("success", true);
                jsonObject.put("msg", "保存成功");
                JsonUtils.write(jsonObject);
            }else{
                jsonObject.put("msg", "修改失败，运单已发出");
                JsonUtils.write(jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("success",false);
            jsonObject.put("msg","保存失败");
            JsonUtils.write(jsonObject);
        }
        return NONE;
    }

    /** 分页查询
     * @return
     */
    @Action(value = "waybill_pageQuery")
    public String waybill_pageQuery(){
        Pageable pageable = new PageRequest(page-1,rows,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
        Page<WayBill> page=wayBillService.pageQuery(model,pageable);
        PageForJson.write(page);
        return NONE;
    }



    /** 运单号查找运单
     * @return
     */
    @Action(value = "findByWayBillNum")
    public String findByWayBillNum(){
        WayBill wayBill=wayBillService.findByWayBillNum(model.getWayBillNum());
        JSONObject jsonObject = new JSONObject();
        if(wayBill!=null){
            jsonObject.put("success",true);
            jsonObject.put("wayBillData",wayBill);
        }else{
            jsonObject.put("success",false);
        }
        JsonUtils.write(jsonObject);
        return NONE;
    }

    @Action("waybill_list")
    public String waybill_list(){
        Pageable pageable = new PageRequest(page-1,rows);
        Page<WayBill> page=wayBillService.page_query(pageable);
        PageForJson.write(page);
        return NONE;
    }
}

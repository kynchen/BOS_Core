package com.kynchen.action.base;/*
 * @author kynchen
 *
 * @date 2018/7/28 19:36
 *
 * @version idea
 */

import bos.domain.base.FixedArea;
import com.alibaba.fastjson.JSONArray;
import com.crm.domain.Customer;
import com.kynchen.action.common.BaseAction;
import com.kynchen.service.base.FixedAreaService;
import com.kynchen.utils.JsonUtils;
import com.kynchen.utils.PageForJson;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea> {

    @Autowired
    private FixedAreaService fixedAreaService;

    @Action(value = "fixed_save", results = {@Result(name = "success", type = "redirect", location = "./pages/base/fixed_area.html")})
    public String fixed_save() {
        fixedAreaService.save(model);
        return SUCCESS;
    }

    /** 查询所有定区
     * @return
     */
    @Action(value = "fixed_findAll")
    public String fixed_findAll(){
        List<FixedArea> fixedAreas = fixedAreaService.findAll();
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(fixedAreas);
        JsonUtils.write(jsonArray);
        return  NONE;
    }

    /** 定区条件分页查询
     * @return
     */
    @Action(value = "fixed_pageQuery")
    public String fixed_pageQuery() {

        Pageable pageable = new PageRequest(page - 1, rows);
        Specification<FixedArea> specification = new Specification<FixedArea>() {
            @Override
            public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(model.getId())) {
                    Predicate p1 = criteriaBuilder.like(root.get("id").as(String.class), "%" + model.getId() + "%");
                    list.add(p1);
                }
                if (StringUtils.isNotBlank(model.getCompany())) {
                    Predicate p2 = criteriaBuilder.like(root.get("company").as(String.class), "%" + model.getCompany() + "%");
                    list.add(p2);
                }

                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };
        Page<FixedArea> page = fixedAreaService.fixed_pageQuery(specification, pageable);
        PageForJson.write(page);
        return NONE;
    }

    /** 查询未关联客户
     * @return
     */
    @Action(value = "fixedArea_findNoAssociationCustomers")
    public String findNoAssociationCustomers() {

        Collection<? extends Customer> collection =
                WebClient.create("http://localhost:8081/crm_management/services/customerService/noassociationcustomer")
                        .accept(MediaType.APPLICATION_JSON)
                        .getCollection(Customer.class);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(collection);
        JsonUtils.write(jsonArray);
        return NONE;

    }

    /** 查询已关联客户
     * @return
     */
    @Action(value = "fixedArea_findHasAssociationFixedAreadCustomers")
    public String findHasAssociationFixedAreadCustomers() {
        Collection<? extends Customer> collection =
                WebClient.create("http://localhost:8081/crm_management/services/customerService/hasassoicationcustomer/"+model.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .getCollection(Customer.class);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(collection);
        JsonUtils.write(jsonArray);
        return NONE;

    }
    private String[] customerIds;

    public void setCustomerIds(String[] customerIds) {
        this.customerIds = customerIds;
    }

    /** 将客户关联到定区
     * @return
     */
    @Action(value = "fixedArea_associationCustomerToFixedArea",results = {@Result(name = "success", type = "redirect", location = "./pages/base/fixed_area.html")})
    public String fixedArea_associationCustomerToFixedArea(){
        String customerIdStr = StringUtils.join(customerIds, ",");
        WebClient.create("http://localhost:8081/crm_management/services/customerService/associationtocustomer?customerIdStr="+customerIdStr+"&fixedAreaId="+model.getId()).put(null);
        return SUCCESS;
    }



    private Integer courierId;
    private Integer takeTimeId;

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public void setTakeTimeId(Integer takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    /** 将快递员关联到定区
     * @return
     */
    @Action(value = "fixedArea_associationCourierToFixedArea",results = {@Result(name="success",type = "redirect",location = "./pages/base/fixed_area.html")})
    public String fixedArea_associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
        return SUCCESS;
    }
}

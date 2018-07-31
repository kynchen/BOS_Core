package com.kynchen.action.base;/*
* @author kynchen
* 
* @date 2018/7/27 1:48
*
* @version idea
*/

import com.kynchen.action.common.BaseAction;
import com.kynchen.domain.base.Courier;
import com.kynchen.service.base.CourierService;
import com.kynchen.utils.JsonUtils;
import com.kynchen.utils.PageForJson;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier> {

    @Autowired
    private CourierService courierService;

    /** 收派员信息保存
     * @return
     */
    @Action(value = "courier_save",results = {@Result(name = "success",location = "./pages/base/courier.html",type = "redirect")})
    public String courier_save(){
        courierService.save(model);
        return SUCCESS;
    }

    /** 条件分页查询收件员
     * @return
     */
    @Action(value = "courier_findAllByPage")
    public String courier_findAllByPage(){

        Pageable pageable = new PageRequest(page-1,rows);

        Specification<Courier> specification = new Specification<Courier>() {
            /**条件查询，返回值如果为null，代表无条件查询
             * @param root 条件表达式 name=? type=?
             * @param criteriaQuery 简单的条件查询，提供where
             * @param criteriaBuilder 构造Predicate对象，条件对象，复杂的条件查询
             * @return 一个或者多个Predicate对象
             */
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> list = new ArrayList<>();

                if(StringUtils.isNotBlank(model.getCourierNum())){
                    Predicate p1 = criteriaBuilder.equal(root.get("courierNum").as(String.class), model.getCourierNum());
                    list.add(p1);
                }
                if(StringUtils.isNotBlank(model.getCompany())){
                    Predicate p2 = criteriaBuilder.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
                    list.add(p2);
                }
                if(StringUtils.isNotBlank(model.getType())){
                    Predicate p3 = criteriaBuilder.equal(root.get("type").as(String.class), model.getType());
                    list.add(p3);
                }
                //多表连接查询，默认连接方式是内连接
                Join<Object, Object> standard = root.join("standard", JoinType.INNER);
                if(model.getStandard()!=null&&StringUtils.isNotBlank(model.getStandard().getName())){
                    Predicate p4 = criteriaBuilder.like(standard.get("name").as(String.class), "%" + model.getStandard().getName() + "%");
                    list.add(p4);
                }
                return criteriaBuilder.and(list.toArray(new Predicate[0]));
            }
        };

        Page<Courier> page = courierService.findAllByPage(specification,pageable);

        PageForJson.write(page);

        return NONE;
    }

    private String Ids;

    public void setIds(String Ids) {
        this.Ids = Ids;
    }

    /** 批量作废
     * @return
     */
    @Action(value = "courier_delete")
    public String courier_delete(){
        String[] ids = Ids.split(",");
        try {
            courierService.delBatch(ids);
            JsonUtils.write("success");
        }catch (Exception e){
            JsonUtils.write("error");
            e.printStackTrace();
        }
        return NONE;
    }

    @Action(value = "courier_restore")
    public String courier_restore(){
        String[] ids = Ids.split(",");
        System.out.println(Ids);
        try{
            courierService.restoreBatch(ids);
            JsonUtils.write("success");
        }catch (Exception e){
            JsonUtils.write("error");
            e.printStackTrace();
        }
        return NONE;
    }

}

package com.kyn.crm.dao;/*
* @author kynchen
* 
* @date 2018/7/29 19:06
*
* @version idea
*/

import com.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    //查询所有未关联的客户
    public List<Customer> findByFixedAreaIdIsNull();
    //查询所有已关联的客户
    public List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query("update Customer set fixedAreaId = ?1 where id=?2 ")
    @Modifying
    public void updateFixedAreaId(String fixedAreaId,Integer id);
}

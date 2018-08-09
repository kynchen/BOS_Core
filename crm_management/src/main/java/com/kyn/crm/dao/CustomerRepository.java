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

    @Query("update Customer set fixedAreaId = null where fixedAreaId=?1")
    @Modifying
    void clearFixedAreaId(String fixedAreaId);

    Customer findByTelephone(String telephone);
    @Query("update Customer set type = 1 where telephone = ?1")
    @Modifying
    void updateType(String telephone);

    public Customer findByTelephoneAndPassword(String telephone,String password);

    @Query("select fixedAreaId from Customer where address = ?1")
    String findFixedAreaIdByAddress(String address);
}

package com.kyn.crm.service;/*
 * @author kynchen
 *
 * @date 2018/7/29 18:35
 *
 * @version idea
 */

import com.crm.domain.Customer;

import javax.ws.rs.*;
import java.util.List;

@Produces({"application/xml","application/json"})
@Consumes({"application/xml","application/json"})
public interface CustomerService {

    //查询所有未关联的客户
    @Path("/noassociationcustomer")
    @GET
    //@Produces({"application/xml","application/json"})
    public List<Customer> findNoAssociationCustomer();

    //查询所有已关联的客户
    @Path("/hasassoicationcustomer/{fixedareaid}")
    @GET
    //@Produces({"application/xml","application/json"})
    public List<Customer> findHasAssociationCustomer(@PathParam("fixedareaid") String fixedAreaId);

    //将客户和定区关联，将客户id拼接成字符串
    @Path("/associationtocustomer")
    @PUT
    public void AssociationToCustomer(@QueryParam("customerIdStr") String customerIdStr, @QueryParam("fixedAreaId") String fixedAreaId);

    @Path("/customer")
    @POST
   // @Consumes({"application/xml","application/json"})
    public void customer_regist(Customer customer);

    @Path("/customer/telephone/{telephone}")
    @GET
    public Customer findByTelephone(@PathParam("telephone") String telephone);

    @Path("/customer/updatetype/{telephone}")
    public void updateType(@PathParam("telephone") String telephone);

    @Path("/customer/login")
    @GET
    public Customer login(@QueryParam("telephone") String telephone,@QueryParam("password") String password);

    @Path("/customer/findFixedAreaIdByAddress")
    @GET
    public String findFixedAreaIdByAddress(@QueryParam("address") String address);
}

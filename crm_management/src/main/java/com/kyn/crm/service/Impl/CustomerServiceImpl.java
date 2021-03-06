package com.kyn.crm.service.Impl;/*
* @author kynchen
* 
* @date 2018/7/29 19:09
*
* @version idea
*/

import com.crm.domain.Customer;
import com.kyn.crm.dao.CustomerRepository;
import com.kyn.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findNoAssociationCustomer() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findHasAssociationCustomer(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void AssociationToCustomer(String customerIdStr, String fixedAreaId) {
        customerRepository.clearFixedAreaId(fixedAreaId);
        if("null".equals(customerIdStr)){
            return;
        }
        String[] customerId = customerIdStr.split(",");
            for (String s :
                    customerId) {
                    Integer id = Integer.parseInt(s);
                    customerRepository.updateFixedAreaId(fixedAreaId, id);

        }
    }

    @Override
    public void customer_regist(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer findByTelephone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public void updateType(String telephone) {
        customerRepository.updateType(telephone);
    }

    @Override
    public Customer login(String telephone, String password) {
        return customerRepository.findByTelephoneAndPassword(telephone,password);
    }

    @Override
    public String findFixedAreaIdByAddress(String address) {
        return customerRepository.findFixedAreaIdByAddress(address);
    }
}

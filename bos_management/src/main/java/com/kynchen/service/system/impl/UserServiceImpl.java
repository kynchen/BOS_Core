package com.kynchen.service.system.impl;/*
* @author kynchen
* 
* @date 2018/8/12 16:54
*
* @version idea
*/

import bos.domain.system.Role;
import bos.domain.system.User;
import com.kynchen.dao.system.RoleRepository;
import com.kynchen.dao.system.UserRepository;
import com.kynchen.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User model, String roleId) {
        userRepository.save(model);
        if(roleId!=null){
            Role role = roleRepository.findOne(Integer.parseInt(roleId));
            model.getRoles().add(role);
        }
    }
}

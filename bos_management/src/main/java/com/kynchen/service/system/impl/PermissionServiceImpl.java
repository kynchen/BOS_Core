package com.kynchen.service.system.impl;/*
* @author kynchen
* 
* @date 2018/8/12 19:11
*
* @version idea
*/

import bos.domain.system.Permission;
import com.kynchen.dao.system.PermissionRepository;
import com.kynchen.service.system.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public void save(Permission model) {
        permissionRepository.save(model);
    }
}

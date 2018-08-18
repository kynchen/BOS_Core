package com.kynchen.service.system.impl;/*
* @author kynchen
* 
* @date 2018/8/12 19:11
*
* @version idea
*/

import bos.domain.system.Menu;
import bos.domain.system.Permission;
import bos.domain.system.Role;
import com.kynchen.dao.system.MenuRepository;
import com.kynchen.dao.system.PermissionRepository;
import com.kynchen.dao.system.RoleRepository;
import com.kynchen.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class  RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Role> findByUserId(Integer id) {
        return roleRepository.findByUserId(id);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void saveRole(Role model, String[] permissionIds, String menuIds) {
        roleRepository.save(model);
        if(permissionIds!=null){
            for(String permissionId:permissionIds){
                Permission permission = permissionRepository.findOne(Integer.parseInt(permissionId));
                model.getPermissions().add(permission);
            }
        }
        if(menuIds!=null){
            String[] split = menuIds.split(",");
            for(String menuId:split){
                Menu menu = menuRepository.findOne(Integer.parseInt(menuId));
                model.getMenus().add(menu);
            }
        }
    }
}

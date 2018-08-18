package com.kynchen.service.system.impl;/*
* @author kynchen
* 
* @date 2018/8/13 10:28
*
* @version idea
*/

import bos.domain.system.Menu;
import bos.domain.system.Role;
import bos.domain.system.User;
import com.kynchen.dao.system.MenuRepository;
import com.kynchen.dao.system.RoleRepository;
import com.kynchen.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public void save(Menu model) {
        if(model.getParentMenu()!=null&&model.getParentMenu().getId()==0){
            model.setParentMenu(null);
        }
        menuRepository.save(model);
    }

    @Override
    public List<Menu> showMenu(User user) {

        List<Role> roles=roleRepository.findByUserId(user.getId());
        for (Role role:roles){
            if("admin".equals(role.getName())){
                return menuRepository.findAll();
            }
        }
        return menuRepository.findByUserId(user.getId());

    }
}

package com.kynchen.service.system;/*
 * @author kynchen
 *
 * @date 2018/8/12 19:10
 *
 * @version idea
 */

import bos.domain.system.Role;

import java.util.List;

public interface RoleService {
    List<Role> findByUserId(Integer id);

    List<Role> findAll();

    void saveRole(Role model, String[] permissionIds, String menuIds);
}

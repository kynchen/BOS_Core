package com.kynchen.service.system;/*
 * @author kynchen
 *
 * @date 2018/8/12 19:10
 *
 * @version idea
 */

import bos.domain.system.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();

    void save(Permission model);
}

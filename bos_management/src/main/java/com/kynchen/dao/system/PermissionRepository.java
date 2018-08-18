package com.kynchen.dao.system;/*
 * @author kynchen
 *
 * @date 2018/8/12 19:12
 *
 * @version idea
 */

import bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
}

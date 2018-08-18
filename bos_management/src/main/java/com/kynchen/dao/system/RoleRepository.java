package com.kynchen.dao.system;/*
 * @author kynchen
 *
 * @date 2018/8/12 19:12
 *
 * @version idea
 */

import bos.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query("from Role r inner join fetch r.users u where u.id=?1")
    List<Role> findByUserId(Integer id);

}

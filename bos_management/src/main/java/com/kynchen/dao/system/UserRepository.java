package com.kynchen.dao.system;/*
 * @author kynchen
 *
 * @date 2018/8/12 16:55
 *
 * @version idea
 */

import bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}

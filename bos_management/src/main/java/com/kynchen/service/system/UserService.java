package com.kynchen.service.system;/*
 * @author kynchen
 *
 * @date 2018/8/12 16:54
 *
 * @version idea
 */

import bos.domain.system.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<User> findAll();

    void save(User model, String roleId);
}

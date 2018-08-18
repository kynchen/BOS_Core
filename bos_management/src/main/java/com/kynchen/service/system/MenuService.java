package com.kynchen.service.system;/*
 * @author kynchen
 *
 * @date 2018/8/13 10:27
 *
 * @version idea
 */

import bos.domain.system.Menu;
import bos.domain.system.User;

import java.util.List;

public interface MenuService {
    List<Menu> findAll();

    void save(Menu model);

    List<Menu> showMenu(User user);
}

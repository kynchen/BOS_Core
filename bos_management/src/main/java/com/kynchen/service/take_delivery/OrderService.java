package com.kynchen.service.take_delivery;/*
 * @author kynchen
 *
 * @date 2018/8/8 16:31
 *
 * @version idea
 */

import bos.domain.take_delivery.Order;

public interface OrderService {

    void save(Order order);

    Order findOrderByOrderNum(String orderNum);
}

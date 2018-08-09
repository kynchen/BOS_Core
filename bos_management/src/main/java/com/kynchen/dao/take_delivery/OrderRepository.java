package com.kynchen.dao.take_delivery;/*
 * @author kynchen
 *
 * @date 2018/8/8 16:26
 *
 * @version idea
 */

import bos.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}

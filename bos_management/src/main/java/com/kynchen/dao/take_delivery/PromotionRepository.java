package com.kynchen.dao.take_delivery;/*
 * @author kynchen
 *
 * @date 2018/8/3 22:03
 *
 * @version idea
 */

import bos.domain.take_delivery.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface PromotionRepository extends JpaRepository<Promotion,Integer> {
    @Query("update Promotion set status ='2' where endDate<?1 and status='1' ")
    @Modifying
    void updateDate(Date date);
}

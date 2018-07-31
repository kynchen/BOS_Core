package com.kynchen.dao.base;/*
 * @author kynchen
 *
 * @date 2018/7/27 1:52
 *
 * @version idea
 */

import com.kynchen.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourierRepository extends JpaRepository<Courier,Integer>,JpaSpecificationExecutor<Courier> {

    @Query(value = "update Courier set deltag = '1' where id = ?1")
    @Modifying
    public void updateDeltag(Integer id);

    @Query(value = "update Courier set deltag = '' where id = ?1")
    @Modifying
    void restoreDeltag(Integer id);


}

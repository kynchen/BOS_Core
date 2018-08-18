package com.kynchen.elastic;/*
 * @author kynchen
 *
 * @date 2018/8/11 20:07
 *
 * @version idea
 */

import bos.domain.take_delivery.WayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WayBillElasticRepository extends ElasticsearchRepository<WayBill,Integer> {
}

package com.kynchen.service.take_delivery.impl;/*
* @author kynchen
* 
* @date 2018/8/9 15:54
*
* @version idea
*/

import bos.domain.take_delivery.WayBill;
import com.kynchen.dao.take_delivery.WayBillRepository;
import com.kynchen.elastic.WayBillElasticRepository;
import com.kynchen.service.take_delivery.WayBillService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;
    @Autowired
    private WayBillElasticRepository wayBillElasticRepository;

    @Override
    public void save(WayBill model) {
        wayBillRepository.save(model);
        wayBillElasticRepository.save(model);
    }

    /** elastic条件分页查询
     * @param model
     * @param pageable
     * @return
     */
    @Override
    public Page<WayBill> pageQuery(WayBill model, Pageable pageable) {
        if(StringUtils.isBlank(model.getWayBillNum())
                &&StringUtils.isBlank(model.getSendAddress())
                &&StringUtils.isBlank(model.getRecAddress())
                &&StringUtils.isBlank(model.getSendProNum())
                &&(model.getSignStatus()==null||model.getSignStatus()==0)) {
            return wayBillElasticRepository.findAll(pageable);
        }else{

            BoolQueryBuilder query = getBoolQueryBuilder(model);

            SearchQuery searchQuery =new  NativeSearchQuery(query);
            searchQuery.setPageable(pageable);
            return wayBillElasticRepository.search(searchQuery);
        }
    }



    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBillRepository.findByWayBillNum(wayBillNum);
    }

    @Override
    public void syncIndex() {
        List<WayBill> wayBills = wayBillRepository.findAll();
        wayBillElasticRepository.save(wayBills);
    }

    @Override
    public Page<WayBill> page_query(Pageable pageable) {
        return wayBillElasticRepository.findAll(pageable);
    }

    /** 查询 用于导出excel
     * @param model
     * @return
     */
    @Override
    public List<WayBill> findAllWayBills(WayBill model) {
        if(StringUtils.isBlank(model.getWayBillNum())
                &&StringUtils.isBlank(model.getSendAddress())
                &&StringUtils.isBlank(model.getRecAddress())
                &&StringUtils.isBlank(model.getSendProNum())
                &&(model.getSignStatus()==null||model.getSignStatus()==0)) {
            return wayBillRepository.findAll();
        }else {
            //布尔查询类型
            //must同时满足 should等同于or
            BoolQueryBuilder query = getBoolQueryBuilder(model);

            SearchQuery searchQuery = new NativeSearchQuery(query);
            Pageable pageable = new PageRequest(0,Integer.MAX_VALUE);
            searchQuery.setPageable(pageable);
            return wayBillElasticRepository.search(searchQuery).getContent();
        }
    }

    /** elastic条件分页查询
     * @param model
     * @return
     */
    private BoolQueryBuilder getBoolQueryBuilder(WayBill model) {
        //布尔查询类型
        //must同时满足 should等同于or
        BoolQueryBuilder query = new BoolQueryBuilder();
        //运单号
        if(StringUtils.isNotBlank(model.getWayBillNum())){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("wayBillNum",model.getWayBillNum());
            query.must(termQueryBuilder);
        }
        //寄件人地址
        if(StringUtils.isNotBlank(model.getSendAddress())){
            WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder("sendAddress", "*"+model.getSendAddress()+"*");

            //对输入长的内容进行分词再匹配
            QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(model.getSendAddress())
                    .field("sendAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
            BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
            queryBuilder.should(wildcardQueryBuilder);
            queryBuilder.should(queryStringQueryBuilder);

            query.must(queryBuilder);
        }
        //收件人地址
        if(StringUtils.isNotBlank(model.getRecAddress())){
            WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder("recAddress", "*"+model.getRecAddress()+"*");

            QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(model.getRecAddress())
                    .field("recAddress").defaultOperator(QueryStringQueryBuilder.Operator.AND);
            BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
            queryBuilder.should(wildcardQueryBuilder);
            queryBuilder.should(queryStringQueryBuilder);
            query.must(queryBuilder);
        }
        //快递产品类型编号
        if(StringUtils.isNotBlank(model.getSendProNum())){
//                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("sendProNum", model.getSendProNum());
            WildcardQueryBuilder wildcardQueryBuilder = new WildcardQueryBuilder("sendProNum", "*"+model.getSendProNum()+"*");
            QueryStringQueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(model.getSendProNum()).field("sendPorNum").defaultOperator(QueryStringQueryBuilder.Operator.AND);
            BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
            boolQueryBuilder.should(wildcardQueryBuilder);
            boolQueryBuilder.should(queryStringQueryBuilder);
            query.must(boolQueryBuilder);
        }
        //配载要求
        if(model.getSignStatus()!=null&&model.getSignStatus()!=0){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("signStatus", model.getSignStatus());
            query.must(termQueryBuilder);
        }
        return query;
    }
}

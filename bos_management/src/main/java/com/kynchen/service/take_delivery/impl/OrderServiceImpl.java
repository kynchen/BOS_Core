package com.kynchen.service.take_delivery.impl;/*
* @author kynchen
* 
* @date 2018/8/8 16:31
*
* @version idea
*/

import bos.domain.base.Area;
import bos.domain.base.Courier;
import bos.domain.base.FixedArea;
import bos.domain.base.SubArea;
import bos.domain.constans.Constans;
import bos.domain.take_delivery.Order;
import bos.domain.take_delivery.WorkBill;
import com.kynchen.dao.base.AreaRepository;
import com.kynchen.dao.base.FixedAreaRepository;
import com.kynchen.dao.take_delivery.OrderRepository;
import com.kynchen.dao.take_delivery.WorkBillRepository;
import com.kynchen.service.take_delivery.OrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private WorkBillRepository workBillRepository;

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;
    /** 自动分单 保存订单
     * @param order
     */
    @Override
    public void save(Order order) {

        //订单状态，已下单
        order.setStatus("1");
        //订单号
        order.setOrderNum(UUID.randomUUID().toString());
        //订单日期
        order.setOrderTime(new Date());
        //寄件人地址
        Area sendArea = order.getSendArea();
        Area area = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict()).get(0);
        //收件人地址
        Area recArea = order.getRecArea();
        Area recArea1=areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(),recArea.getCity(),recArea.getDistrict()).get(0);
        order.setSendArea(area);
        order.setRecArea(recArea1);
        //webService远程调用crm根据地址匹配定区编码
        String fixedAreaId = WebClient.create(Constans.CRM_MANAGEMENT_URL + "/services/customerService/customer/findFixedAreaIdByAddress?address="
                + order.getSendAddress()).accept(MediaType.APPLICATION_JSON).get(String.class);
        //根据定区编码关联快递员
        if(fixedAreaId!=null) {
            FixedArea fixedArea1 = fixedAreaRepository.findOne(fixedAreaId);
            Iterator<Courier> iterator = fixedArea1.getCouriers().iterator();
            if(iterator.hasNext()){
                Courier courier = iterator.next();
                if(courier!=null){
                    ////自动分单，关联快递员成功，保存订单
                    saveAutoOrder(order, courier);
                    //发送短信通知快递员
                    generateWorkBill(order);
                    return;
                }
            }

        }
        //如果定区为空，通过省市区的关键字或辅助关键字来查找分区，再查找定区再匹配快递员

        for(SubArea subArea:area.getSubareas()){
            if(order.getSendAddress().contains(subArea.getKeyWords())||order.getSendAddress().contains(subArea.getAssistKeyWords())){
                Iterator<Courier> iterator=subArea.getFixedArea().getCouriers().iterator();
                if(iterator.hasNext()){
                    Courier courier = iterator.next();
                    if(courier!=null){
                        //自动分单
                        saveAutoOrder(order, courier);
                        //发送短信通知快递员
                        generateWorkBill(order);
                        return;
                    }
                }

            }
        }
        //如果以上都查不到定区，进行人工调度
        order.setOrderType("2");
        orderRepository.save(order);
        System.out.println("进入人工调度....");
    }

    //自动分单
    private void saveAutoOrder(Order order, Courier courier) {
        order.setCourier(courier);
        order.setOrderType("1");
        orderRepository.save(order);
    }
    private void generateWorkBill(final Order order){
        final WorkBill workBill = new WorkBill();
        workBill.setType("新");
        workBill.setPickstate("新单");
        workBill.setBuildtime(new Date());
        workBill.setRemark(order.getRemark());
        final String smsNumber = RandomStringUtils.randomNumeric(4);
        workBill.setSmsNumber(smsNumber);
        workBill.setCourier(order.getCourier());
        workBill.setOrder(order);
        workBillRepository.save(workBill);
        //发送短信通知快递员
        jmsTemplate.send("sendCourier", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone",order.getCourier().getTelephone());
                mapMessage.setString("name",order.getSendName());
                mapMessage.setString("sendPhone",order.getTelephone());
                return mapMessage;
            }
        });

        workBill.setPickstate("已通知");
    }
}

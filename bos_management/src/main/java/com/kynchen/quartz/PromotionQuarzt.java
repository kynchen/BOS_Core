package com.kynchen.quartz;/*
* @author kynchen
* 
* @date 2018/8/5 11:10
*
* @version idea
*/

import com.kynchen.service.take_delivery.PromotionService;
import com.kynchen.service.take_delivery.WayBillService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/** 任务调度，修改活动过期状态
 *
 */
public class PromotionQuarzt implements Job
{
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private WayBillService wayBillService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        promotionService.updateDate(new Date());
        wayBillService.syncIndex();
    }
}

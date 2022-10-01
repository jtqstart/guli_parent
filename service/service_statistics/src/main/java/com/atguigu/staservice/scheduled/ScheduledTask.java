package com.atguigu.staservice.scheduled;

import com.atguigu.staservice.service.StatisticsDailyService;
import com.atguigu.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author jtqstart
 * @create 2022-09-05 17:38
 */
@Component
public class ScheduledTask {

    @Autowired
    StatisticsDailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ? ")
    public void task1(){
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(),-1));
        dailyService.createStaDaily(day);
        System.out.println("生成数据成功"+day);
    }
}

package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-04
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public void createStaDaily(String day) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        R register = ucenterClient.countRegister(day);
        Integer registerNum  = (Integer) register.getData().get("count");
        Integer loginNum  = RandomUtils.nextInt(100,200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        //3封装数据，入库
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);

    }

    @Override
    public Map<String, Object> getStaDaily(String type, String begain, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begain,end);
        wrapper.select(type);
        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        //2 遍历查询结果
        Map<String, Object> staDailyMap = new HashMap<>();
        List<String> dateCalculatedList = new ArrayList<>();
        List<Integer> dataList =  new ArrayList<>();

        for (StatisticsDaily daily : dailyList) {
            //封装X轴数据
            dateCalculatedList.add(daily.getDateCalculated());
            //封装Y轴数据
            switch (type){
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;

            }
        }
        staDailyMap.put("dateCalculatedList",dateCalculatedList);
        staDailyMap.put("dataList",dataList);

        return staDailyMap;
    }
}

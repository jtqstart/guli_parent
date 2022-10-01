package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-04
 */
@RestController
@RequestMapping("/staservice/stadaily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    StatisticsDailyService dailyService;

    @ApiOperation(value = "生成统计数据")
    @GetMapping("createStaDaily/{day}")
    public R createStaDaily(@PathVariable String day){
        dailyService.createStaDaily(day);
        return R.ok();
    }
    @ApiOperation(value = "查询统计数据")
    @GetMapping("getStaDaily/{type}/{begain}/{end}")
    public R getStaDaily(@PathVariable String type,
                         @PathVariable String begain,
                         @PathVariable String end){

        Map<String,Object> map = dailyService.getStaDaily(type,begain,end);
        return R.ok().data(map);
    }
}


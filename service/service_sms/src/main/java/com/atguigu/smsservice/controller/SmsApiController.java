package com.atguigu.smsservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.smsservice.service.SmsApiService;
import com.atguigu.smsservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author jtqstart
 * @create 2022-08-28 14:40
 */
@Api(description = "短信发送")
@RestController
@RequestMapping("/edumsm/sms")
@CrossOrigin
public class SmsApiController {

    @Autowired
    SmsApiService smsApiService;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @ApiOperation(value = "短信发送")
    @GetMapping("sendSmsPhone/{phone}")
    public R sendSmsPhone(@PathVariable String phone){
        String rPhone = redisTemplate.opsForValue().get(phone);
        if (rPhone!=null){
            return R.ok();
        }

        String code = RandomUtil.getFourBitRandom();
        HashMap<String, String> map = new HashMap<>();
        map.put("code",code);
        Boolean aBoolean = smsApiService.sendSmsPhone(phone,map);
        if (aBoolean){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error();
        }
    }

}

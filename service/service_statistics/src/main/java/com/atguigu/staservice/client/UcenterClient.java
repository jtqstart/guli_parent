package com.atguigu.staservice.client;

import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jtqstart
 * @create 2022-09-05 0:39
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @ApiOperation(value = "统计注册人数远程调用")
    @GetMapping("/ucenterservice/ucentermember/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}

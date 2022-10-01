package com.atguigu.orderservice.client;

import com.atguigu.vo.UcenterMemberForOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jtqstart
 * @create 2022-09-03 23:56
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @ApiOperation(value = "根据memberId获取用户信息跨模块")
    @RequestMapping("/ucenterservice/ucentermember/getUcenterForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterForOrder(@PathVariable("memberId") String memberId);
}

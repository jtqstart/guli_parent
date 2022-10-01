package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.atguigu.ucenterservice.utils.MD5;
import com.atguigu.vo.UcenterMemberForOrder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-28
 */
@RestController
@RequestMapping("/ucenterservice/ucentermember")
@CrossOrigin
@Api(description = "用户注册与登录")
public class UcenterMemberController {

    @Autowired
    UcenterMemberService ucenterMemberService;

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public R login(@RequestBody LoginVo loginVo){

        String token = ucenterMemberService.login(loginVo);

        return R.ok().data("token",token);
    }

    @ApiOperation(value = "根据token字符串获取用户信息")
    @GetMapping("getUcenterByToken")
    public R getUcenterByToken(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        return R.ok().data("ucenterMember",ucenterMember);
    }

    @ApiOperation(value = "根据memberId获取用户信息跨模块")
    @GetMapping("getUcenterForOrder/{memberId}")
    public UcenterMemberForOrder getUcenterForOrder(@PathVariable String memberId){
        UcenterMemberForOrder memberForOrder = new UcenterMemberForOrder();
        UcenterMember member = ucenterMemberService.getById(memberId);
        BeanUtils.copyProperties(member,memberForOrder);
        return memberForOrder;
    }

    @ApiOperation(value = "统计注册人数远程调用")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day){
        Integer count = ucenterMemberService.countRegister(day);
        return R.ok().data("count",count);
    }
}


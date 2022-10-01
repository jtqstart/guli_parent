package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-27
 */
@Api(description = "前台banner展示")
@RestController
@RequestMapping("/cmsservice/crmbanner")
@CrossOrigin
public class CrmBannerController {
    @Autowired
    CrmBannerService bannerService;

    @ApiOperation("查询所有banner信息")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> bannerList = bannerService.getAllBanner();
        return R.ok().data("bannerList",bannerList);
    }
}


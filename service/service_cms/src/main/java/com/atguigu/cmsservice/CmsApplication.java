package com.atguigu.cmsservice;

import com.atguigu.cmsservice.controller.CrmBannerController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jtqstart
 * @create 2022-08-27 9:06
 */
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.cmsservice.mapper")
@SpringBootApplication
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}

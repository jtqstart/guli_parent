package com.atguigu.orderservice.client;

import com.atguigu.vo.CourseWebVoForOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jtqstart
 * @create 2022-09-03 23:53
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    @ApiOperation(value = "根据课程id查询课程相关信息跨模块")
    @GetMapping("/eduservice/educourse/getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(@PathVariable("courseId") String courseId);
}

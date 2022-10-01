package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jtqstart
 * @create 2022-09-01 17:34
 */
@Api(description = "前台讲师列表")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class FrontTeacherController {

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduCourseService courseService;

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageListWeb/{page}/{limit}")
    public R pageListWeb(@PathVariable Long page,@PathVariable Long limit){
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        Map<String,Object> map = teacherService.pageListWeb(teacherPage);
        return R.ok().data(map);
    }

    @ApiOperation(value = "通过id查询讲师与课程")
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        queryWrapper.orderByDesc("gmt_create");
        List<EduCourse> courseList = courseService.list(queryWrapper);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}

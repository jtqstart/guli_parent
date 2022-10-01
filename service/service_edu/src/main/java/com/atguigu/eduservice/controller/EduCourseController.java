package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoForm;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.vo.CourseInfoVo;
import com.atguigu.vo.CourseWebVoForOrder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-21
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/educourse")
@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseId = eduCourseService.addCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }

    @GetMapping("getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable String id){
        CourseInfoForm courseInfoForm = eduCourseService.getCourseInfoById(id);
        return R.ok().data("courseInfo",courseInfoForm);
    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        eduCourseService.updateCourseInfo(courseInfoForm);
        return R.ok();
    }
    @ApiOperation("确认发布课程信息")
    @GetMapping("getCoursePublishById/{id}")
    public R getCoursePublishById(@PathVariable String id){
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishById(id);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @PutMapping("publishCourse/{id}")
    @ApiOperation("根据id发布课程")
    public R publishCourse(@PathVariable String id){
        EduCourse course = eduCourseService.getById(id);
        course.setStatus("Normal");
        eduCourseService.updateById(course);
        return R.ok();
    }

    @GetMapping("getCourseInfo")
    public R getCourseInfo(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "条件分页查询课程列表")
    @PostMapping("getCourseInfo/{page}/{limit}")
    public R getCourseInfo(@RequestBody CourseQuery courseQuery,
                           @PathVariable Long page,
                           @PathVariable Long limit){
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String parentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(teacherId)){
            queryWrapper.eq("teacher_id",teacherId);
        }
        if (!StringUtils.isEmpty(parentId)){
            queryWrapper.eq("subject_parent_id",parentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            queryWrapper.eq("subject_Id",subjectId);
        }

        Page<EduCourse> coursePage = new Page<>(page,limit);
        eduCourseService.page(coursePage,queryWrapper);
        List<EduCourse> courseList = coursePage.getRecords();
        long total = coursePage.getTotal();
        return R.ok()
                .data("list",courseList)
                .data("total",total);
    }

    @ApiOperation("根据id删除课程")
    @DeleteMapping("delCourseInfo/{id}")
    public R delCourseInfo(@PathVariable String id){
        eduCourseService.delCourseInfo(id);
        return R.ok();
    }

    @ApiOperation(value = "根据课程id查询课程相关信息跨模块")
    @GetMapping("getCourseInfoForOrder/{courseId}")
    public CourseWebVoForOrder getCourseInfoForOrder(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getFrontCourseInfo(courseId);
        CourseWebVoForOrder courseWebVoForOrder = new CourseWebVoForOrder();
        BeanUtils.copyProperties(courseInfoVo,courseWebVoForOrder);
        return courseWebVoForOrder;
    }
}


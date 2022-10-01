package com.atguigu.eduservice.controller;


import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-14
 */
@RestController
@RequestMapping("/eduservice/eduteacher")
@Api(description = "讲师管理")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;

    @GetMapping
    @ApiOperation(value = "所有讲师列表")
    public R getAllTeacher(){

        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("list",list);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除讲师")
    public R delTeacher(@PathVariable String id){
        boolean byId = eduTeacherService.removeById(id);
        if (byId){
            return R.ok().message("删除成功");
        }else {
            return R.error().message("删除失败");
        }
    }

    @ApiOperation(value = "分页查询讲师列表")
    @GetMapping("getTeacherPage/{current}/{limit}")
    public R getTeacherPage(@PathVariable Long current,@PathVariable Long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        eduTeacherService.page(page,null);
        List<EduTeacher> teacherList = page.getRecords();
        long total = page.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherList",teacherList);
        map.put("total",total);
        return R.ok().data(map);
    }

    @ApiOperation(value = "条件分页查询讲师列表")
    @PostMapping("getTeacherPageList/{current}/{limit}")
    public R getTeacherPageList(@PathVariable Long current,
                              @PathVariable Long limit,
                              @RequestBody TeacherQuery teacherQuery){
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");

        Page<EduTeacher> page = new Page<>(current,limit);
        eduTeacherService.page(page,wrapper);
        List<EduTeacher> teacherList = page.getRecords();
        long total = page.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherList",teacherList);
        map.put("total",total);
        return R.ok().data(map);
    }

    @ApiOperation(value ="添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){
        boolean b = eduTeacherService.save(teacher);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean update = eduTeacherService.updateById(teacher);
        if (update){
            return R.ok();
        }else {
            return R.error();
        }
    }
}


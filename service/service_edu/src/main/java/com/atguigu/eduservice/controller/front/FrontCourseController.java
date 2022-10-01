package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.vo.CourseInfoVo;
import com.atguigu.eduservice.vo.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author jtqstart
 * @create 2022-09-02 8:50
 */
@Api(description = "分页课程列表")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduChapterService chapterService;

    @Autowired
    OrderClient orderClient;

    @PostMapping("coursePageList/{page}/{limit}")
    public R coursePageList(@PathVariable Long page,
                             @PathVariable Long limit,
                             @RequestBody CourseQueryVo courseQueryVo){
        Page<EduCourse> pageParam  = new Page<>(page, limit);
        Map<String,Object> map = courseService.coursePageList(pageParam,courseQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation("获取前台课程详情")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){

        CourseInfoVo courseInfoVo = courseService.getFrontCourseInfo(courseId);

        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoById(courseId);

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse = orderClient.isBuyCourse(courseId, memberId);

        return R.ok().data("courseInfoVo",courseInfoVo)
                .data("chapterVideoList",chapterVideoList)
                .data("isBuyCourse",buyCourse);
    }
}

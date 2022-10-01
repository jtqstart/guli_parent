package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.vo.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(description = "章节管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/educhapter")
public class EduChapterController {
    @Autowired
    EduChapterService eduChapterService;

    @ApiOperation(value = "根据课程id查询章节、小节信息")
    @GetMapping("getChapterVideoById/{courseId}")
    public R getChapterVideoById(@PathVariable String courseId){
          List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoById(courseId);
          return R.ok().data("chapterVideoList",chapterVoList);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        eduChapterService.save(chapter);
        return R.ok();
    }

    @ApiOperation("编辑章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter){
        eduChapterService.updateById(chapter);
        return R.ok();
    }
    @ApiOperation("根据id查询章节")
    @GetMapping("getChapterById/{id}")
    public R getChapterById(@PathVariable String id){
        EduChapter chapter = eduChapterService.getById(id);
        return R.ok().data("chapter",chapter);
    }

    @ApiOperation("删除章节")
    @DeleteMapping("deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id){
        eduChapterService.removeById(id);
        return R.ok();
    }

}


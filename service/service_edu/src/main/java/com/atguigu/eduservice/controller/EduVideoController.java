package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-08-21
 */
@Api(description = "小节管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/eduvideo")
public class EduVideoController {
    @Autowired
    EduVideoService eduVideoService;

    @Autowired
    VodClient vodClient;

    @ApiOperation("添加小结")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video){
        eduVideoService.save(video);
        return R.ok();
    }

    @ApiOperation("删除小结")
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);
        if (video.getVideoSourceId() != null){
            vodClient.delVideo(video.getVideoSourceId());
        }
        eduVideoService.removeById(id);
        return R.ok();
    }

    @ApiOperation("根据id查询小节")
    @GetMapping("/getVideoById/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);
        return R.ok().data("video",video);
    }

    @ApiOperation("修改小节")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        eduVideoService.updateById(video);
        return R.ok();
    }
}


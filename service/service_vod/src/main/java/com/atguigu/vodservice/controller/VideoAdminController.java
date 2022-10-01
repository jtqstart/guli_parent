package com.atguigu.vodservice.controller;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.commonutils.R;
import com.atguigu.vodservice.utils.AliyunVodSDKUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author jtqstart
 * @create 2022-08-25 22:56
 */
@Api(description = "视频管理")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VideoAdminController {

    @ApiOperation("上传视频")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            String title = filename.substring(0,filename.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest("LTAI5tNGGVEBcj3iBHvgWi1p","YGio321JKdAm8kPnRPLIvxjUgULEvB",title,filename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = response.getVideoId();
            return R.ok().data("videoId",videoId);
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频上传失败");
        }

    }
    @ApiOperation("批量删除视频")
    @DeleteMapping("delVideoList")
    public R delVideoList(@RequestParam("videoIdList") List<String> videoIdList) {
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tNGGVEBcj3iBHvgWi1p", "YGio321JKdAm8kPnRPLIvxjUgULEvB");
        //DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            //request.setVideoIds("VideoId1,VideoId2");
            String videos = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videos);
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }

    }
    @ApiOperation("删除视频")
    @DeleteMapping("delVideo/{videoId}")
    public R delVideo(@PathVariable("videoId") String videoId){
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tNGGVEBcj3iBHvgWi1p", "YGio321JKdAm8kPnRPLIvxjUgULEvB");
        try {
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    @GetMapping("getVideoPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable String videoId){
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient("LTAI5tNGGVEBcj3iBHvgWi1p", "YGio321JKdAm8kPnRPLIvxjUgULEvB");
        try {
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //播放凭证
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            return null;
        }
    }
}

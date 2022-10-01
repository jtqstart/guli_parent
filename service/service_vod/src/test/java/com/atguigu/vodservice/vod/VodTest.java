package com.atguigu.vodservice.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @author jtqstart
 * @create 2022-08-25 21:07
 */

public class VodTest {

    @Test
    public void vodtest1() throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tNGGVEBcj3iBHvgWi1p", "YGio321JKdAm8kPnRPLIvxjUgULEvB");
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("0aca2d73567c4af591c0497deb4b81ff");
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
    }

    @Test
    public void vodtest2() {
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tNGGVEBcj3iBHvgWi1p", "YGio321JKdAm8kPnRPLIvxjUgULEvB");
        try {
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId("31bb24fec93f4a798a88c34a3cd37620");
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
    }

    @Test
    public void test3(){
        String accessKeyId = "LTAI5tNGGVEBcj3iBHvgWi1p";
        String accessKeySecret = "YGio321JKdAm8kPnRPLIvxjUgULEvB";
        String title = "888";
        String fileName = "D:\\6 - What If I Want to Move Faster.mp4";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        String url = response.getVideoId();
        System.out.println("url = " + url);


    }

}

package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jtqstart
 * @create 2022-08-26 22:15
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R delVideoList(List<String> videoIdList) {
        return R.error().message("删除失败");
    }

    @Override
    public R delVideo(String videoId) {
        return R.error().message("删除失败");
    }
}

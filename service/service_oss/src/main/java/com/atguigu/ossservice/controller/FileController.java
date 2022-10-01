package com.atguigu.ossservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.ossservice.service.FileService;
import com.fasterxml.jackson.databind.type.MapLikeType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jtqstart
 * @create 2022-08-18 22:44
 */
@Api(description = "文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class FileController {

    @Autowired
    FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile file){
        String url = fileService.uploadFile(file);
        return R.ok().data("url",url);
    }
}

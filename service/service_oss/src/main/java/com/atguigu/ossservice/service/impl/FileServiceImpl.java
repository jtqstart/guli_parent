package com.atguigu.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.ossservice.service.FileService;
import com.atguigu.ossservice.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author jtqstart
 * @create 2022-08-18 22:50
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(MultipartFile file) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtil.END_POINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName="guli-20220818";
        String fileName=file.getOriginalFilename();

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        fileName= UUID.randomUUID().toString().replaceAll("-","")+fileName;
        String path = new DateTime().toString("yyyy-MM-dd");
        fileName = path+"/"+fileName;

        try {
            InputStream fileInputStream = file.getInputStream();
            ossClient.putObject(bucketName,fileName,fileInputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            String url="https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001,"上传失败");
        }

    }
}

package com.atguigu.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author jtqstart
 * @create 2022-08-18 22:48
 */
public interface FileService {
    String uploadFile(MultipartFile file);
}

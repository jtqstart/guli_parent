package com.atguigu.smsservice.service;

import java.util.Map;

/**
 * @author jtqstart
 * @create 2022-08-28 14:42
 */
public interface SmsApiService {
    Boolean sendSmsPhone(String phone, Map<String,String> map);
}

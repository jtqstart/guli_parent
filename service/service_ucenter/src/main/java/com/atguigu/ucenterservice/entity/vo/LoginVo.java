package com.atguigu.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jtqstart
 * @create 2022-08-28 16:36
 */
@Data
public class LoginVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}

package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jtqstart
 * @create 2022-08-20 22:30
 */
@Data
public class TwoSubjectVo {
    @ApiModelProperty(value = "课程类别ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;


}

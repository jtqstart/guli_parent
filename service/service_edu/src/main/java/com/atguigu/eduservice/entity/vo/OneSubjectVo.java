package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jtqstart
 * @create 2022-08-20 22:28
 */
@Data
public class OneSubjectVo<TwoSubjectVo> {
    @ApiModelProperty(value = "课程类别ID")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String title;

    private List<TwoSubjectVo> children;

}

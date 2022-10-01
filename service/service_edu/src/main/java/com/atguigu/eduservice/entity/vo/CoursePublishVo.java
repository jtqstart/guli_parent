package com.atguigu.eduservice.entity.vo;

import lombok.Data;

/**
 * @author jtqstart
 * @create 2022-08-25 13:37
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}

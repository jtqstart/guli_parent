package com.atguigu.eduservice.entity.vo;

import lombok.Data;

/**
 * @author jtqstart
 * @create 2022-08-25 16:19
 */
@Data
public class CourseQuery {
    String subjectParentId;
    String subjectId;
    String title;
    String teacherId;
}

package com.atguigu.eduservice.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author jtqstart
 * @create 2022-08-19 21:17
 */
@Data
public class DemoData {
    @ExcelProperty(value = "学生编号",index = 0)
    private int id;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;
}

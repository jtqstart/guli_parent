package com.atguigu.eduservice.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

/**
 * @author jtqstart
 * @create 2022-08-19 22:29
 */
public class ReadTest {
    @Test
    public void read(){
        EasyExcel.read("D:\\write.xlsx",DemoData.class,new ExcelListener()).sheet().doRead();
    }
}

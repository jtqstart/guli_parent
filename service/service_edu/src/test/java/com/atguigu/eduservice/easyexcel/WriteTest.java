package com.atguigu.eduservice.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jtqstart
 * @create 2022-08-19 21:21
 */

public class WriteTest {

    @Test
    public void write(){
        String fileName="D:\\write.xlsx";

        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(data());

    }

    private static List<DemoData> data() {
        List list = new ArrayList<DemoData>();
        for (int i=1;i<=10;i++){
            DemoData data = new DemoData();
            data.setId(i);
            data.setName("张三"+i);
            list.add(data);
        }
        return list;
    }
}

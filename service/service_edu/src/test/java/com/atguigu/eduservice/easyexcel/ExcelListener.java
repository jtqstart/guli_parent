package com.atguigu.eduservice.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;

import java.util.Map;

/**
 * @author jtqstart
 * @create 2022-08-19 22:26
 */

public class ExcelListener extends AnalysisEventListener<DemoData> {

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("data = " + data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("headMap = " + headMap);
    }
}

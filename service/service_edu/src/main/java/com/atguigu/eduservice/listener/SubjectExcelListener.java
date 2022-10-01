package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author jtqstart
 * @create 2022-08-19 23:06
 */
public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener(){}
    public SubjectExcelListener(EduSubjectService eduSubjectService){
        this.eduSubjectService=eduSubjectService;
    }

    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext context) {
        System.out.println("data = " + data);
        if (data==null){
            throw new GuliException(20001,"添加课程失败");
        }
        EduSubject oneSubject = existOneSubject(data.getOneSubjectName());
        if (oneSubject==null){
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(data.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }
        String id = oneSubject.getId();
        System.out.println("id = " + id);

        EduSubject twoSubject = existTwoSubject(data.getTwoSubjectName(), id);
        if (twoSubject==null){
            twoSubject = new EduSubject();
            twoSubject.setParentId(id);
            twoSubject.setTitle(data.getTwoSubjectName());
            eduSubjectService.save(twoSubject);
        }
    }

    private EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        wrapper.eq("title",name);
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

    private EduSubject existTwoSubject(String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",pid);
        wrapper.eq("title",name);
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}

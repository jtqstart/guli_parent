package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.ExcelSubjectData;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.atguigu.eduservice.entity.vo.TwoSubjectVo;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubjectVo> getAllSubject() {
        QueryWrapper<EduSubject> oneQueryWrapper = new QueryWrapper<>();
        oneQueryWrapper.eq("parent_id",0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneQueryWrapper);

        QueryWrapper<EduSubject> twoQueryWrapper = new QueryWrapper<>();
        twoQueryWrapper.ne("parent_id",0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoQueryWrapper);

        List<OneSubjectVo> oneSubjectVos = new ArrayList<>();
        for (int i=0;i<oneSubjectList.size();i++){
            EduSubject oneSubject = oneSubjectList.get(i);
            OneSubjectVo oneSubjectVo = new OneSubjectVo();

            BeanUtils.copyProperties(oneSubject,oneSubjectVo);

            List<TwoSubjectVo> twoSubjectVos = new ArrayList<>();
            for (int j=0;j<twoSubjectList.size();j++){
                EduSubject twoSubject = twoSubjectList.get(j);

                if (oneSubject.getId().equals(twoSubject.getParentId())){
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    BeanUtils.copyProperties(twoSubject,twoSubjectVo);
                    twoSubjectVos.add(twoSubjectVo);
                }
            }
            oneSubjectVo.setChildren(twoSubjectVos);
            oneSubjectVos.add(oneSubjectVo);
        }
        return oneSubjectVos;
    }
}

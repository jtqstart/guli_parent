package com.atguigu.eduservice.service.impl;

import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.vo.CourseInfoForm;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.eduservice.vo.CourseInfoVo;
import com.atguigu.eduservice.vo.CourseQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-21
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduVideoService eduVideoService;

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    VodClient vodClient;

    @Override
    public CourseInfoVo getFrontCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = baseMapper.getFrontCourseInfo(courseId);

        EduCourse course = baseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);

        return courseInfoVo;
    }

    @Override
    public String addCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        boolean save = this.save(eduCourse);
        if (!save){
            throw new GuliException(20001,"创建课程失败");
        }
        String id = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(courseInfoForm.getDescription());

        eduCourseDescriptionService.save(eduCourseDescription);

        return id;
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        EduCourse eduCourse = this.getById(id);
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        courseInfoForm.setDescription(eduCourseDescription.getDescription());

        return courseInfoForm;
    }

    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);

        boolean update = this.updateById(eduCourse);
        if (!update){
            throw new GuliException(20001,"修改失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoForm.getId());
        eduCourseDescription.setDescription(courseInfoForm.getDescription());

        eduCourseDescriptionService.updateById(eduCourseDescription);

    }

    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishById(id);
        return coursePublishVo;
    }

    @Override
    public void delCourseInfo(String id) {
        //删除视频
        QueryWrapper<EduVideo> videoQueryWrapper1 = new QueryWrapper<>();
        videoQueryWrapper1.eq("course_id",id);
        List<EduVideo> videoList = eduVideoService.list(videoQueryWrapper1);
        ArrayList<String> videoIdList = new ArrayList<>();
        for (EduVideo eduVideo : videoList) {
            videoIdList.add(eduVideo.getVideoSourceId());
        }
        if(videoIdList.size()>0){
            vodClient.delVideoList(videoIdList);
        }

        QueryWrapper<EduVideo> videoQueryWrapper2 = new QueryWrapper<>();
        videoQueryWrapper2.eq("course_id",id);
        eduVideoService.remove(videoQueryWrapper2);

        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",id);
        eduChapterService.remove(chapterQueryWrapper);

        eduCourseDescriptionService.removeById(id);

        int delete = baseMapper.deleteById(id);
        if (delete==0){
            throw new GuliException(20001,"删除失败");
        }

    }

    @Override
    public Map<String, Object> coursePageList(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())){
            queryWrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())){
            queryWrapper.eq("subject_id",courseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())){
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())){
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())){
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,queryWrapper);

        long pages = pageParam.getPages();
        long total = pageParam.getTotal();
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long size = pageParam.getSize();
        boolean hasPrevious = pageParam.hasPrevious();
        boolean hasNext = pageParam.hasNext();

        HashMap<String, Object> map = new HashMap<>();
        map.put("items",records);
        map.put("pages",pages);
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);

        return map;
    }
}

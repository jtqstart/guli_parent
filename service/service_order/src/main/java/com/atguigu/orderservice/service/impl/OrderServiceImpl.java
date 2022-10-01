package com.atguigu.orderservice.service.impl;

import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.commonutils.R;
import com.atguigu.orderservice.client.EduClient;
import com.atguigu.orderservice.client.UcenterClient;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.mapper.OrderMapper;
import com.atguigu.orderservice.service.OrderService;
import com.atguigu.orderservice.utils.OrderNoUtil;
import com.atguigu.vo.CourseWebVoForOrder;
import com.atguigu.vo.UcenterMemberForOrder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    EduClient eduClient;

    @Autowired
    UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String memberId) {
        CourseWebVoForOrder courseInfoForOrder = eduClient.getCourseInfoForOrder(courseId);
        if (courseInfoForOrder==null){
            throw new GuliException(20001,"获取课程信息失败");
        }
        UcenterMemberForOrder ucenterForOrder = ucenterClient.getUcenterForOrder(memberId);
        if (courseInfoForOrder==null){
            throw new GuliException(20001,"获取用户信息失败");
        }

        String orderNo = OrderNoUtil.getOrderNo();

        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoForOrder.getTitle());
        order.setCourseCover(courseInfoForOrder.getCover());
        order.setTeacherName(courseInfoForOrder.getTeacherName());
        order.setTotalFee(courseInfoForOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterForOrder.getMobile());
        order.setNickname(ucenterForOrder.getNickname());
        order.setStatus(0);//未支付
        order.setPayType(1);//1：微信
        baseMapper.insert(order);

        return orderNo;
    }
}

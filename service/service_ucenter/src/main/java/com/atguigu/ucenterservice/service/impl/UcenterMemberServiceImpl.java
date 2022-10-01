package com.atguigu.ucenterservice.service.impl;

import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.ucenterservice.entity.UcenterMember;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.mapper.UcenterMemberMapper;
import com.atguigu.ucenterservice.service.UcenterMemberService;
import com.atguigu.ucenterservice.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-28
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {

        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        if (StringUtils.isEmpty(nickname)|| StringUtils.isEmpty(mobile)||
                StringUtils.isEmpty(password)|| StringUtils.isEmpty(code)){
           throw new GuliException(20001,"注册失败");
        }

        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(ucenterMemberQueryWrapper);
        if (integer>0){
            throw new GuliException(20001,"重复注册");
        }

        String rCode = redisTemplate.opsForValue().get(mobile);
        if (!rCode.equals(code)){
            throw new GuliException(20001,"验证码错误");
        }

        String encrypt = MD5.encrypt(password);
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(encrypt);
        member.setAvatar("https://guli-20220818.oss-cn-hangzhou.aliyuncs.com/1442297999141.jpg");
        member.setIsDisabled(false);
        baseMapper.insert(member);
    }

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw  new GuliException(20001,"手机号或密码有误");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember member = baseMapper.selectOne(wrapper);
        if (member==null){
            throw new GuliException(20001,"手机号或密码有误");
        }

        String encrypt = MD5.encrypt(password);
        if (!member.getPassword().equals(encrypt)){
            throw new GuliException(20001,"手机号或密码有误");
        }

        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return token;
    }

    @Override
    public Integer countRegister(String day) {
        Integer count = baseMapper.countRegister(day);
        return count;
    }
}

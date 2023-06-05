package com.yzdbx.dkpan.service.impl;

import com.yzdbx.dkpan.entity.Constants;
import com.yzdbx.dkpan.entity.data.RedisMapper;
import com.yzdbx.dkpan.exception.BusinessException;
import com.yzdbx.dkpan.service.UserInfoService;
import com.yzdbx.dkpan.service.domain.LoginBack;
import com.yzdbx.dkpan.service.domain.UserInfo;
import com.yzdbx.dkpan.service.repository.UserInfoRepository;
import com.yzdbx.dkpan.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: 一只大笨象
 * @Date: 2023/05/30
 * @description:
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final RedisMapper redisMapper;
    private final JwtUtil jwtUtil;

    /**
     * 用户注册
     *
     * @param userInfo
     */
    @Override
    public void userRegisterHandler(UserInfo userInfo) {
        UserInfo user = userInfoRepository.findUserByEmail(userInfo.getEmail());
        if (Objects.nonNull(user)) {
            throw new BusinessException("该邮箱已被占用,换一个吧");
        }
        userInfoRepository.addUser(userInfo);
    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @param type  0:注册 1:登录
     * @return
     */
    @Override
    public String sendEmail(String email, Integer type) {
        if (type == 0 && Objects.nonNull(userInfoRepository.findUserByEmail(email))) {
            throw new BusinessException("该邮箱已被占用,换一个吧");
        }
        return userInfoRepository.sendEmail(email);
    }

    /**
     * 用户登录
     *
     * @param
     * @param loginParams
     * @param loginType   1:密码登录 2:邮箱验证码登录
     * @param emailCode
     */
    @Override
    public LoginBack userLoginHandler(UserInfo loginParams, Integer loginType, String emailCode) {
        UserInfo user = null;
        if (loginType == 1) {
            //TODO 密码登录
             user = userInfoRepository.findUserByEmail(loginParams.getEmail());
            if (Objects.isNull(user)) {
                throw new BusinessException("该邮箱不存在");
            }
            if (!user.getPassWord().equals(loginParams.getPassWord())) {
                throw new BusinessException("密码输入有误");
            }
        } else {
            // TODO 邮箱登录
            Object redisCodeVal = redisMapper.getKey(String.format(Constants.CHECK_CODE_EMAIL_KEY,
                    emailCode));

            if (Objects.isNull(emailCode)) {
                throw new BusinessException("邮箱验证码已过期");
            }
            if (!((String) redisCodeVal).equalsIgnoreCase(emailCode)) {
                throw new BusinessException("邮箱验证码不正确");
            }
        }
        // TODO 登录成功生成token回返
        String token = jwtUtil.createToken(loginParams.getEmail());
        return LoginBack.builder().nickName(user.getNickName())
                .email(user.getEmail())
                .token(token)
                .build();

    }
}

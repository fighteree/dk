package com.yzdbx.dkpan.controller;

import com.yzdbx.dkpan.controller.api.ApiUserInfo;
import com.yzdbx.dkpan.controller.converter.UserInfoConverter;
import com.yzdbx.dkpan.entity.RestEntityResponse;
import com.yzdbx.dkpan.entity.Constants;
import com.yzdbx.dkpan.entity.dto.LoginInfoDto;
import com.yzdbx.dkpan.exception.BusinessException;
import com.yzdbx.dkpan.exception.InvalidParamsException;
import com.yzdbx.dkpan.service.UserInfoService;
import com.yzdbx.dkpan.service.domain.LoginBack;
import com.yzdbx.dkpan.service.repository.UserInfoRepository;
import com.yzdbx.dkpan.entity.RestVoidResponse;
import com.yzdbx.dkpan.util.VerifyCodeUtil;
import com.yzdbx.dkpan.entity.data.RedisMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author 一只大笨象
 * @since 2023-05-30
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserInfoController implements ApiUserInfo {
    private final UserInfoService userInfoService;
    private final UserInfoRepository userInfoRepository;
    private final RedisMapper redisMapper;
    private final UserInfoConverter converter;

    /**
     * @param response
     */
    @GetMapping(value = "/check-code")
    public void checkCode(HttpServletResponse response, HttpSession session) {
        VerifyCodeUtil verifyCodeUtil = new VerifyCodeUtil();
        verifyCodeUtil.generateCode();
        String code = verifyCodeUtil.getText();//验证码
        log.info("生成验证码是[{}]", code);
        session.setAttribute(Constants.CHECK_CODE_KEY, code);
        verifyCodeUtil.writeCodeToRespone(response);
    }

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @param type  0:注册 1:登录
     * @return
     */
    @GetMapping("/send-email")
    public RestVoidResponse sendEmail(String email, Integer type) {
        userInfoService.sendEmail(email, type);
        return RestVoidResponse.success();
    }

    /**
     * 用户注册
     *
     * @param args
     * @return
     */
    @PostMapping("/user/register")
    public RestVoidResponse doRegister(@RequestBody @Validated DoRegisterArgs args,
                                       HttpSession session, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidParamsException();
        }
        Object sessionAttribute = session.getAttribute(Constants.CHECK_CODE_KEY);
        String checkCode = "";
        if (Objects.nonNull(sessionAttribute)) {
            checkCode = ((String) sessionAttribute);
        }
        if (!checkCode.equalsIgnoreCase(args.getCheckCode())) {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
            throw new BusinessException("图片验证码输入有误");
        }
        Object code = redisMapper.getKey(String.format(Constants.CHECK_CODE_EMAIL_KEY, args.getEmailCode()));
        if (Objects.isNull(code)) {
            throw new BusinessException("邮箱验证码已过期");
        }
        if (!((String) code).equalsIgnoreCase(args.getEmailCode())) {
            throw new BusinessException("邮箱验证码输入有误");
        }
        userInfoService.userRegisterHandler(converter.from(args));
        session.removeAttribute(Constants.CHECK_CODE_KEY);
        return RestVoidResponse.success("注册成功");

    }
    /**
     * 用户登录
     *
     * @param args
     * @param :密码登录 2:邮箱验证码登录
     * @return
     */
    @PostMapping("/user/login")
    public RestEntityResponse<LoginInfoDto> doLogin(@RequestBody @Validated DoLoginArgs args,
                                                    BindingResult result,
                                                    HttpSession session) {
        if (result.hasErrors()) {
            throw new InvalidParamsException();
        }
        Object sessionAttribute = session.getAttribute(Constants.CHECK_CODE_KEY);
        String checkCode = "";
        if (Objects.nonNull(sessionAttribute)) {
            checkCode = (String) sessionAttribute;
        }
        if (!checkCode.equalsIgnoreCase(args.getCheckCode())) {
            session.removeAttribute(Constants.CHECK_CODE_KEY);
            throw new BusinessException("图片验证码输入不正确");
        }
        LoginBack loginBack = userInfoService.userLoginHandler(converter.from(args), args.getLoginType(), args.getEmailCode());
        session.removeAttribute(Constants.CHECK_CODE_KEY);
        LoginInfoDto dto = new LoginInfoDto();
        BeanUtils.copyProperties(loginBack, dto);
        return RestEntityResponse.success(dto);
    }

}

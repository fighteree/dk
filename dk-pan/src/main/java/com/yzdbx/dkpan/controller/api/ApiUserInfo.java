package com.yzdbx.dkpan.controller.api;

import com.yzdbx.dkpan.controller.UserInfoController;
import com.yzdbx.dkpan.entity.RestEntityResponse;
import com.yzdbx.dkpan.entity.RestVoidResponse;
import com.yzdbx.dkpan.entity.dto.LoginInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * @Author: 一只大笨象
 * @Date: 2023/06/02
 * @description:
 */
@Tag(name = "用户管理", description = "用于处理用户相关的请求")
public interface ApiUserInfo {


    /**
     * @param response
     */
    @Operation(
            method = "GET",
            summary = "获取一个图片验证码"
    )
    void checkCode(HttpServletResponse response, HttpSession session);

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @param type  0:注册 1:登录
     * @return
     */
    @Operation(
            method = "GET",
            summary = "获取邮箱验证码",
            parameters = {
                    @Parameter(
                            name = "email",
                            description = "接受验证码的邮箱",
                            example = "940663597@qq.com"
                    )
            }
    )
    public RestVoidResponse sendEmail(String email, Integer type);

    /**
     * 用户注册
     *
     * @param args
     * @return
     */
    @Operation(
            method = "POST",
            summary = "用户注册",
            parameters = {
                    @Parameter(
                            in = ParameterIn.DEFAULT,
                            schema = @Schema(
                                    implementation = DoRegisterArgs.class
                            )
                    )
            }

    )
    RestVoidResponse doRegister(UserInfoController.DoRegisterArgs args,
                                HttpSession session, BindingResult result);


    /**
     * 用户注册参数模型
     */
    @Data
    @Schema(description = "用户注册信息")
    public static class DoRegisterArgs {
        @NotBlank(message = "密码不能为空")
        @Schema(name = "passWord", description = "密码", example = "12456", required = true)
        private String passWord;
        @Schema(name = "nickName", description = "昵称", example = "tommy", required = true)
        @NotBlank(message = "昵称不能为空")
        private String nickName;
        @Schema(name = "email", description = "邮箱", example = "xxxx.qq.com", required = true)
        @NotBlank(message = "邮箱不能为空")
        private String email;
        @Schema(name = "checkCode", description = "图片验证码", required = true)
        @NotBlank(message = "图片验证码不能为空")
        private String checkCode;
        @Schema(name = "emailCode", description = "邮箱验证码", required = true)
        @NotBlank(message = "邮箱验证码不能为空")
        private String emailCode;
    }

    /**
     * 用户登录
     *
     * @param args
     * @param :密码登录 2:邮箱验证码登录
     * @return
     */
    @Operation(
            method = "POST",
            summary = "用户登录",
            parameters = {
                    @Parameter(
                            in = ParameterIn.DEFAULT,
                            schema = @Schema(implementation = DoLoginArgs.class)
                    )
            }
    )
    public RestEntityResponse<LoginInfoDto> doLogin(UserInfoController.DoLoginArgs args,
                                                    BindingResult result,
                                                    HttpSession session);

    /**
     * 用户登录参数模型
     */
    @Data
    @Schema(description = "用户登录信息")
    public static class DoLoginArgs extends UserInfoController.DoRegisterArgs {
        @Schema(name = "loginType", description = "登录类型 1:密码登录 2:邮箱登录", required = false)
        private Integer loginType = 1;// 1:密码登录 2:邮箱登录
    }
}

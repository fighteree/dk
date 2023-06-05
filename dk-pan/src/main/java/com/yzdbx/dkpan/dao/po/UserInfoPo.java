package com.yzdbx.dkpan.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author 一只大笨象
 * @since 2023-05-30
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user_info")
public class UserInfoPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID(使用雪花算法生成)
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 密码
     */
    @TableField("pass_word")
    private String passWord;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone_num")
    private String phoneNum;

    /**
     * 已使用空间 单位:字节
     */
    @TableField("used_capcity")
    private Long usedCapcity;

    /**
     * 总空间 单位:字节
     */
    @TableField("total_capcity")
    private Long totalCapcity;

    /**
     * 注册时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最后一次登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 状态 0:禁用 1:正常
     */
    @TableField("status")
    private Boolean status;


}

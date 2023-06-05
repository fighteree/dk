package com.yzdbx.dkpan.dao.mysql;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzdbx.dkpan.dao.po.UserInfoPo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author 一只大笨象
 * @since 2023-05-30
 */
public interface UserInfoMapper extends BaseMapper<UserInfoPo> {

    UserInfoPo findUserByEmail(String email);
}

package com.yzdbx.dkpan.dao.po.converter;

import com.yzdbx.dkpan.dao.po.UserInfoPo;
import com.yzdbx.dkpan.service.domain.UserInfo;
import org.mapstruct.Mapper;

/**
 * @Author: 一只大笨象
 * @Date: 2023/05/31
 * @description:
 */
@Mapper(componentModel = "spring")
public interface UserInfoPoConverter {
    UserInfoPo from(UserInfo userInfo);
}

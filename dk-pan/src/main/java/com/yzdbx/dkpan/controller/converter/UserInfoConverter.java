package com.yzdbx.dkpan.controller.converter;

import com.yzdbx.dkpan.controller.UserInfoController;
import com.yzdbx.dkpan.dao.po.UserInfoPo;
import com.yzdbx.dkpan.service.domain.UserInfo;
import org.mapstruct.Mapper;

/**
 * @Author: 一只大笨象
 * @Date: 2023/05/31
 * @description:
 */
@Mapper(componentModel = "spring")
public interface UserInfoConverter {
    UserInfo from(UserInfoController.DoRegisterArgs doRegisterArgs);
    UserInfo from(UserInfoPo po);
}

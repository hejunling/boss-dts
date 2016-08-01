package com.ancun.boss.persistence.biz;

import com.ancun.boss.pojo.userInfo.UserInfoInput;
import com.ancun.boss.pojo.userInfo.UserInfoListInput;
import com.ancun.boss.pojo.userInfo.UserInfoPojo;

import java.util.List;

/**
 * @author mif
 * @version 1.0
 * @Created on 2015/9/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface BizSystemUserInfoMapper {

    List<UserInfoPojo> selectUserList(UserInfoListInput infoInput);
    
    UserInfoPojo selectUser(String userno);
}

package com.ancun.boss.business.service.bizuser;

import com.ancun.boss.business.pojo.bizuser.BizEntAddSubAccountInput;
import com.ancun.boss.business.pojo.bizuser.BizEntAddSubAccountOutPut;
import com.ancun.boss.business.pojo.bizuser.BizEntSubAccountListInput;
import com.ancun.boss.business.pojo.bizuser.BizEntSubAccountListOutPut;
import com.ancun.core.exception.EduException;

/**
 * 企业用户子账号相关service
 * User: zkai
 * Date: 2016/4/25
 * Time: 21:22
 * Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */

public interface IBizEntSubAccountService {

    /**
     * 企业用户添加子账号
     *
     * @param input
     * @return
     * @throws EduException
     */
    public BizEntAddSubAccountOutPut bizEntAddSubAccount(BizEntAddSubAccountInput input);

    /**
     * 企业用户子账号列表
     */
    public BizEntSubAccountListOutPut bizEntSubAccountList(BizEntSubAccountListInput input);
}

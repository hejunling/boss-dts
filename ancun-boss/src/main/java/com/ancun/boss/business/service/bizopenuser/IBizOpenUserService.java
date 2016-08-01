package com.ancun.boss.business.service.bizopenuser;

import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserBatchInput;
import com.ancun.boss.business.pojo.bizopenuser.OpenBizUserInput;


public interface IBizOpenUserService {
	public void openUserSingle(OpenBizUserInput input);
	
	public void openUserBatch(OpenBizUserBatchInput input);

}

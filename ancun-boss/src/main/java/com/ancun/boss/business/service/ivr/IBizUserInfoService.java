package com.ancun.boss.business.service.ivr;

import java.util.List;

import com.ancun.boss.business.persistence.module.BizComboInfo;
import com.ancun.boss.business.persistence.module.BizUserInfo;

public interface IBizUserInfoService {
	public List<BizUserInfo> selectSingle(String userNo);
	public int deleteSingle(String userNo);
	public List<BizComboInfo> selectDefaultComboByRpcode(String rpcode);
	public List<BizComboInfo> selecComboTypeById(Long id);
	
}

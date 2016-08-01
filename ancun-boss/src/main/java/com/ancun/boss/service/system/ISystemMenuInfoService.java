package com.ancun.boss.service.system;

import java.util.List;

import com.ancun.boss.persistence.model.SystemMenuInfo;

public interface ISystemMenuInfoService {

	public abstract List<SystemMenuInfo> selectPoplarSystemMenuInfo(
			SystemMenuInfo systemMenuInfo);

	public abstract SystemMenuInfo selectSingleSystemMenuInfo(
			SystemMenuInfo systemMenuInfo);

	public abstract int insertSystemMenuInfo(SystemMenuInfo systemMenuInfo);

	public abstract int deleteLogicalPoplarSystemMenuInfo(SystemMenuInfo systemMenuInfo);

	public abstract int deleteLogicalSingleSystemMenuInfo(SystemMenuInfo systemMenuInfo);

	/**不更新排序信息的方法，也就是只更新名称、URL等其他信息*/
	public abstract int updateNotOrderInfoSystemMenuInfo(
			SystemMenuInfo systemMenuInfo);

}
package com.ancun.boss.pojo.workorder;

import java.util.List;

import com.ancun.boss.pojo.BossBasePojo;

/**
 * 工单服务数据导入input
 *
 * @Created on 2015年10月20日
 * @author chenb
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public class WorkOrderInfoListInput extends BossBasePojo {
	
	private List<GatherWorkOrderInfomeInput> workOrderList;

	public List<GatherWorkOrderInfomeInput> getWorkOrderList() {
		return workOrderList;
	}

	public void setWorkOrderList(List<GatherWorkOrderInfomeInput> workOrderList) {
		this.workOrderList = workOrderList;
	}
}

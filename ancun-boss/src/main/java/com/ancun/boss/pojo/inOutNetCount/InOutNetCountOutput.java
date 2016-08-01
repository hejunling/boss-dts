package com.ancun.boss.pojo.inOutNetCount;

import java.util.List;


public class InOutNetCountOutput {
	
	// 在网数据
	private List<InOutData> inNetList;
	
	//离网数据
	private List<InOutData> outNetList;

	public List<InOutData> getInNetList() {
		return inNetList;
	}

	public void setInNetList(List<InOutData> inNetList) {
		this.inNetList = inNetList;
	}

	public List<InOutData> getOutNetList() {
		return outNetList;
	}

	public void setOutNetList(List<InOutData> outNetList) {
		this.outNetList = outNetList;
	}
	
	
}

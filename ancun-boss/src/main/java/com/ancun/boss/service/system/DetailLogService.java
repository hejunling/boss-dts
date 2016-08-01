package com.ancun.boss.service.system;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ancun.boss.persistence.mapper.DetailLogMapper;
import com.ancun.boss.persistence.model.DetailLog;

@Service
public class DetailLogService {
	@Resource
	private DetailLogMapper detailLogMapper;
	
	@Async
	public void saveLog(DetailLog log){
		this.detailLogMapper.insert(log);
	}
}

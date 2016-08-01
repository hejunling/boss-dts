package com.ancun.boss.persistence.biz.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.ancun.boss.business.persistence.module.BizUserWhiteList;

/**
 * 鉴权白名单验证
 * @author 静好
 *
 */
public interface BizUserWhiteAuthMapper {
	
	/**
	 * 根据电话号码查询是否为鉴权白名单
	 * userTel 用户号码
	 * type   白名单类型
	 * @return 
	 */
	 
	 List<BizUserWhiteList> selectByNoAndType(@Param("userTel") String userTel, @Param("type")String type,@Param("rpcode") String rpcode);
	
}

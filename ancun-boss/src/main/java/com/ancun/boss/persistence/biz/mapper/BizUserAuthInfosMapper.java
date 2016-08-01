package com.ancun.boss.persistence.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.boss.business.persistence.module.BizUserAuthInfo;

/**
 * 如果是上海音证宝
 * 查找是否存在鉴权记录
 * 存在与否均存入数据库
 * @author 静好
 *
 */
public interface BizUserAuthInfosMapper {
	
	
	
	/**
	 * 根据电话号码查找是否存在鉴权成功记录
	 * @param userTel
	 * @return
	 */
	List<BizUserAuthInfo> selectAuthExist(@Param("userTel") String userTel, @Param("authResult")String authResult);
	
	/**
	 * 把新的鉴权记录存入数据库
	 * @param userTel
	 * @param authResult
	 * @return
	 */
	int insertAuthInfo(@Param("userTel")String userTel, @Param("authResult") String authResult,@Param("authSource") String authSource);
	
	/**
	 * 更新原有数据的鉴权合法标识
	 * @param userTel
	 * @param authResult
	 * @return
	 */
	int updateAuthInfo(@Param("userTel")String userTel, @Param("authResult") String authResult);

}

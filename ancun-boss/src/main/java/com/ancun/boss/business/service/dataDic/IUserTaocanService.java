package com.ancun.boss.business.service.dataDic;


import com.ancun.boss.business.pojo.taocanInfo.*;

import java.util.HashMap;

/**
 * 
 *用户套餐信息
 * @Created on 2016年04月05日
 * @author zkai
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
public interface IUserTaocanService {
	
	/**
	 * 套餐添加
	 * @param in
	 * @return
	 */
	public void addTaocanInfo(TaocanInfoInput in);

	/**
	 * 套餐更新
	 * @param in
	 * @return
	 */
	public void updateTaoCanInfo(TaocanInfoInput in);
	
	/**
	 * 套餐查询
	 * @param in
	 * @return
	 */
	public TaocanListOutput getTaocanList(TaocanQueryInput in);

	/**
	 * 套餐信息获取
	 * @param tcid
	 * @return
	 */
	public TaocanDetailedOutput getTaocanInfo(Long tcid, String bizno);

	/**
	 * 判断该套餐是否在用，在用不可以删除
	 * @param
	 * @return
	 */
	public void deleteTaocan(TaocanDetailedInput in);

	/**
	 * 返回套餐map
	 * @param
	 * @return
	 */
	public HashMap<String,String> getTaocanAsmap();

	/**
	 * 根据业务编号取得套餐信息
	 * @param bizNo 业务编号
	 * @return
     */
	public TaocanListOutput getTaocanByBizNo(String bizNo);


}

package com.ancun.boss.service.auth;

import java.util.List;

import javax.annotation.Resource;

import com.ancun.boss.business.constant.Constant;
import org.springframework.stereotype.Service;

import com.ancun.boss.business.persistence.mapper.BizProviceMapper;
import com.ancun.boss.business.persistence.module.BizProvice;
import com.ancun.boss.business.persistence.module.BizProviceExample;
import com.ancun.boss.business.persistence.module.BizProviceExample.Criteria;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.common.biz.pojo.common.RegionAreaInfo;
import com.ancun.common.biz.regionarea.RegionAreaInfoBiz;
import com.ancun.core.exception.EduException;
import com.ancun.utils.StringUtil;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 判断业务和手机号码归属地是否同一个地方
 * @author hjl
 *
 * @时间 2016-4-11 上午9:52:28
 */
@Service
public class PhoneBizCheckService {
	
	@Resource
	private BizProviceMapper bizProvinceMapper;
	
	@Resource
	private RegionAreaInfoBiz regionAreaInfoBiz;
	
	public boolean checkPhoneAreaAndBiz(String provinceCode,String phone,String bizNo) {
		BizProviceExample example = new BizProviceExample();
		Criteria cc = example.createCriteria();
		cc.andBizNoEqualTo(bizNo);
		List<BizProvice> listBizProvince = bizProvinceMapper.selectByExample(example);
		// 没有相应的记录，需要进行数据库的配置
		if(null == listBizProvince || listBizProvince.size()==0) {
			throw new EduException(MessageConstant.NO_CONFIG_OF_DB);
		}
		
		// 大于1的记录，说明配置有重复，需要检查
		if(listBizProvince.size() > 1) {
			throw new EduException(MessageConstant.MANY_CONFIG_OF_DB);
		}
		
		// 如果输入省份代码跟业务对应的省份代码不一样，要检查相关配置和输入参数
		BizProvice bizProvice = listBizProvince.get(0);
		String bizRpCode = bizProvice.getRpcode();
		/**
		 * update by zkai
		 * 总部不做归属地鉴权
		 */
		if(!bizRpCode.equals(Constant.ALL_PROVICE_RPCODE)){

			if( !bizRpCode.equals(provinceCode)) {
				throw new EduException(MessageConstant.NOT_THE_SAME_PROVNCE_CODE);
			}
			//根据用户号码获取号码归属地信息Model
			RegionAreaInfo regionAreaInfo = getRegionAreaInfo(phone);
			if(regionAreaInfo==null){
				//抛归属地信息为空异常
				throw new EduException(MessageConstant.REGIONAREA_IS_NULL);
			}
			//用户省份编号为空或者请求的省份编号和用户的省份编号不一致
			String rpCode = regionAreaInfo.getProvinceCode();
			if (StringUtil.isBlank(rpCode)  ||  !provinceCode.equals(rpCode)) {
				//抛用户省份编号为空或者请求的省份编号和用户的省份编号不一致异常
				throw new EduException(MessageConstant.NOT_EQUAL_RPCODE);
			}

			// 如果配置省份代码跟服务查询出来的不一样，也得检查数据库配置和服务省份代码
			if( !bizRpCode.equals(rpCode)) {
				throw new EduException(MessageConstant.NOT_THE_SAME_PROVNCE_CODE_OF_SERVICE);
			}

		}

		return true;
	}
	
	/**
	 * 根据主叫号码取得归属地信息
	 *
	 * @param callfrom
	 * @return
	 */
	private RegionAreaInfo getRegionAreaInfo(String callfrom) {
		return regionAreaInfoBiz.getRegionAreaInfo(callfrom);
	}
}

package com.ancun.common.persistence.mapper.sh;

public interface OtherMapper {
	
	/**
	 * @param userTel
	 * @return
	 */
	Object getOpenSeq(String usertel);
	
	/**
	 * @param tcid
	 * @return
	 */
	Object selectTcType(Long tcid);

}

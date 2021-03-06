package com.ancun.boss.persistence.mapper;

import com.ancun.boss.persistence.model.MarketCheck;
import com.ancun.boss.persistence.model.MarketCheckExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MarketCheckMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int countByExample(MarketCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int deleteByExample(MarketCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String phoneNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int insert(MarketCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int insertSelective(MarketCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    List<MarketCheck> selectByExample(MarketCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    MarketCheck selectByPrimaryKey(String phoneNo);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") MarketCheck record, @Param("example") MarketCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") MarketCheck record, @Param("example") MarketCheckExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MarketCheck record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MARKET_CHECK
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MarketCheck record);
}
package com.ancun.boss.service.workorder.impl;

import com.ancun.boss.constant.BasicConfigConstant;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.ConfigConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.BizSystemUserInfoMapper;
import com.ancun.boss.persistence.biz.BizWorkOrderExchangeMapper;
import com.ancun.boss.persistence.biz.BizWorkOrderMapper;
import com.ancun.boss.persistence.biz.mapper.BizUserRoleMapper;
import com.ancun.boss.persistence.mapper.*;
import com.ancun.boss.persistence.model.*;
import com.ancun.boss.pojo.system.BasicConfigInput;
import com.ancun.boss.pojo.userInfo.UserInfoPojo;
import com.ancun.boss.pojo.workorder.*;
import com.ancun.boss.service.system.IBasicConfigService;
import com.ancun.boss.service.workorder.IWorkOrderService;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.common.biz.email.SendEmail;
import com.ancun.common.biz.sms.SendSMS;
import com.ancun.core.exception.EduException;
import com.ancun.utils.StringUtil;
import com.ancun.utils.TimeUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 工单服务service实现
 *
 * @Created on 2015年10月15日
 * @author 摇光
 * @version 1.0
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class WorkOrderServiceImpl implements IWorkOrderService {

    /** 工单标题 */
    private static final String NOTICE_TITLE = "工单相关";

    /** 处理通知 */
    private static final String HANDLE_NOTICE = "有一个工单需要你处理！工单编号是{0}，来电号码是{1}。";

    /** 完成通知 */
    private static final String COMPLETE_NOTICE = "你的工单已经被处理了！工单编号是{0}，来电号码是{1}。";

    /** 用户工单Mapper */
    @Resource
    private UserWorkOrderInfoMapper userWorkOrderInfoMapper;

    /** 工单查询Mapper */
    @Resource
    private BizWorkOrderMapper bizWorkOrderMapper;

    /** 用户工单流转详细Mapper */
    @Resource
    private UserWorkOrderExchangeDetailInfoMapper detailInfoMapper;

    /** 系统用户表 */
    @Resource
    private SystemUserInfoMapper userInfoMapper;

    /** 系统用户表 */
    @Resource
    private BizWorkOrderExchangeMapper orderExchangeMapper;

    /** 工单回访记录 */
    @Resource
    private WorkOrderVisitRecordMapper vistMapper;

    /** 基础数据 */
    @Resource
    private IBasicConfigService basicConfigService;

	@Resource
	private ImportFailedRecordMapper importMapper;

	@Resource
	private BizSystemUserInfoMapper userMapper;

    /** 系统消息Mapper */
    @Resource
    private SystemNoticeMapper systemNoticeMapper;

    /** 异步线程池 */
    @Resource
    private ThreadPoolTaskExecutor threadPool;

    /** 发送短信 */
    @Resource
    private SendSMS sendSMS;

    /** 发送邮件 */
    @Resource
    private SendEmail sendEmail;

    @Resource
    private BizUserRoleMapper bizUserRoleMapper;

	private String errorMsg = "";

    /** 通知方式 */
    private final String noticeType;
    /** 工单完成通知地址 */
    private final String completeNoticeAddress;

    /**
     * 初期化系统变量
     */
    @Autowired
    public WorkOrderServiceImpl(BaseDataServiceImpl baseDataService) {
        // 通知方式
        noticeType = baseDataService.getConfigMessage(null, ConfigConstant.NOTICE_TYPE);
        // 工单完成通知地址
        completeNoticeAddress = baseDataService.getConfigMessage(null, ConfigConstant.WORK_ORDER_COMPLETE_NOTICE_ADDRESS);
    }

    /**
     * 更新用户工单信息
     * 1 根据用户工单Id取得用户工单信息
     *      ① 取得成功 更新用户工单信息
     *      ② 未取得
     *          Ⅰ 新增用户工单信息
     *          Ⅱ 新增用户工单流转详细信息
     *
     * @param input 用户工单参数信息
     * @return  影响条数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateUserWorkOrderInfo(GatherWorkOrderInfomeInput input) {

        // 准备更新数据
        UserWorkOrderInfo userWorkOrderInfo = prepareUserWorkOrderInfo(input);

        // 更新数据
        if (!isAddAction(input.getAction())) {
            userWorkOrderInfo.setUserno(null);
            updateUserWorkOrder(userWorkOrderInfo);
            return userWorkOrderInfo.getOrderno();
        }

        // 插入用户工单信息
        userWorkOrderInfoMapper.insertSelective(userWorkOrderInfo);

//        // 准备用户工单流转详细信息
//        UserWorkOrderExchangeDetailInfo detailInfo = prepareExchangeDetailInfo(input);
//
//        // 新增用户工单流转详细信息
//        detailInfoMapper.insertSelective(detailInfo);

        return userWorkOrderInfo.getOrderno();
    }

    /**
     * 流转用户工单信息
     *
     * @param input 用户工单流转详细参数信息
     * @return  工单处理信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public WorkOrderDealOutput transformUserWorkOrder(final TransformWorkOrderInfoInput input) {

        // 准备工单流转详细信息
        UserWorkOrderExchangeDetailInfo detailInfo = prepareExchangeDetailInfo(input);

        // 更新工单流转详细信息表
        long id = updateExchangeDetailInfo(detailInfo);
        detailInfo.setId(id);

        // 准备工单信息
        UserWorkOrderInfo info = prepareUserWorkOrderInfo(input);
        // 更新工单信息
        updateUserWorkOrder(info);

        // 异步通知用户
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                asycNoticeUser(input);
            }
        });

        return createWorkOrderDealOutput(detailInfo);
    }


    /**
     * 查询工单List
     *
     * @param input 用户工单参数信息
     * @return      用户工单信息
     */
	@Override
	public List<WorkOrderInfoPojo> queryWorkOrderInfoList(
			WorkOrderQueryInput input) {


        /**
         * update by zkai
         * 修改用户权限:
         * 1.超级管理员可以查看所有的工单信息 2.客服经理可以查看所有的工单信息 3.普通用户只能查看自己的工单、转给自己的工单
         */

        // 原逻辑
//        SystemUserInfo userInfo = userInfoMapper.selectByPrimaryKey(input.getUserno());
//		input.setDataAccess(userInfo.getOrgno());

        // 修改后的 ----begin-----

        String roleno = null ; // 角色编号
        String superman = null ; // 超级管理员(YES/NO)
        // 查询登入用户权限信息
        System.out.println(input.getUserno());
        List<SystemRoleInfo> systemRoleInfos = bizUserRoleMapper.queryUserRoleList(input.getUserno()); // input.getUserno() 登入用户编号
        if(systemRoleInfos.size() != 1){
            throw new EduException(MessageConstant.DATA_ERROR);
        }else {
            roleno = systemRoleInfos.get(0).getRoleno();
            superman = systemRoleInfos.get(0).getSuperman();
        }
        if(StringUtil.equals(roleno,BizRequestConstant.CUSTOM_SERVICE_MANAGER) || StringUtil.equals(superman,BizRequestConstant.SUPERMAN_YES)){
            input.setDataAccess(BizRequestConstant.DATA_ACCESS_ALL);
        }else {
            input.setDataAccess(BizRequestConstant.DATA_ACCESS_PART);
        }
        //  ----end------


		// 取得工单信息
		List<WorkOrderInfoPojo> orderList = bizWorkOrderMapper.selectOrderList(input);
		for (WorkOrderInfoPojo order:orderList) {
	        UserWorkOrderExchangeDetailInfoExample example = new UserWorkOrderExchangeDetailInfoExample();
	        example.createCriteria().andOrdernoEqualTo(order.getOrderno());
	        List<UserWorkOrderExchangeDetailInfo> infos = detailInfoMapper.selectByExample(example);

	        order.setOrderExchangSize(infos.size());
			order.setLoginUserNo(input.getUserno());
		}

		return orderList;
	}

    /**
     * 工单信息导入
     *
     * @param input 用户工单参数信息
     * @return      工单流转信息
     */
	@Override
	public String importWorkOrderInfo(WorkOrderInfoListInput input) {

		String result  = "";
		List<GatherWorkOrderInfomeInput> list = new ArrayList<GatherWorkOrderInfomeInput>();

		for (GatherWorkOrderInfomeInput order:input.getWorkOrderList()) {
			errorMsg = "";
			if (validateWorkOrderInfoForImport(order, "1", input.getUserno())){
				list.add(order);
			} else {
				insertErrorMsg(input.getUserno(), errorMsg);
			}
		}

		if (list.size()>0) {
			for (GatherWorkOrderInfomeInput order:list) {
				order.setAction(BizRequestConstant.TO_ADD);
				updateUserWorkOrderInfo(order);
			}
		}

		result = "导入成功条数："+ list.size() + "  失败条数:" + (input.getWorkOrderList().size() - list.size());

		return result;
	}

    /**
     * 工单详细画面数据取得
     *
     * @param input 用户工单参数信息
     * @return      工单详细画面数据
     */
	@Override
	public WorkOrderInfoOutput queryWorkOrder(WorkOrderQueryInput input) {

		WorkOrderInfoOutput output = new WorkOrderInfoOutput();

    	// 工单详细取得
    	WorkOrderInfoPojo order = queryWorkOrderInfo(input);

    	output.setOrderInfo(order);

    	// 工单流转信息取得
    	List<WorkOrderExchangePojo> exchangeList = queryWorkOrderExchangeList(input);

    	output.setExchangeList(exchangeList);

    	// 工单回访信息取得
    	List<WorkOrderVisitRecord> visitList = queryWorkOrderVisitList(input);

    	output.setVisitList(visitList);

		return output;
	}

    /**
     * 工单处理画面数据取得
     *
     * @param input 用户工单参数信息
     * @return      工单处理画面数据
     */
	@Override
	public WorkOrderInfoOutput queryWorkorderForHandle(WorkOrderQueryInput input) {

		WorkOrderInfoOutput output = new WorkOrderInfoOutput();

    	// 工单详细取得
    	WorkOrderInfoPojo order = queryWorkOrderInfo(input);

    	output.setOrderInfo(order);

    	// 工单流转信息取得
    	List<WorkOrderExchangePojo> exchangeList = queryWorkOrderExchangeList(input);

    	output.setExchangeList(exchangeList);

    	// 登陆用户信息取得
    	UserInfoPojo user = userMapper.selectUser(input.getUserno());

    	output.setOrgName(user.getOrgname());
    	output.setUserName(user.getUsername());

		return output;
	}

    /**
     * 回访记录信息登陆
     *
     * @param input 处理记录参数信息
     * @return
     */
	@Override
	public void insertWorkOrderVisitRecord(WorkOrderVisitRecordInput input) {

		// 设置回访信息
		WorkOrderVisitRecord record = new WorkOrderVisitRecord();

		record.setOrderno(input.getOrderno());
		record.setVisitorName(input.getVisitorName());
		record.setVisitTime(input.getVisitTime());
		record.setVisitContent(input.getVisitContent());

		// 登陆数据
		vistMapper.insert(record);

	}

    /**
     * 初期化用户工单详细界面
     *
     * @return  用户工单详细
     */
    @Override
    public WorkOrderDetailOutput workOrderDetail(WorkOrderDetailInput input) {

        WorkOrderDetailOutput output = new WorkOrderDetailOutput();

        // 更新操作时取得工单详细
        if (!isAddAction(input.getAction())) {
            // 用户工单详细
            output.setInfo(queryWorkOrder(input.getOrderno()));
        }

        // 下拉框列表
        output.setSelects(basicConfigService.queryByMoudlecodes(input.getMoudlecodes(), input.getUserno()));

        return output;
    }

    /**
     * 工单内容校验
     *
     * @return  错误信息
     */
	@Override
	public String validateWorkOrderInfo(GatherWorkOrderInfomeInput input) {

    	errorMsg = "";

    	if ( !validateWorkOrderInfoForImport(input, "2", input.getUserno())) {
    		return errorMsg;
    	}

		return "";
	}

    /**
     * 判断是否新增操作
     *
     * @param action 编辑类型
     * @return true(新增)/false(修改)
     */
    private boolean isAddAction(String action){
        return Objects.equal(BizRequestConstant.TO_ADD, action);
    }

    /**
     * 生成用户工单编号
     *
     * @param input 用户工单参数信息
     * @return  用户工单编号
     */
    private String createOrderNo(GatherWorkOrderInfomeInput input) {
        return Joiner.on("_").skipNulls().join(TimeUtils.getSysTimeLong(), input.getUserno());
    }

    /**
     * 根据用户工单ID判断该用户工单是否存在
     *
     * @param orderno   用户工单ID
     * @return  true(存在)/false(不存在)
     */
    private boolean exist(String orderno) {

        UserWorkOrderInfoExample example = new UserWorkOrderInfoExample();
        example.createCriteria().andOrdernoEqualTo(orderno);

        List<UserWorkOrderInfo> workOrderInfos = userWorkOrderInfoMapper.selectByExample(example);

        // 取得用户工单信息
        return workOrderInfos.size() > 0;
    }

    /**
     * 根据用户工单参数信息生成用户工单实体用于更新用户工单表用
     *
     * @param input 用户工单参数信息
     * @return      用户工单信息
     */
    private UserWorkOrderInfo prepareUserWorkOrderInfo(GatherWorkOrderInfomeInput input){

        UserWorkOrderInfo info = new UserWorkOrderInfo();

        // 创建人（用户编号）
        info.setUserno(input.getUserno());
        // 创建时间
        info.setCreateTime(new Date());
        // 当前任务人
        info.setCurrentTaskUserno(input.getUserno());
        // 工单状态（1:未完成;2:完成）
        info.setStatus(BizRequestConstant.UNCOMPLETE);
        // 工单完成时间
//        info.setCompleteTime(DateUtil.convertStringToDate(DateUtil.DEFAULT_FORMAT_DB, input.getCompleteTime()));
        // 工单编号
        info.setOrderno(input.getOrderNo());
        // 如果是新增操作则生成工单编号
        if (isAddAction(input.getAction()) && StringUtil.isEmpty(input.getOrderNo())) {
            info.setOrderno(createOrderNo(input));
        }
        // 用户名称
        info.setUsername(input.getUserName());
        // 来电号码
        info.setCallPhone(input.getCallPhone());
        // 设备号码
        info.setEquipPhone(input.getEquipPhone());
        // 工单类型
        info.setOrderType(input.getOrderType());
        // 工单来源
        info.setOrderSource(input.getOrderSource());
        // 投诉来源
        info.setComplaintSource(input.getComplaintSource());
        // 投诉类型
        info.setComplaintType(input.getComplaintType());
        // 投诉属性
        info.setComplaintProperty(input.getComplaintProperty());
        // 要求完成时间
        info.setRctime(input.getRcTime());
        // 服务请求ID
        info.setRequestid(input.getRequestId());
        // 工单来源人
        info.setOrderFrom(input.getOrderFrom());
        // 工单内容
        info.setContent(input.getContent());
        // 业务
        info.setBusiness(input.getBusiness());

        return info;
    }

    /**
     * 根据用户工单参数信息生成用户工单流转详细信息
     *
     * @param input 用户工单参数信息
     * @return      用户工单流转详细信息
     */
    private UserWorkOrderExchangeDetailInfo prepareExchangeDetailInfo(GatherWorkOrderInfomeInput input) {

        UserWorkOrderExchangeDetailInfo info = new UserWorkOrderExchangeDetailInfo();

        // 工单编号
        info.setOrderno(input.getOrderNo());
        // 如果是新增操作则生成工单编号
        if (isAddAction(input.getAction())) {
            info.setOrderno(createOrderNo(input));
        }
        // 处理时间
        info.setHandleTime(new Date());
        // 部门编号
        String orgno = userInfoMapper.selectByPrimaryKey(input.getUserno()).getOrgno();
        info.setOrgno(orgno);
        // 工单处理用户编号
        info.setHandleUserno(input.getUserno());
        // 状态
        info.setStatus(BizRequestConstant.UNCOMPLETE);

        return info;
    }

    /**
     * 根据用户工单流转详细参数信息生成用户工单流转详细信息
     *
     * @param input 用户工单流转详细参数信息
     * @return      用户工单流转详细信息
     */
    private UserWorkOrderExchangeDetailInfo prepareExchangeDetailInfo(TransformWorkOrderInfoInput input) {

        UserWorkOrderExchangeDetailInfo info = new UserWorkOrderExchangeDetailInfo();
        // 工单详细ID
        info.setId(input.getId());
        // 工单编号
        info.setOrderno(input.getOrderno());
        // 处理时间
        info.setHandleTime(new Date());
        // 部门编号
        String orgno = userInfoMapper.selectByPrimaryKey(input.getUserno()).getOrgno();
        info.setOrgno(orgno);
        // 工单处理用户编号
        info.setHandleUserno(input.getUserno());
        // 处理回复内容
        info.setHandleContent(input.getHandleContent());
        // 状态
        info.setStatus(BizRequestConstant.UNCOMPLETE);

        // 如果工单状态完成则设置完成时间
        if (isComplete(input)) {
            // 完成时间
            info.setFinishTime(new Date());
            // 完成
            info.setStatus(BizRequestConstant.COMPLETE);
        } else {
            // 下一个任务人
            info.setNextUserno(input.getHandleUserno());
        }

        return info;
    }

    /**
     * 根据用户工单流转详细参数信息判断工单是否完成
     *
     * @param input 用户工单流转详细参数信息
     * @return  true(完成)/false(未完成)
     */
    private boolean isComplete(TransformWorkOrderInfoInput input){
        return Objects.equal(BizRequestConstant.COMPLETE, input.getStatus());
    }

    /**
     * 根据用户工单流转详细参数信息生成用户工单实体用于更新用户工单表用
     *
     * @param input 用户工单流转详细参数信息
     * @return      用户工单信息
     */
    private UserWorkOrderInfo prepareUserWorkOrderInfo(TransformWorkOrderInfoInput input){

        UserWorkOrderInfo info = new UserWorkOrderInfo();
        // 工单编号
        info.setOrderno(input.getOrderno());
        // 当前任务人
        info.setCurrentTaskUserno(input.getHandleUserno());

        info.setStatus(input.getStatus());

        // 如果工单状态完成则设置完成时间
        if (isComplete(input)) {
            info.setCompleteTime(new Date());
        }

        return info;
    }

    /**
     * 更新用户工单信息表
     *
     * @param info 用户工单信息
     * @return  影响条数
     */
    private int updateUserWorkOrder(UserWorkOrderInfo info) {

        UserWorkOrderInfoExample example = new UserWorkOrderInfoExample();
        example.createCriteria().andOrdernoEqualTo(info.getOrderno());

        return userWorkOrderInfoMapper.updateByExampleSelective(info, example);
    }

    /**
     * 更新用户工单流转信息表
     *
     * @param info 用户工单信息
     * @return  影响条数
     */
    private long updateExchangeDetailInfo(UserWorkOrderExchangeDetailInfo info) {

        // 影响条数
        long id = 0;

        // 如果ID存在
        if (info.getId() != null && info.getId() != 0) {
            detailInfoMapper.updateByPrimaryKeySelective(info);
            id = info.getId();
        } else {
            detailInfoMapper.insertSelective(info);
            UserWorkOrderExchangeDetailInfoExample example = new UserWorkOrderExchangeDetailInfoExample();
            example.createCriteria()
                    .andOrdernoEqualTo(info.getOrderno())
                    .andHandleUsernoEqualTo(info.getHandleUserno());
            List<UserWorkOrderExchangeDetailInfo> infos = detailInfoMapper.selectByExample(example);
            id = infos.get(0).getId();
        }

        return id;
    }

    /**
     * 查询工单
     *
     * @param input 用户工单参数信息
     * @return      用户工单详细
     */
	private WorkOrderInfoPojo queryWorkOrderInfo(WorkOrderQueryInput input) {

		// 取得工单详细
		WorkOrderInfoPojo orderInfo = bizWorkOrderMapper.selectOrderInfo(input);

		return orderInfo;
	}


    /**
     * 查询工单流转信息
     *
     * @param input 用户工单参数信息
     * @return      工单流转信息
     */
	private List<WorkOrderExchangePojo> queryWorkOrderExchangeList(WorkOrderQueryInput input) {

		List<WorkOrderExchangePojo> exchangeList = orderExchangeMapper.selectExchangeList(input);

		return exchangeList;
	}

    /**
     * 查询工单回访记录
     *
     * @param input 用户工单参数信息
     * @return      工单流转信息
     */
	private List<WorkOrderVisitRecord> queryWorkOrderVisitList(WorkOrderQueryInput input){

		WorkOrderVisitRecordExample example = new WorkOrderVisitRecordExample();
		WorkOrderVisitRecordExample.Criteria c = example.createCriteria();

		c.andOrdernoEqualTo(input.getOrderNo());
		example.setOrderByClause("ID desc");

		List<WorkOrderVisitRecord> list = vistMapper.selectByExample(example);

		return list;
	}

    /**
     * 服务工单导入内容校验
     *
     * @param input
     * @return
     * @throws EduException
     */
    private boolean validateWorkOrderInfoForImport(GatherWorkOrderInfomeInput input, String importFlg, String userno) {

    	boolean result = true;
    	if (importFlg.equals("1")) {
    		errorMsg = "来电号码为" + input.getCallPhone() + "的数据:";
    	}

    	BasicConfigInput basicConfigInput = new BasicConfigInput();

    	if (StringUtil.isEmpty(input.getUserName())) {
    		result = false;
    		errorMsg += "用户名称不能为空。";
    	} else if (input.getUserName().length() > 12) {
    		result = false;
    		errorMsg += "用户名称长度不能超出12位。";
    	}

    	if (StringUtil.isEmpty(input.getCallPhone())) {
    		result = false;
    		errorMsg += "来电号码不能为空。";
    	} else if (input.getCallPhone().length() > 20) {
    		result = false;
    		errorMsg += "来电号码长度不能超出20位。";
    	} else if (!input.getCallPhone().matches("^(((1([3,4,5,7,8][0-9]))\\d{8})|(0\\d{2}-?\\d{8})|(0\\d{3}-?\\d{7,8}))$")) {
    		errorMsg += "来电号码格式有误。";
    		result = false;
    	}

    	if (!StringUtil.isEmpty(input.getEquipPhone()) && input.getEquipPhone().length() > 20) {
    		result = false;
    		errorMsg += "设备号码长度不能超出12位。";
    	}

    	if (StringUtil.isEmpty(input.getOrderType())) {
    		result = false;
    		errorMsg += "工单类型不能为空。";
    	} else if (importFlg.equals("2") && input.getOrderType().length() > 1) {
    		result = false;
    		errorMsg += "工单类型长度不能超出1位。";
    	} else if (importFlg.equals("1")) {
        	basicConfigInput.setMoudlecode(BasicConfigConstant.ORDER_TYPE);
        	basicConfigInput.setUserno(userno);
        	List<SystemBasicConfig> orderTypeList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        	boolean flg = false;
        	for (SystemBasicConfig order:orderTypeList) {
        		if (order.getName().equals(input.getOrderType())) {
        			input.setOrderType(order.getCode());
        			flg = true;
        			break;
        		}
        	}
    		if (!flg) {
    			errorMsg += "工单类型不存在。";
        		result = false;
    		}

    	}

    	if (StringUtil.isEmpty(input.getOrderSource())) {
    		result = false;
    		errorMsg += "工单来源不能为空。";
    	} else if (importFlg.equals("2") && input.getOrderSource().length() > 1) {
    		result = false;
    		errorMsg += "工单来源长度不能超出1位。";
    	} else if (importFlg.equals("1")) {
        	basicConfigInput.setMoudlecode(BasicConfigConstant.ORDER_SOURCE);
        	basicConfigInput.setUserno(userno);
        	List<SystemBasicConfig> orderSourceList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        	boolean flg = false;
        	for (SystemBasicConfig order:orderSourceList) {
        		if (order.getName().equals(input.getOrderSource())) {
        			input.setOrderSource(order.getCode());
        			flg = true;
        			break;
        		}
        	}
    		if (!flg) {
    			errorMsg += "工单来源不存在。";
        		result = false;
    		}

    	}

    	if (!StringUtil.isEmpty(input.getOrderType()) && input.getOrderType().equals("2") && StringUtil.isEmpty(input.getComplaintSource())) {
    		result = false;
    		errorMsg += "投诉来源不能为空。";
    	}

    	if (!StringUtil.isEmpty(input.getComplaintSource())) {

    		if (input.getComplaintSource().length() > 12) {
        		result = false;
        		errorMsg += "投诉来源长度不能超出12位。";
        	}
	    	if (importFlg.equals("1") && input.getOrderType().equals("2")) {
	            	basicConfigInput.setMoudlecode(BasicConfigConstant.COMP_SOURCE);
	            	basicConfigInput.setUserno(userno);
	            	List<SystemBasicConfig> orderSourceList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
	            	boolean flg = false;
	            	for (SystemBasicConfig order:orderSourceList) {
	            		if (order.getName().equals(input.getComplaintSource())) {
	            			input.setComplaintSource(order.getCode());
	            			flg = true;
	            			break;
	            		}
	            	}
	        		if (!flg) {
	        			errorMsg += "投诉来源不存在。";
	            		result = false;
	        		}
	
	        	}
    	}

    	if (StringUtil.isEmpty(input.getComplaintType())) {
    		result = false;
    		errorMsg += "投诉类型不能为空。";
    	} else if (importFlg.equals("2") && input.getComplaintType().length() > 1) {
    		result = false;
    		errorMsg += "投诉类型长度不能超出1位。";
    	} else if (importFlg.equals("1")) {
        	basicConfigInput.setMoudlecode(BasicConfigConstant.COMP_TYPE);
        	basicConfigInput.setUserno(userno);
        	List<SystemBasicConfig> orderSourceList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        	boolean flg = false;
        	for (SystemBasicConfig order:orderSourceList) {
        		if (order.getName().equals(input.getComplaintType())) {
        			input.setComplaintType(order.getCode());
        			flg = true;
        			break;
        		}
        	}
    		if (!flg) {
    			errorMsg += "投诉类型不存在。";
        		result = false;
    		}

    	}

    	if (StringUtil.isEmpty(input.getComplaintProperty())) {
    		result = false;
    		errorMsg += "投诉属性不能为空。";
    	} else if (importFlg.equals("2") && input.getComplaintProperty().length() > 1) {
    		result = false;
    		errorMsg += "投诉属性长度不能超出1位。";
    	} else if (importFlg.equals("1")) {
        	basicConfigInput.setMoudlecode(BasicConfigConstant.COMP_PRO);
        	basicConfigInput.setUserno(userno);
        	List<SystemBasicConfig> orderSourceList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        	boolean flg = false;
        	for (SystemBasicConfig order:orderSourceList) {
        		if (order.getName().equals(input.getComplaintProperty())) {
        			input.setComplaintProperty(order.getCode());
        			flg = true;
        			break;
        		}
        	}
    		if (!flg) {
    			errorMsg += "投诉属性不存在。";
        		result = false;
    		}

    	}

    	if (!StringUtil.isEmpty(input.getRcTime()) && input.getRcTime().length() > 24) {

    		errorMsg += "要求完成时间长度不能超出24位。";
    		result = false;
    	}

    	if (!StringUtil.isEmpty(input.getRequestId()) && input.getRequestId().length() > 20) {

    		errorMsg += "请求ID长度不能超出20位。";
    		result = false;
    	}

    	if (!StringUtil.isEmpty(input.getOrderFrom()) && input.getOrderFrom().length() > 12) {

    		errorMsg += "工单来源人长度不能超出12位。";
    		result = false;
    	}

    	if (StringUtil.isEmpty(input.getContent())) {

    		errorMsg += "工单内容不能为空。";
    		result = false;
    	} else if (input.getContent().length() > 512) {

    		errorMsg += "工单内容长度不能超过512位。";
    		result = false;
    	}

    	if (StringUtil.isEmpty(input.getBusiness())) {
    		result = false;
    		errorMsg += "业务不能为空。";
    	} else if (importFlg.equals("2") && input.getBusiness().length() > 32) {
    		result = false;
    		errorMsg += "业务长度不能超出32位。";
    	} else if (importFlg.equals("1")) {
        	basicConfigInput.setMoudlecode(BasicConfigConstant.BIZ_NAME);
        	basicConfigInput.setUserno(userno);
        	List<SystemBasicConfig> orderSourceList =  basicConfigService.queryListByMoudleCode(basicConfigInput);
        	boolean flg = false;
        	for (SystemBasicConfig order:orderSourceList) {
        		if (order.getName().equals(input.getBusiness())) {
        			input.setBusiness(order.getCode());
        			flg = true;
        			break;
        		}
        	}
    		if (!flg) {
    			errorMsg += "业务不存在。";
        		result = false;
    		}

    	}

    	if (importFlg.equals("1")) {
    		if (StringUtil.isEmpty(input.getUserno())) {
        		result = false;
        		errorMsg += "用户编号不能为空。";
    		} else if (input.getUserno().length() > 32) {
        		result = false;
        		errorMsg += "用户编号不能超出32位。";
    		} else {
    	    	// 用户编号校验
    	    	SystemUserInfoExample example = new SystemUserInfoExample();
    	    	SystemUserInfoExample.Criteria c = example.createCriteria();

    	    	c.andUsernoEqualTo(input.getUserno());
    	    	List<SystemUserInfo> list = userInfoMapper.selectByExample(example);

    	    	// 用户编号不存在时，提示错误
    	    	if (list == null || list.size() == 0) {
    	    		result = false;
    	    		errorMsg += "用户编号不存在。";
    	    	}
    		}
    	}

    	return result;
    }

    /**
     * 登陆错误信息
     *
     * @param userno
     * @param msg
     * @return
     * @throws EduException
     */
    private void insertErrorMsg(String userno, String msg) {

		ImportFailedRecord record = new ImportFailedRecord();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		record.setMenuno("c2009c03-7791-40b5-b570-e88cec7a3f17");
		record.setMenuname("工单服务管理");
		record.setFuncname("数据导入");
		record.setImportTime(df.format(new Date()));
		record.setFailedRemark(msg);
		record.setUserno(userno);

		importMapper.insert(record);
    }

    /**
     * 取得用户工单详细信息
     *
     * @param orderno   用户工单编号
     * @return 用户工单详细信息
     */
    private WorkOrderDetail queryWorkOrder(String orderno){

        // 构造条件
        UserWorkOrderInfoExample example = new UserWorkOrderInfoExample();

        example.createCriteria().andOrdernoEqualTo(orderno);

        // 取得用户工单详细
        List<UserWorkOrderInfo> orders = userWorkOrderInfoMapper.selectByExample(example);

        // 用户工单不存在时，提示错误
        if (orders == null || orders.size() == 0) {
            throw new EduException(MessageConstant.WORK_ORDER_NOT_EXIST);
        }
        // 用户工单出现多条
        else if (orders.size() > 1) {
            throw new EduException(MessageConstant.WORK_ORDER_EXIST_MORE);
        }

        return orderInfoToWorkOrderDetail(orders.get(0));
    }

    /**
     * 将用户工单信息变为用户工单详细
     *
     * @param info 用户工单信息
     * @return  用户工单详细
     */
    private WorkOrderDetail orderInfoToWorkOrderDetail(UserWorkOrderInfo info){

        WorkOrderDetail detail = new WorkOrderDetail();

        detail.setId(info.getId());

        detail.setOrderno(info.getOrderno());

        detail.setBusiness(info.getBusiness());

        detail.setCallPhone(info.getCallPhone());

        detail.setComplaintProperty(info.getComplaintProperty());

        detail.setComplaintSource(info.getComplaintSource());

        detail.setComplaintType(info.getComplaintType());

        detail.setContent(info.getContent());

        detail.setEquipPhone(info.getEquipPhone());

        detail.setOrderFrom(info.getOrderFrom());

        detail.setOrderType(info.getOrderType());

        detail.setOrderSource(info.getOrderSource());

        detail.setRctime(info.getRctime());

        detail.setRequestid(info.getRequestid());

        detail.setUsername(info.getUsername());

        return detail;
    }

    /**
     * 创建工单处理结果
     *
     * @param info  工单处理详细
     * @return  工单处理结果
     */
    private WorkOrderDealOutput createWorkOrderDealOutput(final UserWorkOrderExchangeDetailInfo info) {

        WorkOrderDealOutput output = new WorkOrderDealOutput();

        output.setId(info.getId().toString());

        output.setHandleContent(info.getHandleContent());

        output.setHandleTime(info.getHandleTime());

        output.setStatus(info.getStatus());

        BasicConfigInput input = new BasicConfigInput();
        input.setMoudlecode(BasicConfigConstant.ORG);
        List<SystemBasicConfig> orgs = basicConfigService.queryListByMoudleCode(input);
        SystemBasicConfig config = Iterators.find(orgs.iterator(), new Predicate<SystemBasicConfig>() {
            @Override
            public boolean apply(SystemBasicConfig input) {
                return Objects.equal(input.getCode(), info.getOrgno());
            }
        });
        output.setOrgName(config.getName());

        // 取得用户信息
        SystemUserInfo userInfo = userInfoMapper.selectByPrimaryKey(info.getHandleUserno());
        output.setUserName(userInfo.getUsername());

        return output;
    }

    /**
     * 创建提醒信息
     *
     * @param userno    被提醒人
     * @param content   通知内容
     * @return          提醒信息
     */
    private SystemNotice createNotic(String userno, String content){

        SystemNotice notice = new SystemNotice();

        notice.setTitle(NOTICE_TITLE);
        notice.setCommitTime(new Date());
        notice.setType(noticeType);
        notice.setContent(content);
        notice.setUserno(userno);
        notice.setReadLabel(BizRequestConstant.NOTICE_UNREAD);

        return notice;
    }

    /**
     * 通知用户
     *
     * @param notice        通知实体
     * @param completeFlg   工单是否完成
     */
    private void noticeUser(final SystemNotice notice, final boolean completeFlg){
        // 取得用户信息
        final SystemUserInfo userInfo = userInfoMapper.selectByPrimaryKey(notice.getUserno());

        // 如果是短信通知或者短信和邮件都通知
        if (noticeAllorSMS(notice.getType())) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    sendSMS.send(userInfo.getContactphone(), notice.getContent(), "0", true);
                }
            });
        }

        // 如果是邮件通知或者短信和邮件都通知
        if (noticeAllorEmail(notice.getType())) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String noticeAddress = userInfo.getEmail();
                    if (completeFlg) {
                        noticeAddress = completeNoticeAddress;
                    }
                    sendEmail.send(noticeAddress, notice.getTitle(), notice.getContent(), null, true);
                }
            });
        }

    }

    /**
     * 如果是短信和邮件通知或者是短信通知
     *
     * @param type  通知类型
     * @return      判断结果
     */
    private boolean noticeAllorSMS (String type){
        return Objects.equal(BizRequestConstant.NOTICE_TYPE_ALL, type)
                || Objects.equal(BizRequestConstant.NOTICE_TYPE_SMS, type);
    }

    /**
     * 如果是短信和邮件通知或者是邮件通知
     *
     * @param type  通知类型
     * @return      判断结果
     */
    private boolean noticeAllorEmail (String type){
        return Objects.equal(BizRequestConstant.NOTICE_TYPE_ALL, type)
                || Objects.equal(BizRequestConstant.NOTICE_TYPE_EMAIL, type);
    }

    /**
     * 通知用户
     *
     * @param input 通知实体
     */
    private void asycNoticeUser(TransformWorkOrderInfoInput input){

        // 设置工单编号条件
        UserWorkOrderInfoExample example = new UserWorkOrderInfoExample();
        example.createCriteria().andOrdernoEqualTo(input.getOrderno());
        // 取得数据
        List<UserWorkOrderInfo> infos = userWorkOrderInfoMapper.selectByExample(example);

        // 工单数据不存在
        if (infos.size() <= 0) {
            throw new EduException(MessageConstant.WORK_ORDER_NOT_EXIST);
        }
        else if (infos.size() > 1) {
            throw new EduException(MessageConstant.WORK_ORDER_EXIST_MORE);
        }

        // 通知内容
        String content = "";
        // 被通知人
        String userNo = "";
        // 工单实体
        UserWorkOrderInfo info = infos.get(0);
        // 工单完成状态
        boolean completeFlg = false;

        // 插入消息提醒
        if (!Strings.isNullOrEmpty(input.getHandleUserno())) {
            // 下一个处理人
            userNo = input.getHandleUserno();
            // 通知内容
            content = MessageFormat.format(HANDLE_NOTICE, info.getOrderno(), info.getCallPhone());
        }
        // 工单已经完全处理结束
        else {
            // 创建人
            userNo = info.getUserno();
            // 通知内容
            content = MessageFormat.format(COMPLETE_NOTICE, info.getOrderno(), info.getCallPhone());
            // 工单完成
            completeFlg = true;
        }

        // 发送通知
        final SystemNotice notice = createNotic(userNo, content);
        systemNoticeMapper.insertSelective(notice);
        // 发送提醒
        this.noticeUser(notice, completeFlg);
    }

}

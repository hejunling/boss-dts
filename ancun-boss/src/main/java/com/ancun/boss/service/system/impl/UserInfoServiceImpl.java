package com.ancun.boss.service.system.impl;

import com.ancun.boss.constant.BasicConfigConstant;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.BizSystemUserInfoMapper;
import com.ancun.boss.persistence.mapper.*;
import com.ancun.boss.persistence.model.*;
import com.ancun.boss.pojo.userInfo.*;
import com.ancun.boss.service.system.IUserInfoService;
import com.ancun.boss.util.PyUtil;
import com.ancun.core.exception.EduException;
import com.ancun.utils.DESUtils;
import com.ancun.utils.MD5Utils;
import com.ancun.utils.StringUtil;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理业务实现类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/21
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Resource
    private SystemUserInfoMapper systemUserInfoMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private BizSystemUserInfoMapper bizSystemUserInfoMapper;

    @Resource
    private UserDatapermissionMapper userDatapermissionMapper;

    @Resource
    private SystemRoleInfoMapper systemRoleInfoMapper;

    @Resource
    private SystemBasicConfigMapper systemBasicConfigMapper;


    /**
     * 系统基础数据配置mapper
     */
    @Resource
    private SystemBasicConfigMapper configMapper;

    @Override
    public UserInfoListOutput queryUserList(UserInfoListInput input)
            throws EduException {

        List<UserInfoPojo> userInfoPojoList = bizSystemUserInfoMapper
                .selectUserList(input);

        UserInfoListOutput output = new UserInfoListOutput();
        output.setUserinfolist(userInfoPojoList);
        output.setPageinfo(input.getPage());

        return output;
    }

    @Override
    public UserInfoOutput queryUserInfo(String userno) throws EduException {
        if (StringUtil.isEmpty(userno)) {
            throw new EduException(MessageConstant.USER_NO_MUST_BE_NOT_EMPTY);
        }

        SystemUserInfo systemUserInfo = systemUserInfoMapper
                .selectByPrimaryKey(userno);

        UserInfoOutput output = new UserInfoOutput();
        output.setUserinfo(systemUserInfo);

        // 用户角色
        List<SystemRoleInfo> userRoleList = setUserRoles(userno);
        output.setRolelist(userRoleList);


        //用户数据权限
        List<SystemBasicConfig> datapermissionList = setDataPermissions(userno);
        output.setDatapermissionlist(datapermissionList);

        return output;
    }

    /**
     * 设置用户已选择角色
     *
     * @param userno
     * @return
     */
    private List<SystemRoleInfo> setUserRoles(String userno) {
        List<SystemRoleInfo> systemRoleInfoList = queryRoleInfoList();
        if (systemRoleInfoList == null || systemRoleInfoList.size() <= 0) {
            throw new EduException(MessageConstant.SYSTEM_ROLE_IS_NULL);
        }
        List<UserRole> roleInfoList = queryUserRoles(userno);
        if (roleInfoList == null || roleInfoList.size() <= 0) {
            return systemRoleInfoList;
        }

        for (SystemRoleInfo systemRoleInfo : systemRoleInfoList) {
            for (UserRole userRole : roleInfoList) {
                if (systemRoleInfo.getRoleno().equals(userRole.getRoleno())) {
                    systemRoleInfo.setIschecked(true);
                }
            }
        }

        return systemRoleInfoList;
    }

    /**
     * 设置用户已选择 数据权限
     *
     * @param userno
     * @return
     */
    private List<SystemBasicConfig> setDataPermissions(String userno) {
        List<SystemBasicConfig> systemBasicConfigList = querySystemBasicConfigs();
        if (systemBasicConfigList == null || systemBasicConfigList.size() <= 0) {
            throw new EduException(MessageConstant.SYSTEM_DATA_PERMISSION_IS_NULL);
        }

        List<UserDatapermission> userDatapermissionList = queryUserDataPermissions(userno);
        if (userDatapermissionList == null || userDatapermissionList.size() <= 0) {
            return systemBasicConfigList;
        }

        for (SystemBasicConfig systemBasicConfig : systemBasicConfigList) {
            for (UserDatapermission userDatapermission : userDatapermissionList) {
                if (systemBasicConfig.getBasicno().equals(userDatapermission.getBasicno())) {
                    systemBasicConfig.setIschecked(true);//已选中

                }
            }
        }
        return systemBasicConfigList;
    }

    /**
     * 查询已有数据权限
     *
     * @param userno
     * @return
     */
    private List<UserDatapermission> queryUserDataPermissions(String userno) {
        UserDatapermissionExample example = new UserDatapermissionExample();
        UserDatapermissionExample.Criteria criteria = example.createCriteria();
        criteria.andUsernoEqualTo(userno);
        return userDatapermissionMapper.selectByExample(example);
    }

    /**
     * 查询所有数据权限内容
     *
     * @return
     */
    private List<SystemBasicConfig> querySystemBasicConfigs() {
        SystemBasicConfigExample example = new SystemBasicConfigExample();
        SystemBasicConfigExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        criteria.andMoudlecodeEqualTo(BizRequestConstant.MOUDLE_CODE_BIZ_NAME);
        return systemBasicConfigMapper.selectByExample(example);
    }

    /**
     * 查询 所用角色
     *
     * @return
     */
    private List<SystemRoleInfo> queryRoleInfoList() throws EduException {
        SystemRoleInfoExample example = new SystemRoleInfoExample();
        SystemRoleInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        return systemRoleInfoMapper.selectByExample(example);
    }


    @Override
    public SystemUserInfo selectSingleSystemUserInfoLogin(
            SystemUserInfo systemUserInfo) throws EduException {
        if (systemUserInfo == null) {
            throw new EduException(MessageConstant.QUERY_USER_INFO_FAILED);
        }

        if (StringUtil.isEmpty(systemUserInfo.getUserno())) {
            throw new EduException(MessageConstant.USER_NO_IS_NULL);
        }

        SystemUserInfoExample ex = new SystemUserInfoExample();
        SystemUserInfoExample.Criteria c = ex.createCriteria();
        c.andUsernoEqualTo(systemUserInfo.getUserno());
        c.andPwdEqualTo(DESUtils.encrypt(MD5Utils.md5(systemUserInfo.getPwd()), null));
        c.andDeletedEqualTo(BizRequestConstant.DELETE_NO);

        List<SystemUserInfo> list = systemUserInfoMapper.selectByExample(ex);

        if (list != null && list.size() > 0) {
            systemUserInfo = list.get(0);
        } else {
            throw new EduException(MessageConstant.USER_NOT_EXIST);
        }
        systemUserInfo.setPwd(null);

        return systemUserInfo;
    }

    @Override
    public SystemUserInfo selectSingleSystemUserInfo(SystemUserInfo systemUserInfo) throws EduException{
    	if (systemUserInfo == null) {
            throw new EduException(MessageConstant.QUERY_USER_INFO_FAILED);
        }

        if (StringUtil.isEmpty(systemUserInfo.getUserno())) {
            throw new EduException(MessageConstant.USER_NO_IS_NULL);
        }

        SystemUserInfoExample ex = new SystemUserInfoExample();
        SystemUserInfoExample.Criteria c = ex.createCriteria();
        c.andUsernoEqualTo(systemUserInfo.getUserno());
        c.andPwdEqualTo(DESUtils.encrypt(MD5Utils.md5(systemUserInfo.getPwd()), null));
        c.andDeletedEqualTo(BizRequestConstant.DELETE_NO);

        List<SystemUserInfo> list = systemUserInfoMapper.selectByExample(ex);

        if (list != null && list.size() > 0) {
            systemUserInfo = list.get(0);
        } else {
            throw new EduException(MessageConstant.PWD_ERROR);
        }
        return systemUserInfo;
    }

    /**
     * 查询用户-角色表
     *
     * @param userno
     * @return
     */
    private List<UserRole> queryUserRoles(String userno) {
        UserRoleExample ex = new UserRoleExample();
        UserRoleExample.Criteria c = ex.createCriteria();
        c.andUsernoEqualTo(userno);
        return userRoleMapper.selectByExample(ex);
    }

    @Override
    public void addOrUpdateUserInfo(UserInfoInput input) throws EduException {

        //校验邮箱是否已存在
//        validateEmail(input.getJobno(), input.getEmail());


        SystemUserInfo systemUserInfo = new SystemUserInfo();
        systemUserInfo.setContactphone(input.getContactphone());
        systemUserInfo.setEmail(input.getEmail());
        systemUserInfo.setJob(input.getJob());
        systemUserInfo.setOrgno(input.getOrgno());
        systemUserInfo.setUsername(input.getUsername());
        systemUserInfo.setSpell(PyUtil.cn2py(input.getUsername()));
        systemUserInfo.setRemark(input.getRemark());

        if (input.getModifyflag().equals(BizRequestConstant.TO_ADD)) {
            // 检验用户工号是否已存在
            validateUserNo(input.getJobno());
            // 新增
            systemUserInfo.setUserno(input.getJobno());
            // 未删除
            systemUserInfo.setDeleted(BizRequestConstant.DELETE_NO);
            // 初始密码
            systemUserInfo.setPwd(DESUtils.encrypt(
                    MD5Utils.md5(BizRequestConstant.INIT_PWD), null));

            // 插入用户 - 角色中间表
            if (StringUtil.isNotEmpty(input.getRolenos())) {
                addUserRole(input.getJobno(), input.getRolenos());
            }

            //数据权限保存
            if (StringUtil.isNotEmpty(input.getBasicnos())) {
                systemUserInfo.setDatapermission(BizRequestConstant.HAS_YES);
                addDataPermission(input.getJobno(), input.getBasicnos());
            }

            try {
                systemUserInfoMapper.insertSelective(systemUserInfo);
            } catch (Exception e) {
                throw new EduException(MessageConstant.EMAIL_HAS_USED);
            }

        } else {
            // 修改

            SystemUserInfoExample example = new SystemUserInfoExample();
            SystemUserInfoExample.Criteria c = example.createCriteria();
            c.andUsernoEqualTo(input.getJobno());

            // 修改用户 - 角色中间表
            if (StringUtil.isEmpty(input.getRolenos())) {
                throw new EduException(MessageConstant.ROLE_MUST_BE_NOT_EMPTY_CODE);
            }
            updateUserRole(input.getJobno(), input.getRolenos());

            //修改 数据权限
//            if (StringUtil.isNotEmpty(input.getBasicnos())) {
                systemUserInfo.setDatapermission(BizRequestConstant.HAS_YES);
                updateDataPermission(input.getJobno(), input.getBasicnos());
//            }

            // 捕获主键重复异常
            try {
                systemUserInfoMapper.updateByExampleSelective(systemUserInfo,
                        example);
            } catch (Exception e) {
                throw new EduException(MessageConstant.EMAIL_HAS_USED);
            }


        }

    }

    /**
     * 数据权限修改
     *
     * @param userno
     * @param basicnos
     */
    private void updateDataPermission(String userno, String basicnos) {
        deleteDataPermission(userno);

        addDataPermission(userno, basicnos);
    }

    /**
     * 删除用户原有数据权限
     *
     * @param userno
     */
    private void deleteDataPermission(String userno) {
        UserDatapermissionExample example = new UserDatapermissionExample();
        UserDatapermissionExample.Criteria criteria = example.createCriteria();
        criteria.andUsernoEqualTo(userno);
        userDatapermissionMapper.deleteByExample(example);

    }

    /**
     * 数据权限保存
     *
     * @param userno
     * @param basicnos
     */
    private void addDataPermission(String userno, String basicnos) {
        if (StringUtil.isEmpty(basicnos)) {
            return;
        }
        String[] basicArray = basicnos.split(",");
        for (String basicno : basicArray) {
            if (StringUtil.isEmpty(basicno)) {
                continue;
            }
            UserDatapermission datapermission = new UserDatapermission();
            datapermission.setBasicno(StringUtil.nullToIntZero(basicno));
            datapermission.setUserno(userno);

            userDatapermissionMapper.insertSelective(datapermission);
        }


    }

    /**
     * 修改 用户 - 角色中间表
     *
     * @param userno
     * @param rolenos
     */
    private void updateUserRole(String userno, String rolenos) {
        // 1.删除原有角色信息
        deleteUserRole(userno);

        // 2.插入现有角色信息
        addUserRole(userno, rolenos);
    }

    /**
     * 删除原有角色信息
     *
     * @param userno
     */
    private void deleteUserRole(String userno) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria c = example.createCriteria();
        c.andUsernoEqualTo(userno);
        userRoleMapper.deleteByExample(example);
    }

    /**
     * 插入用户 - 角色 中间表
     *
     * @param userno
     * @param rolenos
     */
    private void addUserRole(String userno, String rolenos) {
        // 插入用户成功,继续关联角色
        String[] roleArry = rolenos.split(",");
        UserRole userRole = null;

        for (String roleno : roleArry) {
            if (StringUtil.isEmpty(roleno)) {
                continue;
            }
            userRole = new UserRole();
            userRole.setRoleno(roleno);
            userRole.setUserno(userno);
            userRoleMapper.insertSelective(userRole);
        }
    }

    @Override
    public void deleteUserInfo(String userNO) throws EduException {

        SystemUserInfoExample example = new SystemUserInfoExample();
        SystemUserInfoExample.Criteria c = example.createCriteria();
        c.andUsernoEqualTo(userNO);

        SystemUserInfo systemUserInfo = new SystemUserInfo();
        systemUserInfo.setDeleted(BizRequestConstant.DELETE_YES);

        // 1.逻辑删除
        systemUserInfoMapper.updateByExampleSelective(systemUserInfo, example);

        // 2.同时删除中间表相关信息
        deleteUserRole(userNO);
    }

    @Override
    public SystemUserInfo queryByUserName(String userName) throws EduException {
        SystemUserInfoExample example = new SystemUserInfoExample();
        SystemUserInfoExample.Criteria c = example.createCriteria();

        c.andUsernameEqualTo(userName);
        c.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        List<SystemUserInfo> userInfoList = systemUserInfoMapper
                .selectByExample(example);

        SystemUserInfo systemUserInfo = null;
        if (userInfoList != null && userInfoList.size() == 1) {
            systemUserInfo = userInfoList.get(0);
        } else {
            throw new EduException(
                    MessageConstant.REQUEST_USER_NOT_EXISTS_OR_ABNORMAL);
        }

        return systemUserInfo;
    }

    /**
     * 根据用户名中关键字模糊查询用户信息
     *
     * @return 用户下拉框用数据
     */
    @Override
    public List<UserSelectOutput> queryAllUserForSelect() {

//        // 如果用户名关键字为空
//        if (Strings.isNullOrEmpty(userName)) {
//            return null;
//        }

        SystemUserInfoExample example = new SystemUserInfoExample();
        SystemUserInfoExample.Criteria c = example.createCriteria();

//        // 模糊条件创建
//        String name = "%" + userName + "%";
//
//        c.andUsernameLike(name);
        c.andDeletedEqualTo(BizRequestConstant.DELETE_NO);

        // 取得用户列表
        List<SystemUserInfo> userInfoList = systemUserInfoMapper
                .selectByExample(example);

        // 取得部门列表
        SystemBasicConfigExample configExample = new SystemBasicConfigExample();
        configExample.createCriteria().andMoudlecodeEqualTo(BasicConfigConstant.ORG);

        final List<SystemBasicConfig> orgs = configMapper.selectByExample(configExample);

        // 根据用户列表构建用户下拉框列表
        List<UserSelectOutput> outputList = Lists.transform(userInfoList, new Function<SystemUserInfo, UserSelectOutput>() {
            @Override
            public UserSelectOutput apply(final SystemUserInfo input) {

                UserSelectOutput output = new UserSelectOutput();

                // 取得部门名称
                SystemBasicConfig org = Iterables.find(orgs, new Predicate<SystemBasicConfig>() {
                    @Override
                    public boolean apply(SystemBasicConfig orgInput) {
                        return Objects.equal(orgInput.getCode(), input.getOrgno());
                    }
                });
                String orgName = org.getName();

                // 用户编号
                output.setCode(input.getUserno());

                // 用户显示名称
                String displayName = Joiner.on("-").skipNulls().join(input.getUserno(), orgName, input.getUsername());
                output.setName(displayName);

                return output;
            }
        });

        return outputList;
    }

    @Override
    public int updateIndividualUserPwd(SystemUserInfo systemUserInfo)
			throws EduException{
    	 SystemUserInfoExample example = new SystemUserInfoExample();
         SystemUserInfoExample.Criteria c = example.createCriteria();

         SystemUserInfo systemUserInfo2 = new SystemUserInfo();
         systemUserInfo2.setUserno(systemUserInfo.getUserno());
         systemUserInfo2.setPwd(systemUserInfo.getPwd());
         return systemUserInfoMapper.updateByPrimaryKeySelective(systemUserInfo2);
    }


    /**
     * 校验工号 是否存在
     *
     * @param userno
     * @return
     * @throws EduException
     */
    private void validateUserNo(String userno)
            throws EduException {
        SystemUserInfoExample example = new SystemUserInfoExample();
        SystemUserInfoExample.Criteria c = example.createCriteria();
        c.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        c.andUsernoEqualTo(userno);
        List<SystemUserInfo> userInfoList = systemUserInfoMapper
                .selectByExample(example);
        if (userInfoList != null && userInfoList.size() > 0) {
            throw new EduException(MessageConstant.USERNO_HAS_EXISTS);
        }
    }

    /**
     * 校验邮箱地址是否存在
     *
     * @param userno
     * @param email
     */
    private void validateEmail(String userno, String email) {
        SystemUserInfoExample example = new SystemUserInfoExample();
        SystemUserInfoExample.Criteria c = example.createCriteria();

        c.andEmailEqualTo(email);
//        c.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        if (StringUtil.isNotEmpty(userno)) {
            c.andUsernoNotEqualTo(userno);
        }
        List<SystemUserInfo> userInfoList = systemUserInfoMapper
                .selectByExample(example);
        if (userInfoList != null && userInfoList.size() > 0) {
            throw new EduException(MessageConstant.EMAIL_HAS_USED);
        }
    }
}

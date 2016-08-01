package com.ancun.boss.service.system.impl;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.boss.persistence.biz.BizFunctionInfoMapper;
import com.ancun.boss.persistence.mapper.*;
import com.ancun.boss.persistence.model.*;
import com.ancun.boss.pojo.roleInfo.RoleInfoInput;
import com.ancun.boss.pojo.roleInfo.RoleInfoListOutput;
import com.ancun.boss.pojo.roleInfo.RolePermissionOutput;
import com.ancun.boss.service.system.IRoleInfoService;
import com.ancun.boss.util.PyUtil;
import com.ancun.core.exception.EduException;
import com.ancun.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 角色管理实现类
 *
 * @author mif
 * @version 1.0
 * @Created on 2015/9/24
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2015
 */
@Service
public class RoleInfoServiceImpl implements IRoleInfoService {

    private static final Logger logger = LoggerFactory.getLogger(RoleInfoServiceImpl.class);


    @Resource
    private SystemRoleInfoMapper systemRoleInfoMapper;

    @Resource
    private SystemMenuInfoMapper systemMenuInfoMapper;

    @Resource
    private RoleMenuFunctionMapper roleMenuFunctionMapper;

    @Resource
    private FunctionInfoMapper functionInfoMapper;

    @Resource
    private BizFunctionInfoMapper bizFunctionInfoMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public RoleInfoListOutput queryRoleList(RoleInfoInput roleInfoInput) throws EduException {
        SystemRoleInfoExample example = new SystemRoleInfoExample();
        SystemRoleInfoExample.Criteria criteria = example.createCriteria();
        example.setPage(roleInfoInput.getPage());

        // 角色名
        if (StringUtil.isNotEmpty(roleInfoInput.getRolename())) {
            criteria.andRolenameLike("%" + roleInfoInput.getRolename() + "%");
        }
        // 未删除
        criteria.andDeletedEqualTo(BizRequestConstant.DELETE_NO);

        List<SystemRoleInfo> roleInfoList = systemRoleInfoMapper.selectByExample(example);

        RoleInfoListOutput roleInfoListOutput = new RoleInfoListOutput();
        roleInfoListOutput.setRoleinfolist(roleInfoList);
        roleInfoListOutput.setPageinfo(example.getPage());
        return roleInfoListOutput;
    }

    @Override
    public SystemRoleInfo queryRoleInfo(String roleno) throws EduException {
        if (StringUtil.isEmpty(roleno)) {
            throw new EduException(MessageConstant.ROLE_NO_MUST_BE_NOT_EMPTY);
        }
        return systemRoleInfoMapper.selectByPrimaryKey(roleno);
    }

    @Override
    public void addOrUpdateRoleInfo(SystemRoleInfo roleInfo) throws EduException {

        validateRoleName(roleInfo);
        roleInfo.setSpell(PyUtil.cn2py(roleInfo.getRolename()));


        if (StringUtil.isEmpty(roleInfo.getRoleno())) {
            //新增
            roleInfo.setRoleno(UUID.randomUUID().toString());
            roleInfo.setDeleted(BizRequestConstant.DELETE_NO);

            systemRoleInfoMapper.insertSelective(roleInfo);

            //如果为管理员 删除所有相关 菜单权限和功能权限
            if (StringUtil.isNotEmpty(roleInfo.getSuperman()) && roleInfo.getSuperman().equals(BizRequestConstant.HAS_YES)) {
                deleteRoleMenuFunByRoleno(roleInfo.getRoleno());
            } else {
                // 保存菜单/功能权限
                if (StringUtil.isNotEmpty(roleInfo.getMenunos())) {
                    this.saveMunoPermission(roleInfo.getRoleno(), roleInfo.getMenunos());
                } else {
                    throw new EduException(MessageConstant.PERMISSION_UNCHOSED);
                }
            }

        } else {
            //修改
            SystemRoleInfoExample example = new SystemRoleInfoExample();
            SystemRoleInfoExample.Criteria criteria = example.createCriteria();
            criteria.andRolenoEqualTo(roleInfo.getRoleno());
            systemRoleInfoMapper.updateByExampleSelective(roleInfo, example);
            //如果为管理员 删除所有相关 菜单权限和功能权限
            if (StringUtil.isNotEmpty(roleInfo.getSuperman()) && roleInfo.getSuperman().equals(BizRequestConstant.HAS_YES)) {
                deleteRoleMenuFunByRoleno(roleInfo.getRoleno());
            } else {
                //删除 原有菜单权限和功能权限  再新增
                if (StringUtil.isNotEmpty(roleInfo.getMenunos())) {
                    deleteRoleMenuFunByRoleno(roleInfo.getRoleno());

                    this.saveMunoPermission(roleInfo.getRoleno(), roleInfo.getMenunos());
                } else {
                    throw new EduException(MessageConstant.PERMISSION_UNCHOSED);
                }
            }
        }
    }

    /**
     * 校验角色名是否存在
     *
     * @param roleInfo
     */
    private void validateRoleName(SystemRoleInfo roleInfo) {
        if (StringUtil.isEmpty(roleInfo.getRolename())) {
            throw new EduException(MessageConstant.ROLE_NAME_MUST_BE_NOT_EMPTY);
        }

        if (StringUtil.isEmpty(roleInfo.getSuperman())) {
            throw new EduException(MessageConstant.IS_SUPERMAN_MUST_BE_NOT_EMPTY);
        } else {
            if (roleInfo.getSuperman().equals(BizRequestConstant.HAS_NO) && StringUtil.isEmpty(roleInfo.getMenunos())) {
                throw new EduException(MessageConstant.PERMISSION_UNCHOSED);
            }
        }

        SystemRoleInfoExample example = new SystemRoleInfoExample();
        SystemRoleInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        criteria.andRolenameEqualTo(roleInfo.getRolename());
        if (StringUtil.isNotEmpty(roleInfo.getRoleno())) {
            criteria.andRolenoNotEqualTo(roleInfo.getRoleno());
        }

        int cout = systemRoleInfoMapper.countByExample(example);
        if (cout > 0) {
            throw new EduException(MessageConstant.ROLE_NAME_EXISTS);
        }
    }

    /**
     * 根据角色编号 删除角色权限表
     *
     * @param roleno
     */
    private void deleteRoleMenuFunByRoleno(String roleno) {
        RoleMenuFunctionExample example = new RoleMenuFunctionExample();
        RoleMenuFunctionExample.Criteria c = example.createCriteria();
        c.andRolenoEqualTo(roleno);
        roleMenuFunctionMapper.deleteByExample(example);
    }

    /**
     * 保存菜单/功能权限
     *
     * @param roleno
     * @param menunos
     * @throws EduException
     */
    private void saveMunoPermission(String roleno, String menunos) throws EduException {
        String[] menunoArr = menunos.split(",");
        for (int i = 0; i < menunoArr.length; i++) {
            //过滤第一个为空的
            if (StringUtil.isEmpty(menunoArr[i]))
                continue;
            //菜单权限_功能权限
            if (menunoArr[i].indexOf("_") > -1) {
                String menuno = menunoArr[i].substring(0, menunoArr[i].indexOf("_"));
                String funno = menunoArr[i].substring(menunoArr[i].indexOf("_") + 1, menunoArr[i].length());

                RoleMenuFunction roleMenuFunction = new RoleMenuFunction();
                roleMenuFunction.setRoleno(roleno);
                roleMenuFunction.setMenuno(menuno);
                roleMenuFunction.setFucno(funno);
                roleMenuFunctionMapper.insertSelective(roleMenuFunction);
            } else {
                throw new EduException(MessageConstant.PERMISSION_UNCHOSED);
            }

        }
    }

    @Override
    public void deleteRoleInfo(String roleno) throws EduException {

        if (StringUtil.isEmpty(roleno)) {
            throw new EduException(MessageConstant.ROLE_NO_MUST_BE_NOT_EMPTY);
        }

        SystemRoleInfoExample example = new SystemRoleInfoExample();
        SystemRoleInfoExample.Criteria criteria = example.createCriteria();
        criteria.andRolenoEqualTo(roleno);

        SystemRoleInfo roleInfo = new SystemRoleInfo();
        roleInfo.setDeleted(BizRequestConstant.DELETE_YES);
        //1.逻辑删除
        systemRoleInfoMapper.updateByExampleSelective(roleInfo, example);

        //2.同时删除 相关权限
        deleteRoleMenuFunByRoleno(roleno);

        //3.删除角色用户中间表
        deleteUserRoleInfo(roleno);

    }

    /**
     * 删除角色 用户中间表
     *
     * @param roleno
     */
    private void deleteUserRoleInfo(String roleno) {
        UserRoleExample example = new UserRoleExample();
        UserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andRolenoEqualTo(roleno);

        userRoleMapper.deleteByExample(example);
    }

    @Override
    public RolePermissionOutput rolePermission(String roleno) throws EduException {
        // 顶级菜单
        List<SystemMenuInfo> plist = this.queryParentList();
        if (plist == null || plist.size() < 0)
            return null;

        RoleMenuFunctionExample example = new RoleMenuFunctionExample();
        RoleMenuFunctionExample.Criteria criteria = example.createCriteria();
        criteria.andRolenoEqualTo(roleno);

        // 角色关联的菜单功能权限
        List<RoleMenuFunction> roleMenuFunctionList = roleMenuFunctionMapper.selectByExample(example);
        // 功能权限
//        List<FunctionInfo> functionInfoList = functionInfoMapper.selectByExample(new FunctionInfoExample());

        for (SystemMenuInfo menuInfo : plist) {
            List<SystemMenuInfo> subList = menuInfo.getSubmenulist();
            //子菜单
            if (subList == null || subList.size() < 0)
                continue;
            for (SystemMenuInfo sub : subList) {
                //菜单关联功能权限
                List<FunctionInfo> functionInfoList = bizFunctionInfoMapper.queryFunByMenuNo(sub.getMenuno());

                if (functionInfoList == null || functionInfoList.size() < 0) {
                    continue;
                }

//              // 是否已选
                if (setFunctionChecked(functionInfoList, roleMenuFunctionList, sub)) {
                    sub.setIschecked(true); //二级菜单已选
                    menuInfo.setIschecked(true); //顶级菜单已选
                }
//                logger.debug("the first function ischecked ={},menuname = {}", functionInfoList.get(0).ischecked(), sub.getMenuname());
            }
        }

        RolePermissionOutput output = new RolePermissionOutput();
        output.setMenuinfolist(plist);
        return output;
    }

    /**
     * 设置是否选中
     *
     * @param functionInfoList
     * @param roleMenuFunctionList
     * @param sub
     */
    private boolean setFunctionChecked(List<FunctionInfo> functionInfoList, List<RoleMenuFunction> roleMenuFunctionList, SystemMenuInfo sub) {
        //是否有数据权限
        boolean hasFun = false;
        List<FunctionInfo> list = new LinkedList<FunctionInfo>();
        for (FunctionInfo functionInfo : functionInfoList) {
//            FunctionInfo info = new FunctionInfo();
            boolean ischecked = false;
            for (RoleMenuFunction roleMenuFunction : roleMenuFunctionList) {
                if (roleMenuFunction.getMenuno().equals(sub.getMenuno()) && functionInfo.getFucno().equals(roleMenuFunction.getFucno())) {
//                    logger.debug("已选中的功能权限：funno={},menuno={}", functionInfo.getFucno(), sub.getMenuno());
                    functionInfo.setIschecked(true);
                    hasFun = true;
                }
            }
            list.add(functionInfo);
        }
        sub.setFunctioninfolist(list);
        return hasFun;
    }


    /**
     * 查询顶级菜单， 设置二级菜单 和功能权限
     *
     * @return
     * @throws EduException
     */
    private List<SystemMenuInfo> queryParentList() throws EduException {
        SystemMenuInfoExample example = new SystemMenuInfoExample();
        SystemMenuInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        example.setOrderByClause("ORDERED");

        //全部菜单
        List<SystemMenuInfo> allList = systemMenuInfoMapper.selectByExample(example);

        // 顶级菜单
        List<SystemMenuInfo> pList = new LinkedList<SystemMenuInfo>();
        if (allList == null || allList.size() <= 0)
            return null;
        for (SystemMenuInfo menuInfo : allList) {
            if (menuInfo.getPmenuinfo().equals("0")) {
                pList.add(menuInfo);
            }
        }

        if (pList == null || pList.size() <= 0)
            return null;

        // 二级菜单
        List<SystemMenuInfo> subList = null;
        for (SystemMenuInfo pmenuInfo : pList) {
            subList = new LinkedList<SystemMenuInfo>();
            for (SystemMenuInfo menuInfo : allList) {
                if (pmenuInfo.getMenuno().equals(menuInfo.getPmenuinfo())) {
                    subList.add(menuInfo);
                }
            }
            pmenuInfo.setSubmenulist(subList);
        }

        return pList;
    }

    /**
     * 根据父节点查询子节点
     *
     * @param pmenuinfo
     * @return
     * @throws EduException
     */
    private List<SystemMenuInfo> queryByPmenuinfo(String pmenuinfo) throws EduException {
        SystemMenuInfoExample example = new SystemMenuInfoExample();
        SystemMenuInfoExample.Criteria criteria = example.createCriteria();
        criteria.andPmenuinfoEqualTo(pmenuinfo);
        criteria.andDeletedEqualTo(BizRequestConstant.DELETE_NO);
        example.setOrderByClause("ORDERED");
        return systemMenuInfoMapper.selectByExample(example);
    }

}

package com.ancun.boss.system;

import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.persistence.model.SystemRoleInfo;
import com.ancun.boss.pojo.roleInfo.RoleInfoInput;
import com.ancun.test.api.BaseAPi;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2015/10/27.
 */
public class RoleInfoControllerTest extends BaseAPi {
    @Before
    public void setUp() {
        this.url = "http://127.0.0.1:8080/ancun-boss";
        this.accessId = "1a68f027825606cd3d8aafdc7167dabe";
        this.accessKey = "MTI4NDZlN2VmMDRmOTUwZDc5YmE0MmMwZmY3OTc0NGE=";
    }


    @Test
    public void testQueryRoleList() throws Exception {
        RoleInfoInput input = new RoleInfoInput();
        input.setRolename("营销");

        input.setAccessid(accessId);
        input.setAccesskey(accessKey);
        super.responseJSON("queryRoleList",input);

    }

    @Test
    public void testQueryRoleInfo() throws Exception {
        SystemRoleInfo roleInfo = new SystemRoleInfo();
        roleInfo.setRoleno("0754dbf4-0963-4c35-8944-c3ae2815fdda");


        roleInfo.setAccessid(accessId);
        roleInfo.setAccesskey(accessKey);
        super.responseJSON("queryRoleInfo", roleInfo);

    }

    @Test
    public void testAddOrUPdateRoleInfo() throws Exception {

        SystemRoleInfo roleInfo = new SystemRoleInfo();
        roleInfo.setRoleno("d51a322d-3ab3-42ce-a770-19ed0a233d3d");
        roleInfo.setRolename("mif_test_r2");
        roleInfo.setSuperman(BizRequestConstant.HAS_NO);

        roleInfo.setMenunos("63e427a1-bb09-43d9-bba7-9022ad26ebc2_1,63e427a1-bb09-43d9-bba7-9022ad26ebc2_2");

        roleInfo.setAccessid(accessId);
        roleInfo.setAccesskey(accessKey);
        super.responseJSON("addOrUpdateRoleInfo", roleInfo);


    }

    @Test
    public void testDeleteRoleInfo() throws Exception {
        SystemRoleInfo roleInfo = new SystemRoleInfo();
        roleInfo.setRoleno("d51a322d-3ab3-42ce-a770-19ed0a233d3d");

        roleInfo.setAccessid(accessId);
        roleInfo.setAccesskey(accessKey);
        super.responseJSON("deleteRoleInfo", roleInfo);
    }

    @Test
    public void testRolePermission() throws Exception {

        SystemRoleInfo roleInfo = new SystemRoleInfo();
        roleInfo.setRoleno("d51a322d-3ab3-42ce-a770-19ed0a233d3d");


        roleInfo.setAccessid(accessId);
        roleInfo.setAccesskey(accessKey);
        super.responseJSON("rolePermission", roleInfo);
    }
}
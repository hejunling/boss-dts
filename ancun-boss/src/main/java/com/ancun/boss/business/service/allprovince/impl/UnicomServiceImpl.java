package com.ancun.boss.business.service.allprovince.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ancun.boss.business.constant.Constant;
import com.ancun.common.biz.email.SendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ancun.boss.business.persistence.mapper.BizComboInfoMapper;
import com.ancun.boss.business.persistence.module.BizComboInfo;
import com.ancun.boss.business.persistence.module.BizComboInfoExample;
import com.ancun.boss.business.pojo.allprovince.ProvinceServiceInput;
import com.ancun.boss.business.service.allprovince.IProvinceService;
import com.ancun.boss.constant.BizRequestConstant;
import com.ancun.boss.constant.MessageConstant;
import com.ancun.common.biz.cache.BaseDataServiceImpl;
import com.ancun.core.exception.EduException;
import com.ancun.utils.HttpClientUtils;
import com.ancun.utils.TimeUtils;

@Service("UniService")
public class UnicomServiceImpl implements IProvinceService {

    @Resource
    BizComboInfoMapper comboMapper;

    @Resource
    private BaseDataServiceImpl baseDataService;

    private Logger log = LoggerFactory.getLogger(UnicomServiceImpl.class);

    @Override
    public void open(ProvinceServiceInput provinceInput) {

        BizComboInfoExample example = new BizComboInfoExample();
        BizComboInfoExample.Criteria c = example.createCriteria();
        c.andTcidEqualTo(Long.valueOf(provinceInput.getTcID()));

        List<BizComboInfo> combo = comboMapper.selectByExample(example);

        if (combo.size() == 0) {
            throw new EduException(MessageConstant.FARE_REQUEST_ERROR);
        }

        Date openTime = provinceInput.getOpenTime();
        String dayTime = TimeUtils.format(openTime, "yyyy-MM-dd");
        if (!fareRquest(BizRequestConstant.FARE_OPEN, provinceInput.getUserno(), dayTime, combo.get(0).getPrice())) {
            throw new EduException(MessageConstant.FARE_REQUEST_ERROR);
        }

    }

    @Override
    public void cancel(ProvinceServiceInput provinceInput) {
        if (!fareRquest(BizRequestConstant.FARE_CANCEL, provinceInput.getUserno())) {
            throw new EduException(MessageConstant.FARE_REQUEST_ERROR);
        }
    }

    @Override
    public void postChangeRequest(String userno, String changeTcType, String oldTcID, String newTcID, boolean rollback) {

    }

    /**
     * 调用联通用户套餐修改接口
     * @param provinceInput
     * add by zkai on 2015/05/06
     */
    @Override
    public boolean postChangeRequest(ProvinceServiceInput provinceInput) {

        String title = "联通调用远程接口异常";
        String content = "套餐修改，逻辑：1.退订原套餐；2.开通新套餐。如果异常，请按逻辑调用远程接口 \n " ;
        SendEmail sendEmail = new SendEmail();

        // 取消用户  参数: userno-用户编号 ,oldTc-原套餐
        ProvinceServiceInput cancelInput = new ProvinceServiceInput();
        cancelInput.setUserno(provinceInput.getUserno());
        cancelInput.setTcID(provinceInput.getOldTc());
        try{
            cancel(cancelInput);
            log.debug("退订原套餐成功...下面开始重新开通新套餐");
        }catch (Exception e){
            log.error("退订原套餐，失败：",e);
            content += "退订原套餐失败，原套餐信息：套餐编号-"+provinceInput.getOldTc()+" 用户号码-"+provinceInput.getUserTel()+" \n "
                            +"失败套餐开通需要信息：新套餐编号-"+provinceInput.getNewTc()
                                 +" 用户号码-"+provinceInput.getUserTel()+" 开通时间"+provinceInput.getOpenTime();
            sendEmail.send( Constant.SEND_EMAIL_TO , title, content, null, true);
            return false;
        }


        // 开通用户  参数：套餐编号，开通时间 userno-用户编号
        try{
            ProvinceServiceInput openInput = new ProvinceServiceInput();
            openInput.setUserno(provinceInput.getUserno());
            openInput.setTcID(provinceInput.getNewTc());
            openInput.setOpenTime(provinceInput.getOpenTime());
            open(openInput);
            log.debug("重新开通新套餐成功...");
        }catch (Exception e){
            log.error("退订原套餐，开通新套餐失败：",e);
            content += "退订原套餐，开通新套餐失败 \n "+"失败套餐开通需要信息：新套餐编号-"+provinceInput.getNewTc()
                              +" 用户号码-"+provinceInput.getUserTel()+" 开通时间"+provinceInput.getOpenTime();
            sendEmail.send( Constant.SEND_EMAIL_TO , title, content, null, true);
            return false;
//            log.error("退订原套餐，开通新套餐失败...下面需要回滚原套餐");
//            try {
//                open(cancelInput);
//                log.error("回滚原套餐成功...");
//            }catch(EduException ex) {
//                log.error("回滚原套餐时也异常",ex);
//            }
        }
        return true;
    }

    /**
     * 发送计费请求
     *
     * @param key
     * @param type
     * @param params
     * @return
     */
    private boolean fareRquest(String key, Object... params) {

        // 默认计费开通成功
        boolean fareFlg = true;
        // 获取计费开通的URL
        String fareUrl = baseDataService.getConfigMessage("ALL", key);
        // 设置参数
        fareUrl = MessageFormat.format(fareUrl, params);
        try {
            // 发送计费开通请求
            byte[] result = HttpClientUtils.getBytes(fareUrl);
            // 开通失败
            if (result == null) {
                fareFlg = false;
            }
        } catch (Exception e) {
            log.error("业务异常," + MessageConstant.FARE_REQUEST_ERROR, e);
            fareFlg = false;
        }

        return fareFlg;
    }

    public static void main(String[] args) {
        String url = "http://192.168.0.224:22445/fareOpen.do?phone={0}&openDate={1}&money={2}";
        String format = MessageFormat.format(url, "15896329632", TimeUtils.getSysdate(), "2");
        try {
            byte[] result = HttpClientUtils.getBytes(format);
            System.out.println(new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

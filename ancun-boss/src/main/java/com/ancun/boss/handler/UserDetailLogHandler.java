package com.ancun.boss.handler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ancun.boss.persistence.model.DetailLog;
import com.ancun.boss.service.system.DetailLogService;
import com.ancun.boss.util.ThreadLocalUtil;
import com.ancun.core.constant.RequestConst;
import com.ancun.core.spring.handler.LogHandler;

/**
 * 用户详细操作日志
 * @author hjl
 *
 * @时间 2015-9-22 上午9:57:04
 */
public class UserDetailLogHandler extends HandlerInterceptorAdapter {
	
	private Logger log = LoggerFactory.getLogger(LogHandler.class);
	
	@Resource
	private DetailLogService detailLogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}
	
	
	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
		// 将请求信息写入数据库
		DetailLog content = ThreadLocalUtil.getContent();
		try{
			if(detailLogService != null && null != content){
				String userno = content.getUserno();
				userno = StringUtils.isBlank(userno) ? (String)request.getAttribute(RequestConst.USER_NO) : userno;
				if(StringUtils.isBlank(userno)) {
					userno = "100000";
				}
				content.setUserno(userno);
				detailLogService.saveLog(content);
			}
		}catch(Exception e){
			// 忽略
			log.error("日志写入数据库失败", e);
		}
	}
}

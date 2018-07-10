package com.example.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.service.WxReceiveMsgService;

/**
 * 响应微信公共号的消息的异步任务
 * @ClassName: ImReceiveMsgAsyncTask
 * @Description: 
 * @author 
 * 2018年6月4日  tck 创建
 */
@Component
public class WxReceiveMsgAsyncTask {

	@Autowired
	private WxReceiveMsgService wxReceiveMsgService;

	/**
	 * @Async和@Transactional分开写为了使事务生效
	 * 
	 * @author
	 * 2018年5月23日 tck 创建
	 */
	@Async
	public void doExcuteAsyncSubscribeTask(String openId) {
		wxReceiveMsgService.doSaveWxUserInfo(openId);
	}
	
	/**
	 * @Async和@Transactional分开写为了使事务生效
	 * 
	 * @author
	 * 2018年5月23日 tck 创建
	 */
	@Async
	public void doExcuteAsyncUnSubscribeTask(String openId) {
		wxReceiveMsgService.doDeleteWxUserInfo(openId);
	}
}

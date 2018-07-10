package com.example.service;

public interface WxReceiveMsgService {
	
	/**
	 * 把用户的openId获取uuid后查询,把公共号对应的openId反写进用户信息表(为了推送流程审批消息用)
	 * @param openId
	 * @return
	 * @author
	 * 2018年6月4日 tck 创建
	 */
	public Object doSaveWxUserInfo(String openId);
	
	/**
	 * 用户的openId清除openId
	 * @param openId
	 * @return
	 * @author
	 * 2018年6月4日 tck 创建
	 */
	public Object doDeleteWxUserInfo(String openId);
	
}

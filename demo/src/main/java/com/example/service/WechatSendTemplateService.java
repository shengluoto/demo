package com.example.service;

/**
 * 发送微信的模版消息
 * @ClassName: SendWechatTemplateService
 * @Description: 
 * @author 
 * 2018年5月30日  tck 创建
 */
public interface WechatSendTemplateService {
	
	/**
	 * 发送模版消息
	 * @param jonsStr
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年5月30日 tck 创建
	 */
	public Object doSendTemplateInfo(String jsonStr, String loginName, String token, String clientType);
}

package com.example.service;

import com.example.data.TokenParamModel;

/**
 * 微信中各个token的获取方法
 * @ClassName: WechatTokenService
 * @Description: 
 * @author 
 * 2018年5月30日  tck 创建
 */
public interface WechatTokenService {
	
	/**
	 * 获取公共号的token(调用公共号的各接口需带上该token)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年5月30日 tck 创建
	 */
	public String getAccessToken(String loginName, String token, String clientType);
	
	/**
	 * 获取网页授权的token
	 * @param code 回调携带的code
	 * @param param 链接的参数
	 * @param type web端授权,还是微信端的授权(web,wx)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月6日 tck 创建
	 */
	public Object getOAuthToken(String code, TokenParamModel param, String type, String loginName, String token, String clientType);
	
	/**
	 * 刷新网页授权的token
	 * @param refreshToken
	 * @param appId
	 * @param type web端授权,还是微信端的授权(web,wx)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月6日 tck 创建
	 */
	public Object refreshOAuthToken(String refreshToken, String appId, String type, String loginName, String token, String clientType);
	
	/**
	 * 根据openId获取网页授权的用户信息
	 * @param openId
	 * @param appId
	 * @param type web端授权,还是微信端的授权(web,wx)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月6日 tck 创建
	 */
	public Object getUserInfoByOAuth(String openId, String appId, String type, String loginName, String token, String clientType);
	
	/**
	 * 根据code直接获取网页授权的用户信息
	 * @param param
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月7日 tck 创建
	 */
	public Object getUserInfoByCode(TokenParamModel param,String loginName, String token, String clientType);
}

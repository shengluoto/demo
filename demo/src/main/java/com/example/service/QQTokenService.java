package com.example.service;

/**
 * 微信中各个token的获取方法
 * @ClassName: WechatTokenService
 * @Description: 
 * @author 
 * 2018年5月30日  tck 创建
 */
public interface QQTokenService {
	
	/**
	 * 获取网页授权的token
	 * @param code 回调携带的code
	 * @param isRefreshToken 是否刷新token,0是1否
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年5月31日 tck 创建
	 */
	public Object getOAuthTokenAndOpenId(String code, String loginName, String token, String clientType);
	
	/**
	 * 刷新网页授权的token
	 * @param refreshToken 刷新token
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年5月31日 tck 创建
	 */
	public Object refreshOAuthToken(String refreshToken, String loginName, String token, String clientType);
	
	/**
	 * 根据openId获取网页授权的用户信息
	 * @param openId
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年5月31日 tck 创建
	 */
	public Object getUserInfoByOAuth(String openId, String loginName, String token, String clientType);
	
	/**
	 * 根据code获取网页授权的用户信息
	 * @param code
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月7日 tck 创建
	 */
	public Object getUserInfoByCode(String code, String loginName, String token, String clientType);
	
}

package com.example.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 固定的不变值的配置内容
 * @author
 *  2017-12-25 tck 新建
 *  2018-06-11 shinry 添加钉钉的配置信息
 */
@Configuration
@PropertySource(value={"classpath:fixedValue.properties"},encoding="utf-8")
public class FixValueConstant {
	//获取token的url
	@Value("${wechat.accessTokenUrl}")
	public String wechatAccessTokenUrl;
	
	//微信获取用户信息的地址
	@Value("${wechat.userInfoUrl}")
	public String wechatUserInfoUrl;
	
	//QQtoken的url
	@Value("${qq.accessTokenUrl}")
	public String qqAccessTokenUrl;
	
	//QQ获取openId的地址
	@Value("${qq.openIDUrl}")
	public String qqOpenIDUrl;
	
	
	//QQ获取用户信息的地址
	@Value("${qq.userInfoUrl}")
	public String qqUserInfoUrl;
	
	//获取公共号token的地址
	@Value("${wechat.publicPlatTokenUrl}")
	public String wechatPublicPlatTokenUrl;
	
	//获取网页token的地址
	@Value("${wechat.OAuthTokenUrl}")
	public String wechatOAuthTokenUrl;
	
	//获取网页刷新token的地址
	@Value("${wechat.OAuthRefreshTokenUrl}")
	public String wechatOAuthRefreshTokenUrl;
	
	//获取授权用户的信息
	@Value("${wechat.OAuthUserInfoUrl}")
	public String wechatOAuthUserInfoUrl;
	
	//获取公共号用户的信息
	@Value("${wechat.publicUserInfoUrl}")
	public String wechatPublicUserInfoUrl;
	//钉钉获取access_token
	@Value("${dingding.accessTokenUrl}")
	public String dingdingaccessTokenUrl;
	//钉钉获取持久临时授权
	@Value("${dingding.get_persistent_codeUrl}")
	public String dingdingget_persistent_codeUrl;
	//钉钉获取snToken
	@Value("${dingding.get_sns_tokenUtl}")
	public String dingdingget_sns_tokenUtl;
	//钉钉获取用户信息
	@Value("${dingding.getuserinfo}")
	public String dingdinggetuserinfo;
	//钉钉获取用户id
	@Value("${dingding.getUserId}")
	public String dingdinggetUserId;
}
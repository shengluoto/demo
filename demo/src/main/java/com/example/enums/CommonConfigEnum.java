package com.example.enums;


/**
 * 通用配置中key值的枚举
 * @ClassName: CommonConfigEnum
 * @Description: 
 * @author 
 * 2018年6月1日  tck 创建
 * 2018年6月4号shinry修改
 */
public enum CommonConfigEnum {
	WXCHECKSERVERTOKEN("wxCheckServerToken", "微信公共号验证服务器"),
	WXTHIRDLOGINAPPID("wxThirdLoginAppId", "微信开放平台web应用Id"),
	WXTHIRDLOGINAPPSECRET("wxThirdLoginAppSecret", "微信开放平台web应用密码"),
	QQTHIRDLOGINAPPID("qqThirdLoginAppId", "QQ互联web应用Id"),
	QQTHIRDLOGINAPPSECRET("qqThirdLoginAppSecret", "QQ互联web应用密码"),
	THIRDLOGINREDIRECTURI("thirdLoginRedirectUri", "第三方登录的重定向地址"),
	WXPUBLICAPPID("wxPublicAppId", "微信公共号Id"),
	WXPUBLICAPPSECRET("wxPublicAppSecret", "微信公共号密码"),
	WXPUBLICTOKENRENEWAL("wxPublicTokenRenewal", "微信公共号token剩余存活时间"),
	WXPUBLICREFRESHTOKENEXPIRE("wxPublicRefreshTokenExpire", "微信公共号网页刷新token存活时间"),
	WXPUBLICTEMPLATEID("wxPublicTemplateId", "微信公共号模版Id"),
	WXPUBLICJUMPURL("wxPublicJumpUrl", "微信公共号模版跳转链接"),
	WXPUBLICAUTHSCOPE("wxPublicAuthScope", "微信公共号网页授权模式"),
	TENTMANAGEBASIC("tentmanageBasic", "腾讯短信发送配置"),
	VERIFYINGCODETEMPLATEID("verifyingCodeTemplateId", "发送验证码模板id"),
	VERTIFYINGCODEMINUTE("vertifyingCodeMinute", "重置密码邮箱有效时间"),
	DINGDINGCRPID("dingdingcrpId", "钉钉微id"),
	DINGDINGSCPECT("dingdingscpect", "钉钉scrpect"),
	DINGDINGAGENTID("dingdingagentId", "微应用id"),
	RESETPASSTEMID("resetPassTemId", "重置密码id"),
	WXENCODINGAESKEY("wxEncodingAesKey", "微信公共号的加密密文"),
	WXPUBLICWELCOME("wxPublicWelcome", "微信公共号关注欢迎语"),
	WXPUBLICENCODE("wxPublicEncode", "微信公共号传输消息的加密模式"),
	DDSCANCODEAPPID("ddscancodeAppid","钉钉扫码Appid"),
	DDSCANSCREPT("ddscanscrept","钉钉scrept"),
	REGISTERVERTIFYCODEMINUTE("registerVertifyCodeMinute","注册短信验证码有效时间");
    ;

    private String key;
    private String message;

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	CommonConfigEnum(String key) {
        this.key = key;
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	CommonConfigEnum(String key,String message) {
		this.key=key;
		this.message=message;
	}
}
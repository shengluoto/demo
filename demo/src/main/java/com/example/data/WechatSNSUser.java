package com.example.data;

import lombok.Data;

/**
 * 网页授权得到的用户信息
 * @ClassName: WechatSNSUser
 * @Description: 
 * @author 
 * 2018年5月31日  tck 创建
 */
@Data
public class WechatSNSUser {
	private String openId;			//用户标识
	private String nickName;		//昵称
	private String sex;				//性别,0未知,1男,2女
	private String country;			//国家
	private String province;		//省份
	private String city;			//城市
	private String headImgUrl;		//头像链接
	private String unionid;			//用户全应用的标识
}

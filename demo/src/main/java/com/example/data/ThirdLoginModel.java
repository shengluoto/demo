package com.example.data;

import lombok.Data;

/**
 * 第三方登录jsonStr的处理模型
 * @ClassName: ThirdLoginModel
 * @Description: 
 * @author 
 * 2018年1月11日  tck 创建
 */
@Data
public class ThirdLoginModel {
	private String thirdLoginAcct;   		// 第三方账号
	private String thirdLoginType;		    // 第三方账号类型，wechat，qq
	private String nickName;		        // 第三方账号昵称
	private String phone;		    		// 手机号码
	private String password;		    	// 密码
//	private String customerId;		    	// 客户id
	private String customerName;		    // 客户名
}

package com.example.data;

import com.example.util.Parameter;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 第三方登录的字段提取
 * @ClassName: ThirdLoginParamAndPropModel
 * @Description: 
 * @author 
 * 2018年1月11日  tck 创建
 */
@Data
@NoArgsConstructor
public class ThirdLoginParamAndAcctModel {
	private Parameter parameter;   			// 查询字段
	private String accountProp;		// 查询字段拼接
	
	public ThirdLoginParamAndAcctModel(Parameter parameter, String accountProp) {
		this.parameter = parameter;
		this.accountProp = accountProp;
	}
}

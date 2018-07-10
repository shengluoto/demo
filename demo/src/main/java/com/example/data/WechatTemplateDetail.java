package com.example.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信模版消息对象的详情
 * @ClassName: WechatTemplateDetail
 * @Description: 
 * @author 
 * 2018年5月31日  tck 创建
 */
@Data
@NoArgsConstructor
public class WechatTemplateDetail {
	private String color; //颜色
	private String value; //内容
	
	public WechatTemplateDetail(String color, String value) {
		super();
		this.color = color;
		this.value = value;
	}
}

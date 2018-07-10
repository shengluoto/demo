package com.example.data;

import java.util.Map;

import lombok.Data;

/**
 * 微信模版消息对象
 * @ClassName: WechateTemplate
 * @Description: 
 * @author 
 * 2018年5月31日  tck 创建
 */
@Data
public class WechateTemplate {
	private String touser; 									//发送给的用户
	private String template_id;								//消息模版id
	private String url;										//重定向地址
	private Map<String, WechatTemplateDetail> data;			//详细信息
}

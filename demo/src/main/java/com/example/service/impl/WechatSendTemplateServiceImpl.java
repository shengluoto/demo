package com.example.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.data.WechatTemplateDetail;
import com.example.data.WechateTemplate;
import com.example.enums.CommonConfigEnum;
import com.example.exception.ResultException;
import com.example.service.CommonConfigService;
import com.example.service.WechatSendTemplateService;
import com.example.service.WechatTokenService;
import com.example.util.Encodes;
import com.example.util.OkHttpUtil;
import com.example.util.ResponseUtils;

/**
 * 发送微信的模版消息
 * @ClassName: SendWechatTemplateService
 * @Description: 
 * @author 
 * 2018年5月30日  tck 创建
 */
@Service
public class WechatSendTemplateServiceImpl implements WechatSendTemplateService {
	
	@Autowired
	private WechatTokenService wechatTokenService;
	
	@Autowired
	private CommonConfigService configService;

	/**
	 * 发送模版消息jsonStr{toUser:用户逗号分隔,title:标题,dataStr:日期}
	 * @param jonsStr
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年5月30日 tck 创建
	 */
	@Override
	public Object doSendTemplateInfo(String jsonStr, String loginName, String token, String clientType) {
		// jsonStr为空，直接返回
//		if(ChkUtil.isEmpty(jsonStr)){
//			throw new ResultException(ResultEnum.SAVEDATA_NULL,token,loginName,clientType);
//		}
		// loginName为空，直接返回
//		if(ChkUtil.isEmpty(loginName)){
//			throw new ResultException(ResultEnum.USERID_NULL,token,loginName,clientType);
//		}
		// 解析json格式数据
		JSONObject json = new JSONObject();
//		try{
//			json=(JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr));
//		}catch(Exception e){
//			throw new ResultException(ResultEnum.FORMAT_ERROR,token,loginName,clientType);
//		}
		String[] toUserArray = String.valueOf(json.get("touser")).split(",");
		String title = "您好!有事件需处理如下:";
//		String content = String.valueOf(json.get("title"));
//		String title = String.valueOf(json.get("title"));
		String content = String.valueOf(json.get("title")+"，"+json.get("content"));
		String dateStr = String.valueOf(json.get("strDate"));
//		String taskApplyId = String.valueOf(json.get("taskApplyId"));
		// 发送模版信息
		int sendCount = toUserArray.length;
		String body = null;
		String accessToken = "";
		try {
			for (int i=0; i<sendCount; i++) {
				body = setTemplateInfo(toUserArray[i], title, content, dateStr, loginName, accessToken, clientType);
				accessToken = wechatTokenService.getAccessToken(loginName, token, clientType);
				String responseStr = OkHttpUtil.postJsonParams("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken, body);
				// 解析json格式数据判断发送是否成功
				JSONObject responseJson = JSON.parseObject(Encodes.unescapeHtml(responseStr));
				if(!"0".equals(responseJson.get("errcode").toString())){
					throw new ResultException(-2, "微信模板消息发送失败:"+responseJson.get("errmsg"), token, loginName, clientType);
				}
			}
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		return null;
	}

	/**
	 * 把信息放入微信要求的模版格式的json中
	 * @param toUser 发送给的用户
	 * @param title 标题
	 * @param content 内容
	 * @param dateStr 时间
	 * @return
	 * @author
	 * 2018年5月30日 tck 创建
	 */
	private String setTemplateInfo(String toUser, String title, String content, String dateStr, String loginName, String token, String clientType){
		List<String> keyLisy = Arrays.asList(CommonConfigEnum.WXPUBLICTEMPLATEID.getKey(),
			    							 CommonConfigEnum.WXPUBLICAPPID.getKey(),
			    							 CommonConfigEnum.WXPUBLICAUTHSCOPE.getKey(),
			    							 CommonConfigEnum.WXPUBLICJUMPURL.getKey());
		Map<String,String> configMap = configService.doGetCommonConfigInfo(keyLisy, loginName, token, clientType);
		WechateTemplate wt = new WechateTemplate();
		// 设置发送给的用户
		wt.setTouser(toUser);
		// 设置选择模版
		wt.setTemplate_id(configMap.get(CommonConfigEnum.WXPUBLICTEMPLATEID.getKey()));
		// 设置点击模版消息时跳转的url
		String encodeRedirectUrl = "";
		try {
			encodeRedirectUrl = URLEncoder.encode(configMap.get(CommonConfigEnum.WXPUBLICJUMPURL.getKey()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new ResultException(-2, "URL编码失败", token, loginName, clientType);
		}
		StringBuilder url = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
									.append(configMap.get(CommonConfigEnum.WXPUBLICAPPID.getKey()))
									.append("&redirect_uri=")
									.append(encodeRedirectUrl)
									.append("&response_type=code")
									.append("&scope=")
									.append(configMap.get(CommonConfigEnum.WXPUBLICAUTHSCOPE.getKey()))
									.append("&state=STATE#wechat_redirect");
		wt.setUrl(url.toString());
		// 添加通知的详情
		Map<String,WechatTemplateDetail> detailMap = new HashMap<>();
		// 标题
		detailMap.put("first", new WechatTemplateDetail("#173177",title));
		// 内容
		detailMap.put("keyword1", new WechatTemplateDetail("#173177",content));
		// 时间
		detailMap.put("keyword2", new WechatTemplateDetail("#173177",dateStr));
		// 地点
		detailMap.put("keyword3", new WechatTemplateDetail("#173177",""));
		// 备注
		detailMap.put("remark", new WechatTemplateDetail("#173177","希望尽快处理!"));
		wt.setData(detailMap);
		return ResponseUtils.toJSONString(wt);
	}
}

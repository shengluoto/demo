package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.example.enums.CommonConfigEnum;
import com.example.exception.ResultException;
import com.example.service.CommonConfigService;
import com.example.task.WxReceiveMsgAsyncTask;
import com.example.util.ChkUtil;
import com.example.util.crypto.SHA1;
import com.example.util.crypto.WxCryptUtil;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatCoreController {
	
	@Autowired
	private CommonConfigService configService;
	
	@Autowired
	private WxReceiveMsgAsyncTask wxReceiveMsgAsyncTask;

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 首次接入验证
	 * @param signature
	 * @param nonce
	 * @param timestamp
	 * @param echostr
	 * @return
	 * @author
	 * 2018年5月31日 tck 创建
	 */
	@ApiOperation("首次接入验证")
	@GetMapping(value="/sign")
	public void doCheckSignature(String signature, String nonce, String timestamp, String echostr, HttpServletResponse response){
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		try {
//			response.setContentType("application/plain;charset=UTF-8");
			PrintWriter out = response.getWriter();
			if (checkSignature(signature, nonce, timestamp)) {
				log.info("微信接入成功");
				out.write(echostr);
			} else {
				log.info("微信接入失败");
				out.write("");
			}
			out.close();
		} catch (IOException e) {
			throw new ResultException(-3, e.getMessage(), null, null, null);
		}
	}
	
	/**
	 * 验证签名
	 * @param signature
	 * @param nonce
	 * @param timestamp
	 * @return
	 * @author
	 * 2018年5月31日 tck 创建
	 */
	private boolean checkSignature(String signature, String nonce, String timestamp) {
		String token = configService
							.doGetCommonConfigInfo(CommonConfigEnum.WXCHECKSERVERTOKEN.getKey(), null, null, null)
							.get(CommonConfigEnum.WXCHECKSERVERTOKEN.getKey());
		String sha1Str = SHA1.gen(token, timestamp, nonce);
		return ChkUtil.isEmpty(sha1Str) ? false:sha1Str.equals(signature);
	}
	
	/**
	 * 微信发送消息回执post验证服务器
	 * @return
	 * @author
	 * 2018年5月31日 tck 创建
	 */
	@PostMapping(value="/sign")
	public String doCheckSignatureAgain(String signature, String nonce, String timestamp, HttpServletRequest request, HttpServletResponse response){
		// 查询后台配置的参数
		List<String> keyLisy = Arrays.asList(CommonConfigEnum.WXCHECKSERVERTOKEN.getKey(),
				 							 CommonConfigEnum.WXPUBLICAPPID.getKey(),
				 							 CommonConfigEnum.WXENCODINGAESKEY.getKey(),
				 							 CommonConfigEnum.WXPUBLICWELCOME.getKey(),
				 							 CommonConfigEnum.WXPUBLICENCODE.getKey());
		Map<String,String> configMap = configService.doGetCommonConfigInfo(keyLisy, null, null, null);
		WxCryptUtil pc = new WxCryptUtil(configMap.get(CommonConfigEnum.WXCHECKSERVERTOKEN.getKey()),
					 			    	 configMap.get(CommonConfigEnum.WXENCODINGAESKEY.getKey()), 
					 			    	 configMap.get(CommonConfigEnum.WXPUBLICAPPID.getKey()));
	    try {
	    	// 解密微信发来的xml信息
	    	byte[] body = new byte[request.getContentLength()];
			IOUtils.readFully(request.getInputStream(), body);
			String encryptedXml = new String(body, "utf-8");
			String decryptedXml = pc.decrypt(signature, timestamp, nonce, encryptedXml);
			// xml解析解密后的消息
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse(new InputSource(new StringReader(decryptedXml)));
		    Element root = document.getDocumentElement();
		    String toUserName = root.getElementsByTagName("ToUserName").item(0).getTextContent();
		    String event = "";
		    if (!ChkUtil.isEmptyAllObject(root.getElementsByTagName("Event").item(0))) { // 获取加密的节点
		    	event = root.getElementsByTagName("Event").item(0).getTextContent();
		    }
		    String fromUserName = root.getElementsByTagName("FromUserName").item(0).getTextContent();
		    String createTime = root.getElementsByTagName("CreateTime").item(0).getTextContent();
		    // 消息去重
		    if (ChkUtil.isEmpty(redisTemplate.opsForValue().get(fromUserName+createTime))) {
		    	redisTemplate.opsForValue().set(fromUserName+createTime, "1", 6L, TimeUnit.SECONDS);
		    } else {
		    	return null;
		    }
		    // 若是关注操作则显示欢迎信息
		    response.setCharacterEncoding("utf-8");
		    response.setContentType("application/xml;charset=UTF-8");
		    PrintWriter out = response.getWriter(); 
		    String textResponseXml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>createTime</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[responseMsg]]></Content></xml>";
		    if ("subscribe".equals(event)) {
		    	// 异步处理把用户的openId获取uuid后查询,把公共号对应的openId反写进用户信息表(为了推送流程审批消息用)
		    	wxReceiveMsgAsyncTask.doExcuteAsyncSubscribeTask(fromUserName);
		    	textResponseXml = textResponseXml.replace("toUser", fromUserName)
		    				   					 .replace("fromUser", toUserName)
		    				   					 .replace("createTime", Long.toString(System.currentTimeMillis() / 1000L))
		    				   					 .replace("responseMsg", configMap.get(CommonConfigEnum.WXPUBLICWELCOME.getKey()));
		    } else if ("unsubscribe".equals(event)) {
		    	// 异步处理把用户的openId清除openId
		    	wxReceiveMsgAsyncTask.doExcuteAsyncUnSubscribeTask(fromUserName);
		    } else {
		    	textResponseXml = textResponseXml.replace("toUser", fromUserName)
		    			.replace("fromUser", toUserName)
		    			.replace("createTime", Long.toString(System.currentTimeMillis() / 1000L))
		    			.replace("responseMsg", "该功能尚未开发,敬请期待!");
		    }
		    //正式接入时设置为加密模式后,返回xml的需加密,"1"为明文传输,其他为加密传输
		    if ("1".equals(configMap.get(CommonConfigEnum.WXPUBLICENCODE.getKey()))) {
		    	out.write(textResponseXml);
		    } else {
		    	out.write(pc.encrypt(textResponseXml));
		    }
		    out.close();
	    } catch (SAXException e) {
			throw new ResultException(-3, e.getMessage(), null, null, null);
	    } catch (ParserConfigurationException e) {
			throw new ResultException(-3, e.getMessage(), null, null, null);
		} catch (UnsupportedEncodingException e) {
			throw new ResultException(-3, e.getMessage(), null, null, null);
		} catch (IOException e) {
			throw new ResultException(-3, e.getMessage(), null, null, null);
		}
	    return null;
	}
}
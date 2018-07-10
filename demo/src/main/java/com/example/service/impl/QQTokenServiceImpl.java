package com.example.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.constant.FixValueConstant;
import com.example.constant.RedisConstant;
import com.example.enums.CommonConfigEnum;
import com.example.exception.ResultException;
import com.example.service.CommonConfigService;
import com.example.service.QQTokenService;
import com.example.util.ChkUtil;
import com.example.util.Encodes;
import com.example.util.OkHttpUtil;

/**
 * QQ第三方登录token和用户信息获取方法
 * @ClassName: WechatTokenServiceImpl
 * @Description: 
 * @author 
 * 2018年5月30日  tck 创建
 */
@Service
public class QQTokenServiceImpl implements QQTokenService {
	
	@Autowired
	private FixValueConstant wc;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private CommonConfigService configService;
	
	/**
	 * 获取网页授权的token
	 * @param code 回调携带的code
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年5月31日 tck 创建
	 */
	public Object getOAuthTokenAndOpenId(String code, String loginName, String token, String clientType){
		if (ChkUtil.isEmpty(code)) {
			throw new ResultException(-2, "授权凭证不能为空", token, loginName, clientType);
		}
		Map<String,String> map = new HashMap<>();
		List<String> keyLisy = Arrays.asList(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey(),
			    							 CommonConfigEnum.QQTHIRDLOGINAPPSECRET.getKey(),
			    							 CommonConfigEnum.THIRDLOGINREDIRECTURI.getKey());
		Map<String,String> configMap = configService.doGetCommonConfigInfo(keyLisy, loginName, token, clientType);
		try {
			// 获取authToken
			String authTokenUrl = wc.qqAccessTokenUrl
					   	   			  .replace("APPID", configMap.get(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey()))
					   	   			  .replace("APPSECRET", configMap.get(CommonConfigEnum.QQTHIRDLOGINAPPSECRET.getKey()))
					   	   			  .replace("CODE", code)
					   	   			  .replace("REDIRECTURI", configMap.get(CommonConfigEnum.THIRDLOGINREDIRECTURI.getKey()));
//			HttpURLConnection conn = IMHttpUtil.CreateGetHttpConnection(authTokenUrl);
//			// 解析json格式数据
//			String response = Encodes.unescapeHtml(IMHttpUtil.returnResult(conn));
			String conn = OkHttpUtil.get(authTokenUrl, null);
			String response = Encodes.unescapeHtml(conn);
			// 错误信息为json格式
			if (response.contains("error")) {
				String[] error = StringUtils.substringsBetween(response, "(", ")");
				JSONObject errorJson = JSON.parseObject(error[0]);
				throw new ResultException(-2, "获取QQ登录token失败:"+errorJson.get("error_description"), token, loginName, clientType);
			}
			// 正确信息为字符串access_token=123&expires_in=7776000&refresh_token=123,好变态
			String[] tokenArray = StringUtils.split(response, "&");
			String accessToken = StringUtils.split(tokenArray[0], "=")[1];
			String expires = StringUtils.split(tokenArray[1], "=")[1];
//			String refreshToken = StringUtils.split(tokenArray[2], "=")[1];
			// 获取用户openId
			String opeonIdUrl = wc.qqOpenIDUrl.replace("ACCESS_TOKEN", accessToken);
//			HttpURLConnection conn2 = IMHttpUtil.CreateGetHttpConnection(opeonIdUrl);
//			// 解析json格式数据
//			String openIdResp = Encodes.unescapeHtml(IMHttpUtil.returnResult(conn2));
			String conn2 = OkHttpUtil.get(opeonIdUrl, null);
			String openIdResp = Encodes.unescapeHtml(conn2);
			String[] openIdArray = StringUtils.substringsBetween(openIdResp, "(", ")");
			JSONObject openIdJson = JSON.parseObject(openIdArray[0]);
			// 校验获取是否正确
			if (openIdResp.contains("error")) {
				throw new ResultException(-2, "获取QQ用户信息失败:"+openIdJson.get("error_description"), token, loginName, clientType);
			}
			String openId = openIdJson.getString("openid");
			map.put("openId", openId);
			map.put("uuid", openIdJson.getString("unionid"));
			// auth_token和auth_refresh_token放入缓存
			redisTemplate.opsForValue()
				.set(String.format(RedisConstant.QQ_OAUTH_TOKEN_PREFIX,openId), accessToken, Long.parseLong(expires), TimeUnit.SECONDS);
//			int refreshTokenExpire = Integer.parseInt(configMap.get(CommonConfigEnum.WXPUBLICREFRESHTOKENEXPIRE.getKey()));
//			redisTemplate.opsForValue()
//				.set(String.format(RedisConstant.OAUTH_REFRESH_TOKEN_PREFIX,openId), refreshToken, refreshTokenExpire, TimeUnit.DAYS);
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		return map;
	}
	
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
	public Object refreshOAuthToken(String refreshToken, String loginName, String token, String clientType) {
		Map<String,String> map = new HashMap<>();
		String appId = configService.doGetCommonConfigInfo(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey(), loginName, token, clientType)
									.get(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey());
		try {
			// 获取刷新后的accessToken
			String url = wc.wechatOAuthRefreshTokenUrl
						   .replace("APPID", appId)
						   .replace("REFRESH_TOKEN", refreshToken);
			String conn = OkHttpUtil.get(url, null);
			String response = Encodes.unescapeHtml(conn);
			// 解析json格式数据
			JSONObject json = JSON.parseObject(Encodes.unescapeHtml(response));
			// 校验获取是否正确
			if (!ChkUtil.isEmpty(json.get("errcode"))) {
				throw new ResultException(-2, "刷新网页授权token失败:"+json.get("errmsg"), token, loginName, clientType);
			}
			String accessToken = json.getString("access_token");
			String openId = json.getString("openid");
			map.put("openId", openId);
			map.put("accessToken", accessToken);
			// auth_token放入缓存
			redisTemplate.opsForValue()
				.set(String.format(RedisConstant.OAUTH_TOKEN_PREFIX,openId), accessToken, json.getLong("expires_in"), TimeUnit.SECONDS);
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		return map;
	}
	
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
	public Object getUserInfoByOAuth(String openId, String loginName, String token, String clientType){
		Map<String,Object> map = new HashMap<>();
		String appId = configService.doGetCommonConfigInfo(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey(), loginName, token, clientType)
									.get(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey());
		try {
			if (ChkUtil.isEmpty(openId)) {
				throw new ResultException(-2, "用户标识不能为空", token, loginName, clientType);
			}
			String accessToken = redisTemplate.opsForValue().get(String.format(RedisConstant.QQ_OAUTH_TOKEN_PREFIX,openId));
			if (ChkUtil.isEmpty(accessToken)) {
//				// 尝试获取刷新token
//				String refreshToken = redisTemplate.opsForValue().get(String.format(RedisConstant.QQ_OAUTH_REFRESH_TOKEN_PREFIX,openId));
//				if (!ChkUtil.isEmpty(refreshToken)) {
//					Map<String,String> result = (Map<String,String>)refreshOAuthToken(refreshToken, loginName, token, clientType);
//					accessToken = result.get("accessToken");
//				} else {
					throw new ResultException(-2, "获取QQ授权用户的token失败!请重新授权!", token, loginName, clientType);
//				}
			}
			// 获取用户信息
			String url = wc.qqUserInfoUrl
						   .replace("ACCESS_TOKEN", accessToken)
						   .replace("OPENID", openId)
						   .replace("APPID", appId);
			String conn = OkHttpUtil.get(url, null);
			String response = Encodes.unescapeHtml(conn);
			// 解析json格式数据
			JSONObject json = JSON.parseObject(Encodes.unescapeHtml(response));
			// 校验获取是否正确
			if (json.getInteger("ret") < 0) {
				throw new ResultException(-2, "获取QQ授权用户的信息失败:"+json.get("msg"), token, loginName, clientType);
			}
			map.put("uuid", String.valueOf(json.get("uuid")));
			map.put("nickname", String.valueOf(json.get("nickname")));
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		return map;
	}

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
	public Object getUserInfoByCode(String code, String loginName, String token, String clientType){
		if (ChkUtil.isEmpty(code)) {
			throw new ResultException(-2, "授权凭证不能为空", token, loginName, clientType);
		}
		Map<String,String> map = new HashMap<>();
		List<String> keyLisy = Arrays.asList(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey(),
			    							 CommonConfigEnum.QQTHIRDLOGINAPPSECRET.getKey(),
			    							 CommonConfigEnum.THIRDLOGINREDIRECTURI.getKey());
		Map<String,String> configMap = configService.doGetCommonConfigInfo(keyLisy, loginName, token, clientType);
		try {
			// 获取authToken
			String authTokenUrl = wc.qqAccessTokenUrl
					   	   			  .replace("APPID", configMap.get(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey()))
					   	   			  .replace("APPSECRET", configMap.get(CommonConfigEnum.QQTHIRDLOGINAPPSECRET.getKey()))
					   	   			  .replace("CODE", code)
					   	   			  .replace("REDIRECTURI", configMap.get(CommonConfigEnum.THIRDLOGINREDIRECTURI.getKey()));
//			HttpURLConnection conn = IMHttpUtil.CreateGetHttpConnection(authTokenUrl);
//			// 解析json格式数据
//			String response = Encodes.unescapeHtml(IMHttpUtil.returnResult(conn));
			String conn = OkHttpUtil.get(authTokenUrl, null);
			String response = Encodes.unescapeHtml(conn);
			// 错误信息为json格式
			if (response.contains("error")) {
				String[] error = StringUtils.substringsBetween(response, "(", ")");
				JSONObject errorJson = JSON.parseObject(error[0]);
				throw new ResultException(-2, "获取QQ登录token失败:"+errorJson.get("error_description"), token, loginName, clientType);
			}
			// 正确信息为字符串access_token=123&expires_in=7776000&refresh_token=123,好变态
			String[] tokenArray = StringUtils.split(response, "&");
			String accessToken = StringUtils.split(tokenArray[0], "=")[1];
			// 获取用户openId
			String opeonIdUrl = wc.qqOpenIDUrl.replace("ACCESS_TOKEN", accessToken);
//			HttpURLConnection conn2 = IMHttpUtil.CreateGetHttpConnection(opeonIdUrl);
//			// 解析json格式数据
//			String openIdResp = Encodes.unescapeHtml(IMHttpUtil.returnResult(conn2));
			String conn2 = OkHttpUtil.get(opeonIdUrl, null);
			String openIdResp = Encodes.unescapeHtml(conn2);
			String[] openIdArray = StringUtils.substringsBetween(openIdResp, "(", ")");
			JSONObject openIdJson = JSON.parseObject(openIdArray[0]);
			// 校验获取是否正确
			if (openIdResp.contains("error")) {
				throw new ResultException(-2, "获取QQ用户信息失败:"+openIdJson.get("error_description"), token, loginName, clientType);
			}
			String openId = openIdJson.getString("openid");
			map.put("uuid", openIdJson.getString("unionid"));
			// 获取用户信息
			String url = wc.qqUserInfoUrl
						   .replace("ACCESS_TOKEN", accessToken)
						   .replace("OPENID", openId)
						   .replace("APPID", configMap.get(CommonConfigEnum.QQTHIRDLOGINAPPID.getKey()));
//			HttpURLConnection conn3 = IMHttpUtil.CreateGetHttpConnection(url);
//			// 解析json格式数据
//			JSONObject json = JSON.parseObject(Encodes.unescapeHtml(IMHttpUtil.returnResult(conn3)));
			String conn3 = OkHttpUtil.get(url, null);
			// 解析json格式数据
			JSONObject json = JSON.parseObject(Encodes.unescapeHtml(conn3));
			// 校验获取是否正确
			if (json.getInteger("ret") < 0) {
				throw new ResultException(-2, "获取QQ授权用户的信息失败:"+json.get("msg"), token, loginName, clientType);
			}
			map.put("nickName", String.valueOf(json.get("nickname")));
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		return map;
	}
		
}

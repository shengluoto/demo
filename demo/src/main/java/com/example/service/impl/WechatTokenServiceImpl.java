package com.example.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.constant.FixValueConstant;
import com.example.constant.RedisConstant;
import com.example.data.TokenParamModel;
import com.example.data.WechatSNSUser;
import com.example.enums.CommonConfigEnum;
import com.example.exception.ResultException;
import com.example.service.CommonConfigService;
import com.example.service.WechatTokenService;
import com.example.util.ChkUtil;
import com.example.util.Encodes;
import com.example.util.OkHttpUtil;

/**
 * 微信中各个token的获取方法
 * @ClassName: WechatTokenServiceImpl
 * @Description: 
 * @author 
 * 2018年5月30日  tck 创建
 */
@Service
public class WechatTokenServiceImpl implements WechatTokenService {
	
	@Autowired
	private FixValueConstant wc;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private CommonConfigService configService;

	@Autowired
	private RedissonClient redisson;
	
	/**
	 * 获取公共号的token(调用公共号的各接口需带上该token)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年5月30日 tck 创建
	 */
	public String getAccessToken(String loginName, String token, String clientType){
		List<String> keyLisy = Arrays.asList(CommonConfigEnum.WXPUBLICTOKENRENEWAL.getKey(),
											 CommonConfigEnum.WXPUBLICAPPID.getKey(),
											 CommonConfigEnum.WXPUBLICAPPSECRET.getKey());
		Map<String,String> configMap = configService.doGetCommonConfigInfo(keyLisy, loginName, token, clientType);
		// 加锁
		RLock lock = redisson.getLock("anyLock");
		lock.lock();
		String accessToken = redisTemplate.opsForValue().get("wxPublicToken");
		try {
			// 为空或者小于规定的存活时间就重新获取token
			Long tokenRenewal = Long.parseLong(configMap.get(CommonConfigEnum.WXPUBLICTOKENRENEWAL.getKey()));
			if (ChkUtil.isEmpty(accessToken) || redisTemplate.getExpire("wxPublicToken") < tokenRenewal) {
				String url = wc.wechatPublicPlatTokenUrl
							   .replace("APPID", configMap.get(CommonConfigEnum.WXPUBLICAPPID.getKey()))
							   .replace("APPSECRET", configMap.get(CommonConfigEnum.WXPUBLICAPPSECRET.getKey()));
				String conn = OkHttpUtil.get(url, null);
				// 解析json格式数据
				JSONObject json = JSON.parseObject(Encodes.unescapeHtml(conn));
				// 校验获取是否正确
				if (!ChkUtil.isEmpty(json.get("errcode"))) {
					throw new ResultException(-2, "获取微信公共号token失败:"+json.get("errmsg"), token, loginName, clientType);
				}
				// 放入缓存
				accessToken = json.getString("access_token");
				redisTemplate.opsForValue().set("wxPublicToken", accessToken, json.getLong("expires_in"), TimeUnit.SECONDS);
			}
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		lock.unlock();
		return accessToken;
	}
	
	/**
	 * 获取网页授权的token
	 * @param code 回调携带的code
	 * @param param 链接的参数
	 * @param type web端授权,还是微信端的授权(web,wx)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月6日 tck 创建
	 */
	public Object getOAuthToken(String code, TokenParamModel param, String type, String loginName, String token, String clientType){
		if (ChkUtil.isEmpty(code)) {
			throw new ResultException(-2, "授权凭证不能为空", token, loginName, clientType);
		}
		Map<String,String> map = new HashMap<>();
		try {
			if ("authdeny".equals(code)) {
				throw new ResultException(-2, "获取微信网页授权时传入的code不能为空", token, loginName, clientType);
			}
			// 获取accessToken
			String url = wc.wechatOAuthTokenUrl
						   .replace("APPID", param.getAppId())
						   .replace("APPSECRET", param.getAppSecret())
						   .replace("CODE", code);
			String conn = OkHttpUtil.get(url, null);
			// 解析json格式数据
			JSONObject json = JSON.parseObject(Encodes.unescapeHtml(conn));
			// 校验获取是否正确
			if (!ChkUtil.isEmpty(json.get("errcode"))) {
				throw new ResultException(-2, "获取微信网页授权token失败:"+json.get("errmsg"), token, loginName, clientType);
			}
			String accessToken = json.getString("access_token");
			String refreshToken = json.getString("refresh_token");
			String openId = json.getString("openid");
			map.put("openId", openId);
			map.put("accessToken", accessToken);
			// auth_token和auth_refresh_token放入缓存
			redisTemplate.opsForValue()
				.set(String.format(RedisConstant.OAUTH_TOKEN_PREFIX+type,openId), accessToken, json.getLong("expires_in"), TimeUnit.SECONDS);
			int refreshTokenExpire = Integer.parseInt(param.getRefreshTokenExpire());
			redisTemplate.opsForValue()
				.set(String.format(RedisConstant.OAUTH_REFRESH_TOKEN_PREFIX+type,openId), refreshToken, refreshTokenExpire, TimeUnit.DAYS);
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		return map;
	}
	
	/**
	 * 刷新网页授权的token
	 * @param refreshToken
	 * @param appId
	 * @param type web端授权,还是微信端的授权(web,wx)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月6日 tck 创建
	 */
	public Object refreshOAuthToken(String refreshToken, String appId, String type, String loginName, String token, String clientType) {
		Map<String,String> map = new HashMap<>();
		try {
			// 获取刷新后的accessToken
			String url = wc.wechatOAuthRefreshTokenUrl
						   .replace("APPID", appId)
						   .replace("REFRESH_TOKEN", refreshToken);
			String conn = OkHttpUtil.get(url, null);
			// 解析json格式数据
			JSONObject json = JSON.parseObject(Encodes.unescapeHtml(conn));
			// 校验获取是否正确
			if (!ChkUtil.isEmpty(json.get("errcode"))) {
				throw new ResultException(-2, "刷新微信网页授权token失败:"+json.get("errmsg"), token, loginName, clientType);
			}
			String accessToken = json.getString("access_token");
			String openId = json.getString("openid");
			map.put("openId", openId);
			map.put("accessToken", accessToken);
			// auth_token放入缓存
			redisTemplate.opsForValue()
				.set(String.format(RedisConstant.OAUTH_TOKEN_PREFIX+type,openId), accessToken, json.getLong("expires_in"), TimeUnit.SECONDS);
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
	 * @param appId
	 * @param type web端授权,还是微信端的授权(web,wx)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月6日 tck 创建
	 */
	@SuppressWarnings("unchecked")
	public Object getUserInfoByOAuth(String openId, String appId, String type, String loginName, String token, String clientType){
		Map<String,Object> map = new HashMap<>();
		try {
			if (ChkUtil.isEmpty(openId)) {
				throw new ResultException(-2, "用户标识不能为空", token, loginName, clientType);
			}
			String accessToken = redisTemplate.opsForValue().get(String.format(RedisConstant.OAUTH_TOKEN_PREFIX+type,openId));
			if (ChkUtil.isEmpty(accessToken)) {
				// 尝试获取刷新token
				String refreshToken = redisTemplate.opsForValue().get(String.format(RedisConstant.OAUTH_REFRESH_TOKEN_PREFIX+type,openId));
				if (!ChkUtil.isEmpty(refreshToken)) {
					Map<String,String> result = (Map<String,String>)refreshOAuthToken(refreshToken, appId, type, loginName, token, clientType);
					accessToken = result.get("accessToken");
				} else {
					throw new ResultException(-2, "获取微信授权用户的token失败!请重新授权!", token, loginName, clientType);
				}
			}
			// 获取用户信息
			String url = wc.wechatOAuthUserInfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
			String conn = OkHttpUtil.get(url, null);
			// 解析json格式数据
			JSONObject json = JSON.parseObject(Encodes.unescapeHtml(conn));
			// 校验获取是否正确
			if (!ChkUtil.isEmpty(json.get("errcode"))) {
				throw new ResultException(-2, "获取微信授权用户的信息失败:"+json.get("errmsg"), token, loginName, clientType);
			}
			WechatSNSUser wss = new WechatSNSUser();
			wss.setOpenId(String.valueOf(json.get("openid")));
			wss.setNickName(String.valueOf(json.get("nickname")));
			wss.setSex(String.valueOf(json.get("sex")));
			wss.setProvince(String.valueOf(json.get("province")));
			wss.setCity(String.valueOf(json.get("city")));
			wss.setCountry(String.valueOf(json.get("country")));
			wss.setHeadImgUrl(String.valueOf(json.get("headimgurl")));
			wss.setUnionid(String.valueOf(json.get("unionid")));
			map.put("userInfo", wss);
			map.put("uuid", String.valueOf(json.get("unionid")));
			map.put("nickname", String.valueOf(json.get("nickname")));
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		return map;
	}

	/**
	 * 根据code直接获取网页授权的用户信息
	 * @param param
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月7日 tck 创建
	 */
	public Object getUserInfoByCode(TokenParamModel param,String loginName, String token, String clientType){
		if (ChkUtil.isEmpty(param.getCode())) {
			throw new ResultException(-2, "授权凭证不能为空", token, loginName, clientType);
		}
		Map<String,String> map = new HashMap<>();
		try {
			if ("authdeny".equals(param.getCode())) {
				throw new ResultException(-2, "获取微信网页授权时传入的code不能为空", token, loginName, clientType);
			}
			// 获取accessToken
			String tokenUrl = wc.wechatOAuthTokenUrl
							    .replace("APPID", param.getAppId())
							    .replace("APPSECRET", param.getAppSecret())
							    .replace("CODE", param.getCode());
			String tokenConn = OkHttpUtil.get(tokenUrl, null);
			// 解析json格式数据
			JSONObject tokenJson = JSON.parseObject(Encodes.unescapeHtml(tokenConn));
			// 校验获取是否正确
			if (!ChkUtil.isEmpty(tokenJson.get("errcode"))) {
				throw new ResultException(-2, "获取微信网页授权token失败:"+tokenJson.get("errmsg"), token, loginName, clientType);
			}
			String accessToken = tokenJson.getString("access_token");
			String openId = tokenJson.getString("openid");
			// 获取用户信息
			String userInfoUrl = wc.wechatOAuthUserInfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
			String userInfoConn = OkHttpUtil.get(userInfoUrl, null);
			// 解析json格式数据
			JSONObject userInfoJson = JSON.parseObject(Encodes.unescapeHtml(userInfoConn));
			// 校验获取是否正确
			if (!ChkUtil.isEmpty(userInfoJson.get("errcode"))) {
				throw new ResultException(-2, "获取微信授权用户的信息失败:"+userInfoJson.get("errmsg"), token, loginName, clientType);
			}
			map.put("uuid", String.valueOf(userInfoJson.get("unionid")));
			map.put("nickName", String.valueOf(userInfoJson.get("nickname")));
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, loginName, clientType);
		}
		return map;
	}
}

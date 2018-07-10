package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.constant.FixValueConstant;
import com.example.enums.ResultEnum;
import com.example.exception.ResultException;
import com.example.service.WechatTokenService;
import com.example.service.WxReceiveMsgService;
import com.example.util.ChkUtil;
import com.example.util.Encodes;
import com.example.util.OkHttpUtil;

@Service
public class WxReceiveMsgServiceImpl implements WxReceiveMsgService {

	@Autowired
	private FixValueConstant wc;
	
	@Autowired
	private WechatTokenService wechatTokenService;
	
//	@Autowired
//	private UserService userService;
	
	/**
	 * 把用户的openId获取uuid后查询,把公共号对应的openId反写进用户信息表(为了推送流程审批消息用)
	 * @param openId
	 * @return
	 * @author
	 * 2018年6月4日 tck 创建
	 */
	@Transactional
	public Object doSaveWxUserInfo(String openId) {
		try{
			if (ChkUtil.isEmpty(openId)) {
				throw new ResultException(-2, "用户标识不能为空", null, null, null);
			}
			// 获取公共号的token
			String accessToken = wechatTokenService.getAccessToken(null, null, null);
			// 获取用户信息
			String url = wc.wechatPublicUserInfoUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
			String conn = OkHttpUtil.get(url, null);
			// 解析json格式数据
			JSONObject json = JSON.parseObject(Encodes.unescapeHtml(conn));
			// 校验获取是否正确
			if (!ChkUtil.isEmpty(json.get("errcode"))) {
				throw new ResultException(-2, "获取公共号用户的信息失败:"+json.get("errmsg"), null, null, null);
			}
			String unionId = String.valueOf(json.get("unionid"));
			// 根据uuid查询已绑定微信第三方的账户,并反写微信公共号的openId
			if (!ChkUtil.isEmpty(unionId)) {
//				Parameter param = new Parameter();
//				param.put("unionId", unionId);
//				List<User> userList = userService.find(" from User where state<>'1' and wechatThirdLoginId=:unionId", param);
//				if (!ChkUtil.isEmptyAllObject(userList)) {
//					List<User> updateUserOpenIdList = new ArrayList<>(userList.size()); 
//					for (User user : userList) {
//						user.setMicroMessage(openId);
//						user.setLastUpdateTime(new Date());
//						updateUserOpenIdList.add(user);
//					}
//					userService.save(updateUserOpenIdList);
//				}
			}
		} catch (JSONException e) {
			throw new ResultException(ResultEnum.FORMAT_ERROR, null, null, null);
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), null, null, null);
		}
		return null;
	}
	
	/**
	 * 用户的openId清除openId
	 * @param openId
	 * @return
	 * @author
	 * 2018年6月4日 tck 创建
	 */
	@Transactional
	public Object doDeleteWxUserInfo(String openId) {
		try{
			if (ChkUtil.isEmpty(openId)) {
				throw new ResultException(-2, "用户标识不能为空", null, null, null);
			}
//			Parameter param = new Parameter();
//			param.put("openId", openId);
//			List<User> userList = userService.find(" from User where state<>'1' and microMessage=:openId", param);
//			if (!ChkUtil.isEmptyAllObject(userList)) {
//				List<User> updateUserOpenIdList = new ArrayList<>(userList.size()); 
//				for (User user : userList) {
//					user.setMicroMessage("");
//					user.setLastUpdateTime(new Date());
//					updateUserOpenIdList.add(user);
//				}
//				userService.save(updateUserOpenIdList);
//			}
		} catch (JSONException e) {
			throw new ResultException(ResultEnum.FORMAT_ERROR, null, null, null);
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), null, null, null);
		}
		return null;
	}
}

package com.example.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.data.ThirdLoginModel;
import com.example.data.ThirdLoginParamAndAcctModel;
import com.example.data.TokenParamModel;
import com.example.entity.User;
import com.example.enums.CommonConfigEnum;
import com.example.enums.ResultEnum;
import com.example.enums.ThirdLoginFieldEnum;
import com.example.enums.ThirdLoginReturnEnum;
import com.example.exception.ResultException;
import com.example.service.CommonConfigService;
import com.example.service.QQTokenService;
import com.example.service.WechatTokenService;
import com.example.util.ChkUtil;
import com.example.util.Encodes;
import com.example.util.OptionUtil;
import com.example.util.Parameter;

import io.vavr.control.Try;

@Service
public class WechatService {

	@Autowired
	private WechatTokenService wechatTokenService;
	
	@Autowired
	private QQTokenService qqTokenService;
	
	@Autowired
	private CommonConfigService configService;
	
	/**
	 * 根据第三方登录的code获取accessToken
	 * @param jsonStr{code:code,state:状态码,thirdLoginType：第三方登录类型} 
	 * @author 
	 * 	2017-12-24 tck 新建
	 * 2017-06-12 shinry添加钉钉登陆
	 */
	@SuppressWarnings("all")
	public Object doGetThirgLoginCode(String thirdLoginType,String code, String loginName, String token, String clientType) {
		Map map = new HashMap();
		Map result = null;
		String openId = null;
		switch (thirdLoginType) {
			case "qq":
				result = (Map)qqTokenService.getUserInfoByCode(code, loginName, token, clientType);
				map.put("nickName", result.get("nickName"));
				map.put("thirdLoginAcct", result.get("uuid"));
				break;
			case "wechat":
				List<String> keyLisy = Arrays.asList(CommonConfigEnum.WXTHIRDLOGINAPPID.getKey(),
													 CommonConfigEnum.WXTHIRDLOGINAPPSECRET.getKey());
				Map<String,String> configMap = configService.doGetCommonConfigInfo(keyLisy, loginName, token, clientType);
				TokenParamModel param = new TokenParamModel();
				param.setAppId(configMap.get(CommonConfigEnum.WXTHIRDLOGINAPPID.getKey()));
				param.setAppSecret(configMap.get(CommonConfigEnum.WXTHIRDLOGINAPPSECRET.getKey()));
				param.setCode(code);
				result = (Map)wechatTokenService.getUserInfoByCode(param, loginName, token, clientType);
				map.put("nickName", result.get("nickName"));
				map.put("thirdLoginAcct", result.get("uuid"));
				break;
		}
		return map;
	}
	
	/**
	 * 第三方登录认证初始化接口
	 * @param clientType 客户端类型(android/ios/web等)
	 * @param jsonStr{thirdLoginAcct:第三方账号,thirdLoginType:第三方账号类型}
	 * @author 
	 * 	2018-1-10 tck 新建
	 */
	@SuppressWarnings("all")
	public Object doThirdLoginData(String clientType, String jsonStr) {
		Map map = new HashMap();
		// jsonStr空校验
		OptionUtil.of(jsonStr).getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL,null,null,clientType));
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				 .getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR,null,null,clientType));
		String thirdLoginAcct = ChkUtil.fieldCheck(json.get("thirdLoginAcct"),true,80, "第三方账号","", null, null, clientType);
		String thirdLoginType = ChkUtil.fieldCheck(json.get("thirdLoginType"),true,26, "第三方账号类型","", null, null, clientType);
		String nickName = ChkUtil.fieldCheck(json.get("nickName"),false,80, "昵称","", null, null, clientType);
		ThirdLoginModel thirdLoginModel = new ThirdLoginModel();
		thirdLoginModel.setThirdLoginAcct(thirdLoginAcct);
		thirdLoginModel.setThirdLoginType(thirdLoginType);
		thirdLoginModel.setNickName(nickName);
		// 根据第三方账号获取用户集合
		return map;
	}
	
	/**
	 * 根据第三方登录类型得到查询参数
	 * @param
	 * @return void
	 * @author
	 * 2018年1月11日  tck 创建
	 */
	private ThirdLoginParamAndAcctModel getThirdLoginQueryParam(String thirdLoginAcct, String thirdLoginType){
		Parameter parameter =new Parameter();
		String accountProp = "";
		// 绑定手机号对应的第三方账号
		for(ThirdLoginFieldEnum fieldEnum : ThirdLoginFieldEnum.values()) {
			// 账号类型枚举判断
			if (fieldEnum.getThirdLoginType().equals(thirdLoginType)) {
				// 账号对应的数据库字段提取
				if (fieldEnum.getThirdLoginType().equals(thirdLoginType)) {
					String thirdLoginAcctField = fieldEnum.getThirdLoginAcctField();
					parameter.put(thirdLoginAcctField, thirdLoginAcct);
					accountProp = " " + thirdLoginAcctField + "=:" + thirdLoginAcctField + " ";
					break;
				}
			}
		}
		ThirdLoginParamAndAcctModel tlpm = new ThirdLoginParamAndAcctModel(parameter, accountProp);
		return tlpm;
	}
	
	/**
	 * 第三方登录认证接口
	 * @param clientType 客户端类型(android/ios/web等)
	 * @param jsonStr{customerId:客户id,thirdLoginAcct:第三方账号,thirdLoginType:第三方账号类型}
	 * @author 
	 * 	2018-01-10 tck 新建
	 */
	@SuppressWarnings("all")
	public Object doThirdLoginInfo(String clientType, String jsonStr){
		Map map = new HashMap();
		// jsonStr空校验
		OptionUtil.of(jsonStr).getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL,null,null,clientType));
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				 .getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR,null,null,clientType));
		String customerId = ChkUtil.fieldCheck(json.get("customerId"), true, 50, "客户id", "", null, null, clientType);
		String thirdLoginAcct = ChkUtil.fieldCheck(json.get("thirdLoginAcct"), true, 80, "第三方账号", "", null, null, clientType);
		String thirdLoginType = ChkUtil.fieldCheck(json.get("thirdLoginType"), true, 26, "第三方账号类型", "", null, null, clientType);
		// 根据客户id和第三方账号，校验登录是否合法
		ThirdLoginParamAndAcctModel tlpm = getThirdLoginQueryParam(thirdLoginAcct, thirdLoginType);
		Parameter param = tlpm.getParameter();
		return null;
	}
	
	/**
	 * 绑定第三方账号接口
	 * @param clientType 客户端类型(android/ios/web等)
	 * @param jsonStr{phone:电话号码,password:密码,customerId:客户id,thirdLoginAcct:第三方账号,thirdLoginType:第三方账号类型}
	 * @return Object
	 * @author
	 * 2018年1月10日  tck 创建
	 */
	@SuppressWarnings("all")
	public Object doBindThirdAccount(String clientType, String jsonStr) {
		Map map = new HashMap();
		ThirdLoginModel thirdLoginModel = getThirdLoginModelByJsonStr(clientType, jsonStr);
		String thirdLoginAcct = thirdLoginModel.getThirdLoginAcct();
		String thirdLoginType = thirdLoginModel.getThirdLoginType();
		String phone = thirdLoginModel.getPhone();
		String password = thirdLoginModel.getPassword();
		String nickName = thirdLoginModel.getNickName();
		// 检验要绑定的电话、密码是否正确
		List<User> bindUserList = verifyIsLoginSuccess(clientType, phone, password);
		// 根据第三方账号获取已有用户集合，校验微信是否对应多个手机号
		List<User> userHasPhoneList = getUserListByThirdLoginAcct(thirdLoginAcct, thirdLoginType);
		getPhoneAndCheckByThirdLoginAcct(clientType, userHasPhoneList);
		// 输入的绑定信息是否已被其他第三方账号绑定过,解绑第三方账号(0是1否)
		String oldThirdLoginAcct = getOldTihrdLoginAcctByUser(bindUserList.get(0), thirdLoginType);
		return map;
	}
	
	/**
	 * 检验电话、密码是否正确
	 * @param phone:电话号码
	 * @param password:密码
	 * @return User
	 * @author
	 * 2018年1月11日  tck 创建
	 */
	private List<User> verifyIsLoginSuccess(String clientType, String phone, String password){
		Parameter parameter =new Parameter();
		parameter.put("phone", phone);
		return null;
	}
	
	/**
	 * 得到用户的第三方账号
	 * @param User:实体类
	 * @param thirdLoginType:第三方账号类型
	 * @return String
	 * @author
	 * 2018年1月13日  tck 创建
	 */
	private String getOldTihrdLoginAcctByUser(User user, String thirdLoginType){
		String thridLoginAcct = "";
		try{
			Class<User> userClass = User.class;
			// 绑定手机号对应的第三方账号
			for(ThirdLoginFieldEnum fieldEnum : ThirdLoginFieldEnum.values()) {
				// 账号类型枚举判断
				if (fieldEnum.getThirdLoginType().equals(thirdLoginType)) {
					// 账号对应的数据库字段提取
					String thirdLoginAcctField = getUserMethod(fieldEnum.getThirdLoginAcctField(), "get");
					Method getThirdLoginAcct = userClass.getDeclaredMethod(thirdLoginAcctField);
					Object thridLoginAcctObj = getThirdLoginAcct.invoke(user);
					if (!ChkUtil.isEmpty(thridLoginAcctObj)) {
						thridLoginAcct = thridLoginAcctObj.toString();
					}
					break;
				}
			}
		}catch(Exception e){
			throw new ResultException(-3, e.getMessage(), null, null, null);
		}
		return thridLoginAcct;
	}
	
	/**
	 * 根据第三方账号获取已有用户集合
	 * @param thirdLoginAcct:第三方账号
	 * @param thirdLoginType:第三方账号类型
	 * @return List<User>
	 * @author
	 * 2018年1月11日  tck 创建
	 */
	private List<User> getUserListByThirdLoginAcct(String thirdLoginAcct, String thirdLoginType){
		ThirdLoginParamAndAcctModel tlpm = getThirdLoginQueryParam(thirdLoginAcct, thirdLoginType);
		return null;
	}
	
	/**
	 * 校验第三方账号是否对应多个手机号，若第三方账号绑定过手机号，则返回对应手机号
	 * @param
	 * @return String
	 * @author
	 * 2018年1月11日  tck 创建
	 */
	private String getPhoneAndCheckByThirdLoginAcct(String clientType, List<User> userList){
		Set<String> userMobileNoSet = new HashSet<>();
		String oldPhone = "";
		return oldPhone;
	}
	
	/**
	 * 绑定更新用户
	 * @param
	 * @return User
	 * @author
	 * 2018年1月11日  tck 创建
	 */
	private User bindUserByThirdLoginAcct(User user, String thirdLoginAcct, String nickName ,String thirdLoginType){
		try{
			Class<User> userClass = User.class;
			// 绑定手机号对应的第三方账号
			for(ThirdLoginFieldEnum fieldEnum : ThirdLoginFieldEnum.values()) {
				// 账号类型枚举判断
				if (fieldEnum.getThirdLoginType().equals(thirdLoginType)) {
					String thirdLoginAcctField = getUserMethod(fieldEnum.getThirdLoginAcctField(), "set");
					Method setThirdLoginAcct = userClass.getDeclaredMethod(thirdLoginAcctField, String.class);
					setThirdLoginAcct.invoke(user, thirdLoginAcct);
					String thirdLoginNickNameField = getUserMethod(fieldEnum.getThirdLoginNickNameField(), "set");
					Method setThirdLoginNickName = userClass.getDeclaredMethod(thirdLoginNickNameField, String.class);
					setThirdLoginNickName.invoke(user, nickName);
					break;
				}
			}
		}catch(Exception e){
			throw new ResultException(-3, e.getMessage(), null, null, null);
		}
		user.setLastUpdateTime(new Date());
		return user;
	}
	
	/**
	 * 首字母大写
	 * @param
	 * @return String
	 * @author
	 * 2018年1月11日  tck 创建
	 */
	private String getUserMethod(String userField, String opType) {
		 char[] cs = userField.toCharArray();
	     cs[0]-=32;
	     return opType + String.valueOf(cs);
	}
	
	/**
	 * 解析绑定时传来的jsonStr
	 * @param
	 * @return ThirdLoginModel
	 * @author
	 * 2018年1月11日  tck 创建
	 * 2018-4-19 tck 
	 */
	@SuppressWarnings("all")
	private ThirdLoginModel getThirdLoginModelByJsonStr(String clientType, String jsonStr){
		ThirdLoginModel thirdLoginModel = new ThirdLoginModel();
		return thirdLoginModel;
	}
	
	/**
	 * 换手机号的绑定第三方账号接口
	 * @param clientType 客户端类型(android/ios/web等)
	 * @param jsonStr{phone:电话号码,password:密码,customerId:客户id,thirdLoginAcct:第三方账号,thirdLoginType:第三方账号类型}
	 * @return Object
	 * @author
	 * 2018年1月11日  tck 创建
	 */
	@SuppressWarnings("all")
	public Object doChangePhoneToBind(String clientType, String jsonStr) {
		Map map = new HashMap();
		ThirdLoginModel thirdLoginModel = getThirdLoginModelByJsonStr(clientType, jsonStr);
		String thirdLoginAcct = thirdLoginModel.getThirdLoginAcct();
		String thirdLoginType = thirdLoginModel.getThirdLoginType();
		String phone = thirdLoginModel.getPhone();
		String password = thirdLoginModel.getPassword();
		String nickName = thirdLoginModel.getNickName();
		// 检验电话、密码是否正确
		List<User> bindUserList = verifyIsLoginSuccess(clientType, phone, password); 
		// 根据第三方账号获取已有用户集合，校验微信是否对应多个手机号
		List<User> unBindUserList = getUserListByThirdLoginAcct(thirdLoginAcct, thirdLoginType);
		String oldPhone = getPhoneAndCheckByThirdLoginAcct(clientType, unBindUserList);
		// 解绑第三方账号(0是1否)
		if (!ChkUtil.isEmpty(oldPhone) && !oldPhone.equals(bindUserList.get(0).getPhone())) {
			List<User> updateUserList = new ArrayList<>();
			for (User unBindUser : unBindUserList) {
				// 解绑的用户信息置空
				unBindUser = bindUserByThirdLoginAcct(unBindUser, "", "", thirdLoginType);
				updateUserList.add(unBindUser);
			}
			// 更新绑定的用户信息
			for (User bindUser : bindUserList) {
				bindUser = bindUserByThirdLoginAcct(bindUser, thirdLoginAcct, nickName, thirdLoginType);
				updateUserList.add(bindUser);
			}
		}
		map.put(ThirdLoginReturnEnum.THIRDLOGINMODEL.getCode(), thirdLoginModel);
		return map;
	}
	
	/**
	 * 手机号+单位已被其他第三方账号绑定，绑定新的账号接口
	 * @param clientType 客户端类型(android/ios/web等)
	 * @param jsonStr{phone:电话号码,password:密码,customerId:客户id,thirdLoginAcct:第三方账号,thirdLoginType:第三方账号类型}
	 * @return Object
	 * @author
	 * 2018年1月11日  tck 创建
	 */
	@SuppressWarnings("all")
	public Object doUpdateBindThirdLoginAcct(String clientType, String jsonStr) {
		Map map = new HashMap();
		ThirdLoginModel thirdLoginModel = getThirdLoginModelByJsonStr(clientType, jsonStr);
		String thirdLoginAcct = thirdLoginModel.getThirdLoginAcct();
		String thirdLoginType = thirdLoginModel.getThirdLoginType();
		String phone = thirdLoginModel.getPhone();
		String password = thirdLoginModel.getPassword();
		String nickName = thirdLoginModel.getNickName();
		// 检验电话、密码是否正确
		List<User> bindUserList = verifyIsLoginSuccess(clientType, phone, password); 
		// 根据第三方账号获取已有用户集合，校验微信是否对应多个手机号
		List<User> userList = getUserListByThirdLoginAcct(thirdLoginAcct, thirdLoginType);
		getPhoneAndCheckByThirdLoginAcct(clientType, userList);
		return map;
	}
	
	/**
	 * 第三方账号解绑和绑定接口
	 * @param clientType 客户端类型(android/ios/web等)
	 * @param jsonStr{userId:用户id,thirdLoginType:第三方账号类型}
	 * @return Object
	 * @author
	 * 2018-01-11  shinry 创建
	 */
	@SuppressWarnings("all")
	public Object doUnBindThirdAcct(String jsonStr,String loginName,String token,String clientType) {
		// jsonStr空校验和解析
		OptionUtil.of(jsonStr).getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL,token,loginName,clientType));
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				 .getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR,token,loginName,clientType));
		// 用户id
		String userId = ChkUtil.fieldCheck(json.get("userId"),true,50, "用户id", "", token, loginName, clientType);
		// 第三方账号
		String thirdLoginAcct = ChkUtil.fieldCheck(json.get("thirdLoginAcct"),false,80, "第三方账号", "", token, loginName, clientType);
		// 第三方账号昵称
		String nickName = ChkUtil.fieldCheck(json.get("nickName"),false,80, "第三方账号昵称", "", token, loginName, clientType);
		// 第三方账号类型
		String thirdLoginType = ChkUtil.fieldCheck(json.get("thirdLoginType"),true,26, "第三方账号类型", "", token, loginName, clientType);
		return null;
	}

}
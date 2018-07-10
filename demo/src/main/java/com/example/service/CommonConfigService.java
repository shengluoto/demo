package com.example.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台系统的通用配置
 * @ClassName: CommonConfigService
 * @Description: 
 * @author 
 * 2018年4月26日  tck 创建
 */
public interface CommonConfigService /*extends BaseService<CommonConfig>*/ {
	
	/**
	 * 通用配置初保存接口
	 * @param jsonStr 保存数据:{name:名称,key:键,value:值,description:描述} 
	 * @param loginName 用户名
	 * @param token 安全令牌
	 * @param clientType 客户端类型(android/ios/web等)
	 * @return null
	 * @author
	 *	2018年4月26日  tck 创建
	 */
	public Object doSaveCommonConfigInfo(String jsonStr,String loginName,String token,String clientType);
	
	/**
	 * 通用配置修改初始化接口
	 * @param commonConfigId 配置的Id
	 * @return CommonConfigModel 配置的信息
	 * @param loginName 用户名
	 * @param token 安全令牌
	 * @param clientType 客户端类型(android/ios/web等)
	 * @author
	 *	2018年4月26日  tck 创建
	 */
	public Object doUpdateCommonConfigData(String commonConfigId,String loginName,String token,String clientType);
	
	/**
	 * 通用配置修改初保存接口
	 * @param jsonStr 保存数据:{commonConfigId:配置Id,value:值,description:描述}
	 * @param loginName 用户名
	 * @param token 安全令牌
	 * @param clientType 客户端类型(android/ios/web等)
	 * @return null
	 * @author
	 *	2018年4月26日  tck 创建
	 */
	public Object doUpdateCommonConfigInfo(String jsonStr,String loginName,String token,String clientType);
	
	/**
	 * 后台系统的通用配置查询接口
	 * @param jsonStr 查询条件:{name:名称,key:键,value:值,description:描述,orderBy:排序条件}
	 * @param request
	 * @param response
	 * @param pageNo 页码
	 * @param pageSize 页面记录数
	 * @param loginName 用户名
	 * @param token 安全令牌
	 * @param clientType 客户端类型(android/ios/web等)
	 * @return page
	 * @author
	 *	2018年4月26日  tck 创建
	 */
	public Object doSelectCommonConfigInfo(HttpServletRequest request, HttpServletResponse response, 
			String jsonStr, String pageNo, String pageSize, String loginName, String token, String clientType);

	/**
	 * 根据key先查缓存,缓存中没命中,则查数据,把查出的值放入缓存
	 * @param keys Collection的实现类 或 以分号分隔的字符串
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年6月1日 tck 创建
	 */
	public Map<String,String> doGetCommonConfigInfo(Object keys, String loginName, String token, String clientType);
}

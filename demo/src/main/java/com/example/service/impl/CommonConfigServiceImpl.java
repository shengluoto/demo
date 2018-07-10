package com.example.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.service.CommonConfigService;

/**
 * 后台系统的通用配置
 * @ClassName: CommonConfigServiceImpl
 * @Description: 
 * @author 
 * 2018年4月26日  tck 创建
 */
@Service
@Transactional
public class CommonConfigServiceImpl /*extends BaseServiceImpl<CommonConfig>*/ implements CommonConfigService {
//	@Resource
//	private CommonConfigDao commonConfigDao;
//
//	@Override
//	public BaseDao<CommonConfig> getBaseDao() {
//		return commonConfigDao;
//	}
//	
//	@Autowired
//	private AccessService accessService;

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private RedissonClient redisson;
	
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
	public Object doSaveCommonConfigInfo(String jsonStr,String loginName,String token,String clientType){
		return null;
	}
	
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
	@SuppressWarnings("all")
	public Object doUpdateCommonConfigData(String commonConfigId, String loginName, String token, String clientType){
		return null;
	}
	
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
	@SuppressWarnings("all")
	public Object doUpdateCommonConfigInfo(String jsonStr, String loginName, String token, String clientType){
		return null;
	}
	
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
	@SuppressWarnings("all")
	public Object doSelectCommonConfigInfo(HttpServletRequest request, HttpServletResponse response,
			String jsonStr, String pageNo, String pageSize, String loginName, String token, String clientType){
		Map<String,Object> map = new HashMap<>();
		return map;
	}
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
	@SuppressWarnings("unchecked")
	public Map<String,String> doGetCommonConfigInfo(Object keys, String loginName, String token, String clientType) {
		Map<String,String> map = new HashMap<>();
//		if (ChkUtil.isEmpty(keys)) {
//			throw new ResultException(-2, "查询键值不能为空!", token, loginName, clientType);
//		}
		int length = 0;
		String[] keysStr = {};
		if (keys instanceof Collection) { // 若传入的是Collection
			Collection<String> keysCollection = (Collection<String>) keys;
			length = keysCollection.size();
			// 转成数组
			List<String> keyList = new ArrayList<>();
			keysCollection.forEach(key->keyList.add(key));
			keysStr = new String[length];
			keyList.toArray(keysStr);
		} else if (keys instanceof String) { // 若传入的是拼接的字符串
			keysStr = StringUtils.split(String.valueOf(keys), ";");
			length = keysStr.length;
		}
		// 初始化下下
//		String value = "";
//		String property = "";
//		Parameter param = null;
//		CommonConfig config = null;
		// 加锁
//		RLock lock = redisson.getLock("anyLock");
//		lock.lock();
//		for(int i=0; i<length; i++) {
//			value = redisTemplate.opsForValue().get(String.format(RedisConstant.COMMONCONFIG_PREFIX,keysStr[i]));
			// 缓存没有则去数据库中查询
//			if (ChkUtil.isEmpty(value)) {
//				property=" from CommonConfig where state<>'1' and key=:key ";
//				param = new Parameter();
//				param.put("key", keysStr[i]);
//				config = commonConfigDao.getByHql(property, param);
//				if(ChkUtil.isEmptyAllObject(config)){
//					throw new ResultException(ResultEnum.DATA_NULL,token,loginName,clientType);
//				}
//				if(config.getState().equals("1")){
//					throw new ResultException(ResultEnum.DATA_DELETED,token,loginName,clientType);
//				}
//				if(config.getState().equals("2")){
//					throw new ResultException(ResultEnum.DATA_DISABLED,token,loginName,clientType);
//				}
//				value = config.getValue();
//				redisTemplate.opsForValue()
//					.set(String.format(RedisConstant.COMMONCONFIG_PREFIX,config.getKey()), config.getValue());
//				map.put(config.getKey(), config.getValue());
//			} else {
//				map.put(keysStr[i], value);
//			}
//		}
		// 解锁
//		lock.unlock();
		return map;
	}
}

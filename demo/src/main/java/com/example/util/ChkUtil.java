package com.example.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class ChkUtil {
	public static final String EXP_IdCard = "(^\\d{18}$)|(^\\d{15}$)";
	public static final String EXP_a_z = "[a-z]*";
	public static final String EXP_A_Z = "[A-Z]*";
	public static final String EXP_a_z_A_Z = "[a-zA-Z]*";
	public static final String EXP_0_9 = "[0-9]*";
	public static final String EXP_number = "[0-9]*|[0-9]*\\.[0-9]*";
	public static final String EXP_0_9_a_z = "[0-9a-z]*";
	public static final String EXP_0_9_a_z_A_Z = "[0-9a-zA-Z]*";
	public static final String EXP_0_9_a_z__ = "[0-9a-z_]*";
	public static final String EXP_EMAIL = "^([a-z0-9A-Z_]+[_|\\-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	public static final String EXP_PRICE = "^([1-9]\\d+|[1-9])(\\.\\d\\d?)*$";
	public static final String EXP_MOBILE = "[0-9]{11}";
	public static final String EXP_POSTALCODE = "[0-9]{6}";
	public static final String EXP_TEL = "[0-9]{3,4}[-]{1}[0-9]{7,8}";
	public static final String EXP_IP = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
	public static final String EXP_CHINESE = "[һ-�]*";
	public static final String EXP_0_9_a_z_A_Z_CHINESE = "[0-9a-zA-Zһ-�]*";
	public static final String EXP_URL = "^[a-zA-z]+://[^><\"' ]+";
	public static final String EXP_DATE = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}";
	public static final String EXP_DATETIME = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}";
	public static final String EXP_DATETIMESECOND = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}";
	public static final String DATESTRING_TAIL = "000000000";
	public static final String EXP_DATETIMESECOND2 = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[.]{1}[0-9]{1,3}";
	public static final String FILED_NAME = "name";
	public static final String FILED_LENGTH = "length";
	public static final String FILED_TYPE = "type";
	public static final String FILED_ISUNIQUE = "isUnique";
	public static final String FILED_ISCHECKEX = "isCheckEx";
	public static final String FILED_HINT = "hint";
	public static final String FILED_PRICE = "^\\d{1,10}$|^\\d{1,10}[.]\\d{1,2}$";
	public static final String EXP_HTTP = "^((https|http)?://)"
			+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			+ "|" // 允许本地服务器启动的项目
			+ "(localhost)"
			+ "|" // 允许IP和DOMAIN（域名）
			+ "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
			+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
			+ "[a-z]{2,6})" // first level domain- .com or .museum
			+ "(:[0-9]{1,4})*(/[a-zA-Z0-9\\.&%_\\./-~-]*)?"; // 端口- :80
	public static final String EXP_SQL_INJECT_ONE = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
			+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
	public static boolean isEmpty(Object data) {
		return (data == null) || ("".equals(data.toString().trim()));
	}
	public static boolean isEmptyAllObject(Object data) {
		if((data == null) || ("".equals(data.toString().trim()))){
			return (data == null) || ("".equals(data.toString().trim()));
		}else if(data instanceof Map){
			Map map=(Map) data;
			return (data == null) ||( map.size()==0);
		}else if(data instanceof Collection){
			Collection cc=(Collection) data;
			return (data == null) ||( cc.size()==0);
		}else if(data == null || (data instanceof String && data.equals("[]"))){
			return true;
		}else{
			return (data == null) || ("".equals(data.toString().trim()));
		}
			
		
	}

	public static boolean isStr(String str) {
		return !isEmpty(str);
	}

	public static boolean isStr(String str, String patternStr) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}


	public static boolean isMoney(String str) {
		if (isEmpty(str))
			return false;
		try {
			Double.valueOf(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isInteger(Object str) {
		if (isEmpty(str))
			return false;
		try {
			Integer.valueOf(str+"");
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isLong(String str) {
		if (isEmpty(str))
			return false;
		try {
			Long.valueOf(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isDouble(String str) {
		if (isEmpty(str))
			return false;
		try {
			Double.valueOf(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public static boolean isBoolean(String str) {
		if (isEmpty(str))
			return false;
		try {
			Boolean.valueOf(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public static boolean isBigDecimal(Object str) {
		if (isEmpty(str))
			return false;
		try {
			new BigDecimal(str+"");
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public static boolean isBigDecimal(BigDecimal str) {
		if (isEmpty(str))
			return false;
		try {
			new BigDecimal(str+"");
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	public static boolean isYYYYMM(Object str){
		if (isEmpty(str))
			return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			sdf.parse(str+"");
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	public static boolean isYYYYMMDDHHMMSS(Object str){
		if (isEmpty(str))
			return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.parse(str+"");
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	public static boolean isDate(Object str) {
		if (isEmpty(str))
			return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.parse(str+"");
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public static boolean isTime(Object str) {
		if (isEmpty(str))
			return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf.parse(str+"");
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public static boolean isDate(Date str) {
		if (isEmpty(str))
			return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.format(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public static boolean isYear(Object str) {
		if (isEmpty(str))
			return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			sdf.parse(str+"");
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public static boolean isContains(Object str,String sign) {
		if (isEmpty(str))
			return false;
		try {
			String temp=str+"";
			boolean flag=temp.contains(sign);
			return flag;
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * 
	 * SQL参数注入 
	 * 修改内容 
	 * @param regExps 校验数组
	 * @return   
	 * @throws 
	 */
	public static boolean chkParamSqlInject(String... regExps) {
		if(isEmpty(regExps))
			return true;
		
		try {
			for (String string : regExps) {
				if (!chkParamSqlInject(string))
					return false;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 
	 * SQL参数注入
	 * 修改内容 
	 * @param 
	 * @return   
	 * @throws 
	 */
	public static boolean chkParamSqlInject(String regExp) throws UnsupportedEncodingException {
		if(ChkUtil.isEmpty(regExp))
			return true;
		
		Pattern sqlPattern = Pattern.compile(EXP_SQL_INJECT_ONE,Pattern.CASE_INSENSITIVE);
		String param = URLDecoder.decode(regExp,"UTF-8");
		if (sqlPattern.matcher(param).find())
			return false;
		
		return true;
	}
	
	/**
	 * 判断字符串中类型
	* @Description: TODO(描述) 
	* @param  str  数据
	* @author 作者 jacking 
	* @date 2016-7-21 下午4:26:55 
	* @return
	 */
	public static Set getDataTypeSet(Object str) {
		Set<String> data = new HashSet<>();
			   boolean flag=false;
		/*	   if(isEmpty(str)){
				   data.add("java.util.Date");
				   data.add("java.math.BigDecimal");
				   data.add("java.lang.Integer");
				   data.add("java.lang.Long");
				   data.add("int");
				   data.add("long");
				   data.add("java.lang.Double");
				   data.add("double");
				   data.add("java.lang.Float");
				   data.add("float");
				   data.add("java.lang.String");
				   return data;
			   }*/
			   if(str.getClass().getName().equals("java.util.Date")){
				   data.add("java.util.Date");
				   return data;
			   }
			   if ((str+"").matches("\\d*|\\d+(\\.\\d{1,10})")) {
				   data.add("java.math.BigDecimal");
				   data.add("java.lang.String");
				   flag=true;
			   }
			   
			   if((str+"").matches("\\d*")){
				   data.add("java.lang.Integer");
				   data.add("java.lang.Long");
				   data.add("int");
				   data.add("long");
			   }
			   if((str+"").matches("\\d+(\\.\\d{1,10})")){
				   data.add("java.lang.Double");
				   data.add("double");
				   data.add("java.lang.Float");
				   data.add("float");
			   }
			   
			   if(!flag){
				   data.add("java.lang.String");
			   }
			   
		return data;
	}
 
	/**
	 * 比较数据是否符合所指定长度
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param str
	* @param @param oldLength
	* @param @return    设定
	 */
	public static boolean isOKLength(Object str,int oldLength) {
		if(!str.getClass().getName().equals("java.util.Date")){
			if((str+"").length()>oldLength){
				return false;
			}
		}
		 
		return true;
	}
	
	/**
	 * 校验是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(Object str){
		if(ChkUtil.isEmpty(str)){
			return false;
		}
		boolean result = ChkUtil.chekRegex(str, ChkUtil.EXP_number);
		return result;
	}
	/**
	 * 
	* @Description: TODO(描述) 
	* @param  str
	* @param  regexStr
	* @author 作者 jacking 
	* @date 2016-8-1 上午11:25:23 
	* @return
	 */
	public static boolean chekRegex(Object str, String regexStr) {
		if (isEmpty(str)||isEmpty(regexStr)) {
			return true;
		}
		if(!str.getClass().getName().equals("java.util.Date")){
			Pattern pattern = Pattern.compile(regexStr);
			Matcher matcher = pattern.matcher(str+"");
			return matcher.matches();
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(chekRegex("dd", "\\W"));
	}
	
	/**
	 * 判断传值是否为空且是否满足指定长度
	 * @param str
	 * @param oldLength
	 * @return
	 */
	@SuppressWarnings("all")
	public static Map isRightLength(Object str,int oldLength){
		Map map = new HashMap<>();
		if(ChkUtil.isEmpty(str)){
			map.put("code", "4008");
			map.put("message", "参数不能为空！");
			return map;
		}else{
			if(!str.getClass().getName().equals("java.util.Date")){
				if((str+"").length()>oldLength){
					map.put("code", "4008");
					map.put("message", "数据长度不能超过"+oldLength);
					return map;
				}
			}
		}
		map.put("code", "1");
		map.put("message", "");
		return map;
	}
	/**
	 * 字段校验
	 * @param str 被校验字段
	 * @param isRequired 是否必须（true:必须）
	 * @param length 字段长度比较值
	 * @param name 字段显示名称
	 * @param type 类型（0：金额，1：时间yyyy-MM-dd，2：邮箱，3:整数，4:手机，5:带分隔符的字符串，6:list集合，7:数字，8:时间(yyyy-MM),9:身份证，
	 * 				10:double类型数字，[允许正数，负数，0]，12:double类型数字，[只能正数、0（不允许负数）]，13:double类型数字，[只能正数（不允许负数或0）]，
	 * 				11:其他(有其他需要时在此补充)）14设备主机SN(14位，以SN开头) 15传感器编号（12为 16进制），16:电话，17:邮政，18:yyyy-MM-dd HH:mm:ss时间
	 * @param sign 分隔符;;
	 * @return
	 * @author Diego.zhou
	 *         shinry 2017-11-3 添加了设备编号和传感器编号的判断
	 * @time 2017-4-17
	 */
	@SuppressWarnings("all")
	public static Map fieldCheck(Object str,boolean isRequired,int length,String name,String type){
		Map map = new HashMap<>();
		if(ChkUtil.isEmptyAllObject(str)){//若必填且为空时提醒
			if(isRequired){
				map.put("code", "4008");
				map.put("message", name+"不能为空，请检查输入！");
				return map;
			}
			if(!ChkUtil.isEmpty(type) && type.equals("0")){//金额
				map.put("data", 0.00);
			}else if(!ChkUtil.isEmpty(type) && (type.equals("3") || type.equals("7"))){//整数
				map.put("data", 0);
			}else if(!ChkUtil.isEmpty(type) && type.equals("5")){//带;;分隔符的字符串
				map.put("dataId", "");
				map.put("dataName", "");
				map.put("data", "");
			}else{
				map.put("data", "");
			}
		}else{
			//校验类型是否符合
			if(!ChkUtil.isEmpty(type) && type.equals("0")){//金额
				boolean isMoney = ChkUtil.isBigDecimal(str+"");
				if(!isMoney){
					map.put("code", "4008");
					map.put("message", name+"格式错误，请检查输入！");
					return map;
				}
				if(new BigDecimal(str+"").compareTo(new BigDecimal(0))<0){
					map.put("code", "4008");
					map.put("message", name+"为正数，格式错误，请检查输入！");
					return map;
				}
//				String temp1=str+"";
//				int position1 = temp1.indexOf(".");
//				String databe1 = temp1.substring(0, position1);
//				String datapr1 = temp1.substring(position1+1,temp1.length());
//				if(databe1.length()>10){
//					map.put("code", "4008");
//					map.put("message", name+"为正数，格式错误，请检查输入！");
//					return map;
//				}
//				if(datapr1.length()>2){
//					map.put("code", "4008");
//					map.put("message", name+"为正数，格式错误，请检查输入！");
//					return map;
//				}
				if(!(str+"").matches(FILED_PRICE)){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("1")){//日期yyyy-MM-dd
				boolean isDate = ChkUtil.isDate(str);
				if(!isDate){
					map.put("code", "4008");
					map.put("message", name+"为日期类型，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("8")){//日期yyyy-MM
				boolean isDate = ChkUtil.isYYYYMM(str);
				if(!isDate){
					map.put("code", "4008");
					map.put("message", name+"为日期类型，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("18")){//yyyy-MM-dd HH:mm:ss
				boolean isDate = ChkUtil.isYYYYMMDDHHMMSS(str);
				if(!isDate){
					map.put("code", "4008");
					map.put("message", name+"为日期类型，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("2")){//邮箱
				boolean isEmail = ChkUtil.chekRegex(str,ChkUtil.EXP_EMAIL);
				if(!isEmail){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("3")){//整数
				boolean isInteger = ChkUtil.isInteger(str);
				if(!isInteger){
					map.put("code", "4008");
					map.put("message", name+"为整数类型，格式错误，请检查输入！");
					return map;
				}
				if(Integer.valueOf(str+"")<=0){
					map.put("code", "4008");
					map.put("message", name+"为正整数，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("4")){//手机
				boolean isMobile = ChkUtil.chekRegex(str,ChkUtil.EXP_MOBILE);
				if(!isMobile){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("16")){//电话
				boolean isMobile = ChkUtil.chekRegex(str,ChkUtil.EXP_TEL);
				if(!isMobile){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("17")){//邮政
				boolean isMobile = ChkUtil.chekRegex(str,ChkUtil.EXP_POSTALCODE);
				if(!isMobile){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("5")){//带;;分隔符的字符串
				String sign=";;";
				boolean isContains = ChkUtil.isContains(str, sign);
				if(!isContains){
					map.put("code", "4008");
					map.put("message", name+"格式错误，必须包含;;分隔符，请检查输入！");
					return map;
				}
				String temp=str+"";
				int position = temp.indexOf(sign);
				String dataId = temp.substring(0, position);
				String dataName = temp.substring(position+sign.length(),temp.length());
				map.put("dataId", dataId);
				map.put("dataName", dataName);
			}
			if(!ChkUtil.isEmpty(type) && type.equals("7")){//数字
				boolean isInteger = ChkUtil.isInteger(str);
				if(!isInteger){
					map.put("code", "4008");
					map.put("message", name+"为整数类型，格式错误，请检查输入！");
					return map;
				}
				if(Integer.valueOf(str+"")<0){
					map.put("code", "4008");
					map.put("message", name+"为整数，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("9")){//身份证
				boolean isIdCard = ChkUtil.chekRegex(str,ChkUtil.EXP_IdCard);
				if(!isIdCard){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("10")){//double类型数字，允许正数，负数，0
				boolean isDouble = ChkUtil.isDouble(str+"");
				if(!isDouble){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("12")){//double类型数字，只能正数、0（不允许负数）
				boolean isDouble = ChkUtil.isDouble(str+"");
				if(!isDouble){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
				if(Double.valueOf(str+"")<0){
					map.put("code", "4008");
					map.put("message", name+"为正数，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("13")){//double类型数字，只能正数（不允许负数或0）
				boolean isDouble = ChkUtil.isDouble(str+"");
				if(!isDouble){
					map.put("code", "4008");
					map.put("message", name+"，格式错误，请检查输入！");
					return map;
				}
				if(Double.valueOf(str+"")<=0){
					map.put("code", "4008");
					map.put("message", name+"为正数，格式错误，请检查输入！");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type) && type.equals("6")){
				if((str+"").length()<=length){
					map.put("code", "4008");
					map.put("message", name+"长度不能小于"+length);
					return map;
				}
			}else{
				if((str+"").length()>length){
					map.put("code", "4008");
					map.put("message", name+"长度不能超过"+length);
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type)&&type.equals("14")){
				if((str+"").length()!=length){
					map.put("code","4008");
					map.put("message",name+"长度要有"+length+"位");
					return map;
				}
				if(!(str+"").startsWith("SN")){
					map.put("code","4008");
					map.put("message","开头要以SN开头");
					return map;
				}
			}
			if(!ChkUtil.isEmpty(type)&&type.equals("15")){
				if((str+"").length()!=length){
					map.put("code","4008");
					map.put("messgae",name+"长度要为12位");
					return map;
				}
				String regex="[A-Fa-f0-9]{12}";
				boolean flag=(str+"").matches(regex);
				if(!flag){
					map.put("code","4008");
					map.put("message",name+"必须为l6进制字符串");
					return map;
				}
			}
			map.put("data", str);
		}
		map.put("code", "1");
		map.put("message", "校验通过");
		return map;
	}
	/**
	 * 比较两个集合是否相同
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static boolean equalList(List list1, List list2) {
        return (list1.size() == list2.size()) && list1.containsAll(list2);
	}

	/**
	 * 判断类型
	* @param  str  数据
	* @author athena
	* @date 2017-02-22 
	* @return
	 */
	public static String getObjectToString(Object str) {
		String data="";
		if(!ChkUtil.isEmpty(str)){
			if(str.getClass().getName().equals("java.util.Date")){
				SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				data=sdfdate.format(str);
			}else if(str.getClass().getName().equals("java.sql.Timestamp")){
				SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				data=sdfdate.format(str);
			}else if(str.getClass().getName().equals("java.lang.String")){
				data=str+"";
			}else{
				data=String.valueOf(str);
			}
		}
		return data;
	}
	
	/**
	 * 比较两个集合，获取集合修改后不变的值，增加的值，以及删除的值
	 * @param oldList
	 * @param newList
	 * @return
	 * @author Diego.zhou
	 * @time 2017-6-19
	 */
	@SuppressWarnings("all")
	public static Map compareList(List<String> oldList,List<String> newList){
		Map map = new HashMap<>();
		//当职务不为空时,增量校验
		List<String> sameList = new ArrayList<>();//不变的职务集合
		List<String> addList = new ArrayList<>();//新增的职务集合
		List<String> delList = new ArrayList<>();//删除的职务集合
		if(ChkUtil.isEmptyAllObject(oldList)){
			map.put("sameList", sameList);
			map.put("addList", newList);
			map.put("delList", delList);
			return map;
		}
		if(ChkUtil.isEmptyAllObject(newList)){
			map.put("sameList", sameList);
			map.put("addList", addList);
			map.put("delList", oldList);
			return map;
		}
		//新增的集合
		for(int i=0;i<newList.size();i++){
			boolean flag = true;
			for(String idStr : oldList){
				if(newList.get(i).equals(idStr)){
					flag = false;
				}
			}
			if(flag){
				addList.add(newList.get(i));
			}else{
				sameList.add(newList.get(i));
			}
		}
		//oldList中去除sameList的则为删除的集合
		for(String idStr : oldList){
			boolean flag = true;
			for(String index : sameList){
				if(index.equals(idStr)){
					flag = false;
				}
			}
			if(flag){
				delList.add(idStr);
			}
		}
		map.put("sameList", sameList);
		map.put("addList", addList);
		map.put("delList", delList);
		return map;
	}
	
}
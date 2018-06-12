package com.example.demo.test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpMethod;

import com.example.model.ValueNameModel;
import com.example.util.ChkUtil;
import com.example.util.ResponseUtils;
import com.example.util.httpclient.HttpClientCallback;
import com.example.util.httpclient.HttpClientTemplate;
import com.example.util.httpclient.HttpClientUtils;

public class YourThreadTest extends Thread {  
    public void run(){  
		for(int i=0;i<100000;i++) {
			List<ValueNameModel> vnmList=new ArrayList<>();
			ValueNameModel temp1=new ValueNameModel();
			temp1.setName("fromNick");
			temp1.setValue("周周"+i);
			vnmList.add(temp1);
			ValueNameModel temp2=new ValueNameModel();
			temp2.setName("msgType");
			temp2.setValue("TEXT");
			vnmList.add(temp2);
			ValueNameModel temp3=new ValueNameModel();
			temp3.setName("msgidServer");
			temp3.setValue("59036820368344556"+i);
			vnmList.add(temp3);
			ValueNameModel temp4=new ValueNameModel();
			temp4.setName("fromAccount");
			temp4.setValue("8a8aba726385d6b1016385ff6a66008d");
			vnmList.add(temp4);
			ValueNameModel temp5=new ValueNameModel();
			temp5.setName("fromClientType");
			temp5.setValue("IOS");
			vnmList.add(temp5);
			ValueNameModel temp6=new ValueNameModel();
			temp6.setName("fromDeviceId");
			temp6.setValue("D2AADBB4-5409-4B67-A674-96DFE05E45E9");
			vnmList.add(temp6);
			ValueNameModel temp7=new ValueNameModel();
			temp7.setName("eventType");
			temp7.setValue("1");
			vnmList.add(temp7);
			ValueNameModel temp8=new ValueNameModel();
			temp8.setName("body");
			temp8.setValue("as的vad");
			vnmList.add(temp8);
			ValueNameModel temp9=new ValueNameModel();
			temp9.setName("convType");
			temp9.setValue("TEAM");
			vnmList.add(temp9);
			ValueNameModel temp10=new ValueNameModel();
			temp10.setName("msgIdClient");
			temp10.setValue("f748016b-a071-45ce-a4ce-fd3a8b298e63");
			vnmList.add(temp10);
			ValueNameModel temp11=new ValueNameModel();
			temp10.setName("resendFlag");
			temp10.setValue("0");
			vnmList.add(temp10);
			ValueNameModel temp12=new ValueNameModel();
			temp12.setName("msgTimestamp");
			temp12.setValue("1527491721676");
			vnmList.add(temp12);
			ValueNameModel temp13=new ValueNameModel();
			temp13.setName("to");
			temp13.setValue("494793856");
			vnmList.add(temp13);
			ValueNameModel temp14=new ValueNameModel();
			temp14.setName("attach");
			temp14.setValue("");
			vnmList.add(temp14);
    			String requestUrl = "http://10.10.58.242:8087/imReceiveMsg/doSaveImGroupInfo2";
//			String requestUrl = "http://10.10.58.242:8081/protocol/doSaveClickStaInfo/cc";
			Map outMap = this.doRequestOutInterface(requestUrl, "0", "0", "json",null,vnmList);
			System.out.println(outMap.get("message"));
		}
    }  
    
    /**
	 * 调用外部接口的公共方法
	 * @param callMethod 请求地址
	 * @param type 请求方式(定时节点用，post:0,get:1,put:2,delete:3)
	 * @param sendMode 变量发送方式(定时节点用,json:0,url:1)
	 * @param acceptName json变量接收名称
	 * @param valueName 变量值集合List<ValueNameModel>(value,name)
	 * @param restValueName rest变量值集合List<ValueNameModel>(value,name)
	 * @return Map(code,message,json)
	 * @throws Exception
	 * @author 
	 * 	2017-07-02 athena 创建
	 * 	2017-11-21 athena 迁移
	 */
	@SuppressWarnings("all")
	public Map doRequestOutInterface(String callMethod,String type,String sendMode,String acceptName,List<ValueNameModel> restValueName,List<ValueNameModel> valueName){
		Map<String, Object> map = new HashMap<String, Object>();
//		try {
			if(ChkUtil.isEmpty(callMethod)){
				map.put("code", "4008");
				map.put("message", "请求地址不能为空！");
			}
			if(ChkUtil.isEmpty(type)){//请求方式(定时节点用，post:0,get:1,put:2,delete:3)
				map.put("code", "4008");
				map.put("message", "请求方式不能为空！");
				return map;
			}
			if(ChkUtil.isEmpty(type) || (!type.equals("0") && !type.equals("1") && !type.equals("2") && !type.equals("3"))){
				map.put("code", "4008");
				map.put("message", "请求方式有问题！");
				return map;
			}
			if(type.equals("2") || type.equals("3")){
				if (ChkUtil.isEmptyAllObject(restValueName)) {
					map.put("code", "4008");
					map.put("message", "rest变量值不能为空！");
					return map;
				}
			}
			if(ChkUtil.isEmpty(sendMode)){//变量发送方式(定时节点用,json:0,url:1)
				map.put("code", "4008");
				map.put("message", "变量发送方式不能为空！");
				return map;
			}
			final List<NameValuePair> params = new ArrayList<NameValuePair>();
			final String subType=type;
			// 获取变量值
			String valueTemp="";
			if (!ChkUtil.isEmptyAllObject(valueName)) {
				if(sendMode.equals("0")){
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					for(ValueNameModel tvv:valueName){
						paramsMap.put(tvv.getName(), tvv.getValue());
					}
					if(ChkUtil.isEmpty(acceptName)){
						map.put("code", "4008");
						map.put("message", "json变量接收名称有问题！");
						return map;
					}
					params.add(new BasicNameValuePair(acceptName,ResponseUtils.toJSONString(paramsMap)));
				}else{
					if(subType.equals("0") || subType.equals("1")){
						for(ValueNameModel tvv:valueName){
							params.add(new BasicNameValuePair(tvv.getName(),tvv.getValue()));
						}
					}
				}
			}
			if (!ChkUtil.isEmptyAllObject(restValueName)) {
				for(ValueNameModel tvv:restValueName){
					valueTemp=valueTemp+"/"+tvv.getValue();
				}
			}
			final String strurl=callMethod+valueTemp;//合并方法
			net.sf.json.JSONObject json=HttpClientTemplate.connect(new HttpClientCallback() {
				@Override
				public Map<String, Object> doConnect(Map<String,Object> retMap) {
					if(subType.equals("0")){
						retMap=HttpClientUtils.sendRequest(strurl, HttpMethod.POST, params,-1);
					}else if(subType.equals("1")){
						retMap=HttpClientUtils.sendRequest(strurl, HttpMethod.GET, params,-1);
					}else if(subType.equals("2")){
						retMap=HttpClientUtils.sendRequest(strurl, HttpMethod.PUT, params,-1);
					}else if(subType.equals("3")){
						retMap=HttpClientUtils.sendRequest(strurl, HttpMethod.DELETE, params,-1);
					}
					return retMap;
				}
			});
			if(!"1".equals(json.get("code").toString())){
				map.put("code", "4008");
				map.put("message", "外部调用方法："+json.get("msg").toString());
				return map;
			}
			map.put("jsonStr", json);
//		} catch (Exception e) {
//			map.put("code", "4008");
//			map.put("message", "方法有问题！"+e.getMessage());
//			return map;
//		}
		map.put("code", "1");
		map.put("message", "执行成功！");
		return map;
	}
}  

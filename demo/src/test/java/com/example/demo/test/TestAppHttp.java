package com.example.demo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import com.example.model.ValueNameModel;
import com.example.util.ChkUtil;
import com.example.util.HttpUtil;
import com.example.util.ResponseUtils;
import com.example.util.httpclient.HttpClientCallback;
import com.example.util.httpclient.HttpClientTemplate;
import com.example.util.httpclient.HttpClientUtils;

import net.sf.json.JSONObject;

// @RunWith(SpringRunner.class)
// @SpringBootTest
public class TestAppHttp {
    @Test
    public void httpPostTest() {
        Map<String, Object> tm = new HashMap<String, Object>();
        tm.put("userName","tck");
        tm.put("companyName","苏州大话");
        tm.put("mobileNo","18215575816");
        tm.put("linkMan","大哥");
        tm.put("passWord","123");
        tm.put("tradingCertificateNO","2353534");
        String jsonObj = JSONObject.fromObject(tm).toString();
        String requestUrl = "http://10.10.68.31:9053/cssiot-gzz01-web/user/verifyLogin/web/18015575816/550e1bafe077ff0b0b67f4e32f29d751/p0";
        String requestMethod = "get";
        JSONObject job = HttpUtil.httpRequest(requestUrl, requestMethod, jsonObj);
        int a = 5;
        // System.out.println(job.get("errcode"));
    }

    @Test
    public void testBinfa(){
    	for(int i = 0;i<100;i++) {
//    		new Thread("" + i){
//    			public void run(){
//    				System.out.println("Thread: " + getName() + "running");
//    				List<ValueNameModel> vnmList=new ArrayList<>();
//        			ValueNameModel temp1=new ValueNameModel();
//        			temp1.setName("billId");
//        			temp1.setValue("8a8aba7262650fa901626514239b0033");
//        			vnmList.add(temp1);
//        			ValueNameModel temp2=new ValueNameModel();
//        			temp2.setName("billType");
//        			temp2.setValue("Scheme");
//        			vnmList.add(temp2);
//        			String requestMethod = "post";
//        			String requestUrl = "http://10.10.58.242:8087/clickSta/doSaveClickStaInfo/web/1/8a8aba72624d307601624d7bd44b0050";
//        			Map outMap = this.doRequestOutInterface(requestUrl, "0", "0", "jsonStr",null,vnmList);
//    			}
//    		}.start();


    		new Thread(() -> {
    			List<ValueNameModel> vnmList=new ArrayList<>();
    			ValueNameModel temp1=new ValueNameModel();
    			temp1.setName("billId");
    			temp1.setValue("8a8aba7262650fa901626514239b0033");
    			vnmList.add(temp1);
    			ValueNameModel temp2=new ValueNameModel();
    			temp2.setName("billType");
    			temp2.setValue("Scheme");
    			vnmList.add(temp2);
    			String requestMethod = "post";
    			String requestUrl = "http://10.10.58.242:8087/clickSta/doSaveClickStaInfo/web/1/8a8aba72624d307601624d7bd44b0050";
    			Map outMap = this.doRequestOutInterface(requestUrl, "0", "0", "jsonStr",null,vnmList);
    		}).start();
    	}
    }
    
    public Map doRequestOutInterface(String callMethod,String type,String sendMode,String acceptName,List<ValueNameModel> restValueName,List<ValueNameModel> valueName){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
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
			if(!"0".equals(json.get("code").toString())){
				map.put("code", "4008");
				map.put("message", "外部调用方法："+json.get("msg").toString());
				return map;
			}
			map.put("jsonStr", json);
		} catch (Exception e) {
			map.put("code", "4008");
			map.put("message", "方法有问题！"+e.getMessage());
			return map;
		}
		map.put("code", "1");
		map.put("message", "执行成功！");
		return map;
	}
}

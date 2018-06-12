package com.example.demo.test;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.example.model.TestTemplatModel;
import com.example.model.TestTemplatModel2;
import com.example.util.Constants;
import com.example.util.HttpUtil;
import com.example.util.PropertiesLoader;

import net.sf.json.JSONObject;

// @RunWith(SpringRunner.class)
// @SpringBootTest
public class TestHttpPost {
    @Test
    public void httpPostTest() {
        String appID =
                PropertiesLoader.loadProperties(Constants.wechatProPath).getProperty("appID");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date nowTime = new Date();
        String strDate = dfs.format(nowTime);
        TestTemplatModel tm = new TestTemplatModel();
        tm.setTouser("o6kR9wLXvGAJE4ldODPf39Pz0hCE");
        tm.setTitle("测试post");
        tm.setContent("哈哈哈，成功啦");
        tm.setStrDate(strDate);
        String redirect_uri = "http://5c950546.ngrok.io/weixin/auth/getOpenId";
        // String jumpURL = "https://open.weixin.qq.com/connect/oauth2/authorize?"
        // + "appid="+appID
        // + "&redirect_uri=" + redirect_uri
        // + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        String jumpURL =
                "http://6961f97c.ngrok.io/marketPlat/pager/doSelectOutBillDetailByRow?taskApplyId=8a8aba6e5e75495e015e754e44ff000c";
        tm.setJumpURL(jumpURL);
        String requestUrl = "http://10.10.68.31:9051/wechat-dingding-server/weixin/send/sendTemplateData2";
        String requestMethod = "POST";
        String jsonObj = JSONObject.fromObject(tm).toString();
        JSONObject job = HttpUtil.httpRequest(requestUrl, requestMethod, jsonObj);
        int a = 5;
        // System.out.println(job.get("errcode"));
    }

    @Test
    public void testSplit() {
    	FileWriter fwriter = null;  
    	 try {  
    	  fwriter = new FileWriter("E:\\a.txt");  
    	  for (int i=0;i<100000; i++) {
    		  fwriter.write("set click_Scheme_"+"8a8aba7262193f0e016219489c0c0004"+ i +"_2018-03-19 "+ i + "\n");  
    	  }
    	  fwriter.flush();
    	  fwriter.close();
    	 } catch (IOException ex) {  
    	  ex.printStackTrace();  
    	 } 
    }
    
    @Test
    public void testSplit2() {
//        String touser1 = "0000.13200";
//        String touser2 = "00100.13200";
//        String touser3 = "100.13200";
//        String touser4 = "000.00000";
//        String touser5 = "000000";
//        System.out.println(new BigDecimal(touser1).toString());
//        System.out.println(new BigDecimal(touser2).toString());
//        System.out.println(new BigDecimal(touser3).toString());
//        System.out.println(new BigDecimal(touser4).toString());
//        System.out.println(new BigDecimal(touser5).toString());
//    	Set<String> userMobileNoSet = new HashSet<>();
//    	System.out.println(userMobileNoSet.size());
//    	String str = "公司";
//    	String[] strArray = str.split("-");
//    	int a = 5;
	    String appSecret = "sfci50a7s8yqi";
	    Random rand = new Random();
	    int randomIndex = rand.nextInt(1000);
	    long timestamp = new Date().getTime();
	    String str = appSecret + randomIndex + timestamp;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());  
			System.out.println("随机:"+randomIndex + "; 时间戳:"+ timestamp + ";加密后:" + getFormattedText(messageDigest.digest())); 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		LocalDateTime dt = LocalDateTime.now();
//		System.out.println(dt);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String dateStr = df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(1527128962268L),ZoneId.of("Asia/Shanghai")));
		System.out.println(dateStr);
		LocalDateTime date =
			    LocalDateTime.ofInstant(Instant.ofEpochMilli(1527128962268L), ZoneId.systemDefault());
		System.out.println(date);
    }
    
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',  
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; 

    private static String getFormattedText(byte[] bytes) {  
        int len = bytes.length;  
        StringBuilder buf = new StringBuilder(len * 2);  
        // 把密文转换成十六进制的字符串形式  
        for (int j = 0; j < len; j++) {  
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);  
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);  
        }  
        return buf.toString();  
    }  

    @Test
    public void testFor() {
        String touser = "";
        for (int i = 0; i < 3; i++) {
            if ("".equals(touser)) {
                System.out.println(touser);
                break;
            }
            touser = Integer.toString(i);
        }
        System.out.println(1 << 4);
    }
    @Test
    public void testForhh() {
    	String[] valueStrArray = StringUtils.split("#63b2f5;#35cc7e;#c8cbd5;#f2ca6a", ';');
		int length = valueStrArray.length;
		Random rand = new Random();  
		int randomIndex = 0;
    	for (int i = 0; i < 1000; i++) {
    		randomIndex = rand.nextInt(length);
    		if(randomIndex>length){
    			System.out.println(randomIndex);
    		}
    	}
    }
    
    @Test
    public void testDD() throws InterruptedException {
        String title="钉钉测试";
        String content="这a个是w内w容";
        String userId="016807641921373378";
        String tranUrl="https://www.baidu.com";
        String requestUrl = "http://10.10.68.31:9054/wechat-dingding-server/dingding/sendMessage";
//        Map<String, Object> result = sendMessage(title, content, userId, tranUrl);
        TestTemplatModel2 jsonStr = new TestTemplatModel2();
        jsonStr.setContent(content);
        jsonStr.setTitle(title);
        jsonStr.setTranUrl(tranUrl);
        jsonStr.setUserId(userId);
//        for(int i=0;i<20;i++){
//            Thread.sleep(5000);
        JSONObject jsonObject =
                    HttpUtil.httpRequest3(requestUrl, "post", JSONObject.fromObject(jsonStr).toString());
            System.out.println(jsonObject.toString());
//        }
    }

    public Map<String, Object> sendMessage(String title, String content, String userId,
            String tranUrl) {
        JSONObject jsonObject = new JSONObject();
        Map map = new HashMap<>();
        String accessToken = getAccessTokenByGet();
        System.out.println("钉钉获取到accessToken值:" + accessToken);
        String url = "https://oapi.dingtalk.com/message/send?access_token=";
        if (accessToken != null) {
            url += accessToken;
        }
        Map paramterMap = getParamter(title, content, userId, tranUrl);
        jsonObject =
                HttpUtil.httpRequest(url, "post", JSONObject.fromObject(paramterMap).toString());
        if (jsonObject != null) {
            if (jsonObject.getString("errcode").equals("0")) {
                System.out.println("钉钉推送成功" + userId);
                map.put("data", jsonObject);
                map.put("code", "0");
                map.put("messageId", jsonObject.getString("messageId"));
            } else {
                map.put("code", "1");
                map.put("msg", jsonObject.getString("errmsg"));
            }
        } else {
            System.out.println("钉钉推送失败" + userId);
            map.put("msg", "钉钉消息发送失败");
            map.put("code", "1");
        }
        return map;
    }

    public String getAccessTokenByGet() {
        String corpid = PropertiesLoader.loadProperties(Constants.ddProPath).getProperty("corpid");
        String corpsecret =
                PropertiesLoader.loadProperties(Constants.ddProPath).getProperty("corpsecret");
        String getTokenUrl = "https://oapi.dingtalk.com/gettoken?corpid=" + corpid + "&corpsecret="
                + corpsecret + "";
        JSONObject accessToken = HttpUtil.httpRequest(getTokenUrl, "GET", null);
        for (int i = 0; i < 5; i++) {
            if (accessToken != null) {
                break;
            }
            accessToken = HttpUtil.httpRequest(getTokenUrl, "GET", null);
        }
        if (accessToken != null) {
            return accessToken.getString("access_token");
        } else {
            return "";
        }
    }

    public Map getParamter(String title, String content, String userId, String tranUrl) {
        String contentText = "详情";
        String titleBody = "您好，有待办事件需处理如下";
        String userid = "002";
        if (title != null && !"".equals(title.trim())) {
            contentText = title;
        }
        if (userId != null && !"".equals(userId.equals(userid))) {
            userid = userId.replaceAll(",", "|");
        }
        String agentid = "124811335";
        if (PropertiesLoader.loadProperties(Constants.ddProPath).getProperty("agentid") != null) {
            agentid = PropertiesLoader.loadProperties(Constants.ddProPath).getProperty("agentid");
        }
        Map map = new HashMap<String, Object>();
        map.put("agentid", agentid);
        map.put("touser", userid);
        map.put("msgtype", "oa");
        Map map2 = new HashMap<>();
        map2.put("message_url", tranUrl);
        Map map3 = new HashMap<>();
        map2.put("head", map3);
        map3.put("bgColor", "FFBBBBBB");
        map2.put("text", "任务审批");
        Map map4 = new HashMap<>();
        map4.put("title", titleBody);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        map4.put("content", contentText + simpleDateFormat.format(date).toString());
        map2.put("body", map4);
        map.put("oa", map2);
        return map;
    }
}

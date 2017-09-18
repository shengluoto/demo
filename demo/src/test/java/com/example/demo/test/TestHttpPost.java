package com.example.demo.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.TestTemplatModel;
import com.example.util.Constants;
import com.example.util.HttpUtil;
import com.example.util.PropertiesLoader;
import com.google.gson.Gson;

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
        String requestUrl = "http://10.10.68.31:8089/weixin/send/sendTemplateData2";
        String requestMethod = "POST";
        String jsonObj = JSONObject.fromObject(tm).toString();
        JSONObject job = HttpUtil.httpRequest3(requestUrl, requestMethod, jsonObj);
        int a = 5;
        // System.out.println(job.get("errcode"));
    }

    @Test
    public void testSplit() {
        String touser = "afaf,ge,yhrt,fthrt";
        String[] tousers = touser.split(",");
        int b = tousers.length;
        int a = 5;
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
    }
    
    @Test
    public void testDD() {
        String title="钉钉测试";
        String content="这个是内容";
        String userId="016807641921373378";
        String tranUrl="https://www.baidu.com";
        Map<String, Object> result = sendMessage(title, content, userId, tranUrl);
        System.out.println(result.toString());
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
                HttpUtil.httpRequest3(url, "post", JSONObject.fromObject(paramterMap).toString());
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

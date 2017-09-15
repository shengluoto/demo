package com.example.demo.test;

import java.text.SimpleDateFormat;
import java.util.Date;

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

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestHttpPost {
    @Test
    public void httpPostTest(){
        String appID  = PropertiesLoader.loadProperties(Constants.wechatProPath).getProperty("appID");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date nowTime = new Date();
        String strDate = dfs.format(nowTime);
        TestTemplatModel tm = new TestTemplatModel();
        tm.setTouser("o6kR9wLXvGAJE4ldODPf39Pz0hCE");
        tm.setTitle("测试post");
        tm.setContent("哈哈哈，成功啦");
        tm.setStrDate(strDate);
        String redirect_uri = "http://5c950546.ngrok.io/weixin/auth/getOpenId";
//        String jumpURL = "https://open.weixin.qq.com/connect/oauth2/authorize?"
//                + "appid="+appID
//                + "&redirect_uri=" + redirect_uri
//                + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        String jumpURL = "http://6961f97c.ngrok.io/marketPlat/pager/doSelectOutBillDetailByRow?taskApplyId=8a8aba6e5e75495e015e754e44ff000c";
        tm.setJumpURL(jumpURL);
        String requestUrl = "http://10.10.68.31:8089/weixin/send/sendTemplateData2";
        String requestMethod = "POST";
        String jsonObj = JSONObject.fromObject(tm).toString();
        String job = HttpUtil.httpRequest3(requestUrl, requestMethod, jsonObj);
        int a= 5;
//        System.out.println(job.get("errcode"));
    }
    
    @Test
    public void testSplit(){
        String touser = "afaf,ge,yhrt,fthrt";
        String[] tousers = touser.split(",");
        int b = tousers.length;
        int a = 5;
    }
    @Test
    public void testFor(){
        String touser = "";
        for(int i =0 ;i<3;i++){
            if("".equals(touser)){
                System.out.println(touser);
                break;
            }
            touser=Integer.toString(i);
        }
    }
}

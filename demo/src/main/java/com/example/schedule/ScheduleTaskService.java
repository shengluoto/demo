package com.example.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.entity.AccessToken;
import com.example.mapper.AccessTokenMapper;
import com.example.model.DetailData;
import com.example.model.TemplateData;
import com.example.util.HttpUtil;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Configuration
@EnableScheduling
@Service
public class ScheduleTaskService {
    @Autowired
    private AccessTokenMapper accessTokenMapper;

//    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void task1() {
        String accessToken = getAccessToken();
        accessTokenMapper.insert(accessToken);
        System.out.println("MyTask1Cron:" + accessToken);
    }

//    @Scheduled(cron = "0 0/2 * * * ? ")
    public void task2() {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String touser = "o6kR9wLXvGAJE4ldODPf39Pz0hCE";
        String template_id = "SrT8gZn1nLJl6EHrwnfU_50TvudjD7G524vyTat8ahY";
        String url = "http://weixin.qq.com/download";
        TemplateData templateDate = new TemplateData();
        templateDate.setTouser(touser);
        templateDate.setTemplate_id(template_id);
        templateDate.setUrl(url);
        //设置详情属性
        Map<String, DetailData> data = new HashMap<String, DetailData>();
        DetailData first = new DetailData();
        DetailData keyword1 = new DetailData();
        DetailData keyword2 = new DetailData();
        DetailData keyword3 = new DetailData();
        DetailData remark = new DetailData();
        String color = "#173177";
        first.setColor(color);
        first.setValue("您有项任务待审");
        data.put("first", first);
        keyword1.setColor(color);
        keyword1.setValue("为商机申请");
        data.put("keyword1", keyword1);
        keyword2.setColor(color);
        Date nowTime = new Date();
        String strDate = dfs.format(nowTime);
        keyword2.setValue(strDate);
        data.put("keyword2", keyword2);
        keyword3.setColor(color);
        keyword3.setValue("湖南的");
        data.put("keyword3", keyword3);
        remark.setColor(color);
        remark.setValue("请尽快处理");
        data.put("keyword1", keyword1);
        templateDate.setData(data);
        String jsonString = new Gson().toJson(templateDate).toString();
        AccessToken at = accessTokenMapper.getLatestAccessToken();
        
        Date oldTime=at.getCreateTime();
        Date newTime =new Date();
        long between=(newTime.getTime()-oldTime.getTime())/1000;
        String accessToken ="";
        //数据库中的access_token时间若超过7200秒则重新获取
        if(between>7200){
            accessToken = getAccessToken();
            accessTokenMapper.insert(accessToken);
        }else{//否则获取数据库存的access_token
            accessToken = accessTokenMapper.getLatestAccessToken().getAccessToken();
        }
        String postURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
                + accessToken;
        JSONObject reponseData = HttpUtil.httpRequest(postURL, "POST", jsonString);
        System.out.println(reponseData.get("errmsg"));
    }

    public String getAccessToken() {
        String url =
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx88c7d6055939b9ae&secret=3775bc55fff28c4c6915b24b379016da";
        JSONObject accessToken = HttpUtil.httpRequest(url, "GET", null);
        return accessToken.getString("access_token");
    }
}

package com.example.model;

import java.util.List;
import java.util.Map;

public class TemplateData {
    //用户openId
    private String touser;
     //模板消息id
    private String template_id;
    //固定URL
    private String url;
    //详细内容
    private Map<String,DetailData> data;
    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getTemplate_id() {
        return template_id;
    }
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Map<String, DetailData> getData() {
        return data;
    }
    public void setData(Map<String, DetailData> data) {
        this.data = data;
    }
}

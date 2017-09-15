package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.SNSUserInfo;
import com.example.entity.WeixinOauth2Token;
import com.example.util.AdvancedUtil;
import com.example.util.Constants;
import com.example.util.PropertiesLoader;

@RestController
@RequestMapping("weixin/auth")
public class OAuthController {

    @RequestMapping("/getOpenId")
    public void getOpenId(@RequestParam(name = "code", required = false) String code) {
        // 用户同意授权
        if (!"authdeny".equals(code)) {
            String appID =
                    PropertiesLoader.loadProperties(Constants.wechatProPath).getProperty("appID");
            String appsecret = PropertiesLoader.loadProperties(Constants.wechatProPath)
                    .getProperty("appsecret");
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token =
                    AdvancedUtil.getOauth2AccessToken(appID, appsecret, code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
             SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
            System.out.println(openId);
        }
    }
}

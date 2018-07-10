package com.example.data;

import lombok.Data;

@Data
public class TokenParamModel {
	private String code;
	private String appId;
	private String appSecret;
	private String refreshTokenExpire;
}

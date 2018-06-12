package com.example.util.httpclient;

import java.util.Map;

public interface HttpClientCallback {

	Map<String,Object> doConnect(Map<String,Object> retMap);
	
}

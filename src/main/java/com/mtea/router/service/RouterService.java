/**
 * Copyright (C) 2012 macrotea@qq.com Inc., All Rights Reserved.
 */
package com.mtea.router.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mtea.router.global.RouterConfig;


/**
 * 路由器服务类
 * @author 	liangqiye@@gz.iscas.ac.cn
 * @version 1.0 , 2012-11-28 上午11:49:32	
 */
public class RouterService {
	
	private static final Logger logger = LoggerFactory.getLogger(RouterConfig.class);

	/**
	 * 认证登录
	 * @return
	 * liangqiye / 2012-11-28 下午2:09:26
	 */
	public boolean doAuthLogin() {
		boolean flag = false;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		configClient(httpclient);
		HttpGet httpget = new HttpGet(RouterConfig.RouterAuthURL);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
			EntityUtils.consume(response.getEntity());
			logger.info("正在进行路由器认证登录...");
			logger.info("请求路由器登录认证响应的状态值: " + response.getStatusLine());
			logger.info("路由器登录认证是否成功: " + isResponseSuccess(response));
			flag = isResponseSuccess(response);
		} catch (ClientProtocolException e) {
			flag = false;
			logger.error("路由器认证登录失败", e);
		} catch (IOException e) {
			flag = false;
			logger.error("路由器认证登录失败", e);
		}finally{
			shutdown(httpclient);
		}
		return flag;
	}
	
	/**
	 * 重启路由器
	 * @return
	 * liangqiye / 2012-11-28 下午2:14:08
	 */
	public boolean doReboot() {
		boolean flag = false;
		//若登录成功
		if(doAuthLogin()){
			DefaultHttpClient httpclient = new DefaultHttpClient();
			configClient(httpclient);
			HttpGet httpget = new HttpGet(RouterConfig.RouterAuthURL + RouterConfig.RouterRebootURI);
			HttpResponse response;
			try {
				response = httpclient.execute(httpget);
				EntityUtils.consume(response.getEntity());
				logger.info("重启路由器...");
				logger.info("请求路由器重启响应的状态值: " + response.getStatusLine());
				logger.info("重启路由器是否成功: " + isResponseSuccess(response));
				flag = isResponseSuccess(response);
			} catch (ClientProtocolException e) {
				flag = false;
				logger.error("重启路由器失败", e);
			} catch (IOException e) {
				flag = false;
				logger.error("重启路由器失败", e);
			}finally{
				shutdown(httpclient);
			}
		}
		return flag;
	}

	/**
	 * 是否响应成功
	 * @param response
	 * @return
	 * liangqiye / 2012-11-28 下午2:19:45
	 */
	private boolean isResponseSuccess(HttpResponse response) {
		return response.getStatusLine().getStatusCode() == 200;
	}
	
	/**
	 * 退出
	 * macrotea / 2012-6-8 上午9:30:15
	 * @param httpclient 
	 */
	private void shutdown(DefaultHttpClient httpclient) {
		httpclient.getConnectionManager().shutdown();
	}

	/**
	 * 配置
	 * liangqiye / 2012-11-28 上午11:52:53
	 * @param httpclient 
	 */
	private void configClient(DefaultHttpClient httpclient) {
		httpclient.getCredentialsProvider().setCredentials(new AuthScope(RouterConfig.RouterAuthURL.replaceAll("[http://|https://]", ""), Integer.parseInt(RouterConfig.RouterPort)), new UsernamePasswordCredentials(RouterConfig.RouterAuthUsername,RouterConfig.RouterAuthPassword));
	}

}

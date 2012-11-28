/**
 * Copyright (C) 2012 macrotea@qq.com Inc., All Rights Reserved.
 */
package com.mtea.router.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 路由器配置
 * @author liangqiye@@gz.iscas.ac.cn
 * @version 1.0 , 2012-11-28 上午11:53:39
 */
public class RouterConfig {

	private static final Logger logger = LoggerFactory.getLogger(RouterConfig.class);
	/**
	 * 路由配置文件
	 */
	public static final String ROUTER_FILE = "/router.properties";
	
	public static String RouterAuthURL = null;
	public static String RouterPort = null;
	public static String RouterAuthUsername = null;
	public static String RouterAuthPassword = null;
	public static String RouterRebootURI = null;

	static {
		loadRouterConfigFile();
	}

	/**
	 * 加载路由配置文件
	 * liangqiye / 2012-11-28 上午11:57:13
	 */
	public static void loadRouterConfigFile() {
		InputStream inStream = RouterConfig.class.getResourceAsStream(ROUTER_FILE);
		Properties prop = new Properties();
		try {
			prop.load(inStream);
		} catch (IOException e) {
			logger.error(String.format("程序加载路由配置文件: %s 出错!", ROUTER_FILE), e);
		}

		// 设定全局常量
		RouterAuthURL = prop.getProperty("router.auth.url").trim();
		RouterPort = prop.getProperty("router.port").trim();
		RouterAuthUsername = prop.getProperty("router.auth.username").trim();
		RouterAuthPassword = prop.getProperty("router.auth.password").trim();
		RouterRebootURI = prop.getProperty("router.reboot.uri").trim();
		
		logger.info(
			new StringBuilder()
			.append("\n路由器认证页面: ").append(RouterAuthURL)
			.append("\n路由器端口: ").append(RouterPort)
			.append("\n路由器认证用户名: ").append(RouterAuthUsername)
			.append("\n路由器认证密码: ").append(RouterAuthPassword)
			.append("\n路由器重启URI: ").append(RouterRebootURI).toString()
		);
		
	}

}

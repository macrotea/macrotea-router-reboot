/**
 * Copyright (C) 2012 macrotea@qq.com Inc., All Rights Reserved.
 */
package com.mtea.router;

import com.mtea.router.service.RouterService;

/**
 * 程序入口类
 * 
 * @author liangqiye@@gz.iscas.ac.cn
 * @version 1.0 , 2012-11-28 下午2:22:17
 */
public class Main {
	
	public static void main(String[] args) {
		RouterService routerService = new RouterService();
		routerService.doReboot();
	}

}

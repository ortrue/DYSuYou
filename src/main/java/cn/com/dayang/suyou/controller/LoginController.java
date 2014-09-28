/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package cn.com.dayang.suyou.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.dayang.suyou.constants.Constants;
import cn.com.dayang.suyou.enums.MethodTypeEnum;
import cn.com.dayang.suyou.service.UserService;
import cn.com.dayang.suyou.spring.CustomizedPropertyPlaceholder;
import cn.com.dayang.suyou.util.AppUtil;
import cn.com.dayang.suyou.vo.SysUser;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 真正登录的POST请求由Filter完成,
 * @author ivor
 */
@Controller
@RequestMapping(value = "/user")
public class LoginController {
	
	public final static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "index",method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	@RequestMapping(value = "login",method = RequestMethod.GET)
	public String login(Model model,HttpServletRequest request) {
		return "index";
	}
	
	//登录页面
	@RequestMapping(value = "login",method = RequestMethod.POST)
	public String login(@RequestParam(value="username") String username,@RequestParam(value="password") String password, Model model,HttpServletRequest request) {
		
		if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password))
		{
            try {
	            JSONObject paramJson = new JSONObject();
				paramJson.put("username", username);
				paramJson.put("password", password);
				Map<String, Object> treeMap = new HashMap<String, Object>();
				String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
				treeMap.put("reqHost",host+"/login");
				treeMap.put("Method", MethodTypeEnum.METHOD_POST.name());
				treeMap.put("reqJson", paramJson.toString());
				SysUser shiroUser=userService.UserLoginByParam(treeMap);
				if(StringUtils.isNotBlank(shiroUser.getCode()))
				{
					request.getSession().setAttribute(Constants.SESSION_TOKEN, shiroUser);
					if(StringUtils.equals(shiroUser.getCode(), Constants.SYSTEM_CODE_101)){//管理员账号登陆成功
						logger.info(username+" 登陆成功："+shiroUser.getMessage());
						return "redirect:/mail";
					}else if(StringUtils.equals(shiroUser.getCode(), Constants.SYSTEM_CODE_102)){//个人账号登陆成功
						logger.info(username+" 登陆成功："+shiroUser.getMessage());
						return "redirect:/mail";
					}else if(StringUtils.equals(shiroUser.getCode(), Constants.SYSTEM_CODE_103)){//企业账号登陆成功
						//Do Nothing
						return "redirect:/mail";
					}else{
						request.setAttribute(Constants.LOGIN_FAILURE_ERROR_KEY,Constants.LOGIN_FAILURE_ERROR);
						return "index";
					}
				}else{
					request.setAttribute(Constants.LOGIN_FAILURE_ERROR_KEY,Constants.LOGIN_FAILURE_ERROR);
					return "index";
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return "error/500";
			}
		}else{
			request.setAttribute(Constants.LOGIN_FAILURE_ERROR_KEY,Constants.LOGIN_FAILURE_ERROR);
			return "index";
		}
	}
	
	@RequestMapping(value = "logout",method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		SysUser user=AppUtil.getCurrentUser(request);
		if(user!=null){
			request.getSession().setAttribute(Constants.SESSION_TOKEN, null);
			Map<String, Object> treeMap = new HashMap<String, Object>();
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
			treeMap.put("reqHost",host+"/logout");
			treeMap.put("Method", MethodTypeEnum.METHOD_GET.name());
			treeMap.put("reqSession",user.getSessionId());
			String returnStr=userService.UserLogout(treeMap);
			if(StringUtils.isNotBlank(returnStr) && StringUtils.equals(returnStr, Constants.SYSTEM_STATUS_OK)){
				logger.info(user.getUsername()+" 用户注销成功!");
			}
		}
		return "index";
	}
	
	
	
}

/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package cn.com.dayang.suyou.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;

import cn.com.dayang.suyou.constants.Constants;
import cn.com.dayang.suyou.enums.MethodTypeEnum;
import cn.com.dayang.suyou.service.MailService;
import cn.com.dayang.suyou.service.UserService;
import cn.com.dayang.suyou.spring.CustomizedPropertyPlaceholder;
import cn.com.dayang.suyou.util.AppUtil;
import cn.com.dayang.suyou.util.ChargeTypeSwitch;
import cn.com.dayang.suyou.util.StringUtil;
import cn.com.dayang.suyou.vo.SysUser;


/**
 * 用户相关
 * @author ivor
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	public final static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	private final JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
	
	@RequestMapping(value = "findpwd",method = RequestMethod.GET)
	public String findpwd() {
		return "user/findPasswd";
	}
	
	@RequestMapping(value = "findPwdSuccess",method = RequestMethod.GET)
	public String findPwdSuccess() {
		return "user/findPwdSuccess";
	}
	
	@RequestMapping(value = "findpwd", method = RequestMethod.POST)
	public String dofindpwd(@RequestParam(value = "username") String username,
			@RequestParam(value = "email") String email,
			HttpServletRequest request, Model model) {
		try {
		Map<String, Object> treeMap = new HashMap<String, Object>();
		String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
		String reqURL=host+"/findpassword";
		logger.info("Request URL:  "+reqURL);
		treeMap.put("reqHost",reqURL);
		treeMap.put("Method", MethodTypeEnum.METHOD_POST.name());
		Map<String,Object> paraMap=new HashMap<String,Object>();
		paraMap.put("username", username);
		paraMap.put("email", email);
		String reqJson=jsonMapper.toJson(paraMap);
		treeMap.put("reqJson",reqJson);
		String returnEntity=this.userService.findPassword(treeMap);
		if(StringUtils.isNotBlank(returnEntity)){
			JSONObject jsonObj = new JSONObject(returnEntity);
			int code=jsonObj.getInt("Code");
			if(code == 1){
				return "redirect:/user/findPwdSuccess";
			}
		}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "register",method = RequestMethod.GET)
	public String register() {
		return "user/register";
	}
	
	@ResponseBody
	@RequestMapping(value = "register",method = RequestMethod.POST)
	public Map<String, Object> doregister(@RequestParam(value="username") String username,@RequestParam(value="useremail") String useremail,@RequestParam(value="userrepasswd") String userrepasswd,@RequestParam(value="chargetype") String chargetype,HttpServletRequest request, Model model) {
		
		Map<String, Object> returnJsonMap = new HashMap<String, Object>();
		try {
			Map<String, Object> treeMap = new HashMap<String, Object>();
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
			treeMap.put("reqHost",host+"/signup");
			treeMap.put("Method", MethodTypeEnum.METHOD_POST.name());
			Map<String, Object> reqJsonMap = new HashMap<String, Object>();
			reqJsonMap.put("inputUsername", username);
			reqJsonMap.put("inputEmail", useremail);
			reqJsonMap.put("inputPassword", userrepasswd);
			reqJsonMap.put("chargetypeid",Integer.parseInt(chargetype));
			reqJsonMap.put("space", ChargeTypeSwitch.SpaceSwitch(Integer.parseInt(chargetype)));
			reqJsonMap.put("flow", ChargeTypeSwitch.flowSwitch(Integer.parseInt(chargetype)));
			reqJsonMap.put("activetime",1d);//过期时间
			JSONObject jsonObject = new JSONObject(reqJsonMap);
			treeMap.put("reqJson",jsonObject.toString());
			String returnStr=userService.userRegister(treeMap);
			System.out.println(returnStr);
			JSONObject returnJsonObj = new JSONObject(returnStr);
			returnJsonMap.put("code", returnJsonObj.getString("Code"));
			returnJsonMap.put("message", returnJsonObj.getString("Message"));
			returnJsonMap.put("description", returnJsonObj.getString("Description"));
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return returnJsonMap;
	}

	@RequestMapping(value = "updatepwd",method = RequestMethod.GET)
	public String toUpdatePwdPage(Model model) {
		return "user/updatePasswd";
	}
	
	@ResponseBody
	@RequestMapping(value = "updatepwd",method = RequestMethod.POST)
	public Map<String, Object>  doUpdatePwd(@RequestParam(value="oldPwd") String oldPwd,@RequestParam(value="newPwd") String newPwd,@RequestParam(value="reNewPwd") String reNewPwd,HttpServletRequest request, Model model) {
		Map<String, Object> returnJsonMap = new HashMap<String, Object>();
		SysUser user=AppUtil.getCurrentUser(request);
		try {
		if(user!=null){
			request.getSession().setAttribute(Constants.SESSION_TOKEN, null);
			Map<String, Object> treeMap = new HashMap<String, Object>();
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
			treeMap.put("reqHost",host+"/changepassword");
			treeMap.put("Method", MethodTypeEnum.METHOD_POST.name());
			treeMap.put("reqSession",user.getSessionId());
			
			Map<String, Object> reqJsonMap = new HashMap<String, Object>();
			reqJsonMap.put("inputPassword", oldPwd);
			reqJsonMap.put("inputPassword2", newPwd);
			reqJsonMap.put("inputPassword3", reNewPwd);
			JSONObject jsonObject = new JSONObject(reqJsonMap);
			treeMap.put("reqJson",jsonObject.toString());
			String returnStr=userService.updateUserPwd(treeMap);
			System.out.println(returnStr);
			JSONObject returnJsonObj = new JSONObject(returnStr);
			returnJsonMap.put("code", returnJsonObj.getString("Code"));
			returnJsonMap.put("message", returnJsonObj.getString("Message"));
			returnJsonMap.put("description", returnJsonObj.getString("Description"));
	
		}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return returnJsonMap;
	}
	
	
	/**
	 * 验证用户是否有效
	 * @param ctx
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/checkuser",method = RequestMethod.POST)
	public Map<String,Object> isAvailableUser(@RequestParam("username") String username,@RequestParam("email") String email,HttpServletRequest request){
		Map<String,Object> delResponse=new HashMap<String,Object>();
		delResponse.put("availableUser", true);
		Map<String, Object> treeMap = new HashMap<String, Object>();
		String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsHost");
		treeMap.put("reqHost",host+"/isAvailableUser");
		treeMap.put("Method", MethodTypeEnum.METHOD_POST.name());
		Map<String,Object> paraMap=new HashMap<String,Object>();
		if(StringUtils.isNotBlank(username)){
			paraMap.put("username", username);
		}else if(StringUtils.isNotBlank(email)){
			paraMap.put("email", email);
		}
		String reqJson=jsonMapper.toJson(paraMap);
		treeMap.put("reqJson",reqJson);
		String validEntity= mailService.isAvailableUser(treeMap);
		if(StringUtils.isNotBlank(validEntity)){
			if("true".equals(validEntity)){
				if(!StringUtil.isEmail(username)){
					delResponse.put("availableUser",false);
				}
			}
		}
		return delResponse;
	}
	
}

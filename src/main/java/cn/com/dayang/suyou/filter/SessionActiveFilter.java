package cn.com.dayang.suyou.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cn.com.dayang.suyou.constants.Constants;
import cn.com.dayang.suyou.enums.MethodTypeEnum;
import cn.com.dayang.suyou.security.LicenseManager;
import cn.com.dayang.suyou.spring.CustomizedPropertyPlaceholder;
import cn.com.dayang.suyou.util.AppUtil;
import cn.com.dayang.suyou.vo.SysUser;

public class SessionActiveFilter implements Filter{
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		try {
			//判断是否登录，如果不是登陆操作、链接到登陆页面或是过期页面，则验证Session是否超时
			if (session == null || session.getAttribute(Constants.SESSION_TOKEN) == null || !checkLicense()) { 
				response.sendRedirect(req.getServletContext().getContextPath()+"/user/index");
				return ;
			}else{
				SysUser user=AppUtil.getCurrentUser(request);
				//检查服务端Session
				Map<String, Object> treeMap = new HashMap<String, Object>();
				String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
				treeMap.put("reqHost",host+"/checksession");
				treeMap.put("Method", MethodTypeEnum.METHOD_GET.name());
				String responseBody=this.checksession(treeMap,user);
				System.out.println("check service session:  "+responseBody);
				if(StringUtils.isNotBlank(responseBody)){
					JSONObject reqJson = new JSONObject(responseBody);
					int code=reqJson.getInt("Code");
					if(code != 1){
						response.sendRedirect(req.getServletContext().getContextPath()+"/user/index");
						return ;
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		filterChain.doFilter(req, res);
	}
	
	public void destroy() {
		//Do Nothing
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//Do Nothing
	}
	
	public boolean checkLicense(){
		boolean checkF=false;
		String signature=(String)CustomizedPropertyPlaceholder.getContextProperty("system.config.lickey");
		if(StringUtils.isNotBlank(signature)){
			checkF=LicenseManager.getInstance().validate(signature);
		}
		if(!checkF){
			System.out.println("系统证书过期,请联系软件供应商!");
		}
		return checkF;
	}
	
	public String checksession(Map<String, Object> treeMap,SysUser user){
		ClientResponse response  =null;
		String entity=null;;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_GET.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			Cookie cookie=new Cookie("transmissionid", user.getSessionId());
			response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).get(ClientResponse.class);
			entity=response.getEntity(String.class);
		}
		return  entity;
	}
	
}
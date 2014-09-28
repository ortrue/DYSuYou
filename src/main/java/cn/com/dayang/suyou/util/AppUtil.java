package cn.com.dayang.suyou.util;

import javax.servlet.http.HttpServletRequest;
import cn.com.dayang.suyou.constants.Constants;
import cn.com.dayang.suyou.vo.SysUser;

public class AppUtil 
{
	public static SysUser getCurrentUser(HttpServletRequest request)
	{
		SysUser user =null;
		if(request!=null){
			 user = (SysUser) request.getSession().getAttribute(Constants.SESSION_TOKEN);
		}
		return user;
	}
}

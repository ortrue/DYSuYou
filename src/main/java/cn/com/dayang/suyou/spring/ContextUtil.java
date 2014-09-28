package cn.com.dayang.suyou.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext context;
	
    public static ApplicationContext getContext(){
    	return context;
    }
   
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		this.context=contex;
	}
}

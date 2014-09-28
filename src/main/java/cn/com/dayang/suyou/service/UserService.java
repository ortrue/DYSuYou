package cn.com.dayang.suyou.service;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import cn.com.dayang.suyou.entity.Users;
import cn.com.dayang.suyou.enums.MethodTypeEnum;
import cn.com.dayang.suyou.repository.UsersDao;
import cn.com.dayang.suyou.vo.SysUser;

import com.google.common.collect.Maps;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component
@Transactional
public class UserService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UsersDao usersDao;
	
	public SysUser UserLoginByParam(Map<String, Object> treeMap) {
		SysUser user = new SysUser();
		ClientResponse response = null;
		try {
			String method = (String) treeMap.get("Method");
			if (StringUtils.isNotBlank(method)&& StringUtils.equals(method, MethodTypeEnum.METHOD_POST.name())) {
				WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
				response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(treeMap.get("reqJson")).post(ClientResponse.class);
				List<NewCookie> cookieList = response.getCookies();
				for (NewCookie newCookie : cookieList) {
					if (StringUtils.equals(newCookie.getName(),"transmissionid")) {
						user.setSessionId(newCookie.getValue());
					}
				}
				
				user.setStatus(String.valueOf(response.getStatus()));
				JSONObject reqJson = new JSONObject(treeMap.get("reqJson").toString());
				user.setUsername(reqJson.getString("username"));
				user.setPassword(reqJson.getString("password"));
				user.setCode(String.valueOf(response.getStatus()));
				String textEntity = response.getEntity(String.class);
				if (StringUtils.isNotBlank(textEntity)) {
					JSONObject jsonObj = new JSONObject(textEntity);
					user.setCode(jsonObj.getString("Code"));
					user.setMessage(jsonObj.getString("Message"));
					user.setDescription(jsonObj.getString("Description"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return user;
	}
	
	public String UserLogout(Map<String, Object> treeMap){
		ClientResponse response  =null;
		String entity=null;;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_GET.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
			response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).get(ClientResponse.class);
			entity=response.getEntity(String.class);
		}
		return  entity;
	}
	
	
	public String updateUserPwd(Map<String, Object> treeMap){
		ClientResponse response  =null;
		String entity=null;;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_POST.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
			response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).cookie(cookie).entity(treeMap.get("reqJson").toString()).post(ClientResponse.class);
			entity=response.getEntity(String.class);
		}
		return  entity;
	}
	
	public String findPassword(Map<String, Object> treeMap){
		String entity=null;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_POST.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(treeMap.get("reqJson")).post(ClientResponse.class);
			entity=response.getEntity(String.class);
			System.out.println(entity);
		}
		return  entity;
	}
	
	public String userRegister(Map<String, Object> treeMap){
		ClientResponse response  =null;
		String entity=null;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_POST.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(treeMap.get("reqJson")).post(ClientResponse.class);
			entity=response.getEntity(String.class);
		}
		return  entity;
	}
	
	public Page<Users> fingUsersByAll(int pageNumber, int pageSize,String sortType){
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		return this.usersDao.findAll(pageRequest);
	}
	
	public List<Users> findUsersByLikeName(String name){
		Map<String, SearchFilter> filters = Maps.newLinkedHashMap();
		filters.put("name", new SearchFilter("name", Operator.LIKE,name));
		Specification<Users> spec = DynamicSpecifications.bySearchFilter(filters.values(), Users.class);
		List<Users> users= this.usersDao.findAll(spec);
		return users;
	}
	
	public Users findUserByName(String name){
		return this.usersDao.findByName(name);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	public Double[] getSpaceTotal(){
		return this.usersDao.getSpaceTotal();
	}
	
	public int updateUserStatus(Integer userId,Integer status){
		return this.usersDao.updateUserStatus(userId, status);
	}
	
}

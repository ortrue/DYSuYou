package cn.com.dayang.suyou.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.dayang.suyou.entity.ContactGroup;
import cn.com.dayang.suyou.service.ContactGroupListService;
import cn.com.dayang.suyou.util.AppUtil;
import cn.com.dayang.suyou.vo.SysUser;
import cn.com.dayang.suyou.vo.TreeView;

/**
 * 系统中所有分组
 * @author ivor
 */
@Controller
@RequestMapping(value = "/mail")
public class GroupController {

	@Autowired
	private ContactGroupListService contactGroupListService;
	
	/**
	 * 添加组
	 * @param groupName
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "user/group/add",method = RequestMethod.POST)
	public String userGroupAdd(@RequestParam(value="groupName") String groupName,HttpServletRequest request, Model model) {
		SysUser user=AppUtil.getCurrentUser(request);
		if(user!=null){
			//1、判断组是否存在
			List<ContactGroup> groupList=contactGroupListService.findGroupByName(user.getUsername(), groupName);
			if(groupList!=null && groupList.size() > 0){
				return "{\"success\":\"false\",\"msg\":\"该组已存在，请换个组名\"}";
			}else{
				//2、插入用户组
				ContactGroup contactGroupList=new ContactGroup();
				contactGroupList.setType(1);
				contactGroupList.setUsername(user.getUsername());
				contactGroupList.setContactname(groupName);
				boolean addF=contactGroupListService.GroupAdd(contactGroupList);
				if(addF){
					return "{\"success\":\"true\",\"msg\":\"组添加成功\"}";
				}
			}
		}
		return "{\"success\":\"false\",\"msg\":\"组添加失败\"}";
	}
	
	/**
	 * 添加联系人
	 * @param groupName
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "user/contact/add",method = RequestMethod.POST)
	public String contactAdd(@RequestParam(value = "treeId") String treeId,
			@RequestParam(value = "contact") String contact,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "mobile") String mobile,
			HttpServletRequest request, Model model) {
		SysUser user = AppUtil.getCurrentUser(request);
		if (user != null) {
			//1、判断联系人是否存在
			List<ContactGroup> groupList=contactGroupListService.findContactByName(user.getUsername(), treeId,contact);
			if(groupList!=null && groupList.size() > 0){
				return "{\"success\":\"false\",\"msg\":\"该组已存在此联系人\"}";
			}else{
				//2、插入用户组
				ContactGroup contactGroupList=new ContactGroup();
				contactGroupList.setParentid(Integer.parseInt(treeId));
				contactGroupList.setType(1);
				contactGroupList.setUsername(user.getUsername());
				contactGroupList.setContactname(contact);
				contactGroupList.setEmail(email);
				contactGroupList.setMobile(mobile);
				boolean addF=contactGroupListService.GroupAdd(contactGroupList);
				if(addF){
					return "{\"success\":\"true\",\"msg\":\"联系人添加成功\"}";
				}
			}
		}
		return "{\"success\":\"false\",\"msg\":\"联系人添加失败\"}";
	}
	
	@ResponseBody
	@RequestMapping(value = "user/group/all",method = RequestMethod.POST)
	public List<TreeView> userGroupAll(HttpServletRequest request, Model model) {
		SysUser user=AppUtil.getCurrentUser(request);
		List<TreeView> treeViewList=new ArrayList<TreeView>();
		
		if(user!=null){
			List<ContactGroup> contactGroupList=contactGroupListService.GroupAll(user.getUsername());
			if(contactGroupList!=null){
				for (int i=1;i<=contactGroupList.size();i++) {
					ContactGroup contactGroup =contactGroupList.get(i-1);
					TreeView treeView=new TreeView();
					
					if(contactGroup.getParentid()== null){
						treeView.setId(contactGroup.getId()+"");
						treeView.setpId(contactGroup.getParentid());
						treeView.setName(contactGroup.getContactname());
						treeView.setEmail(contactGroup.getEmail());
						treeView.setUrl("");
						
						treeView.setOpen(false);
						treeView.setIsParent(true);
					}else{
						treeView.setId(contactGroup.getParentid()+""+contactGroup.getId());
						treeView.setpId(contactGroup.getParentid());
						treeView.setName(contactGroup.getContactname());
						treeView.setEmail(contactGroup.getEmail());
						treeView.setUrl("");
						
						treeView.setOpen(false);
						treeView.setIsParent(false);
					}
					treeViewList.add(treeView);
				}
			}
		}
		return treeViewList;
	}

	
}

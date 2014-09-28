package cn.com.dayang.suyou.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import cn.com.dayang.suyou.entity.Users;
import cn.com.dayang.suyou.service.AdminService;
import cn.com.dayang.suyou.service.UserService;
import cn.com.dayang.suyou.util.ChargeTypeSwitch;
import cn.com.dayang.suyou.util.DateUtil;
import cn.com.dayang.suyou.util.FileSizeUtil;
import cn.com.dayang.suyou.util.TimeUtil;
import cn.com.dayang.suyou.vo.MailTrendDetailModel;
import cn.com.dayang.suyou.vo.MailTrendModel;
import cn.com.dayang.suyou.vo.UserStatementModel;
import cn.com.dayang.suyou.vo.UsersModel;

/**
 * 系统管理：包括用户管理、用户流水、邮件动态、综合查询
 * @author ivor
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	public final static Logger logger = Logger.getLogger(AdminController.class);
	
	private static final String PAGE_SIZE = "10";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		//1、获得当前用户我的文件夹信息
		return "admin/adminLeft";
	}
	
	/**
	 * 用户管理列表
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "usermanage",method = RequestMethod.GET)
	public String usermanage(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			Model model,HttpServletRequest request) {
		Page<Users> userManageList=this.userService.fingUsersByAll(pageNumber, pageSize, sortType);
		if(userManageList!=null && userManageList.getContent() !=null){
			List<Users> usersList=userManageList.getContent();
			for(int i=0;i<usersList.size();i++){
				Users user=usersList.get(i);
				user.setChargetypeidStr(ChargeTypeSwitch.TypeSwitch(user.getChargetypeid()));
				user.setSpacetotalStr(FileSizeUtil.formatKB2MBOrGB(user.getSpacetotal()));
				user.setSpaceusedStr(FileSizeUtil.formatKB2MBOrGB(user.getSpaceused()));
				user.setDownloadflowtotalStr(FileSizeUtil.formatKB2MBOrGB(user.getDownloadflowtotal()));
				user.setDownloadflowusedStr(FileSizeUtil.formatKB2MBOrGB(user.getDownloadflowused()));
			}
		}
		
		model.addAttribute("userManageList", userManageList);
		
		//1、注册总人数
		model.addAttribute("usersCount", userManageList.getTotalElements());
		//2、总空间
		Double totalarry[]=this.userService.getSpaceTotal();
		model.addAttribute("spaceTotal",FileSizeUtil.formatKB2MBOrGB(totalarry[0]!=null?totalarry[0]:0));
		//3、已用空间
		model.addAttribute("spaceUsedTotal",FileSizeUtil.formatKB2MBOrGB(totalarry[1]!=null?totalarry[1]:0));
		
		return "admin/userManage";
	}
	
	/**
	 * 用户流水列表
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "userstatement",method = RequestMethod.GET)
	public String userstatement(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,Model model,HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		List<UserStatementModel> userStatementResultList=this.adminService.getUserStatementAllByPage(searchParams, pageNumber, pageSize);
		int totalcount=userStatementResultList.size();
		int firstIndex=(pageNumber-1)*pageSize;  
		int toIndex=pageNumber*pageSize;  
		if(toIndex>totalcount){  
		    toIndex=totalcount;  
		}  
		if(firstIndex>toIndex){  
		    firstIndex=0;  
		} 
		
		PageRequest pageRequest=new PageRequest(pageNumber - 1, pageSize);
		Page<UserStatementModel> userStatementList = new PageImpl(userStatementResultList.subList(firstIndex, toIndex), pageRequest, totalcount);
		model.addAttribute("UserStatementList", userStatementList);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		if(searchParams!=null && searchParams.get("userName") !=null){
			String userName=searchParams.get("userName").toString();
			Users userInfo=this.userService.findUserByName(userName);
			userInfo.setChargetypeidStr(ChargeTypeSwitch.TypeSwitch(userInfo.getChargetypeid()));
			userInfo.setSpacetotalStr(FileSizeUtil.formatKB2MBOrGB(userInfo.getSpacetotal()));
			userInfo.setSpaceusedStr(FileSizeUtil.formatKB2MBOrGB(userInfo.getSpaceused()));
			userInfo.setDownloadflowtotalStr(FileSizeUtil.formatKB2MBOrGB(userInfo.getDownloadflowtotal()));
			userInfo.setDownloadflowusedStr(FileSizeUtil.formatKB2MBOrGB(userInfo.getDownloadflowused()));
			model.addAttribute("UserInfo", userInfo);
		}
		return "admin/userStatement";
	}
	
	/**
	 * 获得所有用户
	 * @return
	 */
	@RequestMapping(value = "userall",method = RequestMethod.POST)
	public @ResponseBody List<Users>  getUserAllList(String userName,Model model,HttpServletRequest request) {
		//1、查询所有用户
		List<Users> usersAllList=this.userService.findUsersByLikeName(userName);
		return usersAllList;
	}

	/**
	 * 邮件动态列表
	 * @param pageNumber
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "mailtrend", method = RequestMethod.GET)
	public String mailtrend(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,Model model,HttpServletRequest request) {

		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<MailTrendModel> mailTrendList = this.adminService.getMailTrendListAllByPage(searchParams, pageNumber, pageSize);
		if(mailTrendList!=null && mailTrendList.getContent() !=null){
			List<MailTrendModel> trendList=mailTrendList.getContent();
			for(int i=0;i<trendList.size();i++){
				MailTrendModel mailTrendModel=trendList.get(i);
				mailTrendModel.setAttachmentspaceStr(FileSizeUtil.formatKB2MBOrGB(mailTrendModel.getAttachmentspace()));
			}
		}
		
		model.addAttribute("mailTrendList", mailTrendList);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "admin/mailTrend";
	}
	
	/**
	 * 邮件动态
	 * @param mailid
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "mailTrendDetail/{mailid}", method = RequestMethod.POST)
	public @ResponseBody List<MailTrendDetailModel> mailTrendDetail(@PathVariable(value="mailid") Integer mailid,Model model,HttpServletRequest request) {
		List<MailTrendDetailModel> trendDetailList=new ArrayList<MailTrendDetailModel>();
		List<MailTrendDetailModel> recipientsList=this.adminService.getMailTrendDetailListByRecipients(mailid);
		try {
			if(recipientsList!=null && recipientsList.size() > 0){
				for (MailTrendDetailModel recipientsModel : recipientsList) {
					String title=recipientsModel.getTitle().length() >15?recipientsModel.getTitle().substring(0,15)+"...":recipientsModel.getTitle(); 
					recipientsModel.setContent(recipientsModel.getSendName()+" 在 "+DateUtil.format(TimeUtil.convertString(recipientsModel.getSendTime()),"yyyy-MM-dd HH:mm:ss")+"向 "+recipientsModel.getRecipientName()+"发送一封主题为  <span title="+recipientsModel.getTitle()+">"+title+"</span> 的信息");
					
					Map<String,Object> paraMap=new HashMap<String,Object>();
					paraMap.put("mailid", mailid);
					paraMap.put("recId", recipientsModel.getRecipientUid());
					List<MailTrendDetailModel> resdownloadsList=this.adminService.getMailTrendDetailListByResdownloads(paraMap);
					if(resdownloadsList!=null && resdownloadsList.size() > 0){
						for (MailTrendDetailModel resdownloadsModel : resdownloadsList) {
							String resouceName=resdownloadsModel.getResourceName().length()>25?resdownloadsModel.getResourceName().substring(0,15)+"...":resdownloadsModel.getResourceName();
							resdownloadsModel.setContent(resdownloadsModel.getDownloadName()+" 在 "+DateUtil.format(TimeUtil.convertString(resdownloadsModel.getDownloadTime()),"yyyy-MM-dd HH:mm:ss")+" 下载了 "+resdownloadsModel.getSendName()+"发送的名称为  <span title="+resdownloadsModel.getResourceName()+">"+resouceName+"</span> 的附件.");
						}
						trendDetailList.addAll(resdownloadsList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		trendDetailList.addAll(recipientsList);
		
		return trendDetailList;
	}
	
	/**
	 * 综合查询列表
	 * @param pageNumber
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "integratequery",method = RequestMethod.GET)
	public String query(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,Model model,HttpServletRequest request) {
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Page<UsersModel> integrateQueryList =this.adminService.getIntegrateQueryAllByPage(searchParams, pageNumber, pageSize);
		if(integrateQueryList!=null && integrateQueryList.getContent() !=null){
			List<UsersModel> usersModelList=integrateQueryList.getContent();
			for(int i=0;i<usersModelList.size();i++){
				UsersModel usersModel=usersModelList.get(i);
				usersModel.setSpaceusedStr(FileSizeUtil.formatKB2MBOrGB(usersModel.getSpaceused()));
				usersModel.setMailsTotalStr(FileSizeUtil.formatKB2MBOrGB(usersModel.getMailsTotal()));
				usersModel.setAttaTotalStr(FileSizeUtil.formatKB2MBOrGB(usersModel.getAttaTotal()));
			}
		}
		//1、列表
		model.addAttribute("integrateQueryList", integrateQueryList);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		//2、注册人数
		model.addAttribute("UserTotal", integrateQueryList.getTotalElements());
		//3、活跃人数
		Integer activeUser=this.adminService.getIntegrateQueryActiveUser();
		model.addAttribute("ActiveUser",activeUser);
		//4、下载总量
		Double downloadTotal=this.adminService.getIntegrateQueryAttaTotalSize();
		model.addAttribute("DownloadTotal",FileSizeUtil.formatKB2MBOrGB(downloadTotal));
		
		return "admin/integratedQuery";
	}

	@ResponseBody
	@RequestMapping(value = "user/status",method = RequestMethod.POST)
	public String updateUserStatus(@RequestParam(value="userId") int userId,@RequestParam(value="status") int status,HttpServletRequest request, Model model) {
		int count=this.userService.updateUserStatus(userId, status);
		if(count > 0){
			return "{\"success\":\"true\",\"msg\":\"用户状态修改成功\"}";
		}
		 return "{\"success\":\"false\",\"msg\":\"用户状态修改失败\"}";
	}
	
}

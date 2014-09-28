package cn.com.dayang.suyou.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.mapper.JsonMapper;
import org.springside.modules.utils.Clock;

import cn.com.dayang.suyou.entity.SysAttachments;
import cn.com.dayang.suyou.entity.SysMails;
import cn.com.dayang.suyou.enums.MailDetailTypeEnum;
import cn.com.dayang.suyou.enums.MethodTypeEnum;
import cn.com.dayang.suyou.service.MailService;
import cn.com.dayang.suyou.service.SysAttachmentsService;
import cn.com.dayang.suyou.service.SysMailsService;
import cn.com.dayang.suyou.spring.CustomizedPropertyPlaceholder;
import cn.com.dayang.suyou.util.AppUtil;
import cn.com.dayang.suyou.util.DateConvert;
import cn.com.dayang.suyou.util.FileSizeUtil;
import cn.com.dayang.suyou.util.StringUtil;
import cn.com.dayang.suyou.vo.AttachmentDetail;
import cn.com.dayang.suyou.vo.MailAttachments;
import cn.com.dayang.suyou.vo.MailContent;
import cn.com.dayang.suyou.vo.MailDetailResponse;
import cn.com.dayang.suyou.vo.MenuLeftTotal;
import cn.com.dayang.suyou.vo.NewMailRequest;
import cn.com.dayang.suyou.vo.NewMailResponse;
import cn.com.dayang.suyou.vo.SysUser;

/**
 * 邮件相关
 * @author ivor
 */
@Controller
@RequestMapping(value = "/mail")
public class MailController {

	public final static Logger logger = Logger.getLogger(MailController.class);
	
	private final JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
	
	private static final String PAGE_SIZE = "10";
	
	private Clock clock = Clock.DEFAULT;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SysMailsService sysMailsService;
	
	@Autowired
	private SysAttachmentsService sysAttachmentsService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		//1、获得当前用户我的文件夹信息
		return "mail/mailLeft";
	}
	
	/**
	 * 收件箱
	 * @return
	 */
	@RequestMapping(value = "inbox",method = RequestMethod.GET)
	public String inbox(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,String keyword,
			Model model,HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
			SysUser user=AppUtil.getCurrentUser(request);
			if(user!=null){
				Map<String, Object> treeMap = new HashMap<String, Object>();
				String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
				String reqURL=host+"/inbox/?p="+pageNumber+"&pagesize="+pageSize;
				String keywords=request.getParameter("keyword");
				if(StringUtils.isNotBlank(keywords)){
					reqURL+="&keyword="+URLDecoder.decode(keywords, "UTF-8");
				}
				logger.info("Request URL:  "+reqURL);
				treeMap.put("reqHost",reqURL);
				treeMap.put("Method", MethodTypeEnum.METHOD_GET.name());
				treeMap.put("reqSession",user.getSessionId());
				PageRequest pageRequest=new PageRequest(pageNumber - 1, pageSize);
				Page<MailContent> inboxMailList =mailService.getMailByInbox(treeMap,pageRequest);
				model.addAttribute("inboxMailList", inboxMailList);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "mail/mailInbox";
	}
	
	/**
	 * 查看邮件详情
	 * @return
	 */
	@RequestMapping(value = "detail/{id}/{type}",method = RequestMethod.GET)
	public String mailDetail(@PathVariable(value="id") Integer id,@PathVariable(value="type")String type, HttpServletRequest request,Model model) {

		SysUser user=AppUtil.getCurrentUser(request);
		MailDetailResponse mailDetailResponse=buildMailDetail(user,id,type);
		model.addAttribute("mailDetail",mailDetailResponse);
		model.addAttribute("fwMailType", type);
		return "mail/mailDetail1";
	}
	
	/**
	 * 跳转新建邮件页面
	 * @return
	 */
	@RequestMapping(value = "newmail/{mid}/{type}/{uuid}",method = RequestMethod.GET)
	public String newmail(@PathVariable(value="mid") Integer mid,@PathVariable(value="type")String type,@PathVariable(value="uuid")String uuid,Model model,HttpServletRequest request) {
		SysUser user=AppUtil.getCurrentUser(request);
		model.addAttribute("mailUUID", uuid);
		MailDetailResponse mailDetailResponse=buildMailDetail(user,mid,type);
		model.addAttribute("mailInfo",mailDetailResponse);
		if(mailDetailResponse!=null)
			mailDetailResponse.setTitle("FW:"+mailDetailResponse.getTitle());
		
		return "mail/mailNew";
	}
	
	/**
	 * 新建邮件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "newmail", method = RequestMethod.POST)
	public NewMailResponse mailadd(
			@RequestParam(value = "mailTo") String mailTo,
			@RequestParam(value = "mailTitle") String mailTitle,
			@RequestParam(value = "mailContent") String mailContent,
			@RequestParam(value = "attaInfo") String attaInfo,
			HttpServletRequest request, Model model) {

		NewMailResponse newMailResponse=null;
		try {
			SysUser user=AppUtil.getCurrentUser(request);
			if(user!=null){
				Map<String, Object> treeMap = new HashMap<String, Object>();
				String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
				treeMap.put("reqHost",host+"/newmail");
				treeMap.put("Method", MethodTypeEnum.METHOD_POST.name());
				treeMap.put("reqSession",user.getSessionId());
				
				//1、构建Mail信息
				NewMailRequest newMail=new NewMailRequest();
				newMail.setRecipients(mailTo);
				if(StringUtils.isNotBlank(mailTitle))
					newMail.setTitle(mailTitle);
				else
					newMail.setTitle("(无主题)");
				newMail.setContent(mailContent);
				
				//2、构建附件信息
				List<MailAttachments> attaList=new ArrayList<MailAttachments>();
				//set附件信息
				if(StringUtils.isNotBlank(attaInfo)){
					JSONArray jsonArray =new JSONArray(attaInfo);
					if(jsonArray !=null){
						for(int i=0;i<jsonArray.length();i++){
							JSONObject attaInfoObj = (JSONObject) jsonArray.get(i);
							MailAttachments attachments=new MailAttachments();
							attachments.setFilename(attaInfoObj.getString("filename"));
							attachments.setFilesize(attaInfoObj.getDouble("filesize"));
							attachments.setBlocktrans(0);
							attachments.setBlocktotalnums(0);
							attaList.add(attachments);
						}
					}
				}
				
				newMail.setAttachments(attaList);
				String reqJson=jsonMapper.toJson(newMail);
				treeMap.put("reqJson",reqJson);
				//3、调用接口新建邮件
				newMailResponse=mailService.addMail(treeMap);
				
				if(StringUtils.equals(newMailResponse.getCode(), "1")){
					//4、构建系统Mail信息
					SysMails sysMail=new SysMails();
					sysMail.setSmailid(Long.valueOf(newMailResponse.getMailid()));
					if(StringUtils.isNotBlank(mailTitle))
						sysMail.setTitle(mailTitle);
					else
						sysMail.setTitle("(无主题)");
					sysMail.setContent(mailContent);
					sysMail.setSendername(user.getUsername());
					sysMail.setCreateuser(user.getUsername());
					sysMail.setRecipients(mailTo);
					sysMail.setCreatetime(DateConvert.DateToStr(clock.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
					sysMail.setSendtime(DateConvert.DateToStr(clock.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
					sysMail.setStatus(2);
					//5、保存系统Mail信息
					SysMails newDelMail=this.sysMailsService.saveSysMail(sysMail);
			
					//6、构建系统附件信息
					Map<String, Object> treeMap1 = new HashMap<String, Object>();
					treeMap1.put("reqHost",host+"/mail/"+newMailResponse.getMailid());
					treeMap1.put("Method", MethodTypeEnum.METHOD_GET.name());
					treeMap1.put("reqSession",user.getSessionId());
					MailDetailResponse mailDetailResponse= mailService.findMailById(treeMap1);
					List<AttachmentDetail> attaDetailList=mailDetailResponse.getAttachmentDetail();
					if(attaDetailList!=null){
						for(int j=0;j<attaDetailList.size();j++){
							AttachmentDetail attaDetail=attaDetailList.get(j);
							SysAttachments sysAttachments=new SysAttachments();
							sysAttachments.setMailid(newDelMail.getId());
							sysAttachments.setAttaname(attaDetail.getResname());
							sysAttachments.setSize(attaDetail.getSize());
							sysAttachments.setHash("");
							sysAttachments.setImagewidth(attaDetail.getImagewidth());
							sysAttachments.setImageheight(attaDetail.getImageheight());
							sysAttachments.setCreatetime(DateConvert.DateToStr(clock.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
							sysAttachments.setCompletetime(attaDetail.getCompletetime());
							sysAttachments.setDownloadurl(attaDetail.getUrl());
							sysAttachments.setDescription(attaDetail.getDescription());
							//7、保存系统附件信息
							this.sysAttachmentsService.saveSysAttachments(sysAttachments);
						}
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newMailResponse;
	}
	
	/**
	 * 新建草稿邮件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "draftmailadd", method = RequestMethod.POST)
	public String draftmailadd(
			@RequestParam(value = "mailTo") String mailTo,
			@RequestParam(value = "mailTitle") String mailTitle,
			@RequestParam(value = "mailContent") String mailContent,
			HttpServletRequest request, Model model) {
		    
		    Map<String,Object> delResponse=new HashMap<String,Object>();
			SysUser user=AppUtil.getCurrentUser(request);
			if(user!=null){
				SysMails sysMail=new SysMails();
				
				if(StringUtils.isNotBlank(mailTitle))
					sysMail.setTitle(mailTitle);
				else
					sysMail.setTitle("(无主题)");
				sysMail.setContent(mailContent);
				sysMail.setSendername(user.getUsername());
				sysMail.setCreateuser(user.getUsername());
				sysMail.setRecipients(mailTo);
				sysMail.setCreatetime(DateConvert.DateToStr(clock.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
				sysMail.setStatus(1);
				SysMails newDelMail=this.sysMailsService.saveSysMail(sysMail);
				logger.info("邮件信息存草稿成功!"+newDelMail.getId());
			}
			delResponse.put("success", "true");
			JSONObject jsonObject = new JSONObject(delResponse);
			return jsonObject.toString();
	}
	
	/**
	 * 邮件发送成功页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "success",method = RequestMethod.GET)
	public String mailSuccess(Model model,HttpServletRequest request) {
		return "mail/mailSuccess";
	}
	
	/**
	 * 删除邮件
	 * @param ctx
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="delete",method = RequestMethod.POST)
	public String del(@RequestParam("items") String items,HttpServletRequest request){
		Map<String,Object> delResponse=new HashMap<String,Object>();
		SysUser user=AppUtil.getCurrentUser(request);
		if(user!=null){
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
			if(StringUtils.isNotBlank(items)){
				String mailIds[]=items.split(",");
				for(int i=0;i<mailIds.length;i++){
					//1、查询要删除的邮件
					Map<String, Object> treeMap1 = new HashMap<String, Object>();
					treeMap1.put("reqHost",host+"/mail/"+mailIds[i]);
					treeMap1.put("Method", MethodTypeEnum.METHOD_GET.name());
					treeMap1.put("reqSession",user.getSessionId());
					MailDetailResponse mailDetailResponse= mailService.findMailById(treeMap1);
					
					//2、删除快传服务端邮件
					Map<String, Object> treeMap2 = new HashMap<String, Object>();
					treeMap2.put("reqHost",host+"/mail/"+mailIds[i]);
					treeMap2.put("Method", MethodTypeEnum.METHOD_GET.name());
					treeMap2.put("reqSession",user.getSessionId());
					Map<String,Object> responseMap=mailService.deleteMailById(treeMap2);
					
					//2、保存删除邮件和附件到BS系统数据库
					if(responseMap!=null){
						SysMails sysMail=new SysMails();
						sysMail.setSmailid(Long.valueOf(mailIds[i]));
						sysMail.setTitle(mailDetailResponse.getTitle());
						sysMail.setContent(mailDetailResponse.getContent());
						sysMail.setSendername(mailDetailResponse.getSendername());
						sysMail.setRecipients(mailDetailResponse.getRecipients());
						sysMail.setCreateuser(user.getUsername());
						sysMail.setCreatetime(DateConvert.DateToStr(clock.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
						sysMail.setDeletetime(DateConvert.DateToStr(clock.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
						sysMail.setStatus(3);
						SysMails newDelMail=this.sysMailsService.saveSysMail(sysMail);
						List<AttachmentDetail> attaList=mailDetailResponse.getAttachmentDetail();
						if(attaList!=null){
							for(int j=0;j<attaList.size();j++){
								AttachmentDetail attaDetail=attaList.get(j);
								SysAttachments sysAttachments=new SysAttachments();
								sysAttachments.setMailid(newDelMail.getId());
								sysAttachments.setAttaname(attaDetail.getResname());
								sysAttachments.setSize(attaDetail.getSize());
								sysAttachments.setHash("");
								sysAttachments.setImagewidth(attaDetail.getImagewidth());
								sysAttachments.setImageheight(attaDetail.getImageheight());
								sysAttachments.setCreatetime(DateConvert.DateToStr(clock.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
								sysAttachments.setCompletetime(DateConvert.DateToStr(clock.getCurrentDate(), "yyyy-MM-dd HH:mm:ss"));
								sysAttachments.setDownloadurl(attaDetail.getUrl());
								sysAttachments.setDescription(attaDetail.getDescription());
								this.sysAttachmentsService.saveSysAttachments(sysAttachments);
							}
						}
					}
				}
			}
		}
		delResponse.put("success", "true");
		JSONObject jsonObject = new JSONObject(delResponse);
		return jsonObject.toString();
	}
	
	public MailDetailResponse buildMailDetail(SysUser user,Integer id,String type){
		MailDetailResponse mailDetailResponse=null;
		if(user!=null){
			if(id !=0 && StringUtils.equals(type, MailDetailTypeEnum.SUYOUMAIL.getCode())){
				Map<String, Object> treeMap = new HashMap<String, Object>();
				String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
				treeMap.put("reqHost",host+"/mail/"+id);
				treeMap.put("Method", MethodTypeEnum.METHOD_GET.name());
				treeMap.put("reqSession",user.getSessionId());
				mailDetailResponse= mailService.findMailById(treeMap);
				mailDetailResponse.setRecipients(user.getUsername());
				return mailDetailResponse;
			}else if(id !=0 && StringUtils.equals(type, MailDetailTypeEnum.SYSMAIL.getCode())){
				SysMails sysMails=this.sysMailsService.findSysMailById(id);
				if(sysMails!=null){
					List<SysAttachments> sysAttaList=this.sysAttachmentsService.findSysAttachmentById(id);
					mailDetailResponse=new MailDetailResponse();
					mailDetailResponse.setId(sysMails.getId());
					mailDetailResponse.setCode("");
					mailDetailResponse.setTitle(sysMails.getTitle());
					mailDetailResponse.setSendername(sysMails.getSendername());
					mailDetailResponse.setRecipients(sysMails.getRecipients());
					mailDetailResponse.setContent(sysMails.getContent());
					mailDetailResponse.setSendtime(sysMails.getSendtime());
					mailDetailResponse.setStatus(sysMails.getStatus());
					
					if(sysAttaList!=null){
						List<AttachmentDetail> attaDetailList=new ArrayList<AttachmentDetail>();
						double attaSizeTotal=0;
						for (SysAttachments sysAttachments : sysAttaList) {
							
							AttachmentDetail attaDetail=new AttachmentDetail();
							attaDetail.setId(sysAttachments.getId());
							attaDetail.setResname(sysAttachments.getAttaname());
							attaDetail.setSize(sysAttachments.getSize());
							attaDetail.setCreatetime(sysAttachments.getCreatetime());
							attaDetail.setCompletetime(sysAttachments.getCompletetime());
							attaDetail.setStatus(null);
							attaDetail.setDescription(sysAttachments.getDescription());
							attaDetail.setImagewidth(sysAttachments.getImagewidth());
							attaDetail.setImageheight(sysAttachments.getImageheight());
							attaDetail.setImage("");
							attaDetail.setUrl(sysAttachments.getDownloadurl());
							
							attaDetailList.add(attaDetail);
							attaSizeTotal+=attaDetail.getSize();
						}
						mailDetailResponse.setAttachmentDetail(attaDetailList);
						mailDetailResponse.setAttaCounts(attaDetailList.size());
						mailDetailResponse.setAttaSizeTotal(FileSizeUtil.formatKB2MBOrGB(attaSizeTotal));
					}
					return mailDetailResponse;
				}
			}
		}
		return null;
	}
	
	/**
	 * 已发送邮件、已删除邮件、邮件草稿箱
	 * @return
	 */
	@RequestMapping(value = "mailmng/{mailStat}",method = RequestMethod.GET)
	public String mailmng(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@PathVariable(value="mailStat")String mailStat,@RequestParam(value = "keyword", defaultValue = "")String keyword,Model model,HttpServletRequest request) {
		try {
			SysUser user=AppUtil.getCurrentUser(request);
			if(user!=null){
				if(StringUtils.isNotBlank(keyword)){
					keyword=URLDecoder.decode(keyword, "UTF-8");
				}
				Page<SysMails> sysMailsList = mailService.findSysMailByAll(pageNumber, pageSize, sortType, Integer.parseInt(mailStat),user.getUsername(),keyword);
				model.addAttribute("sysMailsList", sysMailsList);
			}
			if(StringUtils.equals(mailStat, "1")){
				return "mail/mailDraft";
			}else if(StringUtils.equals(mailStat, "2")){
				return "mail/mailSended";
			}else if(StringUtils.equals(mailStat, "3")){
				return "mail/mailDeleted";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		return "";
	}	
	
	/**
	 * 统计菜单上显示的邮件数量
	 * @param ctx
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="total",method = RequestMethod.POST)
	public MenuLeftTotal mailTotal(HttpServletRequest request){
		MenuLeftTotal menuLeftTotal=new MenuLeftTotal();
		SysUser user=AppUtil.getCurrentUser(request);
		int pageNumber=1;
		//1、收件箱数量
		if(user!=null){
			Map<String, Object> treeMap = new HashMap<String, Object>();
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
			treeMap.put("reqHost",host+"/inbox/?p=1&pagesize="+PAGE_SIZE);
			treeMap.put("Method", MethodTypeEnum.METHOD_GET.name());
			treeMap.put("reqSession",user.getSessionId());
			PageRequest pageRequest=new PageRequest(pageNumber - 1, Integer.parseInt(PAGE_SIZE));
			Page<MailContent> inboxMailList =mailService.getMailByInbox(treeMap,pageRequest);
			menuLeftTotal.setMailTotal(inboxMailList.getTotalElements());
			//2、草稿箱数量
			Page<SysMails> mailsList =mailService.findSysMailByAll(pageNumber,Integer.parseInt(PAGE_SIZE), "auto",1,user.getUsername(),null);
			menuLeftTotal.setDraftTotal(mailsList.getTotalElements());
			//3、已发送邮件数量
			Page<SysMails> sendMailsList =mailService.findSysMailByAll(pageNumber,Integer.parseInt(PAGE_SIZE), "auto",2,user.getUsername(),null);
			menuLeftTotal.setSendedTotal(sendMailsList.getTotalElements());
			//4、已删除邮件数量
			Page<SysMails> deleteMailsList =mailService.findSysMailByAll(pageNumber,Integer.parseInt(PAGE_SIZE), "auto",3,user.getUsername(),null);
			menuLeftTotal.setDeleteTotal(deleteMailsList.getTotalElements());
		}
		return menuLeftTotal;
	}
	
	/**
	 * 删除邮件： 已发送邮件、已删除邮件、邮件草稿箱
	 * @param ctx
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/sysmail/delete",method = RequestMethod.POST)
	public String sysMailDel(@RequestParam("items") String items,@RequestParam("status")int status,HttpServletRequest request){
		Map<String,Object> delResponse=new HashMap<String,Object>();
		SysUser user=AppUtil.getCurrentUser(request);
		if(user!=null){
			if(StringUtils.isNotBlank(items)){
				String mailIds[]=items.split(",");
				for(int i=0;i<mailIds.length;i++){
					//1、查询邮件
					SysMails sysMails=this.sysMailsService.findSysMailById(Integer.parseInt(mailIds[i]));
					if(status == 1 || status == 2){
						sysMails.setStatus(3);
						this.sysMailsService.saveSysMail(sysMails);
					}else if(status == 3){
						//2、删除快传服务端邮件
						String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
						Map<String, Object> treeMap2 = new HashMap<String, Object>();
						treeMap2.put("reqHost",host+"/mail/"+sysMails.getSmailid());
						treeMap2.put("Method", MethodTypeEnum.METHOD_GET.name());
						treeMap2.put("reqSession",user.getSessionId());
						Map<String,Object> responseMap=mailService.deleteMailById(treeMap2);
						//3、删除BS端邮件
						if(responseMap!=null && StringUtils.equals(responseMap.get("code").toString(),"301")){
							this.sysMailsService.deleteSysMailById(Integer.parseInt(mailIds[i]));
						}
					}
				}
			}
		}
		delResponse.put("success", "true");
		JSONObject jsonObject = new JSONObject(delResponse);
		return jsonObject.toString();
	}
	
	/**
	 * 获取下载文件链接
	 * @param ctx
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/attachment/download/getlink",method = RequestMethod.POST)
	public Map<String,Object> getAttaDownloadLink(@RequestParam("mailid") String mailid,@RequestParam("resid")String resid,HttpServletRequest request){
		Map<String,Object> delResponse=new HashMap<String,Object>();
		SysUser user=AppUtil.getCurrentUser(request);
		if(user!=null){
			Map<String, Object> treeMap = new HashMap<String, Object>();
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
			treeMap.put("reqHost",host+"/downloadurl/"+mailid+"/"+resid+"/");
			treeMap.put("Method", MethodTypeEnum.METHOD_GET.name());
			treeMap.put("reqSession",user.getSessionId());
			delResponse= mailService.getAttachmentDownloadURL(treeMap);
		}
		return delResponse;
	}
	
	/**
	 * 已发送邮件、已删除邮件、邮件草稿箱
	 * @return
	 */
	@RequestMapping(value = "/attachment/download/updateuseflow",method = RequestMethod.POST)
	public Map<String,Object> updateuseflow(@RequestParam("userflow") Double userflow,Model model,HttpServletRequest request) {
		
		Map<String,Object> delResponse=new HashMap<String,Object>();
		SysUser user=AppUtil.getCurrentUser(request);
		if(user!=null){
			Map<String, Object> treeMap = new HashMap<String, Object>();
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
			treeMap.put("reqHost",host+"/updateuseflow/?useflow="+userflow);
			treeMap.put("Method", MethodTypeEnum.METHOD_GET.name());
			treeMap.put("reqSession",user.getSessionId());
			delResponse= mailService.updateUserFlow(treeMap);
		}
		return delResponse;

	}
	
	/**
	 * 验证用户是否有效
	 * @param ctx
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/availableUser",method = RequestMethod.POST)
	public Map<String,Object> isAvailableUser(@RequestParam("username") String username,HttpServletRequest request){
		Map<String,Object> delResponse=new HashMap<String,Object>();
		delResponse.put("availableUser", true);
		SysUser user=AppUtil.getCurrentUser(request);
		if(user!=null){
			Map<String, Object> treeMap = new HashMap<String, Object>();
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsHost");
			treeMap.put("reqHost",host+"/isAvailableUser");
			treeMap.put("Method", MethodTypeEnum.METHOD_POST.name());
			Map<String,Object> paraMap=new HashMap<String,Object>();
			paraMap.put("username", username);
			String reqJson=jsonMapper.toJson(paraMap);
			treeMap.put("reqJson",reqJson);
			treeMap.put("reqSession",user.getSessionId());
			String validEntity= mailService.isAvailableUser(treeMap);
			if(StringUtils.isNotBlank(validEntity)){
				if("true".equals(validEntity)){
					if(!StringUtil.isEmail(username)){
						delResponse.put("availableUser",false);
					}
				}
			}
		}
		return delResponse;
	}
	
	/**
	 * 发送取消 或者Tab确认关闭
	 * @param ctx
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/senderdeletemail",method = RequestMethod.POST)
	public Map<String,Object> senderdeletemail(@RequestParam("mailid") String mailid,HttpServletRequest request){
		Map<String,Object> delResponse=new HashMap<String,Object>();
		SysUser user=AppUtil.getCurrentUser(request);
		if(user!=null && StringUtils.isNotBlank(mailid)){
			Map<String, Object> treeMap = new HashMap<String, Object>();
			String host =  (String)CustomizedPropertyPlaceholder.getContextProperty("system.config.wsURL");
			treeMap.put("reqHost",host+"/senderdeletemail");
			treeMap.put("Method", MethodTypeEnum.METHOD_POST.name());
			treeMap.put("reqSession",user.getSessionId());
			Map<String,Object> paraMap=new HashMap<String,Object>();
			paraMap.put("mailid", Integer.parseInt(mailid));
			String reqJson=jsonMapper.toJson(paraMap);
			treeMap.put("reqJson",reqJson);
			
			//删除服务端记录
			Map<String,Object> responseMap= mailService.senderDeleteMail(treeMap);
			Integer code=(Integer) responseMap.get("code");
			logger.info("服务端邮件删除成功! "+code);
			
			//删除系统Mail记录
			SysMails sysMails=this.sysMailsService.findSysMailBySmailId(mailid);
			this.sysMailsService.deleteSysMailById(sysMails.getId());
			
		}
		return delResponse;
	}
	
	/**
	 * 附件下载
	 * @param ctx
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping(value="/attachment/download",method = RequestMethod.GET)
	public void  attaDownload(@RequestParam("dlink") String dlink,@RequestParam("filename")String filename,HttpServletResponse response){
		
		ServletOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        byte[] buf = new byte[2048];
        int size = 0;
       
        try {
	        if(StringUtils.isNotBlank(dlink) && StringUtils.isNotBlank(filename)){
	        	String filedisplay = URLEncoder.encode(filename,"UTF-8");
	        	response.reset();
	        	response.setContentType("application/x-download");
	    	    response.setHeader("Content-Disposition", "attachment;filename=\"" + filedisplay + "\"");
	        	
	        	//建立链接
				url = new URL(dlink);
	            httpUrl = (HttpURLConnection) url.openConnection();
	            //连接指定的资源
	            httpUrl.connect();
	            //获取网络输入流
	            bis = new BufferedInputStream(httpUrl.getInputStream());
	            //建立文件
	            fos = response.getOutputStream();
	            System.out.println("正在获取链接[" + dlink + "]的内容...\n将其保存为文件[" +filename + "]");
	            //保存文件
	            while ((size = bis.read(buf)) != -1){
	                fos.write(buf, 0, size);
	            }
	            fos.flush();
	        }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(bis);
			if(httpUrl!=null){
				httpUrl.disconnect();
			}
		}
	}
	
}

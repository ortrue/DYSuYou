package cn.com.dayang.suyou.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cn.com.dayang.suyou.entity.SysMails;
import cn.com.dayang.suyou.enums.MethodTypeEnum;
import cn.com.dayang.suyou.repository.SysMailsDao;
import cn.com.dayang.suyou.util.FileSizeUtil;
import cn.com.dayang.suyou.vo.AttachmentDetail;
import cn.com.dayang.suyou.vo.MailContent;
import cn.com.dayang.suyou.vo.MailDetailResponse;
import cn.com.dayang.suyou.vo.NewMailResponse;

@Component
@Transactional
public class MailService {

	//private final JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
	private final SimpleDateFormat dateFormatS = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private final SimpleDateFormat dateFormatT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	
	@Autowired
	private SysMailsDao sysMailsDao;
	
	public Page<MailContent> getMailByInbox(Map<String, Object> treeMap,PageRequest pageRequest) {
		ClientResponse response  =null;
		Page<MailContent> mailInboxList=null;
		String entity=null;;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_GET.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
			response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).get(ClientResponse.class);
			entity=response.getEntity(String.class);
			System.out.println(entity);
			mailInboxList=converInboxMailResponse(entity,pageRequest);
		}
		return  mailInboxList;
	}
	
	public NewMailResponse addMail(Map<String, Object> treeMap) {
		NewMailResponse newMailResponse=null;
		ClientResponse response  =null;

		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_POST.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
			response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).entity(treeMap.get("reqJson")).post(ClientResponse.class);
			String entity=response.getEntity(String.class);
			System.out.println("New Mail Response:  "+entity);
			newMailResponse=this.convertNewMailResponse(entity);
		}
		return  newMailResponse;
	}
	
	public MailDetailResponse findMailById(Map<String, Object> treeMap) {
		ClientResponse response  =null;
		MailDetailResponse mailDetailResponse=null;
		String entity=null;;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_GET.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
			response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).get(ClientResponse.class);
			entity=response.getEntity(String.class);
			System.out.println(entity);
			mailDetailResponse=this.convertMailDetail(entity);
		}
		return  mailDetailResponse;
	}
	
	public Map<String,Object> deleteMailById(Map<String, Object> treeMap) {
		Map<String,Object> responseMap  =new HashMap<String,Object>();
		ClientResponse response=null;
		String entity=null;;
		String method=(String)treeMap.get("Method");
		try {
			if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_GET.name())){
				WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
				Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
				response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).delete(ClientResponse.class);
				entity=response.getEntity(String.class);
				System.out.println(entity);
				JSONObject jsonObject = new JSONObject(entity);
				responseMap.put("code", jsonObject.getString("Code"));
				responseMap.put("message", jsonObject.getString("Message"));
				responseMap.put("desc", jsonObject.getString("Description"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return  responseMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> PageImpl<T> converInboxMailResponse(String mailContent,PageRequest pageRequest) {
		List<MailContent> mailList = null;
		PageImpl<T> pageImpl = null;
		try {
			if (StringUtils.isNotBlank(mailContent)) {
				mailList = new ArrayList<MailContent>();
				JSONObject jsonObject = new JSONObject(mailContent);

				Long total = jsonObject.getLong("TotalNum");
				String mailBriefs = jsonObject.getString("MailBriefs");

				if(StringUtils.isNotBlank(mailBriefs)&& !StringUtils.equals(mailBriefs, "null")){
					
					JSONArray jsonArray =new JSONArray(mailBriefs);
					for (int i = 0; i < jsonArray.length(); i++) {
						MailContent contentVo = new MailContent();
						JSONObject object = (JSONObject) jsonArray.get(i);
						contentVo.setId(object.getInt("Id"));
						contentVo.setTitle(object.getString("Title"));
						contentVo.setBrief(object.getString("Brief"));
						contentVo.setSenderName(object.getString("Sendername"));
						contentVo.setSendTime(convertDate(object.getString("Sendtime")));
						contentVo.setAttachmentNum(object.getInt("Attachmentnum"));
						contentVo.setAttachmentSpace(object.getDouble("Attachmentspace"));
						contentVo.setStatus(object.getInt("Status"));
						if(contentVo.getAttachmentNum() > 0 && contentVo.getAttachmentSpace() > 0){
							String attaSpace=FileSizeUtil.formatGB2MBOrKB(contentVo.getAttachmentSpace());
							contentVo.setAttaInfo("附件数:"+contentVo.getAttachmentNum()+"个,大小: "+attaSpace);
						}
						mailList.add(contentVo);
					}
				}
				pageImpl = new PageImpl(mailList, pageRequest, total);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return pageImpl;
	}
	
	public NewMailResponse convertNewMailResponse(String responseEntity){
		NewMailResponse newMailResponse=new NewMailResponse();
		try {
			if (StringUtils.isNotBlank(responseEntity)) {
				JSONObject jsonObject = new JSONObject(responseEntity);
				newMailResponse.setCode(jsonObject.getString("code"));
				newMailResponse.setMsg(jsonObject.getString("Msg"));
				newMailResponse.setMailid(jsonObject.getLong("mailid"));
				
				List<Map<String,Object>> attaTokenList=new ArrayList<Map<String,Object>>();
				if(StringUtils.isNotBlank(jsonObject.getString("attachmentupinfos")) && !"null".equals(jsonObject.getString("attachmentupinfos"))){
					JSONArray attaJsonArray =new JSONArray(jsonObject.getString("attachmentupinfos"));
					if(attaJsonArray!=null){
						for(int i=0;i<attaJsonArray.length();i++){
							JSONObject attaTokenObj = (JSONObject) attaJsonArray.get(i);
							
							Map<String,Object> attaTokenMap=new HashMap<String,Object>();
							attaTokenMap.put("attachmentid",attaTokenObj.getInt("attachmentid"));
							attaTokenMap.put("token",attaTokenObj.getString("token"));
							attaTokenMap.put("key", attaTokenObj.getString("key"));
							attaTokenMap.put("filename", attaTokenObj.getString("filename"));
							attaTokenList.add(attaTokenMap);
						}
					}
				}
				newMailResponse.setAttachmentupinfos(attaTokenList);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newMailResponse;
	}
	
	
	public MailDetailResponse convertMailDetail(String responseEntity){
		MailDetailResponse mailDetailResponse=new MailDetailResponse();
		double attaSize=0;
		try {
			if (StringUtils.isNotBlank(responseEntity)) {
				JSONObject jsonObject = new JSONObject(responseEntity);

				mailDetailResponse.setCode(jsonObject.getString("Code"));
				mailDetailResponse.setMailStatus(jsonObject.getInt("MailStatus"));
				  
				JSONObject mailObj = new JSONObject(jsonObject.getString("Mail"));
				mailDetailResponse.setId(mailObj.getInt("Id"));
				mailDetailResponse.setTitle(mailObj.getString("Title"));
				mailDetailResponse.setSendername(mailObj.getString("Sendername"));
				mailDetailResponse.setRecipients(mailObj.getString("Recnames"));
				mailDetailResponse.setContent(mailObj.getString("Content"));
				mailDetailResponse.setSendtime(mailObj.getString("Sendtime"));
				mailDetailResponse.setAttaCounts(mailObj.getInt("Attachmentnum"));
				mailDetailResponse.setAttachmentspace(mailObj.getDouble("Attachmentspace"));
				mailDetailResponse.setStatus(mailObj.getInt("Status"));
				
				if(StringUtils.isNotBlank(jsonObject.getString("Attachments")) && !StringUtils.equals(jsonObject.getString("Attachments"), "null")){
					JSONArray attaJsonArray =new JSONArray(jsonObject.getString("Attachments"));
					List<AttachmentDetail> attaDetailList=new ArrayList<AttachmentDetail>();
					if(attaJsonArray!=null){
						for(int i=0;i<attaJsonArray.length();i++){
							JSONObject mailDetailObj = (JSONObject) attaJsonArray.get(i);
							AttachmentDetail attaDetail=new AttachmentDetail();
							attaDetail.setId(mailDetailObj.getInt("Id"));
							attaDetail.setResname(mailDetailObj.getString("Resname"));
							attaDetail.setSize(mailDetailObj.getDouble("Size"));
							attaDetail.setShortSize(FileSizeUtil.formatKB2MBOrGB(attaDetail.getSize()));
							attaDetail.setStatus(mailDetailObj.getInt("Status"));
							attaDetail.setDescription(mailDetailObj.getString("Description"));
							attaDetail.setImagewidth(mailDetailObj.getLong("Imagewidth"));
							attaDetail.setImageheight(mailDetailObj.getLong("Imagehigh"));
							attaDetail.setImagedata(mailDetailObj.getString("Imagedata"));
							attaDetail.setImage(mailDetailObj.getString("Image"));
							attaDetail.setUrl(mailDetailObj.getString("Url"));
							attaDetailList.add(attaDetail);
							
							attaSize+=attaDetail.getSize();
						}
					}
					mailDetailResponse.setAttachmentDetail(attaDetailList);
					mailDetailResponse.setAttaSizeTotal(FileSizeUtil.formatKB2MBOrGB(attaSize));
					mailDetailResponse.setAttaSizeTotalByte(attaSize);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return mailDetailResponse;
	}
	
	public Page<SysMails> findSysMailByAll(int pageNumber, int pageSize,String sortType,Integer status,final String sender,final String keyword) {
		final Map<String, SearchFilter> filters = Maps.newLinkedHashMap();
		filters.put("status", new SearchFilter("status", Operator.EQ, status));
		filters.put("createuser", new SearchFilter("createuser", Operator.EQ, sender));
		
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		return sysMailsDao.findAll(new Specification<SysMails>(){
			@Override
			public Predicate toPredicate(Root<SysMails> root,CriteriaQuery<?> query, CriteriaBuilder builder) {
		        Predicate basePredicate = DynamicSpecifications.bySearchFilter(filters.values(), SysMails.class).toPredicate(root, query, builder);
				if(StringUtils.isNotBlank(keyword)){
					 List<Predicate> orPredicates = Lists.newArrayList();
					 
				     Path<String> titlePath = root.get("title");
				     Predicate titlePredicate = builder.like(titlePath, "%"+keyword+"%");
				     orPredicates.add(builder.or(titlePredicate));
				     
				     Path<String> sendernamePath = root.get("sendername");
				     Predicate sendernamePredicate = builder.like(sendernamePath, "%"+keyword+"%");
				     orPredicates.add(builder.or(sendernamePredicate));
				     
				     Path<String> recipientsPath = root.get("recipients");
				     Predicate recipientsPredicate = builder.like(recipientsPath, "%"+keyword+"%");
				     orPredicates.add(builder.or(recipientsPredicate));
				     
				     Path<String> contentPath = root.get("content");
				     Predicate contentPredicate = builder.like(contentPath, "%"+keyword+"%");
				     orPredicates.add(builder.or(contentPredicate));
				     
				     Predicate likePredicate = builder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
				     query.where(basePredicate,likePredicate);
				}else{
			        query.where(basePredicate);
				}

				return query.getRestriction();
			}}, pageRequest);
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
	
	public SysMails findSysMailsById(Integer mailId){
		return this.sysMailsDao.findOne(mailId);
	}
	
	public Map<String,Object> getAttachmentDownloadURL(Map<String, Object> treeMap) {
		ClientResponse response =null;
		Map<String,Object> downloadInfoMap=new HashMap<String,Object>();
		String entity=null;
		try {
			String method=(String)treeMap.get("Method");
			if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_GET.name())){
				WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
				Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
				response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).get(ClientResponse.class);
				entity=response.getEntity(String.class);
				System.out.println(entity);
				if(StringUtils.isNotBlank(entity)){
					JSONObject jsonObj = new JSONObject(entity);
					downloadInfoMap.put("Code",jsonObj.getInt("Code"));
					downloadInfoMap.put("Blockorfile",jsonObj.getInt("Blockorfile"));
					downloadInfoMap.put("Blocktotalnums",jsonObj.getInt("Blocktotalnums"));
					downloadInfoMap.put("Url", jsonObj.getString("Url"));
					downloadInfoMap.put("Lastdownloadpath", jsonObj.getString("Lastdownloadpath"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return  downloadInfoMap;
	}
	
	public String isAvailableUser(Map<String, Object> treeMap){
		ClientResponse response =null;
		String entity=null;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_POST.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
			response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).entity(treeMap.get("reqJson")).post(ClientResponse.class);
			entity=response.getEntity(String.class);
			System.out.println(entity);
		}
		return  entity;
	}
	
	public Map<String,Object> senderDeleteMail(Map<String, Object> treeMap){
		ClientResponse response =null;
		Map<String,Object> responseMap=new HashMap<String,Object>();
		try {
			String method=(String)treeMap.get("Method");
			if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_POST.name())){
				WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
				Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
				response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).entity(treeMap.get("reqJson")).post(ClientResponse.class);
				String entity=response.getEntity(String.class);
				System.out.println(entity);
				if(StringUtils.isNotBlank(entity)){
					JSONObject jsonObj = new JSONObject(entity);
					responseMap.put("code", jsonObj.getInt("code"));
					responseMap.put("Msg", jsonObj.getString("Msg"));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return  responseMap;
	}
	
	public Map<String,Object> updateUserFlow(Map<String, Object> treeMap) {
		Map<String,Object> responseMap  =new HashMap<String,Object>();
		ClientResponse response=null;
		String entity=null;;
		String method=(String)treeMap.get("Method");
		if(StringUtils.isNotBlank(method) && StringUtils.equals(method, MethodTypeEnum.METHOD_GET.name())){
			WebResource resource = Client.create().resource(treeMap.get("reqHost").toString());
			Cookie cookie=new Cookie("transmissionid", String.valueOf(treeMap.get("reqSession")));
			response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.TEXT_PLAIN).cookie(cookie).get(ClientResponse.class);
			entity=response.getEntity(String.class);
			System.out.println(entity);
		}
		return  responseMap;
	}
	
	
	public String convertDate(String simpleDate) throws ParseException{
		String standardDate="";
		if(StringUtils.isNotBlank(simpleDate)){
			String tempDate[]=simpleDate.split("\\+");
			standardDate=dateFormatS.format(dateFormatT.parse(tempDate[0]));
		}
		return standardDate;
	}
	
}
	

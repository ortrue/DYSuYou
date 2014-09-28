package cn.com.dayang.suyou.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.dayang.suyou.base.dao.BaseDAOImpl;
import cn.com.dayang.suyou.repository.UsersDao;
import cn.com.dayang.suyou.util.FileSizeUtil;
import cn.com.dayang.suyou.vo.MailTrendDetailModel;
import cn.com.dayang.suyou.vo.MailTrendModel;
import cn.com.dayang.suyou.vo.MailsModel;
import cn.com.dayang.suyou.vo.UserStatementModel;
import cn.com.dayang.suyou.vo.UsersModel;

import com.google.common.collect.Maps;

@Component
@Transactional
public class AdminService {

	@Autowired
	private BaseDAOImpl baseDAOImpl;
	
	@Autowired
	private UsersDao usersDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Page<T> getMailTrendListAllByPage(Map<String, Object> params,int pageNum,int pageSize){
		int totalCount=this.baseDAOImpl.get("userTrendMapper.getMailTrendListAllByPageCount", params);
		List<MailTrendModel> mailList=this.baseDAOImpl.getListOfPage("userTrendMapper.getMailTrendListAllByPage", params, pageNum, pageSize);
		PageRequest pageRequest=new PageRequest(pageNum - 1, pageSize);
		PageImpl<T> pageImpl  = new PageImpl(mailList, pageRequest, totalCount);
		return pageImpl;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public List<UserStatementModel> getUserStatementAllByPage(Map<String, Object> params,int pageNum,int pageSize){
		List<UserStatementModel> userStatementList=new ArrayList<UserStatementModel>();
		if(params!=null){
			List<MailsModel> mailsModelList=this.baseDAOImpl.getList("userStatementMapper.getMailsAllByPara", params);
			for (MailsModel mailsModel : mailsModelList) {
				Map recMaps=Maps.newLinkedHashMap();
				recMaps.put("mailid", mailsModel.getId());
				recMaps.put("userid", mailsModel.getSenderid());
				recMaps.put("startTime", params.get("startTime"));
				recMaps.put("endTime", params.get("endTime"));
				//上传
				List<Map> recipientsList=this.baseDAOImpl.getList("userStatementMapper.getRecipientsByPara", recMaps);
				for (Map map : recipientsList) {
					UserStatementModel userStateMentModel=new UserStatementModel();
					userStateMentModel.setMailId(mailsModel.getId());
					userStateMentModel.setTitle(mailsModel.getTitle());
					userStateMentModel.setUserId(mailsModel.getSenderid());
					userStateMentModel.setUserName(mailsModel.getSendername());
					userStateMentModel.setFlowTotal(mailsModel.getAttachmentspace());
					userStateMentModel.setFlowTotalStr(FileSizeUtil.formatKB2MBOrGB(mailsModel.getAttachmentspace()));
					userStateMentModel.setGmtTime(mailsModel.getSendtime());
					userStateMentModel.setOpType("上传");
					
					userStatementList.add(userStateMentModel);
				}
				//下载
				List<Map> resdownloadsList=this.baseDAOImpl.getList("userStatementMapper.getResdownloadsByPara", recMaps);
				for (Map map : resdownloadsList) {
					UserStatementModel userStateMentModel=new UserStatementModel();
					userStateMentModel.setMailId(mailsModel.getId());
					userStateMentModel.setTitle(mailsModel.getTitle());
					userStateMentModel.setUserId(mailsModel.getSenderid());
					userStateMentModel.setUserName(mailsModel.getSendername());
					userStateMentModel.setFlowTotal(mailsModel.getAttachmentspace());
					userStateMentModel.setFlowTotalStr(FileSizeUtil.formatKB2MBOrGB(mailsModel.getAttachmentspace()));
					userStateMentModel.setGmtTime(String.valueOf(map.get("createtime")));
					userStateMentModel.setOpType("下载");
					userStatementList.add(userStateMentModel);
				}
			}
		}
		return userStatementList;
	}
	
	public List<MailTrendDetailModel> getMailTrendDetailListByRecipients(Integer mailid){
		List<MailTrendDetailModel> list=this.baseDAOImpl.getList("userTrendMapper.getMailTrendDetailListByRecipients", mailid);
		return list;
	}
	
	public List<MailTrendDetailModel> getMailTrendDetailListByResdownloads(Map<String,Object> paraMap){
		List<MailTrendDetailModel> list=this.baseDAOImpl.getList("userTrendMapper.getMailTrendDetailListByResdownloads", paraMap);
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Page<T> getIntegrateQueryAllByPage(Map<String, Object> params,int pageNum,int pageSize){
		int totalCount=this.baseDAOImpl.get("integrateQueryMapper.getUsersAllByPageCount", params);
		List<UsersModel> usersList=this.baseDAOImpl.getListOfPage("integrateQueryMapper.getUsersAllByPage", params, pageNum, pageSize);
		if(usersList!=null){
			for (UsersModel usersModel : usersList) {
				int mailsCount=this.baseDAOImpl.get("integrateQueryMapper.getMailCountByUserId", usersModel.getId());
				int attaCount=this.baseDAOImpl.get("integrateQueryMapper.getAttaCountByUserId", usersModel.getId());
				Double mailsTotal=this.baseDAOImpl.get("integrateQueryMapper.getMailTotalByUserId", usersModel.getId());
				Double attaTotal=this.baseDAOImpl.get("integrateQueryMapper.getAttaTotalByUserId", usersModel.getId());
				
				usersModel.setMailsCount(mailsCount);
				usersModel.setAttaCount(attaCount);
				usersModel.setMailsTotal(mailsTotal!=null?mailsTotal:0);
				usersModel.setAttaTotal(attaTotal!=null?attaTotal:0);
			}
		}
		
		PageRequest pageRequest=new PageRequest(pageNum - 1, pageSize);
		PageImpl<T> pageImpl  = new PageImpl(usersList, pageRequest, totalCount);
		return pageImpl;
	}
	
	public Double getIntegrateQueryAttaTotalSize(){
		Double totalSize=this.baseDAOImpl.get("integrateQueryMapper.getAttaTotalSize",null);
		return totalSize;
	}
	
	public Integer getIntegrateQueryActiveUser(){
		List<Integer> activeUserList=new ArrayList<Integer>();
	    //1、查询
		List<Integer> mailsActiveUserList=this.baseDAOImpl.getList("integrateQueryMapper.getMailsActiveUser");
		List<Integer> reourceActiveUserList=this.baseDAOImpl.getList("integrateQueryMapper.getResourceActiveUser");
		List<Integer> downloadActiveUserList=this.baseDAOImpl.getList("integrateQueryMapper.getDownloadActiveUser");
		//2、合并
		activeUserList.addAll(mailsActiveUserList);
		activeUserList.addAll(reourceActiveUserList);
		activeUserList.addAll(downloadActiveUserList);
		//3、去重
		Set<Integer> set=new LinkedHashSet<Integer>();
		set.addAll(activeUserList); 
		
		return set.size();
	}
	
}

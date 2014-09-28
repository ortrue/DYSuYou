package cn.com.dayang.suyou.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Maps;

import cn.com.dayang.suyou.entity.ContactGroup;
import cn.com.dayang.suyou.repository.ContactGroupListDao;

@Component
@Transactional
public class ContactGroupListService {

	@Autowired
	private ContactGroupListDao contactGroupListDao;
	
	public boolean GroupAdd(ContactGroup contactGroup){
		ContactGroup savedGroup=contactGroupListDao.save(contactGroup);
		return savedGroup.getId()>0?true:false;
	}

	public List<ContactGroup> findGroupByName(String userName,String contactName){
		return this.contactGroupListDao.findGroupByName(userName, contactName);
	}
	
	public List<ContactGroup> findContactByName(String userName,String treeId,String contactName){
		return this.contactGroupListDao.findContactByName(userName,Integer.parseInt(treeId),contactName);
	}
	
	public List<ContactGroup> GroupAll(String username)
	{
		Map<String, SearchFilter> filters = Maps.newLinkedHashMap();
		filters.put("username", new SearchFilter("username", Operator.EQ, username));
		Specification<ContactGroup> spec = DynamicSpecifications.bySearchFilter(filters.values(), ContactGroup.class);
		
		List<ContactGroup> contactGroupList=contactGroupListDao.findAll(spec);
		return contactGroupList.size()>0?contactGroupList:null;
	}

}

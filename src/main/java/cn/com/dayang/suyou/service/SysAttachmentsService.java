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

import cn.com.dayang.suyou.entity.SysAttachments;
import cn.com.dayang.suyou.repository.SysAttachmentsDao;

@Component
@Transactional
public class SysAttachmentsService {

	@Autowired
	private SysAttachmentsDao sysAttachmentsDao;
	
	public SysAttachments saveSysAttachments(SysAttachments sysAttachments){
		return this.sysAttachmentsDao.save(sysAttachments);
	}
	
	public List<SysAttachments> findSysAttachmentById(Integer mailId){
		Map<String, SearchFilter> filters = Maps.newLinkedHashMap();
		filters.put("mailid", new SearchFilter("mailid", Operator.EQ,mailId));
		Specification<SysAttachments> spec = DynamicSpecifications.bySearchFilter(filters.values(), SysAttachments.class);
		return this.sysAttachmentsDao.findAll(spec);
	}
}

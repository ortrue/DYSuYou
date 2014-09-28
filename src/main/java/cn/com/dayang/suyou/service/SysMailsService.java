package cn.com.dayang.suyou.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.google.common.collect.Maps;
import cn.com.dayang.suyou.entity.SysMails;
import cn.com.dayang.suyou.repository.SysMailsDao;

@Component
@Transactional
public class SysMailsService {

	@Autowired
	private SysMailsDao sysMailsDao;
	
	public SysMails saveSysMail(SysMails sysMails){
		return this.sysMailsDao.save(sysMails);
	}

	public SysMails findSysMailById(int mailId){
		return this.sysMailsDao.findOne(mailId);
	}
	
	public void deleteSysMailById(int mailId){
		 this.sysMailsDao.delete(mailId);
	}
	
	public SysMails findSysMailBySmailId(String SmailId){
		Map<String, SearchFilter> filters = Maps.newLinkedHashMap();
		filters.put("smailid", new SearchFilter("smailid", Operator.EQ,SmailId));
		Specification<SysMails> spec = DynamicSpecifications.bySearchFilter(filters.values(), SysMails.class);
		return this.sysMailsDao.findOne(spec);
	}
	
}

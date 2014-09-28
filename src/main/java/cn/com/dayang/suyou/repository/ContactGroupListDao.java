package cn.com.dayang.suyou.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.com.dayang.suyou.entity.ContactGroup;

public interface ContactGroupListDao extends PagingAndSortingRepository<ContactGroup, Integer>,JpaSpecificationExecutor<ContactGroup>{

	@Query("select g from ContactGroup g where g.username=:username and g.contactname=:contactname and g.parentid is null")
	public List<ContactGroup> findGroupByName(@Param("username")String username,@Param("contactname")String contactname);
	
	@Query("select g from ContactGroup g where g.username=:username and g.parentid=:treeId and g.contactname=:contactname and g.parentid is not null")
	public List<ContactGroup> findContactByName(@Param("username")String username,@Param("treeId")Integer treeId,@Param("contactname")String contactname);
}

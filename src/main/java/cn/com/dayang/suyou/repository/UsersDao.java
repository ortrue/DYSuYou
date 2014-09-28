/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package cn.com.dayang.suyou.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.com.dayang.suyou.entity.ContactGroup;
import cn.com.dayang.suyou.entity.Users;

public interface UsersDao extends PagingAndSortingRepository<Users, Long>,JpaSpecificationExecutor<Users>{
	
	public Users findByName(String name);
	
	@Query("select g from ContactGroup g where g.username=:username and g.parentid=:treeId and g.contactname=:contactname and g.parentid is not null")
	public List<ContactGroup> findContactByName(@Param("username")String username,@Param("treeId")Integer treeId,@Param("contactname")String contactname);
	
	
	@Query(nativeQuery = true,value="select sum(spacetotal) as spacetotal,sum(spaceused) as spaceusedtotal from users")
	public Double[] getSpaceTotal();
	
	@Modifying
	@Query(nativeQuery = true,value="update users set status=:status where id = :userId")
	public int updateUserStatus(@Param("userId")Integer userId,@Param("status")Integer status);
	
}

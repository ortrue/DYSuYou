/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package cn.com.dayang.suyou.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

@Entity
@Table(name = "users")
public class Users extends IdEntity {
	private String name;
	private String email;
	private String passwd;
	private String salt;
	private Integer type;
	private Integer status;
	private Date createtime;
	private String singupinfo;
	private String activetime;
	private String roles;
	private Double spacetotal;
	private Double spaceused;
	private Double downloadflowtotal;
	private Double downloadflowused;
	private Integer chargetypeid;

	private String spacetotalStr;
	private String spaceusedStr;
	private String downloadflowtotalStr;
	private String downloadflowusedStr;
	private String chargetypeidStr;
	
	/**
	 * @return the spacetotalStr
	 */
	public String getSpacetotalStr() {
		return spacetotalStr;
	}

	/**
	 * @param spacetotalStr the spacetotalStr to set
	 */
	public void setSpacetotalStr(String spacetotalStr) {
		this.spacetotalStr = spacetotalStr;
	}

	/**
	 * @return the spaceusedStr
	 */
	public String getSpaceusedStr() {
		return spaceusedStr;
	}

	/**
	 * @param spaceusedStr the spaceusedStr to set
	 */
	public void setSpaceusedStr(String spaceusedStr) {
		this.spaceusedStr = spaceusedStr;
	}

	/**
	 * @return the downloadflowtotalStr
	 */
	public String getDownloadflowtotalStr() {
		return downloadflowtotalStr;
	}

	/**
	 * @param downloadflowtotalStr the downloadflowtotalStr to set
	 */
	public void setDownloadflowtotalStr(String downloadflowtotalStr) {
		this.downloadflowtotalStr = downloadflowtotalStr;
	}

	/**
	 * @return the downloadflowusedStr
	 */
	public String getDownloadflowusedStr() {
		return downloadflowusedStr;
	}

	/**
	 * @param downloadflowusedStr the downloadflowusedStr to set
	 */
	public void setDownloadflowusedStr(String downloadflowusedStr) {
		this.downloadflowusedStr = downloadflowusedStr;
	}

	/**
	 * @return the chargetypeidStr
	 */
	public String getChargetypeidStr() {
		return chargetypeidStr;
	}

	/**
	 * @param chargetypeidStr the chargetypeidStr to set
	 */
	public void setChargetypeidStr(String chargetypeidStr) {
		this.chargetypeidStr = chargetypeidStr;
	}

	public Users() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return the singupinfo
	 */
	public String getSingupinfo() {
		return singupinfo;
	}

	/**
	 * @param singupinfo the singupinfo to set
	 */
	public void setSingupinfo(String singupinfo) {
		this.singupinfo = singupinfo;
	}

	/**
	 * @return the spacetotal
	 */
	public Double getSpacetotal() {
		return spacetotal;
	}

	/**
	 * @param spacetotal the spacetotal to set
	 */
	public void setSpacetotal(Double spacetotal) {
		this.spacetotal = spacetotal;
	}

	/**
	 * @return the spaceused
	 */
	public Double getSpaceused() {
		return spaceused;
	}

	/**
	 * @param spaceused the spaceused to set
	 */
	public void setSpaceused(Double spaceused) {
		this.spaceused = spaceused;
	}

	/**
	 * @return the downloadflowtotal
	 */
	public Double getDownloadflowtotal() {
		return downloadflowtotal;
	}

	/**
	 * @param downloadflowtotal the downloadflowtotal to set
	 */
	public void setDownloadflowtotal(Double downloadflowtotal) {
		this.downloadflowtotal = downloadflowtotal;
	}

	/**
	 * @return the downloadflowused
	 */
	public Double getDownloadflowused() {
		return downloadflowused;
	}

	/**
	 * @param downloadflowused the downloadflowused to set
	 */
	public void setDownloadflowused(Double downloadflowused) {
		this.downloadflowused = downloadflowused;
	}

	/**
	 * @return the chargetypeid
	 */
	public Integer getChargetypeid() {
		return chargetypeid;
	}

	/**
	 * @param chargetypeid the chargetypeid to set
	 */
	public void setChargetypeid(Integer chargetypeid) {
		this.chargetypeid = chargetypeid;
	}

	/**
	 * @return the roles
	 */
	public String getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Transient
	@JsonIgnore
	public List<String> getRoleList() {
		// 角色列表在数据库中实际以逗号分隔字符串存储，因此返回不能修改的List.
		return ImmutableList.copyOf(StringUtils.split(roles, ","));
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	/**
	 * @return the activetime
	 */
	public String getActivetime() {
		return activetime;
	}

	/**
	 * @param activetime the activetime to set
	 */
	public void setActivetime(String activetime) {
		this.activetime = activetime;
	}

}
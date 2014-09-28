/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package cn.com.dayang.suyou.vo;

import java.io.Serializable;
import java.util.Date;

public class UsersModel implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String email;
	private String passwd;
	private String salt;
	private Integer type;
	private Integer status;
	private Date createtime;
	private String singupinfo;
	private Double spacetotal;
	private Double spaceused;
	private Double downloadflowtotal;
	private Double downloadflowused;
	private Integer chargetypeid;
	private String activetime;
	private String roles;

	private Integer mailsCount;
	private Integer attaCount;
	private Double mailsTotal;
	private Double attaTotal;
	
	private String spaceusedStr;
	private String mailsTotalStr;
	private String attaTotalStr;
	
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
	 * @return the mailsTotalStr
	 */
	public String getMailsTotalStr() {
		return mailsTotalStr;
	}
	/**
	 * @param mailsTotalStr the mailsTotalStr to set
	 */
	public void setMailsTotalStr(String mailsTotalStr) {
		this.mailsTotalStr = mailsTotalStr;
	}
	/**
	 * @return the attaTotalStr
	 */
	public String getAttaTotalStr() {
		return attaTotalStr;
	}
	/**
	 * @param attaTotalStr the attaTotalStr to set
	 */
	public void setAttaTotalStr(String attaTotalStr) {
		this.attaTotalStr = attaTotalStr;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	
	/**
	 * @return the mailsCount
	 */
	public Integer getMailsCount() {
		return mailsCount;
	}
	/**
	 * @param mailsCount the mailsCount to set
	 */
	public void setMailsCount(Integer mailsCount) {
		this.mailsCount = mailsCount;
	}
	/**
	 * @return the attaCount
	 */
	public Integer getAttaCount() {
		return attaCount;
	}
	/**
	 * @param attaCount the attaCount to set
	 */
	public void setAttaCount(Integer attaCount) {
		this.attaCount = attaCount;
	}
	/**
	 * @return the mailsTotal
	 */
	public Double getMailsTotal() {
		return mailsTotal;
	}
	/**
	 * @param mailsTotal the mailsTotal to set
	 */
	public void setMailsTotal(Double mailsTotal) {
		this.mailsTotal = mailsTotal;
	}
	/**
	 * @return the attaTotal
	 */
	public Double getAttaTotal() {
		return attaTotal;
	}
	/**
	 * @param attaTotal the attaTotal to set
	 */
	public void setAttaTotal(Double attaTotal) {
		this.attaTotal = attaTotal;
	}
}
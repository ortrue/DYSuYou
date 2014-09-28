package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class UserStatementModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String userName;
	private String opType;
	private Integer mailId;
	private String title;
	private Double flowTotal;
	private String flowTotalStr;
	private String gmtTime;
	
	/**
	 * @return the gmtTime
	 */
	public String getGmtTime() {
		return gmtTime;
	}
	/**
	 * @param gmtTime the gmtTime to set
	 */
	public void setGmtTime(String gmtTime) {
		this.gmtTime = gmtTime;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the opType
	 */
	public String getOpType() {
		return opType;
	}
	/**
	 * @param opType the opType to set
	 */
	public void setOpType(String opType) {
		this.opType = opType;
	}
	/**
	 * @return the mailId
	 */
	public Integer getMailId() {
		return mailId;
	}
	/**
	 * @param mailId the mailId to set
	 */
	public void setMailId(Integer mailId) {
		this.mailId = mailId;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the flowTotal
	 */
	public Double getFlowTotal() {
		return flowTotal;
	}
	/**
	 * @param flowTotal the flowTotal to set
	 */
	public void setFlowTotal(Double flowTotal) {
		this.flowTotal = flowTotal;
	}
	/**
	 * @return the flowTotalStr
	 */
	public String getFlowTotalStr() {
		return flowTotalStr;
	}
	/**
	 * @param flowTotalStr the flowTotalStr to set
	 */
	public void setFlowTotalStr(String flowTotalStr) {
		this.flowTotalStr = flowTotalStr;
	}

}

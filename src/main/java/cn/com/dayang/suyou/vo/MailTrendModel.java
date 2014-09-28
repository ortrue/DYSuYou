package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class MailTrendModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer mailId;
	
	private String title;
	
	private String sendUser;
	
	private String recipientUser;

	private Double attachmentspace;
	
	private String gmtTime;

	private String attachmentspaceStr;
	
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
	 * @return the sendUser
	 */
	public String getSendUser() {
		return sendUser;
	}

	/**
	 * @param sendUser the sendUser to set
	 */
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	/**
	 * @return the recipientUser
	 */
	public String getRecipientUser() {
		return recipientUser;
	}

	/**
	 * @param recipientUser the recipientUser to set
	 */
	public void setRecipientUser(String recipientUser) {
		this.recipientUser = recipientUser;
	}

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
	 * @return the attachmentspace
	 */
	public Double getAttachmentspace() {
		return attachmentspace;
	}

	/**
	 * @param attachmentspace the attachmentspace to set
	 */
	public void setAttachmentspace(Double attachmentspace) {
		this.attachmentspace = attachmentspace;
	}
	
	/**
	 * @return the attachmentspaceStr
	 */
	public String getAttachmentspaceStr() {
		return attachmentspaceStr;
	}

	/**
	 * @param attachmentspaceStr the attachmentspaceStr to set
	 */
	public void setAttachmentspaceStr(String attachmentspaceStr) {
		this.attachmentspaceStr = attachmentspaceStr;
	}

}

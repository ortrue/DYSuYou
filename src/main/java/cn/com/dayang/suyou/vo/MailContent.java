package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class MailContent implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private String brief;
	private String senderName;
	private String sendTime;
	private Integer attachmentNum;
	private Double attachmentSpace;
	private Integer status;
	private String attaInfo;

	/**
	 * @return the attaInfo
	 */
	public String getAttaInfo() {
		return attaInfo;
	}
	/**
	 * @param attaInfo the attaInfo to set
	 */
	public void setAttaInfo(String attaInfo) {
		this.attaInfo = attaInfo;
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
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/**
	 * @return the sendTime
	 */
	public String getSendTime() {
		return sendTime;
	}
	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * @return the attachmentNum
	 */
	public Integer getAttachmentNum() {
		return attachmentNum;
	}
	/**
	 * @param attachmentNum the attachmentNum to set
	 */
	public void setAttachmentNum(Integer attachmentNum) {
		this.attachmentNum = attachmentNum;
	}
	/**
	 * @return the attachmentSpace
	 */
	public Double getAttachmentSpace() {
		return attachmentSpace;
	}
	/**
	 * @param attachmentSpace the attachmentSpace to set
	 */
	public void setAttachmentSpace(Double attachmentSpace) {
		this.attachmentSpace = attachmentSpace;
	}
}

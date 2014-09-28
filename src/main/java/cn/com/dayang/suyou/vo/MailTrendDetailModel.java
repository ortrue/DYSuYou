package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class MailTrendDetailModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer mailId;
	private String title;
	private Integer sendUid;
	private String sendName;
	private Integer recipientUid;
	private String recipientName;
	private String sendTime;
	private Integer downloadUid;
	private String downloadName;
	private String downloadTime;
	private Integer resourceId;
	private String resourceName;
	private String content;
	
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
	 * @return the sendUid
	 */
	public Integer getSendUid() {
		return sendUid;
	}
	/**
	 * @param sendUid the sendUid to set
	 */
	public void setSendUid(Integer sendUid) {
		this.sendUid = sendUid;
	}
	/**
	 * @return the sendName
	 */
	public String getSendName() {
		return sendName;
	}
	/**
	 * @param sendName the sendName to set
	 */
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	/**
	 * @return the recipientUid
	 */
	public Integer getRecipientUid() {
		return recipientUid;
	}
	/**
	 * @param recipientUid the recipientUid to set
	 */
	public void setRecipientUid(Integer recipientUid) {
		this.recipientUid = recipientUid;
	}
	/**
	 * @return the recipientName
	 */
	public String getRecipientName() {
		return recipientName;
	}
	/**
	 * @param recipientName the recipientName to set
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
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
	 * @return the downloadUid
	 */
	public Integer getDownloadUid() {
		return downloadUid;
	}
	/**
	 * @param downloadUid the downloadUid to set
	 */
	public void setDownloadUid(Integer downloadUid) {
		this.downloadUid = downloadUid;
	}
	/**
	 * @return the downloadName
	 */
	public String getDownloadName() {
		return downloadName;
	}
	/**
	 * @param downloadName the downloadName to set
	 */
	public void setDownloadName(String downloadName) {
		this.downloadName = downloadName;
	}
	/**
	 * @return the downloadTime
	 */
	public String getDownloadTime() {
		return downloadTime;
	}
	/**
	 * @param downloadTime the downloadTime to set
	 */
	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}
	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}

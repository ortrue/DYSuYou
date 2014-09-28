package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class MailsModel implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String title;
	private Integer senderid;
	private String sendername;
	private String recnames;
	private String content;
	private String textcontent;
	private String sendtime;
	private Integer attachmentnum;
	private Double attachmentspace;
	private Integer status;
	
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
	 * @return the senderid
	 */
	public Integer getSenderid() {
		return senderid;
	}
	/**
	 * @param senderid the senderid to set
	 */
	public void setSenderid(Integer senderid) {
		this.senderid = senderid;
	}
	/**
	 * @return the sendername
	 */
	public String getSendername() {
		return sendername;
	}
	/**
	 * @param sendername the sendername to set
	 */
	public void setSendername(String sendername) {
		this.sendername = sendername;
	}
	/**
	 * @return the recnames
	 */
	public String getRecnames() {
		return recnames;
	}
	/**
	 * @param recnames the recnames to set
	 */
	public void setRecnames(String recnames) {
		this.recnames = recnames;
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
	/**
	 * @return the textcontent
	 */
	public String getTextcontent() {
		return textcontent;
	}
	/**
	 * @param textcontent the textcontent to set
	 */
	public void setTextcontent(String textcontent) {
		this.textcontent = textcontent;
	}
	/**
	 * @return the sendtime
	 */
	public String getSendtime() {
		return sendtime;
	}
	/**
	 * @param sendtime the sendtime to set
	 */
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	/**
	 * @return the attachmentnum
	 */
	public Integer getAttachmentnum() {
		return attachmentnum;
	}
	/**
	 * @param attachmentnum the attachmentnum to set
	 */
	public void setAttachmentnum(Integer attachmentnum) {
		this.attachmentnum = attachmentnum;
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

}

package cn.com.dayang.suyou.vo;

import java.io.Serializable;
import java.util.List;

public class MailDetailResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String code;
	private String title;
	private String sendername;
	private String recipients;
	private String content;
	private String sendtime;
	private Integer status;
	private List<AttachmentDetail> attachmentDetail;
	private Integer attaCounts;
	private Double attachmentspace;
	private String attaSizeTotal;
	private Double attaSizeTotalByte;
	private Integer mailStatus;
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the attachmentDetail
	 */
	public List<AttachmentDetail> getAttachmentDetail() {
		return attachmentDetail;
	}
	/**
	 * @param attachmentDetail the attachmentDetail to set
	 */
	public void setAttachmentDetail(List<AttachmentDetail> attachmentDetail) {
		this.attachmentDetail = attachmentDetail;
	}
	/**
	 * @return the recipients
	 */
	public String getRecipients() {
		return recipients;
	}
	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return the attaCounts
	 */
	public Integer getAttaCounts() {
		return attaCounts;
	}
	/**
	 * @param attaCounts the attaCounts to set
	 */
	public void setAttaCounts(Integer attaCounts) {
		this.attaCounts = attaCounts;
	}
	/**
	 * @return the attaSizeTotal
	 */
	public String getAttaSizeTotal() {
		return attaSizeTotal;
	}
	/**
	 * @param attaSizeTotal the attaSizeTotal to set
	 */
	public void setAttaSizeTotal(String attaSizeTotal) {
		this.attaSizeTotal = attaSizeTotal;
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
	 * @return the mailStatus
	 */
	public Integer getMailStatus() {
		return mailStatus;
	}
	/**
	 * @param mailStatus the mailStatus to set
	 */
	public void setMailStatus(Integer mailStatus) {
		this.mailStatus = mailStatus;
	}
	/**
	 * @return the attaSizeTotalByte
	 */
	public Double getAttaSizeTotalByte() {
		return attaSizeTotalByte;
	}
	/**
	 * @param attaSizeTotalByte the attaSizeTotalByte to set
	 */
	public void setAttaSizeTotalByte(Double attaSizeTotalByte) {
		this.attaSizeTotalByte = attaSizeTotalByte;
	}

}

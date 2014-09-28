package cn.com.dayang.suyou.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_mails")
public class SysMails extends IdEntity{
	
	private Long smailid;
	private String title;
	private String sendername;
	private String recipients;
	private String content;
	private String createuser;
	private String createtime;
	private String sendtime;
	private String deletetime;
	private Integer status;
	
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
	 * @return the createtime
	 */
	public String getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
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
	 * @return the deletetime
	 */
	public String getDeletetime() {
		return deletetime;
	}
	/**
	 * @param deletetime the deletetime to set
	 */
	public void setDeletetime(String deletetime) {
		this.deletetime = deletetime;
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
	 * @return the smailid
	 */
	public Long getSmailid() {
		return smailid;
	}
	/**
	 * @param smailid the smailid to set
	 */
	public void setSmailid(Long smailid) {
		this.smailid = smailid;
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
	 * @return the createuser
	 */
	public String getCreateuser() {
		return createuser;
	}
	/**
	 * @param createuser the createuser to set
	 */
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
}

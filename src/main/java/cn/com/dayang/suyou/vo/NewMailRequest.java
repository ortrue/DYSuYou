package cn.com.dayang.suyou.vo;

import java.io.Serializable;
import java.util.List;

public class NewMailRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	private String recipients;
	private String title;
	private String content;
	private List<MailAttachments> attachments;
	
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
	 * @return the attachments
	 */
	public List<MailAttachments> getAttachments() {
		return attachments;
	}
	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<MailAttachments> attachments) {
		this.attachments = attachments;
	}
}

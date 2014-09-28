package cn.com.dayang.suyou.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class NewMailResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	private String code;
	private String Msg;
	private Long mailid;
	private List<Map<String,Object>> attachmentupinfos;
	
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
	 * @return the msg
	 */
	public String getMsg() {
		return Msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		Msg = msg;
	}
	/**
	 * @return the mailid
	 */
	public Long getMailid() {
		return mailid;
	}
	/**
	 * @param mailid the mailid to set
	 */
	public void setMailid(Long mailid) {
		this.mailid = mailid;
	}
	/**
	 * @return the attachmentupinfos
	 */
	public List<Map<String, Object>> getAttachmentupinfos() {
		return attachmentupinfos;
	}
	/**
	 * @param attachmentupinfos the attachmentupinfos to set
	 */
	public void setAttachmentupinfos(List<Map<String, Object>> attachmentupinfos) {
		this.attachmentupinfos = attachmentupinfos;
	}
	
}

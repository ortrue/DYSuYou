package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class MenuLeftTotal implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long mailTotal;
	private Long draftTotal;
	private Long sendedTotal;
	private Long deleteTotal;
	
	/**
	 * @return the mailTotal
	 */
	public Long getMailTotal() {
		return mailTotal;
	}
	/**
	 * @param mailTotal the mailTotal to set
	 */
	public void setMailTotal(Long mailTotal) {
		this.mailTotal = mailTotal;
	}
	/**
	 * @return the draftTotal
	 */
	public Long getDraftTotal() {
		return draftTotal;
	}
	/**
	 * @param draftTotal the draftTotal to set
	 */
	public void setDraftTotal(Long draftTotal) {
		this.draftTotal = draftTotal;
	}
	/**
	 * @return the sendedTotal
	 */
	public Long getSendedTotal() {
		return sendedTotal;
	}
	/**
	 * @param sendedTotal the sendedTotal to set
	 */
	public void setSendedTotal(Long sendedTotal) {
		this.sendedTotal = sendedTotal;
	}
	/**
	 * @return the deleteTotal
	 */
	public Long getDeleteTotal() {
		return deleteTotal;
	}
	/**
	 * @param deleteTotal the deleteTotal to set
	 */
	public void setDeleteTotal(Long deleteTotal) {
		this.deleteTotal = deleteTotal;
	}

}

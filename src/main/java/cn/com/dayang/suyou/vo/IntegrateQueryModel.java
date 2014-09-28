package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class IntegrateQueryModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	
	private String userName;
	
	private Integer mailCount;
	
	private Integer attaCount;
	
	private Double useSpace;
	
	private Double uploadTotal;
	
	private Double downloadTotal;
	
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
	 * @return the mailCount
	 */
	public Integer getMailCount() {
		return mailCount;
	}

	/**
	 * @param mailCount the mailCount to set
	 */
	public void setMailCount(Integer mailCount) {
		this.mailCount = mailCount;
	}

	/**
	 * @return the attaCount
	 */
	public Integer getAttaCount() {
		return attaCount;
	}

	/**
	 * @param attaCount the attaCount to set
	 */
	public void setAttaCount(Integer attaCount) {
		this.attaCount = attaCount;
	}

	/**
	 * @return the useSpace
	 */
	public Double getUseSpace() {
		return useSpace;
	}

	/**
	 * @param useSpace the useSpace to set
	 */
	public void setUseSpace(Double useSpace) {
		this.useSpace = useSpace;
	}

	/**
	 * @return the uploadTotal
	 */
	public Double getUploadTotal() {
		return uploadTotal;
	}

	/**
	 * @param uploadTotal the uploadTotal to set
	 */
	public void setUploadTotal(Double uploadTotal) {
		this.uploadTotal = uploadTotal;
	}

	/**
	 * @return the downloadTotal
	 */
	public Double getDownloadTotal() {
		return downloadTotal;
	}

	/**
	 * @param downloadTotal the downloadTotal to set
	 */
	public void setDownloadTotal(Double downloadTotal) {
		this.downloadTotal = downloadTotal;
	}
}

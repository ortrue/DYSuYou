package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class MailAttachments implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String filename;
	private Double filesize;
	private Integer blocktrans;
	private Integer blocktotalnums;
	
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the blocktrans
	 */
	public Integer getBlocktrans() {
		return blocktrans;
	}
	/**
	 * @param blocktrans the blocktrans to set
	 */
	public void setBlocktrans(Integer blocktrans) {
		this.blocktrans = blocktrans;
	}
	/**
	 * @return the blocktotalnums
	 */
	public Integer getBlocktotalnums() {
		return blocktotalnums;
	}
	/**
	 * @param blocktotalnums the blocktotalnums to set
	 */
	public void setBlocktotalnums(Integer blocktotalnums) {
		this.blocktotalnums = blocktotalnums;
	}
	/**
	 * @return the filesize
	 */
	public Double getFilesize() {
		return filesize;
	}
	/**
	 * @param filesize the filesize to set
	 */
	public void setFilesize(Double filesize) {
		this.filesize = filesize;
	}
}

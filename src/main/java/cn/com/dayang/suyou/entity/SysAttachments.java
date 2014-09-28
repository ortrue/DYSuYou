package cn.com.dayang.suyou.entity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "sys_attachments")
public class SysAttachments extends IdEntity{

	private Integer mailid;
	private String attaname;
	private Double size;
	private String hash;
	private Long imagewidth;
	private Long imageheight;
	private String createtime;
	private String completetime;
	private String downloadurl;
	private Integer downloadcount;
	private String description;
	
	/**
	 * @return the mailid
	 */
	public Integer getMailid() {
		return mailid;
	}
	/**
	 * @param mailid the mailid to set
	 */
	public void setMailid(Integer mailid) {
		this.mailid = mailid;
	}
	/**
	 * @return the attaname
	 */
	public String getAttaname() {
		return attaname;
	}
	/**
	 * @param attaname the attaname to set
	 */
	public void setAttaname(String attaname) {
		this.attaname = attaname;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * @return the imagewidth
	 */
	public Long getImagewidth() {
		return imagewidth;
	}
	/**
	 * @param imagewidth the imagewidth to set
	 */
	public void setImagewidth(Long imagewidth) {
		this.imagewidth = imagewidth;
	}
	/**
	 * @return the imageheight
	 */
	public Long getImageheight() {
		return imageheight;
	}
	/**
	 * @param imageheight the imageheight to set
	 */
	public void setImageheight(Long imageheight) {
		this.imageheight = imageheight;
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
	 * @return the completetime
	 */
	public String getCompletetime() {
		return completetime;
	}
	/**
	 * @param completetime the completetime to set
	 */
	public void setCompletetime(String completetime) {
		this.completetime = completetime;
	}
	/**
	 * @return the downloadurl
	 */
	public String getDownloadurl() {
		return downloadurl;
	}
	/**
	 * @param downloadurl the downloadurl to set
	 */
	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
	/**
	 * @return the downloadcount
	 */
	public Integer getDownloadcount() {
		return downloadcount;
	}
	/**
	 * @param downloadcount the downloadcount to set
	 */
	public void setDownloadcount(Integer downloadcount) {
		this.downloadcount = downloadcount;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the size
	 */
	public Double getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(Double size) {
		this.size = size;
	}

}

package cn.com.dayang.suyou.vo;

import java.io.Serializable;

public class AttachmentDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String resname;
	private Double size;
	private String shortSize;
	private String createtime;
	private String completetime;
	private Integer status;
	private String description;
	private Long imagewidth;
	private Long imageheight;
	private String imagedata;
	private String image;
	private String url;
	
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
	 * @return the resname
	 */
	public String getResname() {
		return resname;
	}
	/**
	 * @param resname the resname to set
	 */
	public void setResname(String resname) {
		this.resname = resname;
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
	 * @return the imagedata
	 */
	public String getImagedata() {
		return imagedata;
	}
	/**
	 * @param imagedata the imagedata to set
	 */
	public void setImagedata(String imagedata) {
		this.imagedata = imagedata;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the shortSize
	 */
	public String getShortSize() {
		return shortSize;
	}
	/**
	 * @param shortSize the shortSize to set
	 */
	public void setShortSize(String shortSize) {
		this.shortSize = shortSize;
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

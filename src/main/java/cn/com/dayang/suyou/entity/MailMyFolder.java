package cn.com.dayang.suyou.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_my_folder")
public class MailMyFolder extends IdEntity{

	private Integer parentid;
	private String username;
	private String type;
	private Integer foldername;
	private Integer catetory;
	
	/**
	 * @return the parentid
	 */
	public Integer getParentid() {
		return parentid;
	}
	/**
	 * @param parentid the parentid to set
	 */
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the foldername
	 */
	public Integer getFoldername() {
		return foldername;
	}
	/**
	 * @param foldername the foldername to set
	 */
	public void setFoldername(Integer foldername) {
		this.foldername = foldername;
	}
	/**
	 * @return the catetory
	 */
	public Integer getCatetory() {
		return catetory;
	}
	/**
	 * @param catetory the catetory to set
	 */
	public void setCatetory(Integer catetory) {
		this.catetory = catetory;
	}

}

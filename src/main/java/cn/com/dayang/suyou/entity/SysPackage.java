package cn.com.dayang.suyou.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sys_package")
public class SysPackage extends IdEntity{

	public String pakname;
	public String pakdesc;
	public Double monthpay;
	public Double yearpay;
	
	/**
	 * @return the pakname
	 */
	public String getPakname() {
		return pakname;
	}
	/**
	 * @param pakname the pakname to set
	 */
	public void setPakname(String pakname) {
		this.pakname = pakname;
	}
	/**
	 * @return the pakdesc
	 */
	public String getPakdesc() {
		return pakdesc;
	}
	/**
	 * @param pakdesc the pakdesc to set
	 */
	public void setPakdesc(String pakdesc) {
		this.pakdesc = pakdesc;
	}
	/**
	 * @return the monthpay
	 */
	public Double getMonthpay() {
		return monthpay;
	}
	/**
	 * @param monthpay the monthpay to set
	 */
	public void setMonthpay(Double monthpay) {
		this.monthpay = monthpay;
	}
	/**
	 * @return the yearpay
	 */
	public Double getYearpay() {
		return yearpay;
	}
	/**
	 * @param yearpay the yearpay to set
	 */
	public void setYearpay(Double yearpay) {
		this.yearpay = yearpay;
	}

}

package cn.com.dayang.suyou.enums;

public enum MailDetailTypeEnum {

	SUYOUMAIL("1","SUYOUMAIL"),
	SYSMAIL("2","SYSMAIL");

	private String code;
	private String name;
    
	MailDetailTypeEnum(String code,String name) {
        this.code = code;
        this.name=name;
    }
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}

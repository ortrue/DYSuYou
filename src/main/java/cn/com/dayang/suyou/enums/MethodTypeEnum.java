package cn.com.dayang.suyou.enums;

public enum MethodTypeEnum {
	
	METHOD_POST("POST"),
    METHOD_GET("GET");

	private String code;
    
    MethodTypeEnum(String code) {
        this.code = code;
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
}

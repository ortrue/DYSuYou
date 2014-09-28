package cn.com.dayang.suyou.util;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.log4j.Logger;



/**
 * Md5Encrypt加密字符串类
 * @see 
 * @since TR-V1.0
 * @author Leon Liu,  Jul 2, 2012 4:53:55 PM
 * @version 
 */

public class Md5Encrypt {
	/**
	 * logger 当前类的日志记录器
	 * @since OF-v5.0
	 * @author Leon Liu,  Jul 2, 2012 4:53:55 PM
	 */
	private final static Logger logger = Logger.getLogger(Md5Encrypt.class);

	private static MessageDigest SHA = null;

	/**
	 * 将字符串转换成MD5_32位字符串
	 * @param originStr 需要加密的原始字符串
	 * @return String 加密后的MD5字符串
	 * @since OF-v5.0
	 * @author Leon Liu,  Jul 2, 2012 4:53:55 PM
	 */
	public static String encrypt(String originStr) {
		byte[] hashBytes = null;
		try {
			if (SHA == null)
				SHA = MessageDigest.getInstance("SHA");
			synchronized (SHA) { // Not sure if it is thread safe, so...
				SHA.reset();
				hashBytes = SHA.digest(originStr.getBytes());
			}
		} catch (Exception e) {
			logger.error("MD5 encryptting failed", e);
		}
		return toHexString(hashBytes);
	}
	
	
	
		 // MD5加码。32位  
		 public static String MD5(String inStr) {  
		  MessageDigest md5 = null;  
		  try {  
		   md5 = MessageDigest.getInstance("MD5");  
		  } catch (Exception e) {  
		   e.printStackTrace();  
		   return "";  
		  }  
		  char[] charArray = inStr.toCharArray();  
		  byte[] byteArray = new byte[charArray.length];  
		  
		  for (int i = 0; i < charArray.length; i++)  
		   byteArray[i] = (byte) charArray[i];  
		  
		  byte[] md5Bytes = md5.digest(byteArray);  
		  
		  StringBuffer hexValue = new StringBuffer();  
		  
		  for (int i = 0; i < md5Bytes.length; i++) {  
		   int val = ((int) md5Bytes[i]) & 0xff;  
		   if (val < 16)  
		    hexValue.append("0");  
		   hexValue.append(Integer.toHexString(val));  
		  }  
		  
		  return hexValue.toString();  
		 } 
	
	/** 
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private static String toHexString(byte[] b) {
		if (b == null)
			return null;
		StringBuffer buff = new StringBuffer();
		
		for (int i = 0; i < b.length; i++)
			buff.append(Integer.toHexString(b[i] >> 4 & 0x0f)).append(
					Integer.toHexString(b[i] & 0x0f));
		return buff.toString();
	}
	
	
	/**
	 * 生成一个随机密码
	 * @param pwd_len 密码位长度
	 * @return
	 * @since OF-v5.0
	 * @author Leon Liu,  Jul 2, 2012 4:53:55 PM
	 */
	public static String genRandomPwd(int pwd_len) {
		final int maxNum = 62;
		int i;
		int count = 0;
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
				'7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
	

}

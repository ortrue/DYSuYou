package cn.com.dayang.suyou.util;

import java.math.BigDecimal;

public class FileSizeUtil {
	public static String formatKB2MBOrGB(double KBsize) {  
		
        double kiloByte = KBsize/1024;  
        if(kiloByte < 1) { 
        	BigDecimal result0 = new BigDecimal(Double.toString(KBsize));
            return result0.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "Byte";  
        }  
          
        double megaByte = kiloByte/1024;  
        if(megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
        }  
          
        double gigaByte = megaByte/1024;  
        if(gigaByte < 1) {  
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
          
        double teraBytes = gigaByte/1024;  
        if(teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
    } 
	
	public static String formatGB2MBOrKB(double GBsize){
		double KBsize=0;
		if(GBsize > 0){
			KBsize=GBsize*1024*1024*1024;
		}
		return formatKB2MBOrGB(KBsize);
	}
	
}

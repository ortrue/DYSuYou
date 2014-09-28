package cn.com.dayang.suyou.util;

import java.util.UUID;

/**
 */
public class GUID {
	  public static String getNewGUID()
	  {
	    return UUID.randomUUID().toString();
	  }

}

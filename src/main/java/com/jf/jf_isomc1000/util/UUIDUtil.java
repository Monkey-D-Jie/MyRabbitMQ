package com.jf.jf_isomc1000.util;

import java.util.UUID;

public class UUIDUtil {
	  /**
     * 产生UUID
     * 
     * @return 36位UUID
     */
    public static String getUUID() {
    	
        return UUID.randomUUID().toString();
    }
}

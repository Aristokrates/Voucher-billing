package org.pan.voucher.web;

public class WebConstants {
	
    private WebConstants() {
    }

    public static final String USER = "user";
    public static final String USER_ID = "user_id";
    public static final String DEVICE_TYPE = "device_type";
    
    // Device short names (types)
    public static final String DEVICE_ANDROID = "a";
    public static final String DEVICE_WEB = "w";
    
    public static boolean isMobile(String shortType) {
    	return ((shortType != null) 
	    		&& (shortType.equals(WebConstants.DEVICE_ANDROID)));
    }
    
    public static boolean isNotMobile(String shortType) {
    	return ((shortType != null) && 
    			(shortType.equals(WebConstants.DEVICE_WEB)));
    }    

}

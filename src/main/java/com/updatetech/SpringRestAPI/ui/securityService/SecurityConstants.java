package com.updatetech.SpringRestAPI.ui.securityService;

import com.updatetech.SpringRestAPI.SpringApplicationContext;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String SING_UP_URL = "/users/save-user";


    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}

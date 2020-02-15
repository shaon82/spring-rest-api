package com.updatetech.SpringRestAPI.ui.Dto;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random random = new SecureRandom();
    private final String ALPHABET = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String genarateUserId(int length){
        return generaRandomString(length);
    }


    private String generaRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for (int i =0; i<length; i++){
            returnValue.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}

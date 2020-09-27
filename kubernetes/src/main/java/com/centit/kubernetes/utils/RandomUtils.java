package com.centit.kubernetes.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机数工具，主要为了生成符合DNS-1123命名规则的字符
 */
public class RandomUtils {
    
    /**
     * [0-9][a-z]和-
     */
    private final static String DNS1123 = "0123456789qwertyuiopasdfghjklzxcvbnm";
    
    public static String randomDNS1123(){
        
        String chars = RandomStringUtils.random(8, DNS1123);
        return chars;
    }
}

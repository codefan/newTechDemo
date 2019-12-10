package com.centit.test;

import org.checkerframework.checker.nullness.qual.NonNull;


public class TestGuava {
    public static void main(String[] args) {
        //Optional<String>
        String ref = null;
        System.out.println(trimString(ref));
    }

    public static String trimString (@NonNull String str){
        return str.trim();
    }
}

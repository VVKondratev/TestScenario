package ru.vvk.web;

import java.util.Random;

public class Utils {

    //Sixth item
    public static Long randomize(Long num){
        long leftLimit = 1L;
        long rightLimit = num;
        long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        return generatedLong;
    }
}

package ru.vvk.web;

import java.util.Random;

public class Utils {

    //Sixth item
    public static Long randomize(Long num){
        Random rnd = new Random(num);
        return rnd.nextLong();
    }
}

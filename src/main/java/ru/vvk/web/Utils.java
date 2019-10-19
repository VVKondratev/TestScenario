package ru.vvk.web;

import java.util.Random;

public class Utils {

    static int prev = -1;

    //Sixth item
    public static Long randomize(Long num) {
        long leftLimit = 1L;
        long rightLimit = num;
        long generatedLong = 0L;
        do {
            generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        } while (generatedLong == prev);

        return generatedLong;
    }

    public static void wait(int timeOut) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}

package ru.vvk.web;

import ru.vvk.web.config.WebConfig;

/**
 * Entrance point. This class contains method main.
 *
 * @Author Kondratev Vladislav
 */
public class Main {

    /**
     * Method main
     * @param args
     */
    public static void main(String[] args) {
        WebConfig.configure();
        WebPageInteraction wpi = new WebPageInteraction();
    }
}

package com.evasquare.url_shortener;

import java.util.UUID;

public class Utils {
    public static boolean isValidUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return true;
        } else {
            return false;
        }
    }

    public static String generateRandomHash() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}

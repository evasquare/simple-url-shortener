package com.evasquare.url_shortener;

import java.net.URI;
import java.util.UUID;

public class Utils {
    public static boolean isValidUri(String uri) {
        try {
            new URI(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String generateRandomHash() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}

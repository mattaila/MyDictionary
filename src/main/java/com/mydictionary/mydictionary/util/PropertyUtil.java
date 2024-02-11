package com.mydictionary.mydictionary.util;

import java.util.ResourceBundle;

public class PropertyUtil {

    private static final ResourceBundle res;

    private PropertyUtil() throws Exception {}

    static {
        res = ResourceBundle.getBundle("application");
    }

    public static String getProperty(final String key) {
        return res.getString(key);
    }
}

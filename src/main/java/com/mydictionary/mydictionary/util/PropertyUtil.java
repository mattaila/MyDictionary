package com.mydictionary.mydictionary.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.mydictionary.mydictionary.constant.Constants;

public class PropertyUtil {

    private static final Properties properties;

    private PropertyUtil() throws Exception {}

    static {
        properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(Constants.PROPERTY_FILE), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println(String.format("failed to load property file:%s", Constants.PROPERTY_FILE));
        }
    }

    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }
}

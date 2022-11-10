package com.codecool.jira;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

public class Util {
    public static final String CONFIG_PATH = "src/main/resources/init.properties";

    public static Properties readConfig() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(CONFIG_PATH));
            return properties;
        } catch (IOException e) {
            System.out.println("Can't read config file");
        }
        return null;
    }

    public static String readProperty(String value) {
        return Objects.requireNonNull(readConfig()).getProperty(value);
    }

    public static String generateRandomSummary() {
        return UUID.randomUUID().toString();
    }
}

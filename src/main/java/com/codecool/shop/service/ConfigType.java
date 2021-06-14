package com.codecool.shop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public abstract class ConfigType {

    private static final String APP_CONFIG_PATH = "src/main/resources/connection.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConfigType.class);

    private static Properties getProperty() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(APP_CONFIG_PATH));
        } catch (IOException e) {
            logger.error("File not found");
            e.printStackTrace();
        }
        return appProps;
    }

    public static String getDao() {
        return getProperty().getProperty("dao");
    }

    public static List<String> getDatabaseDetails() {
        Properties prop = getProperty();
        List<String> result = Arrays.asList(prop.getProperty("user"), prop.getProperty("database"), prop.getProperty("password"));
        return result;
    }
}

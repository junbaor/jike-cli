package com.github.junbaor.jike.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class SystemUtils {

    public static Properties gitInfo() {
        Properties properties = new Properties();

        ClassLoader classLoader = SystemUtils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.properties");
        if (inputStream == null) {
            return properties;
        }

        try {
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return properties;
    }

}

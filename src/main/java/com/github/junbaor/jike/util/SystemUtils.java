package com.github.junbaor.jike.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

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

    public static void printImage(byte[] imageFileBytes) {
        try {
            byte[] base64 = Base64.encodeBase64(imageFileBytes);
            System.out.write(0x1B);
            System.out.write(("]1337;File=name=;inline=1:").getBytes());
            System.out.write(base64);
            System.out.write(0x07);
            System.out.write(0x0A);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}

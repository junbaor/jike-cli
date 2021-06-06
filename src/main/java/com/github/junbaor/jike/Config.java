package com.github.junbaor.jike;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Config {

    private static final String mobilePhoneNumber = "mobilePhoneNumber";
    private static final String areaCode = "areaCode";
    private static final String accessToken = "x-jike-access-token";
    private static final String refreshToken = "x-jike-refresh-token";
    private static final String userName = "username";
    private static final Properties properties = new Properties();

    public static final String CONFIG_FILE = System.getProperty("user.home") + "/.jike/config.ini";

    static {
        try {
            File file = new File(CONFIG_FILE);
            if (file.exists() && file.isFile()) {
                properties.load(new FileInputStream(file));
            }
        } catch (Exception e) {
            log.error("读取配置文件异常", e);
        }
    }

    public synchronized static String getMobilePhoneNumber() {
        return properties.getProperty(mobilePhoneNumber);
    }

    public synchronized static void setMobilePhoneNumber(String value) {
        properties.setProperty(mobilePhoneNumber, value);
    }

    public synchronized static String getAreaCode() {
        return properties.getProperty(areaCode);
    }

    public synchronized static void setAreaCode(String value) {
        properties.setProperty(areaCode, value);
        syncFile();
    }

    public synchronized static String getAccessToken() {
        return properties.getProperty(accessToken);
    }

    public synchronized static void setAccessToken(String value) {
        properties.setProperty(accessToken, value);
        syncFile();
    }

    public synchronized static String getRefreshToken() {
        return properties.getProperty(refreshToken);
    }

    public synchronized static void setRefreshToken(String value) {
        properties.setProperty(refreshToken, value);
        syncFile();
    }

    public synchronized static String getUserName() {
        return properties.getProperty(userName);
    }

    public synchronized static void setUserName(String value) {
        properties.setProperty(userName, value);
        syncFile();
    }

    public synchronized static void reset() {
        properties.clear();
        syncFile();
    }

    private synchronized static void syncFile() {
        try {
            properties.store(new FileOutputStream(CONFIG_FILE), null);
        } catch (IOException e) {
            log.error("配置文件同步到文件异常", e);
        }
    }

}

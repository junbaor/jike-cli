package com.github.junbaor.jike.util;

import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.YELLOW_TEXT;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String pad(String string, int padSize, String padStr) {
        return colorize(rightPad(string, padSize, padStr), YELLOW_TEXT());
    }

    public static String pad(String string, int padSize, Attribute attribute) {
        String padStr = "　";
        return colorize(rightPad(string, padSize, padStr), attribute);
    }

    public static String pad(String string) {
        return colorize(string, YELLOW_TEXT());
    }

    public static String pad(String string, Attribute... attribute) {
        return colorize(string, attribute);
    }

    public static String pad(String string, int padSize) {
        String padStr = "　";
        return colorize(rightPad(string, padSize, padStr), YELLOW_TEXT());
    }


}

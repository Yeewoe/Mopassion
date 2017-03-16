package org.yeewoe.commonutils.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by acc on 2016/9/1.
 */
public class PatternUtil {

    public static final String PREFIX_EMAIL = "mailto:";
    public static final String PREFIX_PHONE = "tel:";

    public static final String REGEX_URL = "((?:(?:ht|f)tps?://)*(?:[a-zA-Z0-9-]+\\.)+(?:com|net|php|jsp|asp|org|info|mil|gov|edu|name|xxx|[a-z]{2}){1}(?::[0-9]+)?(?:(/|\\?)[a-zA-Z0-9\\^\\.\\{\\}\\(\\)\\[\\]_\\?,'/\\\\+&%\\$:#=~-]*)*)";
    //  public static final String REGEX_URL = "((?:(?:ht|f)tps?://)*(((?:[a-zA-Z0-9-]+\\.)+(?:com|net|org|info|mil|gov|edu|name|xxx|[a-z]{2}){1})|(((2[0-4]\\d|25[0-5]|[01]?\\\\d\\\\d?)\\\\.){3}(2[0-4]\\\\d|25[0-5]|[01]?\\\\d\\\\d?)))(?::[0-9]+)?(?:(/|\\\\?)[a-zA-Z0-9\\\\^\\\\.\\\\{\\\\}\\\\(\\\\)\\\\[\\\\]_\\\\?,'/\\\\\\\\+&%\\\\$:#@=~-]*)*)";
    public static final String REGEX_IP = "((?:(?:ht|f)tps?://)*(?:\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})(?::[0-9]+)?(?![a-z])(?:/[a-zA-Z0-9\\^\\.\\{\\}\\(\\)\\[\\]_\\?,'/\\\\+&%\\$:#=~-]*)*)";
    public static final String REGEX_PHONE = "(^|(?<=\\D))((\\+?)(([0-9]{2,6}-[0-9]{3,9})((-[0-9]{2,9})?)|[0-9]{7,20}))($|(?=\\D))";
    public static final String REGEX_EMAIL = "[_a-zA-Z0-9-]{1,30}(\\.[_a-zA-Z0-9-]+){0,30}@[a-zA-Z0-9-]{1,30}(\\.[a-zA-Z0-9-]+){0,30}(\\.[a-zA-Z]{2,4})";
    public static final String REGEX_PSD = "^w+$";
    public static boolean isIp(String text) {
        Pattern p = Pattern.compile(REGEX_IP, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        if (m.find()) {
            String str = m.group(0);
            return str.equals(text);
        }

        return false;
    }

    public static boolean isPhone(String text) {
        if (text.startsWith(PREFIX_PHONE)) {
            return true;
        }

        Pattern p = Pattern.compile(REGEX_PHONE, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        if (m.find()) {
            String str = m.group(0);
            return str.equals(text);
        }

        return false;
    }

    public static boolean isEmail(String text) {
        if (text.startsWith(PREFIX_EMAIL)) {
            return true;
        }

        Pattern p = Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        if (m.find()) {
            String str = m.group(0);
            return str.equals(text);
        }

        return false;
    }

    public static boolean isPassword(String text) {
//        Pattern p = Pattern.compile(REGEX_PSD, Pattern.CASE_INSENSITIVE);
//        Matcher m = p.matcher(text);
//        if (m.find()) {
//            String str = m.group(0);
//            return str.equals(text);
//        }
//
//        return false;
        return true;
    }
}

package cn.acyou.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式校验工具类
 *
 * @author youfang
 * @version [1.0.0, 2020年07月23日]
 */
public class RegexUtil {
    /**
     * IP地址正则表达式
     */
    private static final Pattern REGEX_IP_ADDRESS = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");
    /**
     * 邮箱正则表达式
     */
    private static final Pattern REGEX_E_MAIL = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    /**
     * 中文（汉字）正则表达式
     */
    private static final Pattern REGEX_CHINESE = Pattern.compile("[\u4e00-\u9fa5]");
    /**
     * 数字正则表达式
     */
    private static final Pattern REGEX_NUMBER = Pattern.compile("^-?\\d+$");
    /**
     * 日期字符串正则表达式
     */
    private static final Pattern REGEX_DATE_STR = Pattern.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");

    /**
     * 日期+时间字符串正则表达式
     */
    private static final Pattern REGEX_DATE_TIME_STR = Pattern.compile("((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)) (20|21|22|23|[0-1]{1}[0-9]):[0-5]{1}[0-9]:[0-5]{1}[0-9]");
    /**
     * 时间字符串正则表达式
     */
    private static final Pattern REGEX_TIME_STR = Pattern.compile("(0[1-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]");
    /**
     * 密码强度正则
     * 密码8位，字母和数字的组合
     */
    public static final String REGEX_STRONG_PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";


    /**
     * 判断是否是正确的IP地址
     *
     * @param ip ip地址
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        Matcher matcher = REGEX_IP_ADDRESS.matcher(ip);
        return matcher.find();
    }

    /**
     * 判断是否是正确的邮箱地址
     *
     * @param email 邮箱
     * @return boolean true,通过，false，没通过
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        Matcher matcher = REGEX_E_MAIL.matcher(email);
        return matcher.find();
    }

    /**
     * 判断是否**含有**中文，仅适合中国汉字，不包括标点
     *
     * @param text 文字
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChinese(String text) {
        if (StringUtils.isEmpty(text)) {
            return false;
        }
        Matcher m = REGEX_CHINESE.matcher(text);
        return m.find();
    }

    public static void main(String[] args) {
        System.out.println(isPhoneNumber("u@qq"));
        System.out.println(isPhoneNumber("11111111111"));
        System.out.println(isPhoneNumber("18222222222"));
        System.out.println(isPhoneNumber("182222222227"));
    }

    /**
     * 判断是否正整数
     *
     * @param number 数字
     * @return boolean true,通过，false，没通过
     */
    public static boolean isNumber(String number) {
        if (StringUtils.isEmpty(number)) {
            return false;
        }
        Matcher m = REGEX_NUMBER.matcher(number);
        return m.find();
    }

    /**
     * 判断几位小数(正数)
     *
     * @param decimal 数字
     * @param count   小数位数
     * @return boolean true,通过，false，没通过
     */
    public static boolean isDecimal(String decimal, int count) {
        if (StringUtils.isEmpty(decimal)) {
            return false;
        }
        String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count + "})?$";
        return decimal.matches(regex);
    }

    /**
     * 判断是否是手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean true,通过，false，没通过
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) {
            return false;
        }
        String regex = "^1[1-9][0-9]\\d{8}$";
        return phoneNumber.matches(regex);
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param text 文本
     * @return boolean true,通过，false，没通过
     */
    public static boolean hasSpecialChar(String text) {
        if (StringUtils.isEmpty(text)) {
            return false;
        }
        // 如果不包含特殊字符
        return text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0;
    }

    /**
     * 适应CJK（中日韩）字符集，部分中日韩的字是一样的
     */
    public static boolean isChinese2(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是日期字符串
     *
     * @param dateStr str日期
     * @return boolean
     */
    public static boolean isDateStr(String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return false;
        }
        Matcher m = REGEX_DATE_STR.matcher(dateStr);
        return m.find();
    }

    /**
     * 是日期时间字符串
     *
     * @param dateTimeStr 日期时间
     * @return boolean
     */
    public static boolean isDateTime(String dateTimeStr) {
        if (StringUtils.isEmpty(dateTimeStr)) {
            return false;
        }
        Matcher m = REGEX_DATE_TIME_STR.matcher(dateTimeStr);
        return m.find();
    }
    /**
     * 是时间字符串
     *
     * @param timeStr 时间 format with "hh:mm:ss"
     * @return boolean
     */
    public static boolean isTime(String timeStr){
        if (StringUtils.isEmpty(timeStr)) {
            return false;
        }
        Matcher m = REGEX_TIME_STR.matcher(timeStr);
        return m.find();
    }

    /**
     * 是中文字符
     *
     * @param c char
     * @return boolean
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 密码强度校验
     *
     * @param password 密码
     */
    public static boolean isStrongPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return false;
        }
        return password.matches(REGEX_STRONG_PASSWORD);
    }
}

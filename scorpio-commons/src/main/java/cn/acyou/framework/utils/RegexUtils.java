package cn.acyou.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式校验工具类
 *
 * @author youfang
 * @version [1.0.0, 2019年11月23日]
 */
@Deprecated
public class RegexUtils {

    /**
     * 判断是否是正确的IP地址
     *
     * @param ip
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIp(String ip) {
        if (null == ip || "".equals(ip))
            return false;
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ip.matches(regex);
    }

    /**
     * 判断是否是正确的邮箱地址
     *
     * @param email
     * @return boolean true,通过，false，没通过
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email))
            return false;
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }

    /**
     * 判断是否含有中文，仅适合中国汉字，不包括标点
     *
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChinese(String text) {
        if (null == text || "".equals(text))
            return false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(text);
        return m.find();
    }

    /**
     * 判断是否正整数
     *
     * @param number 数字
     * @return boolean true,通过，false，没通过
     */
    public static boolean isNumber(String number) {
        if (null == number || "".equals(number))
            return false;
        String regex = "^-?\\d+$";
        return number.matches(regex);
    }

    /**
     * 判断几位小数(正数)
     *
     * @param decimal 数字
     * @param count   小数位数
     * @return boolean true,通过，false，没通过
     */
    public static boolean isDecimal(String decimal, int count) {
        if (null == decimal || "".equals(decimal))
            return false;
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
        if (null == phoneNumber || "".equals(phoneNumber))
            return false;
        String regex = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$";
        return phoneNumber.matches(regex);
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean hasSpecialChar(String text) {
        if (null == text || "".equals(text))
            return false;
        if (text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
            // 如果不包含特殊字符
            return true;
        }
        return false;
    }

    /**
     * 适应CJK（中日韩）字符集，部分中日韩的字是一样的
     */
    public static boolean isChinese2(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDateStr(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return false;
        }
        String regex =
                "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        return dateStr.matches(regex);
    }

    public static boolean isDateTime(String dateTime) {
        if (StringUtils.isBlank(dateTime)) {
            return false;
        }
        String regex =
                "((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)) (20|21|22|23|[0-1]{1}[0-9]):[0-5]{1}[0-9]:[0-5]{1}[0-9]";
        return dateTime.matches(regex);
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
}

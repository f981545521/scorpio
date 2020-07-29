package cn.acyou.framework.utils;

/**
 * 补充 String 相关方法
 *
 * @author youfang
 * @version [1.0.0, 2020-7-24 下午 09:31]
 **/
public class StringUtil {
    public static final String DOT = ".";
    public static final String EMPTY = "";

    /**
     * 拼接重复字符到指定长度
     *
     * <pre>
     * StringUtil.concatLengthChar(4, '9')     = 9999
     * StringUtil.concatLengthChar(4, '0')     = 0000
     * StringUtil.concatLengthChar(5, '8')     = 88888
     * </pre>
     * @param length    长度
     * @param character 字符
     * @return {@link String}
     */
    public static String concatLengthChar(int length, char character) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(character);
        }
        return sb.toString();
    }

}

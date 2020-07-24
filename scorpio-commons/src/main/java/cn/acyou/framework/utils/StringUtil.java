package cn.acyou.framework.utils;

/**
 * 补充 String 相关方法
 *
 * @author youfang
 * @version [1.0.0, 2020-7-24 下午 09:31]
 **/
public class StringUtil {

    /**
     * 拼接重复字符到指定长度
     *
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

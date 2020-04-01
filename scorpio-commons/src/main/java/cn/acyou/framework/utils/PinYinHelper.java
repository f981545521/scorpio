package cn.acyou.framework.utils;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youfang
 */
public class PinYinHelper {
    private static final Logger logger = LoggerFactory.getLogger(PinYinHelper.class);
    private static final String EMPTY_STR = "";
    private static final String COMMA = ",";
    public static final String INITIAL_OTHER = "#";

    /**
     * 中文转拼音 返回拼音首字母 如 中华人民共和国 返回 ZHRMGHG
     *
     * @param str
     * @return 拼音
     */
    public static String tranferToPinYin(String str) {
        try {
            return PinyinHelper.getShortPinyin(str).toUpperCase();
        } catch (PinyinException e) {
            logger.warn(e.getMessage(), e);
        }
        return EMPTY_STR;
    }


    /**
     * 汉字转换位汉语拼音首字母，英文字符不变，特殊字符丢失 支持多音字，
     * 生成方式如（长沙市长:cssc,zssz,zssc,cssz）
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        try {
            return PinyinHelper.getShortPinyin(chines);
        } catch (PinyinException e) {
            logger.warn(e.getMessage(), e);
        }
        return EMPTY_STR;
    }

    /**
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen
     * ,chongdangshen,zhongdangshen,chongdangcan）
     *
     * @param chines 汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        try {
            return PinyinHelper.convertToPinyinString(chines, EMPTY_STR, PinyinFormat.WITHOUT_TONE);
        } catch (PinyinException e) {
            logger.warn(e.getMessage(), e);
        }
        return EMPTY_STR;
    }

    /**
     * parsePinyinAndHead 生成拼音和首字母组合
     * 如：内存 result ：内存,neicun,nc
     *
     * @return String
     */
    public static String parsePinyinAndHead(String str) {
        if (StringUtils.isNotEmpty(str)) {
            return str + COMMA + converterToSpell(str) + COMMA + converterToFirstSpell(str);
        } else {
            return EMPTY_STR;
        }
    }


    /**
     * parsePinyinInitial 生成字符串拼音首字母大写
     * 如：内存 result ：内存,neicun,nc
     * 若首字母是非字母： #
     *
     * @return String
     */
    public static String parsePinyinInitial(String str) {
        if (StringUtils.isNotEmpty(str)) {
            try {
                String shortPy = PinyinHelper.getShortPinyin(str);
                if (StringUtils.isNotEmpty(shortPy)) {
                    char c = shortPy.charAt(0);
                    if ((c > 'A' && c < 'Z') || (c > 'a' && c < 'z')) {
                        return shortPy.substring(0, 1);
                    }
                }
            } catch (PinyinException e) {
                e.printStackTrace();
            }
        }
        return INITIAL_OTHER;
    }

    public static String parsePinyinInitialUpperCase(String str) {
        return parsePinyinInitial(str).toUpperCase();
    }

    public static String parsePinyinInitialLowerCase(String str) {
        return parsePinyinInitial(str).toLowerCase();
    }


    public static void main(String[] args) {
        String str = "内存SKU";
        String pinyin = parsePinyinInitialUpperCase(str);
        System.out.println(str + " result ：" + pinyin);
    }


}

package cn.acyou.framework.utils.printer;

import java.io.UnsupportedEncodingException;

/**
 * @author youfang
 * @version [1.0.0, 2020/12/8]
 **/
public class PrinterUtil {

    public static byte[] str2bytes(String s) {
        if (null == s || "".equals(s)) {
            return null;
        }
        byte[] strBytes;
        try {
            strBytes = s.getBytes("GBK");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
        return strBytes;
    }

    public static String formatStorageCode(){
        return  "^XA\n" +
                "^FO5,25\n" +
                "^BCN,180,Y,N,N\n" +
                "^FDA005-A25-B45^FS\n" +
                "^XZ";
    }

    public static String formatGoodsCode(String content) {
        return "^XA\n" +
                "^SEE:GB18030.DAT^FS\n" +
                "^CWZ,E:SIMSUN.FNT\n" +
                "^CI26\n" +
                "^JMA^LL300^PW400^MD10^PR2^PON^LRN^LH0,0\n" +
                "\n" +
                "\n" +
                "^FO20,20\n" +
                "^AZN,35,35\n" +
                "^FD 切口皮肤组织牵开扩张器^FS\n" +
                "\n" +
                "^FO20,60\n" +
                "^AZN,20,20\n" +
                "^FD 规格:100/50-35/5^FS\n" +
                "\n" +
                "^FO20,85\n" +
                "^AZN,20,20\n" +
                "^FD 型号:标准型^FS\n" +
                "\n" +
                "^FO20,110\n" +
                "^AZN,20,20\n" +
                "^FD 效期:20200827^FS\n" +
                "\n" +
                "^FO30,140\n" +
                "^B7N,8,4,5,,N\n" +
                "^FD" + content + "^FS\n" +
                "\n" +
                "^FO70,220\n" +
                "^A2N,20,10\n" +
                "^FD" + content + "^FS\n" +
                "\n" +
                "^XZ";
    }

    /**
     * 格式商品代码
     *
     * @param title         标题
     * @param specification 规范
     * @param goodsModel    产品模型
     * @param expiryDate    截止日期
     * @param content       内容
     * @return {@link String}
     */
    public static String formatGoodsCode(String title, String specification, String goodsModel, String expiryDate, String content) {
        return "^XA\n" +
                "^SEE:GB18030.DAT^FS\n" +
                "^CWZ,E:SIMSUN.FNT\n" +
                "^CI26\n" +
                "^JMA^LL300^PW400^MD10^PR2^PON^LRN^LH0,0\n" +
                "\n" +
                "\n" +
                "^FO20,20\n" +
                "^AZN,35,35\n" +
                "^FD " + title + "^FS\n" +
                "\n" +
                "^FO20,60\n" +
                "^AZN,20,20\n" +
                "^FD 规格:"+specification+"^FS\n" +
                "\n" +
                "^FO20,85\n" +
                "^AZN,20,20\n" +
                "^FD 型号:"+goodsModel+"^FS\n" +
                "\n" +
                "^FO20,110\n" +
                "^AZN,20,20\n" +
                "^FD 效期:"+expiryDate+"^FS\n" +
                "\n" +
                "^FO30,140\n" +
                "^B7N,8,4,5,,N\n" +
                "^FD" + content + "^FS\n" +
                "\n" +
                "^FO70,220\n" +
                "^A2N,20,10\n" +
                "^FD" + content + "^FS\n" +
                "\n" +
                "^XZ";
    }
}

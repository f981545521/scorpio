package cn.acyou.framework.utils;

/**
 * 单位换算工具
 * @author youfang
 * @version [1.0.0, 2020-4-6 下午 09:02]
 **/
public class UnitConversionUtil {
    /**
     * 字节转换 为可读值
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    public static void main(String[] args) {
        System.out.println(convertFileSize(2097152));
    }
}

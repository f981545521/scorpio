package cn.acyou.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.OptionalDouble;

/**
 * @author youfang
 * @version [1.0.0, 2020/4/3]
 **/
public class MathUtil {

    public static final BigDecimal hundred = new BigDecimal("100");

    public static final BigDecimal percent = new BigDecimal("0.01");

    /**
     * 计算百分之xx
     * <pre>
     *      calculationPercent(2, 5) = 40
     *      calculationPercent(3, 5) = 60
     * </pre>
     *
     * @param molecule    分子
     * @param denominator 分母
     * @return xx%
     */
    public static Integer calculationPercent(Integer molecule, Integer denominator) {
        BigDecimal moleculeDecimal = new BigDecimal(molecule);
        BigDecimal denominatorDecimal = new BigDecimal(denominator);
        BigDecimal divide = moleculeDecimal.divide(denominatorDecimal, 2, RoundingMode.HALF_UP);
        return divide.multiply(hundred).intValue();
    }

    /**
     * 计算平均数
     * @param sourceNumbers 数据源
     * @return 平均数
     */
    public static Double averageDouble(Collection<Object> sourceNumbers){
        OptionalDouble average = Arrays.stream(sourceNumbers.toArray()).mapToDouble(o -> Double.parseDouble(o.toString())).average();
        if (average.isPresent()){
            return average.getAsDouble();
        }else {
            throw new IllegalArgumentException("calculation average faild !");
        }
    }
    /**
     * 计算平均数
     * @param sourceNumbers 数据源
     * @return 平均数
     */
    public static Double averageDouble(Object[] sourceNumbers){
        return averageDouble(Arrays.asList(sourceNumbers));
    }

    public static void main(String[] args) {
        System.out.println(createMaxLong(18));
    }

    /**
     * 创建指定长度的最大long类型
     *
     * Long.MAX_VALUE: 9223372036854775807
     *
     * @param length 长度 <=18
     * @return long
     */
    public static long createMaxLong(int length) {
        if (length == 0){
            return 0;
        }
        if (length > 18){
            throw new IllegalArgumentException("length must be less than 18 .");
        }
        return Long.parseLong(StringUtil.concatLengthChar(length, '9'));
    }

}

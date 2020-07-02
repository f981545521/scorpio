package cn.acyou.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
        Object[] numbers = new Object[]{2,3,4,5,6};
        System.out.println(averageDouble(numbers).intValue());
    }
}

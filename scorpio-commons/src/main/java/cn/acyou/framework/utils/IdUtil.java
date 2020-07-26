package cn.acyou.framework.utils;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.utils.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

/**
 * @author youfang
 * @version [1.0.0, 2020-7-24 下午 08:04]
 **/
@Component
public class IdUtil {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取日期前缀Id
     *
     * @param prefix 前缀
     * @return {@link String}
     */
    public String getDatePrefix4BitId(String prefix) {
        return getDatePrefixId(prefix, 4);
    }

    /**
     * 从Redis中获取ID
     * <p>
     * prefix + 20200724 + 00001
     *
     * @param prefix 前缀
     * @param length 长度
     * @return ID
     */
    public String getDatePrefixId(String prefix, int length) {
        String formatDate = DateUtil.getCurrentDateShortFormat();
        //KEY:  SEQ:20200724
        String key = "SEQ:" + formatDate;
        Long increment = redisUtils.increment(key, 1, 36*60*60);
        long maxV = MathUtil.createMaxLong(length);
        if (increment > maxV) {
            throw new ServiceException("ID获取错误：超出最大值!");
        }
        DecimalFormat df = new DecimalFormat(StringUtil.concatLengthChar(length, '0'));
        return prefix + formatDate + df.format(increment);
    }


}

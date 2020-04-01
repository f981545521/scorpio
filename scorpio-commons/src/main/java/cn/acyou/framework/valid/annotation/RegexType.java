package cn.acyou.framework.valid.annotation;

/**
 * 校验的正则表达式类型
 *
 * @author youfang
 * @version [1.0.0, 2017年11月23日]
 */
public enum RegexType {
    /**
     * 空
     */
    NONE,
    /**
     * 特殊字符
     */
    SPECIALCHAR,
    /**
     * 中文
     */
    CHINESE,
    /**
     * 电子邮箱
     */
    EMAIL,
    /**
     * ip
     */
    IP,
    /**
     * 数字
     */
    NUMBER,
    /**
     * 手机号
     */
    PHONENUMBER,
    /**
     * 日期
     */
    DATE,
    /**
     * 时间(年月日时分秒)
     */
    DATETIME;
}

package cn.acyou.framework.valid;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.utils.ReflectUtils;
import cn.acyou.framework.utils.RegexUtils;
import cn.acyou.framework.valid.annotation.BaseValid;
import cn.acyou.framework.valid.annotation.DateValidType;
import cn.acyou.framework.valid.annotation.EnhanceValid;
import cn.acyou.framework.valid.annotation.RegexType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 增强实体参数校验
 *
 * @author youfang
 * @version [1.0.0, 2019/10/16]
 **/
public class EnhanceValidUtil {
    private static final Logger logger = LoggerFactory.getLogger(EnhanceValidUtil.class);

    /**
     * 增强实体参数校验
     *
     * @param object 校验对象
     */
    public static void valid(Object object) {
        //获取object的类型
        Class<?> clazz = object.getClass();
        //获取该类型声明的成员
        Field[] fields = clazz.getDeclaredFields();
        //遍历属性
        for (Field field : fields) {
            //对于private私有化的成员变量，通过setAccessible来修改器访问权限
            field.setAccessible(true);
            validate(field, object);
            //重新设置会私有权限
            field.setAccessible(false);
        }

    }

    /**
     * 验证注解
     *
     * @param field  字段
     * @param object 校验对象
     */
    private static void validate(Field field, Object object) {
        //获取对象的成员的 增强校验注解信息
        EnhanceValid enhanceValid = field.getAnnotation(EnhanceValid.class);
        //未加注解的字段直接返回
        if (enhanceValid != null) {
            BaseValid[] baseValid = enhanceValid.value();
            validateBaseValid(field, object, baseValid);
        }
        //获取对象的成员的 基本校验注解信息
        BaseValid baseValid = field.getAnnotation(BaseValid.class);
        //未加注解的字段直接返回
        if (baseValid != null) {
            validateBaseValid(field, object, new BaseValid[]{baseValid});
        }

    }

    /**
     * 验证基础类型
     *
     * @param field     字段
     * @param object    校验对象
     * @param baseValidArray 验证注解组
     */
    private static void validateBaseValid(Field field, Object object, BaseValid[] baseValidArray) {
        //当前值
        Object validValue = null;
        String description = "请求参数错误，请检查！";

        try {
            validValue = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //当前字段名称
        String currentFieldName = field.getName();

        for (BaseValid baseValid: baseValidArray){
            //自定义描述
            if (StringUtils.isNotEmpty(baseValid.message())) {
                description = baseValid.message();
            }
            //优先非NULL非Empty
            if (baseValid.notNull()) {
                if (validValue == null || StringUtils.isEmpty(validValue.toString())) {
                    throw new ServiceException(description);
                }
            }
            if (baseValid.notEmpty()) {
                if (validValue == null || StringUtils.isEmpty(validValue.toString())) {
                    logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "参数为NULL");
                    throw new ServiceException(description);
                }
                if (validValue instanceof Collection && ((Collection) validValue).isEmpty()) {
                    logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "集合为空");
                    throw new ServiceException(description);
                }
                if (validValue instanceof Map && ((Map) validValue).isEmpty()) {
                    logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "集合为空");
                    throw new ServiceException(description);
                }
            }
            //非NULL 进一步校验
            if (validValue != null) {
                if (validValue.toString().length() > baseValid.maxLength() && baseValid.maxLength() != 0) {
                    logger.warn("[数据校验]|{}|{}|{}|{}", "valid failed", currentFieldName, "长度超过了", baseValid.maxLength());
                    throw new ServiceException(description);
                }

                if (validValue.toString().length() < baseValid.minLength() && baseValid.minLength() != 0) {
                    logger.warn("[数据校验]|{}|{}|{}|{}", "valid failed", currentFieldName, "长度小于了", baseValid.minLength());
                    throw new ServiceException(description);
                }

                if (NumberUtils.isNumber(validValue.toString()) && baseValid.min() != 0
                        && Integer.parseInt(validValue.toString()) < baseValid.min()) {
                    logger.warn("[数据校验]|{}|{}|{}|{}", "valid failed", currentFieldName, "不能小于", baseValid.min());
                    throw new ServiceException(description);
                }

                if (NumberUtils.isNumber(validValue.toString()) && baseValid.max() != 0
                        && Integer.parseInt(validValue.toString()) > baseValid.max()) {
                    logger.warn("[数据校验]|{}|{}|{}|{}", "valid failed", currentFieldName, "不能大于", baseValid.max());
                    throw new ServiceException(description);
                }

                if (baseValid.notInRange().length > 0) {
                    if (!ArrayUtils.contains(baseValid.notInRange(), validValue.toString())) {
                        logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "取值不在范围内");
                        throw new ServiceException(description);
                    }
                }

                if (baseValid.numberNotInRange().length > 0) {
                    if (!ArrayUtils.contains(baseValid.numberNotInRange(), Integer.parseInt(validValue.toString()))) {
                        logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "取值不在范围内");
                        throw new ServiceException(description);
                    }
                }

                if (baseValid.regexType() != RegexType.NONE) {
                    String result = null;
                    switch (baseValid.regexType()) {
                        case NONE:
                            break;
                        case SPECIALCHAR:
                            if (RegexUtils.hasSpecialChar(validValue.toString())) {
                                logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "不能含有特殊字符");
                                result = description;
                            }
                            break;
                        case CHINESE:
                            if (RegexUtils.isChinese2(validValue.toString())) {
                                logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "不能含有中文字符");
                                result = description;
                            }
                            break;
                        case EMAIL:
                            if (!RegexUtils.isEmail(validValue.toString())) {
                                logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "邮箱地址格式不正确");
                                result = description;
                            }
                            break;
                        case IP:
                            if (!RegexUtils.isIp(validValue.toString())) {
                                logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "IP地址格式不正确");
                                result = description;
                            }
                            break;
                        case NUMBER:
                            if (!RegexUtils.isNumber(validValue.toString())) {
                                logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "不是数字");
                                result = description;
                            }
                            break;
                        case PHONENUMBER:
                            if (!RegexUtils.isPhoneNumber(validValue.toString())) {
                                logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "不是手机号码");
                                result = description;
                            }
                            break;
                        case DATE:
                            if (!RegexUtils.isDateStr(validValue.toString())) {
                                logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "日期格式出错");
                                result = description;
                            }
                            break;
                        case DATETIME:
                            if (!RegexUtils.isDateTime(validValue.toString())) {
                                logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "时间格式出错");
                                result = description;
                            }
                            break;
                        default:
                            break;
                    }
                    if (null != result) {
                        throw new ServiceException(result);
                    }
                }

                if (baseValid.dateValid() != DateValidType.none) {
                    String result = null;
                    if (validValue instanceof Date) {
                        Date validValueDate = (Date) validValue;
                        switch (baseValid.dateValid()) {
                            case none:
                                break;
                            case if_afterNow:
                                if (validValueDate.after(new Date())) {
                                    logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "不能大于当前时间！");
                                    result = description;
                                }
                                break;
                            case if_beforeNow:
                                if (validValueDate.before(new Date())) {
                                    logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "不能小于当前时间！");
                                    result = description;
                                }
                                break;
                            case if_afterSpecifyDate:
                                String afterDateFieldName = baseValid.specifyDateFieldName();
                                if (afterDateFieldName.trim().length() > 0) {
                                    Object fieldValue = ReflectUtils.getFieldValue(object, afterDateFieldName);
                                    if (fieldValue instanceof Date) {
                                        Date filedDate = (Date) fieldValue;
                                        if (validValueDate.before(filedDate)) {
                                            logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "不能大于" + afterDateFieldName + "！");
                                            result = description;
                                        }
                                    }
                                }
                                break;
                            case if_beforeSpecifyDate:
                                String beforeDateFieldName = baseValid.specifyDateFieldName();
                                if (beforeDateFieldName.trim().length() > 0) {
                                    Object fieldValue = ReflectUtils.getFieldValue(object, beforeDateFieldName);
                                    if (fieldValue instanceof Date) {
                                        Date filedDate = (Date) fieldValue;
                                        if (validValueDate.before(filedDate)) {
                                            logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "不能小于" + beforeDateFieldName + "！");
                                            result = description;
                                        }
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                        if (null != result) {
                            throw new ServiceException(result);
                        }

                    }
                }

                if (StringUtils.isNotEmpty(baseValid.regexExpression())) {
                    if (validValue.toString().matches(baseValid.regexExpression())) {
                        logger.warn("[数据校验]|{}|{}|{}", "valid failed", currentFieldName, "格式不正确");
                        throw new ServiceException(description);
                    }
                }

                //实体类型 继续校验
                if (baseValid.entityValid()) {
                    valid(validValue);
                }

                //实体集合实体 继续校验
                if (baseValid.entityCollectionValid()) {
                    if (validValue instanceof Collection) {
                        Collection valueList = (Collection) validValue;
                        for (Object o : valueList) {
                            valid(o);
                        }
                    }
                }

            }
        }
    }

}

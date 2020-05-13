package cn.acyou.scorpio.dto.task;

import cn.acyou.framework.constant.ErrorEnum;

/**
 * 统一错误码处理
 *
 * @author youfang
 * @version [1.0.0, 2020/5/13]
 **/
public enum TaskErrorEnum implements ErrorEnum<TaskErrorEnum> {
    E_200017(200017, "当前状态不正确！"),
    ;

    // 成员变量
    private int code;

    private String message;

    TaskErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 普通方法
    public static String getMessage(int code) {
        for (TaskErrorEnum c : TaskErrorEnum.values()) {
            if (c.getCode() == code) {
                return c.message;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 返回枚举项的 code
     */
    @Override
    public int code() {
        return this.code;
    }

    /**
     * 返回枚举项的 message
     */
    @Override
    public String message() {
        return this.message;
    }

    /**
     * 返回枚举对象
     */
    @Override
    public TaskErrorEnum get() {
        return this;
    }
}

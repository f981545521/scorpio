package cn.acyou.scorpio.dto.task;

import cn.acyou.framework.constant.ErrorEnum;

/**
 * 统一错误码处理
 *
 * @author youfang
 * @version [1.0.0, 2020/5/13]
 **/
public enum TaskErrorEnum implements ErrorEnum<TaskErrorEnum> {
    /**
     * 错误枚举
     */
    E_UNAUTHENTICATED(4001, "未登录，请先登录！"),
    E_LOGIN_TIMEOUT(4001, "登录信息已失效，请重新登录！"),

    E_UNAUTHORIZED(4003, "对不起，您没有访问权限，如需要请联系管理员！"),
    E_NEED_SURE(4004, "需要确认操作！"),
    E_OPTMISTIC_MODIFIED(4005, "页面内容过期了,请刷新页面后再继续操作!"),
    E_OPTMISTIC_REMOVE(4006, "页面数据已经被删除,请稍后刷新再试!"),
    E_INVALID_SORT_PARAMETER(4100, "非法的OrderBy参数，请检查！"),
    E_100001(100001, "登录用户不存在，已经为您自动注册，请联系管理员激活！"),
    E_200017(200017, "当前状态不正确！"),
    E_DELETE(5001,"删除数据失败"),
    E_ZERO(5002,"数量不能为空或者0"),
    E_NULL(5003,"当前对象不存在")
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

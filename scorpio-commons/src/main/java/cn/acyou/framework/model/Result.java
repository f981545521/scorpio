package cn.acyou.framework.model;

import java.io.Serializable;

/**
 * @author youfang
 * @date 2020/03/19 11:59
 **/
public class Result<T> implements Serializable {

    private int code;
    private String message;
    private boolean success = true;
    private T data;

    private static final String SUCCESS_MESSAGE = "处理成功";
    private static final String ERROR_MESSAGE = "未知异常，请联系管理员";

    private Result() {
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(int code, T data, String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> error() {
        return error(500, ERROR_MESSAGE);
    }

    public static <T> Result<T> error(String message) {
        return error(500, message);
    }

    public static <T> Result<T> error(int code, String message) {
        Result result = new Result(code, message);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(String code, String message) {
        Result result = new Result(Integer.parseInt(code), message);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(int code, T data, String message) {
        Result<T> result = new Result(code, data, message);
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> success() {
        return success(null, "处理成功");
    }

    public static <T> Result<T> success(T data) {
        return success(data, "处理成功");
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result(0, data, message);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean notSuccess() {
        return !this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result data(T data) {
        return success(data, (String) null);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}

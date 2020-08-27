package cn.acyou.scorpio.conf;

import cn.acyou.framework.exception.ServiceException;
import cn.acyou.framework.model.Result;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 统一异常处理，返回JSON
 *
 * @author youfang
 * @version [1.0.0, 2020-4-20 下午 09:43]
 **/
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result<Object> handleHttpRequestMethodNotSupportedException(HttpServletRequest request, Exception e){
        Result<Object> resultInfo = Result.error();
        //org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' not supported
        resultInfo.setMessage(String.format("接口[%s]不支持['%s']请求方式。", request.getRequestURI(), request.getMethod()));
        return resultInfo;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Result<Object> handleNoHandlerFoundException(HttpServletRequest request, Exception e){
        Result<Object> resultInfo = Result.error();
        //org.springframework.web.servlet.NoHandlerFoundException: No handler found for GET /student/idGenUtil4
        resultInfo.setMessage(String.format("接口[%s]不存在，请检查！", request.getRequestURI()));
        return resultInfo;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<Object> handleException(HttpServletRequest request, Exception e) {
        Result<Object> resultInfo = Result.error();
        Throwable t = Throwables.getRootCause(e);
        log.error("统一异常处理 => 请求路径：" + request.getRequestURI() + " | 异常信息：" + e.getMessage());
        if (t instanceof ServiceException) {
            Result<Object> result = ((ServiceException) t).getResult();
            if (result != null){
                resultInfo = result;
            }else {
                resultInfo.setCode(400);
                resultInfo.setMessage(t.getMessage());
            }
            //resultInfo.setData(e.getMessage());
        } else {
            e.printStackTrace();
            try {
                //未知异常打印堆栈信息到data中，方便定位错误原因。
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                e.printStackTrace(new PrintWriter(buf, true));
                buf.close();
                resultInfo.setData(buf.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultInfo;
    }

}

package cn.acyou.framework.constant;

/**
 * 错误枚举基类，用于返回错误码
 * HTTP状态码：https://zh.wikipedia.org/wiki/HTTP%E7%8A%B6%E6%80%81%E7%A0%81
 * 2xx成功
 * 3xx重定向
 * 4xx客户端错误     - 代表了客户端看起来可能发生了错误，妨碍了服务器的处理。
 *   400 Bad Request：由于明显的客户端错误（例如，格式错误的请求语法，太大的大小，无效的请求消息或欺骗性路由请求），服务器不能或不会处理该请求。
 *   401 Unauthorized：即用户没有必要的凭据。
 *   403 Forbidden：服务器已经理解请求，但是拒绝执行它。
 *   404 Not Found：请求失败，请求所希望得到的资源未被在服务器上发现。
 *   405 Method Not Allowed：请求方法不能被用于请求相应的资源。
 *   408 Request Timeout：请求超时。
 * 5xx服务器错误     - 表示服务器无法完成明显有效的请求。
 *   500 Internal Server Error：通用错误消息，服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理。没有给出具体错误信息。
 *   502 Bad Gateway：作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应。
 *   503 Service Unavailable：由于临时的服务器维护或者过载，服务器当前无法处理请求。
 *
 * @author youfang
 * @version [1.0.0, 2020/5/13]
 **/
public interface ErrorEnum<C extends Enum<?>> {

    /**
     * 返回枚举项的 code
     *
     * @return int
     */
    int code();

    /**
     * 返回枚举项的 message
     *
     * @return {@link String}
     */
    String message();

    /**
     * 返回枚举对象
     *
     * @return {@link C}
     */
    C get();

}

package cn.acyou.framework.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/16]
 **/
@Slf4j
public class JwtUtil {
    /**
     * Token 过期时间 单位（ms）
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000L;

    /**
     * JWT效验器
     */
    private static final String SIGN_SECRET = "scorpio";

    /**
     * 生成 token
     *
     * @param userId 用户名
     * @return token
     */
    public static String getToken(String userId) {
        try {
            userId = StringUtils.lowerCase(userId);
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SIGN_SECRET);
            return JWT.create()
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("error：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 从 token中获取用户名
     *
     * @return token中包含的用户名
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 从 token中获取过期时间
     *
     * @return token中包含的用户名
     */
    public static Date getExpireDate(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt();
        } catch (JWTDecodeException e) {
            log.error("error：{}", e.getMessage());
            return null;
        }
    }


    /**
     * 校验 token是否正确
     *
     * @param userId  用户ID
     * @return 是否正确
     */
    public static boolean verify(String token, String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SIGN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userId", userId)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.info("token is invalid{}", e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {
        String sign = getToken("1110");
        System.out.println(sign);
        //header (base64后的)                  .  payload (base64后的)                        .  secret
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1OTQ5NjU4MjUsInVzZXJJZCI6IjExMTAifQ.9ws45Gm-Q4LT5WhkCK2_Z6l-auUPpUTx26JX2MWxfX0
        String userId = getUserId(sign);
        System.out.println(userId);

        boolean verify = verify(sign, "1110");
        System.out.println(verify);

        Date expireDate = getExpireDate(sign);
        System.out.println(DateUtil.getDateFormat(expireDate));

    }
}

package cn.acyou.framework.utils;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * wx.miniProgram.appid=wxb029000cd6395a43
 * wx.miniProgram.secret=a1be655ac1e20d858d7cd0c5d0e202d8
 *
 * 获取access_token
 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
 * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb029000cd6395a43&secret=a1be655ac1e20d858d7cd0c5d0e202d8
 * 35_2pHPHgFHe3gqET5H1otkR2Er2CXjyTu9LBam4tKEFKQlxyoC3KZeEaQYLOWIGRtF6dQqHZ1pOohZlzjTcgTzoVSnhSPK-6jKl9kNXsoL0lPQEqND0ZAZ_cpTamdpv6CArrPJNjgxn-boXOi1GYNgADARJY
 *
 *
 * 获取session_key    "001beTIP0O8M262NEoGP0vvQIP0beTIb"
 * <pre>
 *     WXML
 *     <button class='bottom' type='primary' open-type="getPhoneNumber" lang="zh_CN" bindgetphonenumber="getPhoneNumber">
 *         授权登录（手机号）
 *     </button>
 *
 *     JS
 *     getPhoneNumber: function(e){
 *         console.log(e.detail.errMsg)
 *         console.log(e.detail.iv)
 *         console.log(e.detail.encryptedData)
 *         var _this = this;
 *         wx.login({
 *             success: res => {
 *                 console.log(res.code)
 *             }
 *         })
 *     },
 * </pre>
 * https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
 * 请求参数：
 * 属性	类型	默认值	必填	说明
 * appid	string		是	小程序 appId
 * secret	string		是	小程序 appSecret
 * js_code	string		是	登录时获取的 code
 * grant_type	string		是	授权类型，此处只需填写 authorization_code
 *
 * 返回值：
 * openid	string	用户唯一标识
 * session_key	string	会话密钥
 * unionid	string	用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
 * errcode	number	错误码
 * errmsg	string	错误信息
 *
 * https://api.weixin.qq.com/sns/jscode2session?appid=wxb029000cd6395a43&secret=a1be655ac1e20d858d7cd0c5d0e202d8&js_code=001beTIP0O8M262NEoGP0vvQIP0beTIb&grant_type=authorization_code
 * @author youfang
 * @version [1.0.0, 2020-7-9 下午 11:23]
 **/
public class WXUtils {
    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, StandardCharsets.UTF_8);
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String sessionKey = "p370rsuNHSBcnHtLvz3q5A==";
        String iv = "00Nupo9wsDvsvTIgk5uE/Q==";
        String encryptedData =
                "FL+m87sZQZcHtkqGngotapsZNss9HRIaPS5qyfHySYrOCZ1nByO0UxNkwpxcDUjBB15I6p/4Uqw8QYdq5f0X5B7jVACOWn4hU4yRTEU3z8IJ3GeQ78gnweUY6HzomR+7HYzA9gYyAAjRWyzMD+IgqZTfq9dqvQXIuE9YZvrlg2ezL27iK8pjmgp309zT7c92jzCpfx74/9QO1/v4RBspkQ==";
        //String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        //String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        //String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";

        JSONObject userInfo = getUserInfo(encryptedData, sessionKey, iv);
        System.out.println(userInfo);
        //phoneNumber

    }
}

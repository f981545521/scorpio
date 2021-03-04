package cn.acyou.scorpio.test.informal.dll;

import com.sun.jna.Native;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author youfang
 * @version [1.0.0, 2021/3/4]
 **/
public class JnaUtil {

    public static Logger logger = LoggerFactory.getLogger(JnaUtil.class);
    static CLibrary INSTANCE;
    public static CLibrary LED_INSTANCE;

    static {
        // 获取jdk位数
        String bits = System.getProperty("sun.arch.data.model");
        // 获取os名称
        String ops = System.getProperty("os.name");
        logger.info("jdk bits=" + bits);
        logger.info("option sysetm=" + ops);
        if (ops.startsWith("win") || ops.startsWith("Win")) {
            //windows
            if ("32".equals(bits)) {
                logger.info("use CCR_SDKx32.dll");
                INSTANCE = (CLibrary) Native.loadLibrary("CCR_SDKx32.dll", CLibrary.class);
            }
            if ("64".equals(bits)) {
                logger.info("use CCR_SDKx64.dll and LEDControl_x64.dll");
                INSTANCE = (CLibrary) Native.loadLibrary("CCR_SDKx64.dll", CLibrary.class);
                LED_INSTANCE = Native.loadLibrary("LEDControl_x64.dll", CLibrary.class);
            }
        } else {
            //linux
            if ("32".equals(bits)) {
                logger.info("use libCCR_SDKx64-x86_32.so");
                INSTANCE = (CLibrary) Native.loadLibrary("libCCR_SDKx64-x86_32.so", CLibrary.class);
            }
            if ("64".equals(bits)) {
                logger.info("use libCCR_SDKx64-x86_64.so and libLEDControl-x86_64.so");
                INSTANCE = (CLibrary) Native.loadLibrary("libCCR_SDKx64-x86_64.so", CLibrary.class);
                LED_INSTANCE = Native.loadLibrary("libLEDControl-x86_64.so", CLibrary.class);
            }
        }
    }

}

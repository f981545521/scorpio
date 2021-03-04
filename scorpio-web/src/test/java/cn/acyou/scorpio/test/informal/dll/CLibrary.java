package cn.acyou.scorpio.test.informal.dll;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * @author youfang
 * @version [1.0.0, 2021/3/4]
 **/
public interface CLibrary extends Library {

    CLibrary INSTANCE = Native.loadLibrary("win32-x86-64/ZMPCL_x86_64.dll", CLibrary.class);

    int ZM_GetInfo();

    int OpenPort(
            int xx
    );
}

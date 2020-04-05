package cn.acyou.framework.utils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;


/**
 * @author youfang
 * @version [1.0.0, 2019/12/23]
 **/
public class ZipUtil {
    private final static Logger log = LoggerFactory.getLogger(ZipUtil.class);

    public static void zipBatch(String[] fileNames, InputStream[] srcFiles, OutputStream destStream) throws Exception {
        ZipOutputStream zos = new ZipOutputStream(destStream);
        for (int k = 0; k < srcFiles.length; ++k) {
            org.apache.tools.zip.ZipEntry ze = null;
            byte[] buf = new byte[1024];
            ze = new ZipEntry(fileNames[k]);
            ze.setSize(srcFiles.length);
            ze.setTime((new Date()).getTime());
            log.info("正在压缩第" + k + "个输入流!");
            zos.putNextEntry(ze);
            BufferedInputStream is = new BufferedInputStream(srcFiles[k]);
            int readLen;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                zos.write(buf, 0, readLen);
            }
            is.close();
        }
        zos.setEncoding("GBK");
        zos.close();
        log.info("压缩完毕！");
    }

}
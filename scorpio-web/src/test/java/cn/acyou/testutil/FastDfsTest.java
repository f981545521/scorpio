package cn.acyou.testutil;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/29]
 **/
public class FastDfsTest {
    private static final String FASTDFS_CLIENT_PROPERTIES = "fastdfs-client.properties";
    private static String dfsFileName = "M00/00/00/wKgBZF8hD_GAY_scAA0Fgpto1-A274.jpg";
    private static String group_name = "test-image";

    @Test
    public void testUpload() throws Exception {
        TrackerServer trackerServer = null;
        try {
            ClientGlobal.initByProperties(FASTDFS_CLIENT_PROPERTIES);
            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();
            String local_filename = "D:\\temp\\123.jpg";
            String file_ext_name = "jpg";
            StorageClient storageClient = new StorageClient(trackerServer, null);
            NameValuePair valuePair = new NameValuePair();
            valuePair.setName("name");
            valuePair.setValue("tenmao test");
            NameValuePair[] pairs = {valuePair};

            String[] fileIds = storageClient.upload_file(local_filename, file_ext_name,
                    pairs);
            System.out.println("fileIds：" + fileIds);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);
            //组名：group1
            //路径: M00/00/00/wKgBZF8hD_GAY_scAA0Fgpto1-A274.jpg
        } finally {
            try {
                if (null != trackerServer) {
                    trackerServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDownload() throws Exception {    //下载文件
        TrackerServer trackerServer = null;

        try {
            String groupName = "group1";
            String filePath = dfsFileName;
            ClientGlobal.initByProperties(FASTDFS_CLIENT_PROPERTIES);

            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, null);
            byte[] bytes = storageClient.download_file(groupName, filePath);
            Assert.assertNotNull(bytes);
            String storePath = "D:\\temp\\123_download.jpg";
            try (OutputStream out = new FileOutputStream(storePath)) {
                out.write(bytes);
            }
        } finally {
            try {
                if (null != trackerServer) trackerServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testGetFileInfo() { //获取文件信息
        TrackerServer trackerServer = null;

        try {
            String groupName = "group1";
            String filePath = dfsFileName;
            ClientGlobal.initByProperties(FASTDFS_CLIENT_PROPERTIES);

            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, null);
            FileInfo file = storageClient.get_file_info(groupName, filePath);
            System.out.println("ip--->" + file.getSourceIpAddr());
            System.out.println("文件大小--->" + file.getFileSize());
            System.out.println("文件上传时间--->" + file.getCreateTimestamp());
            System.out.println(file.getCrc32());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != trackerServer) trackerServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testGetFileMate() throws Exception { //获取文件的原数据类型
        TrackerServer trackerServer = null;

        try {
            String groupName = "group1";
            String filePath = dfsFileName;
            ClientGlobal.initByProperties(FASTDFS_CLIENT_PROPERTIES);

            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, null);

            //这个值是上传的时候指定的NameValuePair
            NameValuePair[] pairs = storageClient.get_metadata(groupName, filePath);
            if (null != pairs && pairs.length > 0) {
                for (NameValuePair nvp : pairs) {
                    System.out.println(nvp.getName() + ":" + nvp.getValue());
                }
            }
        } finally {
            try {
                if (null != trackerServer) trackerServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDelete() throws Exception { //删除文件
        TrackerServer trackerServer = null;

        try {
            String groupName = "group1";
            String filePath = dfsFileName;
            ClientGlobal.initByProperties(FASTDFS_CLIENT_PROPERTIES);

            TrackerClient tracker = new TrackerClient();
            trackerServer = tracker.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, null);
            int i = storageClient.delete_file(groupName, filePath);
            System.out.println(i == 0 ? "删除成功" : "删除失败:" + i);
        } finally {
            try {
                if (null != trackerServer) trackerServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

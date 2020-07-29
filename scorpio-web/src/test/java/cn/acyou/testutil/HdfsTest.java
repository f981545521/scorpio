package cn.acyou.testutil;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author youfang
 * @version [1.0.0, 2020-7-29 下午 10:12]
 **/
public class HdfsTest {
    //配置链接虚拟机的IP
    public static final String HDFS_PATH = "hdfs://192.168.1.102:9000";
    //hdfs文件系统
    FileSystem fileSystem = null;
    //获取环境对象
    Configuration configuration = null;

    /**
     * 新建目录
     *
     * @throws IOException
     */
    @Test
    public void mkdir() throws IOException {
        //hadoop fs -ls /hdfsapi/test
        fileSystem.mkdirs(new Path("/hdfsapi/test"));
        System.out.println("ok");
    }


    /**
     * 创建文件
     *
     * @throws IOException
     */
    @Test
    public void create() throws IOException {
        //创建文件
        FSDataOutputStream outputStream = fileSystem.create(new Path("/hdfsapi/test/l.txt"));
        outputStream.write("hello hadoop".getBytes());
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 打印
     *
     * @throws IOException
     */
    @Test
    public void cat() throws IOException {
        FSDataInputStream inputStream = fileSystem.open(new Path("/hdfsapi/test/b.txt"));
        IOUtils.copyBytes(inputStream, System.out, 1024);
        inputStream.close();
    }

    /**
     * 重命名
     *
     * @throws IOException
     */
    @Test
    public void rename() throws IOException {
        Path oldPath = new Path("/hdfsapi/test/a.txt");
        Path newPath = new Path("/hdfsapi/test/b.txt");
        Assert.assertTrue(fileSystem.rename(oldPath, newPath));
    }

    /**
     * 上传本地文件到hdfs
     *
     * @throws Exception
     */
    @Test
    public void copyFromLocalFile() throws Exception {
        Path oldPath = new Path("D:\\temp\\hdfsimage.jpg");
        Path newPath = new Path("/hdfsapi/test");
        fileSystem.copyFromLocalFile(oldPath, newPath);
    }

    /**
     * 上传本地文件到hdfs 带进度条
     *
     * @throws Exception
     */
    @Test
    public void copyFromLocalFileWithProgress() throws Exception {
//        Path oldPath = new Path("E:\\ideaIU-2018.1.exe");
//        Path newPath = new Path("/hdfsapi/test");
//        fileSystem.copyFromLocalFile(oldPath,newPath);

        InputStream in = new BufferedInputStream(
                new FileInputStream(
                        new File("E:\\ideaIU-2018.1.exe")));

        FSDataOutputStream outputStream = fileSystem.create(new Path("/hdfsapi/test/ideaIU.exe"), new Progressable() {
            @Override
            public void progress() {
                System.out.print(".");//带进度提醒信息
            }
        });

        IOUtils.copyBytes(in, outputStream, 4096);

    }

    /**
     * 下载文件到本地
     *
     * @throws Exception
     */
    @Test
    public void copyToLocalFile() throws Exception {
//        获取输入流
        InputStream in = fileSystem.open(new Path("/hdfsapi/test/lll.txt"));
//        获取输出流
        OutputStream outputStream = new FileOutputStream(new File("E:\\apache-activemq-5.15.3-bin/a.txt"));
        IOUtils.copyBytes(in, outputStream, configuration);
        in.close();
        outputStream.close();
    }

    /**
     * 列出所有的文件
     */
    @Test
    public void listFiles() throws Exception {
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/hdfsapi/test"));
        for (FileStatus fileStatus : fileStatuses) {
            String isDir = fileStatus.isDirectory() ? "文件夹" : "文件";
            short relication = fileStatus.getReplication();
            long len = fileStatus.getLen();
            String path = fileStatus.getPath().toString();
            System.out.println(isDir + ":" + relication + ":" + len + ":" + path);
        }
    }


    @Test
    public void upset() throws URISyntaxException, IOException {
        //上传文件,路径大家记得改一下
        String file = "E:/hadoopTest/output/test.txt";
        InputStream inputStream = new FileInputStream(new File(file));
        FSDataOutputStream outputStream = fileSystem.create(new Path("/hdfsapi/park/aaa.txt"));
        IOUtils.copyBytes(inputStream, outputStream, configuration);
//        fileSystem.copyFromLocalFile();底层是调用了IOUtils.copyBytes()
    }

    @Test
    public void download() throws URISyntaxException, IOException {
//        获取输入流
        InputStream in = fileSystem.open(new Path("/park/2.txt"));
//        获取输出流
        String file = "E:/hadoopTest/output/test.txt";
        OutputStream outputStream = new FileOutputStream(new File(file));
        IOUtils.copyBytes(in, outputStream, configuration);
        in.close();
        outputStream.close();
    }

    @Test
    public void demo1() throws URISyntaxException, IOException {
        configuration = new Configuration();
        fileSystem = (FileSystem) FileSystem.get(new URI(HDFS_PATH), configuration);
//        1、在hdfs创建目录teacher。
//        2、在teacher目录下上传文件score.txt。
        String file = "E:/hadoopTest/score.txt";
        InputStream inputStream = new FileInputStream(new File(file));
        OutputStream outputStream = fileSystem.create(new Path("/hdfs/teacher/score.txt"));
        IOUtils.copyBytes(inputStream, outputStream, configuration);
//        3、在hdfs创建目录student，并在student目录下创建新目录Tom、LiMing、Jerry.
        fileSystem.mkdirs(new Path("/hdfs/student/Tom"));
        fileSystem.mkdirs(new Path("/hdfs/student/LiMing"));
        fileSystem.mkdirs(new Path("/hdfs/student/Jerry"));
//        4、在Tom目录下上传information.txt,同时上传到LiMing、Jerry目录下。
        file = "E:/hadoopTest/information.txt";
        inputStream = new FileInputStream(new File(file));
        outputStream = fileSystem.create(new Path("/hdfs/student/Tom/information.txt"));
        IOUtils.copyBytes(inputStream, outputStream, configuration);
//        file = "E:/hadoopTest/information.txt";
        inputStream = new FileInputStream(new File(file));

        outputStream = fileSystem.create(new Path("/hdfs/student/LiMing/information.txt"));
        IOUtils.copyBytes(inputStream, outputStream, configuration);
//        file = "E:/hadoopTest/information.txt";
        inputStream = new FileInputStream(new File(file));

        outputStream = fileSystem.create(new Path("/hdfs/student/Jerry/information.txt"));
        IOUtils.copyBytes(inputStream, outputStream, configuration);
//        5、将student重命名为MyStudent。
        fileSystem.rename(new Path("/hdfs/student"), new Path("/hdfs/MyStudent"));
//        6、将Tom下的information.txt下载到E:/tom目录中
        file = "E:/tom";
        inputStream = fileSystem.open(new Path("/hdfs/MyStudent/Tom/information.txt"));
        outputStream = new FileOutputStream(new File(file));
        IOUtils.copyBytes(inputStream, outputStream, configuration);
//        7、将teacher下的score.txt也下载到此目录
        inputStream = fileSystem.open(new Path("/hdfs/teacher/score.txt"));
        outputStream = new FileOutputStream(new File(file));
        IOUtils.copyBytes(inputStream, outputStream, configuration);
//        8、删除hdfs中的Tom、LiMing目录
        fileSystem.delete(new Path("/hdfs/Tom"), true);
        fileSystem.delete(new Path("/hdfs/LiMing"), true);
        inputStream.close();
        outputStream.close();
    }


    @Before
    public void before() throws Exception {
        Configuration configuration = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "hadoop");
        System.out.println("init success");
    }


    @After
    public void after() throws Exception {
        if (fileSystem != null) {
            fileSystem.close();
        }
    }


}

package cn.acyou.scorpio.test.informal.testutil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author youfang
 * @version [1.0.0, 2020/12/18]
 **/
public class SocketTest {

    public static void main(String[] args) throws Exception {
        Socket socketClient = new Socket("192.168.1.120", 9100);
        boolean connected = socketClient.isConnected();

        if (connected) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("开始监听消息");
                        while (true){
                            InputStream inputStream = socketClient.getInputStream();
                            byte[] bytes = new byte[1024];
                            int len;
                            StringBuilder sb = new StringBuilder();
                            while ((len = inputStream.read(bytes)) != -1) {
                                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
                                sb.append(new String(bytes, 0, len,"UTF-8"));
                            }
                            System.out.println("获取了消息: " + sb);
                        }
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            Thread.sleep(1000);
            System.out.println("服务器连接成功");
            OutputStream outputStream = socketClient.getOutputStream();
            String labelCommand = getLabelCommand(
                    "切口皮肤扩张器",
                    "GK888T",
                    "标准型",
                    "20211220",
                    "528661123751231506");
            outputStream.write(labelCommand.getBytes("GBK"));
            System.out.println("消息发送成功");
        } else {
            System.out.println("服务器连接失败");
        }
        Thread.sleep(100000);

    }

    public static String getLabelCommand(String title, String spec, String model, String expiryDate, String content){
        return "I8,1,001\n" +
                "S30\n" +
                "H20\n" +
                "q400\n" +
                "Q400,16\n" +
                "N\n" +
                "T20,30,0,6,1,1,N,\""+title+"\"\n" +
                "T20,90,0,6,1,1,N,\"规格："+spec+"\"\n" +
                "T20,140,0,6,1,1,N,\"型号："+model+"\"\n" +
                "T20,180,0,6,1,1,N,\"效期："+expiryDate+"\"\n" +
                "b20,230,P,00,00,s0,c0,x4,y9,r0,l0,t0,o0,\""+content+"\"\n" +
                "T55,350,0,6,1,1,N,\""+content+"\"\n" +
                "RS0,0,0,1,0\n" +
                "RF1,0,0,10,1,\""+content+"\"\n" +
                "W1,1";
    }
}

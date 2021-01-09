package cn.acyou.scorpio.test.informal.testutil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author youfang
 * @version [1.0.0, 2020/12/18]
 **/
public class SocketTest2 {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 4; i++) {
            Socket socketClient = new Socket("192.168.1.120", 9100);
            OutputStream outputStream = socketClient.getOutputStream();
            String labelCommand = getLabelCommand(
                    "切口皮肤扩张器" + i,
                    "GK888T",
                    "标准型",
                    "20211220",
                    "528661123751231506");
            outputStream.write(labelCommand.getBytes("GBK"));
            //通过shutdownOutput高速服务器已经发送完数据，后续只能接受数据
            socketClient.shutdownOutput();
            InputStream is = socketClient.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            System.out.println("我是客户端，服务器说：" + br.readLine());
        }
    }

    public static String getLabelCommand(String title, String spec, String model, String expiryDate, String content) {
        return "I8,1,001\n" +
                "S30\n" +
                "H20\n" +
                "q400\n" +
                "Q400,16\n" +
                "N\n" +
                "T20,30,0,6,1,1,N,\"" + title + "\"\n" +
                "T20,90,0,6,1,1,N,\"规格：" + spec + "\"\n" +
                "T20,140,0,6,1,1,N,\"型号：" + model + "\"\n" +
                "T20,180,0,6,1,1,N,\"效期：" + expiryDate + "\"\n" +
                "b20,230,P,00,00,s0,c0,x4,y9,r0,l0,t0,o0,\"" + content + "\"\n" +
                "T55,350,0,6,1,1,N,\"" + content + "\"\n" +
                "RS0,0,0,1,0\n" +
                "RF1,0,0,10,1,\"" + content + "\"\n" +
                "PN1,1";
    }
}

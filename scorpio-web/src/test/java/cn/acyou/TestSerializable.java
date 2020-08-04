package cn.acyou;


import cn.acyou.test.Person;

import java.io.*;
import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2020/8/3]
 **/
public class TestSerializable {

    public static void main(String[] args) {
        Person p = new Person();
        p.setId(10);
        p.setName("feige");
        p.setAge(18);
        p.setBirth(new Date());
        writeObj(p);

        //Person p2 = readObj();
        //System.out.println(p2);
    }

    // 序列化
    public static void writeObj(Person p) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("E://1.txt"));
            //java.io.NotSerializableException: cn.acyou.test.Person
            //	at java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1184)
            //  ...
            objectOutputStream.writeObject(p);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 反序列化
    public static Person readObj() {
        Person p = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("E://1.txt"));
            try {
                p = (Person)objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

}

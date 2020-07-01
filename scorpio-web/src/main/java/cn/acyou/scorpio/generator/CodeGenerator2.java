package cn.acyou.scorpio.generator;

import com.google.common.base.CaseFormat;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 使用MySQL，根据数据库表名，生成实体类
 *
 * @author youfang
 * @version [1.0.0, 2018-06-11 下午 02:52]
 **/
public class CodeGenerator2 {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";
    /**
     * 正常情况下读取表注释时，是取不出来的。
     * 需要增加useInformationSchema=true配置
     */
    private static final String URL = "jdbc:mysql://localhost:3306/scorpio?useInformationSchema=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    /**
     * 1. 表名
     */
    private static final String TABLE_NAME = "t_depot";
    /**
     * 2. 类名文件名(下划线也会自动转换)
     */
    private static final String CLASS_NAME = convertCamelCase("depot");
    private static final String AUTHOR = "youfang";

    /**
     * 3. 生成文件路径（根据需要自行修改，文件夹不需要手动建了，自动帮你建）
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String ENTITY_FILE_PATH = PROJECT_PATH +
            "\\spdapi-mapper\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\system\\entity";
    private static final String MAPPER_INTERFACE_PATH = PROJECT_PATH +
            "\\spdapi-mapper\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\system\\mapper";
    private static final String MAPPER_XML_PATH = PROJECT_PATH +
            "\\spdapi-mapper\\src\\main\\resources\\mapper\\system";

    private static final String SERVICE_INTERFACE_PATH = PROJECT_PATH +
            "\\spdapi-service\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\service\\system";
    private static final String SERVICE_INTERFACE_IMPL_PATH = PROJECT_PATH +
            "\\spdapi-service\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\service\\system\\impl";

    private static final String CONTROLLER_PATH = PROJECT_PATH +
            "\\spdapi-web\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\controller\\system";
    /**
     * 4. 运行Main方法
     */
    public static void main(String[] args) {
        generateAll(CLASS_NAME);
    }


    //******************************************************************************************************//
    //你的实体类所在的包的位置
    private static final String ENTITY_PACKAGE_LOCATION = ENTITY_FILE_PATH.substring(
            ENTITY_FILE_PATH.indexOf("\\src\\main\\java\\") + 15).replace("\\", ".");
    private static final String MAPPER_PACKAGE = MAPPER_INTERFACE_PATH.substring(
            MAPPER_INTERFACE_PATH.indexOf("\\src\\main\\java\\") + 15).replace("\\", ".");


    private static final String SERVICE_PACKAGE_LOCATION = SERVICE_INTERFACE_PATH.substring(
            SERVICE_INTERFACE_PATH.indexOf("\\src\\main\\java\\") + 15).replace("\\", ".");
    private static final String SERVICE_IMPL_PACKAGE_LOCATION = SERVICE_INTERFACE_IMPL_PATH.substring(
            SERVICE_INTERFACE_IMPL_PATH.indexOf("\\src\\main\\java\\") + 15).replace("\\", ".");
    private static final String CONTROLLER_PACKAGE_LOCATION = CONTROLLER_PATH.substring(
            CONTROLLER_PATH.indexOf("\\src\\main\\java\\") + 15).replace("\\", ".");

    private static final String serivceInterfaceName = CLASS_NAME + "Service";

    private static Connection connection = null;
    private static StringBuilder ALL_FILED = new StringBuilder();
    private static StringBuilder ALL_ALISA_FILED = new StringBuilder();
    private static String PK_NAME = "";

    /**
     * 生成实体文件&Mapper文件
     *
     * @param className 类名
     */
    private static void generateAll(String className) {
        if (className == null || "".equals(className.trim())) {
            System.out.println("文件名不能为空");
            return;
        }
        connection = getConnections();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            //获取主键
            ResultSet pkRs = dbmd.getPrimaryKeys(null, null, TABLE_NAME);
            while (pkRs.next()) {
                PK_NAME = (String) pkRs.getObject(4);
            }

            ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                //这里干掉IF可对库里面所有表直接生成
                if (TABLE_NAME.equals(tableName)) {
                    ResultSet rs1 = dbmd.getColumns(null, "%", tableName, "%");
                    ResultSet rs2 = dbmd.getColumns(null, "%", tableName, "%");
                    //获取表信息
                    String tableRemark = "";
                    ResultSet rsTableInfo = dbmd.getTables(null, null, TABLE_NAME, new String[]{"TABLE"});
                    while (rsTableInfo.next()) {
                        //表类别(可为null)
                        String tableCat = rsTableInfo.getString("TABLE_CAT");
                        //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知
                        String tableSchemaName = rsTableInfo.getString("TABLE_SCHEM");
                        //表名
                        String tableName2 = rsTableInfo.getString("TABLE_NAME");
                        //表类型,典型的类型是 "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM"。
                        String tableType = rsTableInfo.getString("TABLE_TYPE");
                        //表备注
                        String remarks = rsTableInfo.getString("REMARKS");
                        System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName2 + " - " + tableType + " - " + remarks);
                        tableRemark = remarks;
                    }
                    //要生成文件的内容
                    List<String> mapperContentList = new ArrayList<>();
                    List<String> mapperXmlContentList = new ArrayList<>();
                    List<String> entityContentList = new ArrayList<>();

                    List<String> serviceInterfaceContentList = new ArrayList<>();
                    List<String> serviceInterfaceImplContentList = new ArrayList<>();
                    List<String> controllerContentList = new ArrayList<>();

                    mapperContentList.add("package " + MAPPER_PACKAGE + ";\r\n");
                    mapperContentList.add("\r\n");
                    mapperContentList.add("import cn.com.zhengya.framework.mapper.Mapper;\r\n");
                    mapperContentList.add("import " + ENTITY_PACKAGE_LOCATION + "." + CLASS_NAME + ";\r\n");
                    mapperContentList.add("\r\n");
                    mapperContentList.add("/**\r\n");
                    mapperContentList.add(" * " + TABLE_NAME + " Mapper\r\n");
                    mapperContentList.add(" * " + getDate() + " " + tableRemark + "\r\n");
                    mapperContentList.add(" * @author " + AUTHOR + "\r\n");
                    mapperContentList.add(" */ \r\n");
                    mapperContentList.add("public interface " + CLASS_NAME + "Mapper" + " extends Mapper<" + CLASS_NAME + "> {\r\n");
                    mapperContentList.add("\r\n");
                    mapperContentList.add("\r\n");
                    mapperContentList.add("}\r\n");
                    mapperXmlContentList.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                    mapperXmlContentList.add("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
                    mapperXmlContentList.add("<mapper namespace=\"" + MAPPER_PACKAGE + "." + CLASS_NAME + "Mapper" + "\">\r\n");
                    entityContentList.add("package " + ENTITY_PACKAGE_LOCATION + ";\r\n");
                    entityContentList.add("\r\n");
                    entityContentList.add("import java.io.Serializable;\r\n");
                    entityContentList.add("import javax.persistence.Id;\r\n");
                    entityContentList.add("import javax.persistence.Column;\r\n");
                    entityContentList.add("import javax.persistence.Table;\r\n");
                    entityContentList.add("import javax.persistence.GeneratedValue;\r\n");
                    entityContentList.add("import java.util.Date;\r\n");
                    entityContentList.add("\r\n");
                    entityContentList.add("/**\r\n");
                    entityContentList.add(" * " + TABLE_NAME + " 实体类\r\n");
                    entityContentList.add(" * " + getDate() + " " + tableRemark + "\r\n");
                    entityContentList.add(" * @author " + AUTHOR + "\r\n");
                    entityContentList.add(" */ \r\n");
                    entityContentList.add("@Table(name = \"" + TABLE_NAME + "\")\r\n");
                    entityContentList.add("public class " + className + " implements Serializable{\r\n");
                    entityContentList.add("\r\n    private static final long serialVersionUID = " + new Random().nextLong() + "L;\r\n");
                    System.out.println(TABLE_NAME + " 生成中...");
                    mapperXmlContentList.add("\r\n    <resultMap id=\"Base_Result_Map\" type=\"" + ENTITY_PACKAGE_LOCATION + "." + CLASS_NAME + "\">");
                    while (rs1.next()) {
                        String typeName = rs1.getString("TYPE_NAME");
                        String name = rs1.getString("COLUMN_NAME");
                        if (ALL_FILED.length() <= 0) {
                            ALL_FILED.append(name);
                        } else {
                            ALL_FILED.append(", ").append(name);
                        }
                        if (ALL_ALISA_FILED.length() <= 0) {
                            ALL_ALISA_FILED.append("t.").append(name);
                        } else {
                            ALL_ALISA_FILED.append(", t.").append(name);
                        }
                        String remark = rs1.getString("REMARKS");
                        String result = "result";
                        if (remark.contains("主键")) {
                            result = "id";
                        }
                        mapperXmlContentList.add("\r\n        <" + result + " column=\"" + name + "\" jdbcType=\"" + typeName2JDBCType(typeName) + "\" property=\"" + convertcamelCase(name) + "\"/>");
                        createEntityField(entityContentList, typeName, name, remark);
                    }
                    mapperXmlContentList.add("\r\n    </resultMap>");
                    //提供Get和Set方法
                    entityContentList.add("\r\n");
                    while (rs2.next()) {
                        String name = rs2.getString("COLUMN_NAME");
                        String type = rs2.getString("TYPE_NAME");
                        createEntityGetAndSetMethod(entityContentList, type, name);
                    }
                    entityContentList.add("}\r\n");
                    mapperXmlContentList.add("\r\n");
                    //获取所有字段
                    mapperXmlContentList.add("\r\n    <sql id=\"Base_Column_List\">");
                    mapperXmlContentList.add("\r\n        " + ALL_FILED.toString());
                    mapperXmlContentList.add("\r\n    </sql>");
                    mapperXmlContentList.add("\r\n");
                    //获取所有字段(别名)
                    mapperXmlContentList.add("\r\n    <sql id=\"Alisa_Column_List\">");
                    mapperXmlContentList.add("\r\n        " + ALL_ALISA_FILED.toString());
                    mapperXmlContentList.add("\r\n    </sql>");
                    mapperXmlContentList.add("\r\n");
                    mapperXmlContentList.add("\r\n");
                    mapperXmlContentList.add("    <sql id=\"IF_Test_SET\">\r\n");
                    mapperXmlContentList.add("           <set>\r\n");
                    StringBuilder sb = generateIfTestSentence("it.");
                    mapperXmlContentList.add(sb.toString());
                    mapperXmlContentList.add("           </set>\r\n");
                    mapperXmlContentList.add("        </foreach>\r\n");
                    mapperXmlContentList.add("    </sql>\r\n");
                    mapperXmlContentList.add("\r\n");
                    mapperXmlContentList.add("\r\n");
                    mapperXmlContentList.add("</mapper>");

                    //service/serviceImpl and controller
                    serviceInterfaceContentList.add("package " + SERVICE_PACKAGE_LOCATION + ";\r\n");
                    serviceInterfaceContentList.add("\r\n");
                    serviceInterfaceContentList.add("import cn.com.zhengya.framework.service.Service;\r\n");
                    serviceInterfaceContentList.add("import "+ ENTITY_PACKAGE_LOCATION + "." + CLASS_NAME +";\r\n");
                    serviceInterfaceContentList.add("\r\n");
                    serviceInterfaceContentList.add("/**\r\n");
                    serviceInterfaceContentList.add(" * " + TABLE_NAME + " SERVICE\r\n");
                    serviceInterfaceContentList.add(" * " + getDate() + " " + tableRemark + " 服务\r\n");
                    serviceInterfaceContentList.add(" * @author " + AUTHOR + "\r\n");
                    serviceInterfaceContentList.add(" */ \r\n");
                    serviceInterfaceContentList.add("public interface " + CLASS_NAME + "Service" + " extends Service<" + CLASS_NAME + "> {\r\n");
                    serviceInterfaceContentList.add("\r\n");
                    serviceInterfaceContentList.add("\r\n");
                    serviceInterfaceContentList.add("}\r\n");

                    serviceInterfaceImplContentList.add("package " + SERVICE_IMPL_PACKAGE_LOCATION + ";\r\n");
                    serviceInterfaceImplContentList.add("\r\n");
                    serviceInterfaceImplContentList.add("import lombok.extern.slf4j.Slf4j;\r\n");
                    serviceInterfaceImplContentList.add("import org.springframework.stereotype.Service;\r\n");
                    serviceInterfaceImplContentList.add("import cn.com.zhengya.framework.service.ServiceImpl;\r\n");
                    serviceInterfaceImplContentList.add("import "+ ENTITY_PACKAGE_LOCATION + "." + CLASS_NAME +";\r\n");
                    serviceInterfaceImplContentList.add("import "+ MAPPER_PACKAGE + "." + CLASS_NAME +"Mapper;\r\n");
                    serviceInterfaceImplContentList.add("import "+ SERVICE_PACKAGE_LOCATION + "." + CLASS_NAME +"Service;\r\n");
                    serviceInterfaceImplContentList.add("\r\n");
                    serviceInterfaceImplContentList.add("/**\r\n");
                    serviceInterfaceImplContentList.add(" * " + TABLE_NAME + " SERVICE\r\n");
                    serviceInterfaceImplContentList.add(" * " + getDate() + " " + tableRemark + " 服务\r\n");
                    serviceInterfaceImplContentList.add(" * @author " + AUTHOR + "\r\n");
                    serviceInterfaceImplContentList.add(" */ \r\n");
                    serviceInterfaceImplContentList.add("@Slf4j\r\n");
                    serviceInterfaceImplContentList.add("@Service\r\n");
                    serviceInterfaceImplContentList.add("public class " + CLASS_NAME + "ServiceImpl" + " extends ServiceImpl<" + CLASS_NAME + "Mapper, " + CLASS_NAME + "> implements "+CLASS_NAME+"Service {\r\n");
                    serviceInterfaceImplContentList.add("\r\n");
                    serviceInterfaceImplContentList.add("\r\n");
                    serviceInterfaceImplContentList.add("}\r\n");

                    controllerContentList.add("package " + CONTROLLER_PACKAGE_LOCATION + ";\r\n");
                    controllerContentList.add("\r\n");
                    controllerContentList.add("import io.swagger.annotations.Api;\r\n");
                    controllerContentList.add("import lombok.extern.slf4j.Slf4j;\r\n");
                    controllerContentList.add("import org.springframework.beans.factory.annotation.Autowired;\r\n");
                    controllerContentList.add("import org.springframework.web.bind.annotation.RequestMapping;\r\n");
                    controllerContentList.add("import org.springframework.web.bind.annotation.RestController;\r\n");
                    controllerContentList.add("import "+ SERVICE_PACKAGE_LOCATION + "." + CLASS_NAME +"Service;\r\n");
                    controllerContentList.add("\r\n");
                    controllerContentList.add("/**\r\n");
                    controllerContentList.add(" * " + TABLE_NAME + " SERVICE\r\n");
                    controllerContentList.add(" * " + getDate() + " " + tableRemark + " 接口\r\n");
                    controllerContentList.add(" * @author " + AUTHOR + "\r\n");
                    controllerContentList.add(" */ \r\n");
                    controllerContentList.add("@Slf4j\r\n");
                    controllerContentList.add("@RestController\r\n");
                    controllerContentList.add("@RequestMapping(\"/"+convertcamelCase(className)+"\")\r\n");
                    controllerContentList.add("@Api(value = \""+tableRemark+"\", description = \""+tableRemark+"\", tags = \""+tableRemark+"接口\")\r\n");
                    controllerContentList.add("public class " + CLASS_NAME +"Controller {\r\n");
                    controllerContentList.add("    @Autowired\r\n");
                    controllerContentList.add("    private "+serivceInterfaceName+" "+convertcamelCase(serivceInterfaceName)+";\r\n");
                    controllerContentList.add("\r\n");
                    controllerContentList.add("}\r\n");

                    //Mapper.java文件
                    writeMapperInterfaceFile(mapperContentList);
                    //Mapper.xml文件
                    writeMapperXmlFile(mapperXmlContentList);
                    //Entity.java 文件
                    writeEntityFile(entityContentList);
                    //Service.java文件
                    writeServiceInterfaceFile(serviceInterfaceContentList);
                    //ServiceImpl.java文件
                    writeServiceInterfaceImplFile(serviceInterfaceImplContentList);
                    //Controller.java文件
                    writeControllerFile(controllerContentList);

                    System.out.println("=====   生成成功！   =====");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeEntityFile(List<String> entityContentList) throws Exception {
        new File(ENTITY_FILE_PATH).mkdirs();
        File javaDirectory = new File(String.format("%s\\%s.java", ENTITY_FILE_PATH, CLASS_NAME));
        if (!javaDirectory.exists()) {
            javaDirectory.createNewFile();
        }
        FileWriter javaFw = new FileWriter(javaDirectory);
        PrintWriter javaPw = new PrintWriter(javaFw);
        for (String entityContent : entityContentList) {
            javaPw.write(entityContent);
        }
        javaPw.flush();
        javaPw.close();
    }

    private static void writeMapperXmlFile(List<String> mapperXmlContentList) throws Exception {
        new File(MAPPER_XML_PATH).mkdirs();
        File xmlDirectory = new File(String.format("%s\\%sMapper.xml", MAPPER_XML_PATH, CLASS_NAME));
        FileWriter xmlFw = new FileWriter(xmlDirectory);
        PrintWriter xmlPw = new PrintWriter(xmlFw);
        for (String mapperXmlContent : mapperXmlContentList) {
            xmlPw.write(mapperXmlContent);
        }
        xmlPw.flush();
        xmlPw.close();
    }

    private static void writeMapperInterfaceFile(List<String> mapperContentList) throws Exception {
        new File(MAPPER_INTERFACE_PATH).mkdirs();
        File mapperDirectory = new File(MAPPER_INTERFACE_PATH + "\\" + CLASS_NAME + "Mapper" + ".java");
        FileWriter mapperFw = new FileWriter(mapperDirectory);
        PrintWriter mapperPw = new PrintWriter(mapperFw);
        for (String mapperContent : mapperContentList) {
            mapperPw.write(mapperContent);
        }
        mapperPw.flush();
        mapperPw.close();
    }
    private static void writeServiceInterfaceFile(List<String> serviceInterfaceContentList) throws Exception {
        new File(SERVICE_INTERFACE_PATH).mkdirs();
        File directory = new File(SERVICE_INTERFACE_PATH + "\\" + CLASS_NAME + "Service" + ".java");
        FileWriter fw = new FileWriter(directory);
        PrintWriter pw = new PrintWriter(fw);
        for (String serviceInterfaceContent : serviceInterfaceContentList) {
            pw.write(serviceInterfaceContent);
        }
        pw.flush();
        pw.close();
    }
    private static void writeServiceInterfaceImplFile(List<String> serviceInterfaceImplContentList) throws Exception {
        new File(SERVICE_INTERFACE_IMPL_PATH).mkdirs();
        File directory = new File(SERVICE_INTERFACE_IMPL_PATH + "\\" + CLASS_NAME + "ServiceImpl" + ".java");
        FileWriter fw = new FileWriter(directory);
        PrintWriter pw = new PrintWriter(fw);
        for (String content : serviceInterfaceImplContentList) {
            pw.write(content);
        }
        pw.flush();
        pw.close();
    }

    private static void writeControllerFile(List<String> controllerContentList) throws Exception {
        new File(CONTROLLER_PATH).mkdirs();
        File directory = new File(CONTROLLER_PATH + "\\" + CLASS_NAME + "Controller" + ".java");
        FileWriter fw = new FileWriter(directory);
        PrintWriter pw = new PrintWriter(fw);
        for (String content : controllerContentList) {
            pw.write(content);
        }
        pw.flush();
        pw.close();
    }

    /**
     * 下划线 转 大写类名
     *
     * @param filedName 字段名
     * @return 大写类名
     */
    private static String convertCamelCase(String filedName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, filedName.toLowerCase());
    }

    /**
     * 下划线 转 小写方法名
     *
     * @param filedName 字段名
     * @return 小写方法名
     */
    private static String convertcamelCase(String filedName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, filedName);
    }


    /**
     * 生成属性字段
     */
    private static void createEntityField(List<String> entityContentList, String typeName, String name, String remark) {
        String type = sqlType2JavaType(typeName);
        if (type.equalsIgnoreCase("BigDecimal")) {
            if (!entityContentList.contains("import java.math.BigDecimal;\r\n")) {
                entityContentList.add(7, "import java.math.BigDecimal;\r\n");
            }
        }
        if (remark != null && !"".equals(remark)) {
            entityContentList.add("\t/**\r\n");
            entityContentList.add("\t * " + remark + "\r\n");
            entityContentList.add("\t */\r\n");
        } else {
            entityContentList.add("\t//" + name + "\r\n");
        }
        if (!PK_NAME.equals("") && PK_NAME.equals(name)) {
            entityContentList.add("    @Id\r\n");
            entityContentList.add("    @GeneratedValue(generator = \"JDBC\")\r\n");
        }
        entityContentList.add("    @Column(name = \"" + name + "\")\r\n");
        if ("Date".equals(type)) {
            if (!entityContentList.contains("import com.fasterxml.jackson.annotation.JsonFormat;\r\n")) {
                entityContentList.add(7, "import com.fasterxml.jackson.annotation.JsonFormat;\r\n");
            }
            entityContentList.add("    @JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\r\n");
        }
        entityContentList.add("    private " + type + "	" + convertcamelCase(name) + ";\r\n");
    }

    /**
     * 生成属性字段的Get/Set方法
     */
    private static void createEntityGetAndSetMethod(List<String> entityContentList, String type, String name) {
        entityContentList.add("    public void set" + convertCamelCase(name) + "(" + sqlType2JavaType(type) + " " + convertcamelCase(name) + "){\r\n");
        entityContentList.add("        this." + convertcamelCase(name) + " = " + convertcamelCase(name) + ";\r\n");
        entityContentList.add("    }\r\n");
        entityContentList.add("    public " + sqlType2JavaType(type) + " get" + convertCamelCase(name) + "(){\r\n");
        entityContentList.add("        return " + convertcamelCase(name) + ";\r\n");
        entityContentList.add("    }\r\n");
        entityContentList.add("\r\n");
    }


    // 创建数据库连接
    private static Connection getConnections() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 判断属性类型 SQL类型->Java类型
     *
     * @param sqlType SQL类型
     * @return Java类型
     */
    private static String sqlType2JavaType(String sqlType) {
        String str = null;
        if (sqlType.equalsIgnoreCase("bit")) {
            str = "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            str = "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            str = "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            str = "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            str = "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            str = "float";
        } else if (sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney") || sqlType.equalsIgnoreCase("double")) {
            str = "Double";
        } else if (sqlType.equalsIgnoreCase("decimal")) {
            str = "BigDecimal";
        } else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text") || sqlType.equalsIgnoreCase("longtext")) {
            str = "String";
        } else if (sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")) {
            str = "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            str = "Blod";
        }
        return str;
    }

    /**
     * MySQL type -> mybatis jdbc type
     * https://blog.csdn.net/benben683280/article/details/78798901
     *
     * @param typeName 类型名
     * @return jdbc type
     */
    private static String typeName2JDBCType(String typeName) {
        if (typeName.equalsIgnoreCase("DATETIME")) {
            return "TIMESTAMP";
        }
        if (typeName.equalsIgnoreCase("INT")) {
            return "INTEGER";
        }
        if (typeName.equalsIgnoreCase("LONGTEXT")) {
            return "VARCHAR";
        }
        if (typeName.equalsIgnoreCase("TEXT")) {
            return "VARCHAR";
        }
        return typeName.toUpperCase();
    }

    /**
     * 获取格式化后的时间
     *
     * @return 时间
     */
    private static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 生成Mapper的If test 语句
     *
     * @param alisa 别名
     * @return If test 语句
     */
    private static StringBuilder generateIfTestSentence(String alisa) {
        connection = getConnections();
        StringBuilder sb = new StringBuilder();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (TABLE_NAME.equals(tableName)) {
                    ResultSet rs1 = dbmd.getColumns(null, "%", tableName, "%");
                    //获取表信息
                    while (rs1.next()) {
                        //列名称
                        String name = rs1.getString("COLUMN_NAME");

                        if (!"".equals(PK_NAME) && name.equals(PK_NAME)) {
                            //跳过主键
                            continue;
                        }

                        //Java canmel 命名
                        String javaName = convertcamelCase(name);
                        String typeName = rs1.getString("TYPE_NAME");
                        String type = sqlType2JavaType(typeName);
                        String stringSqlType = "";
                        if ("String".equals(type)) {
                            stringSqlType = " and " + alisa + javaName + "!=''";
                        }
                        String iftest = "<if test=\"" + alisa + javaName + "!=null" + stringSqlType + "\">" + name + " = #{" + alisa + javaName + "},</if>\r\n";
                        sb.append("                " + iftest);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sb;
        }
    }
}



package cn.acyou.scorpio.generator;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
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
 * @version [1.0.0, 2020/09/01]
 **/
public class CodeGenerator3 {

    /**
     * 1。 数据库连接
     */
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    private static final String URL = "jdbc:mysql://192.168.1.92:13306/001kaifa?useInformationSchema=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false";

    private static final List<DbTables> WAIT_GENERATE_TABLES = Lists.newArrayList();

    /*
     * 2. 需要生成的表
     */
    static {
        //需要生成的表：                                   表名        类名文件名(下划线也会自动转换)
        //WAIT_GENERATE_TABLES.add(new DbTables("b_product_sku", convertCamelCase("product_sku")));
        WAIT_GENERATE_TABLES.add(new DbTables("r_processing_record", convertCamelCase("processing_record")));
        WAIT_GENERATE_TABLES.add(new DbTables("r_processing_record_detail", convertCamelCase("processing_record_detail")));
    }

    /**
     * 3. 作者，文件所在模块与子模块
     *
     * MODULE_PACKAGE="base"    SUB_MODULE_PACKAGE="sku" will create in "/base/../sku" directory
     * MODULE_PACKAGE="receipt" SUB_MODULE_PACKAGE = ""  will create in "/receipt/.."  directory
     */
    private static final String AUTHOR = "youfang";//作者                           @NotNull
    private static final String MODULE_PACKAGE = "goods";//模块名 非空            @NotNull
    private static final String SUB_MODULE_PACKAGE = "";//子模块名 可为空 @Nullable
    /**
     * 4. 运行Main方法，可以控制不生成指定文件
     */
    public static void main(String[] args) {
        for (DbTables dbTable: WAIT_GENERATE_TABLES){
            generateFiles(dbTable, true, true, true, true, true);
        }
    }


    //******************************************************************************************************//

    /**
     * 生成文件的路径（根据项目需要自行修改，文件夹不需要手动建了，自动帮你建）
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String ENTITY_FILE_PATH ;
    private static final String MAPPER_INTERFACE_PATH;
    private static final String MAPPER_XML_PATH;

    private static final String SERVICE_INTERFACE_PATH;
    private static final String SERVICE_INTERFACE_IMPL_PATH;
    private static final String CONTROLLER_PATH;

    private static final String SO_PATH;
    private static final String VO_PATH;

    static {
        //处理生成文件路径
        if (MODULE_PACKAGE == null || "".equals(MODULE_PACKAGE.trim())){
            throw new RuntimeException("模块名不能为空");
        }
        //子包
        if (SUB_MODULE_PACKAGE != null && !SUB_MODULE_PACKAGE.isEmpty()){
            ENTITY_FILE_PATH = PROJECT_PATH +               "\\spdapi-mapper\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\"+ MODULE_PACKAGE +"\\entity\\" + SUB_MODULE_PACKAGE;
            MAPPER_INTERFACE_PATH = PROJECT_PATH +          "\\spdapi-mapper\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\"+ MODULE_PACKAGE +"\\mapper\\" + SUB_MODULE_PACKAGE;
            MAPPER_XML_PATH = PROJECT_PATH +                "\\spdapi-mapper\\src\\main\\resources\\mapper\\" + MODULE_PACKAGE + "\\" + SUB_MODULE_PACKAGE;
            SERVICE_INTERFACE_PATH = PROJECT_PATH +         "\\spdapi-service\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\service\\" + MODULE_PACKAGE + "\\" + SUB_MODULE_PACKAGE;
            SERVICE_INTERFACE_IMPL_PATH = PROJECT_PATH +    "\\spdapi-service\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\service\\"+ MODULE_PACKAGE +"\\"+ SUB_MODULE_PACKAGE +"\\impl";
            CONTROLLER_PATH = PROJECT_PATH +                "\\spdapi-web\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\controller\\" + MODULE_PACKAGE + "\\" + SUB_MODULE_PACKAGE;
            SO_PATH = PROJECT_PATH +                        "\\spdapi-dto\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\dto\\" + MODULE_PACKAGE + "\\" + SUB_MODULE_PACKAGE + "\\req";
            VO_PATH = PROJECT_PATH +                        "\\spdapi-dto\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\dto\\" + MODULE_PACKAGE + "\\" + SUB_MODULE_PACKAGE + "\\vo";
        }else {
            ENTITY_FILE_PATH = PROJECT_PATH +               "\\spdapi-mapper\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\"+ MODULE_PACKAGE +"\\entity";
            MAPPER_INTERFACE_PATH = PROJECT_PATH +          "\\spdapi-mapper\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\"+ MODULE_PACKAGE +"\\mapper";
            MAPPER_XML_PATH = PROJECT_PATH +                "\\spdapi-mapper\\src\\main\\resources\\mapper\\" + MODULE_PACKAGE;
            SERVICE_INTERFACE_PATH = PROJECT_PATH +         "\\spdapi-service\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\service\\" + MODULE_PACKAGE ;
            SERVICE_INTERFACE_IMPL_PATH = PROJECT_PATH +    "\\spdapi-service\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\service\\"+ MODULE_PACKAGE +"\\impl";
            CONTROLLER_PATH = PROJECT_PATH +                "\\spdapi-web\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\controller\\" + MODULE_PACKAGE ;
            SO_PATH = PROJECT_PATH +                        "\\spdapi-dto\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\dto\\" + MODULE_PACKAGE + "\\req";
            VO_PATH = PROJECT_PATH +                        "\\spdapi-dto\\src\\main\\java\\cn\\com\\zhengya\\spdapi\\dto\\" + MODULE_PACKAGE + "\\vo";
        }
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

    private static final String SO_PACKAGE_LOCATION = SO_PATH.substring(
            SO_PATH.indexOf("\\src\\main\\java\\") + 15).replace("\\", ".");
    private static final String VO_PACKAGE_LOCATION = VO_PATH.substring(
            VO_PATH.indexOf("\\src\\main\\java\\") + 15).replace("\\", ".");

    private static Connection connection = null;
    private static String TABLE_NAME = null;
    private static String CLASS_NAME = null;
    private static StringBuilder ALL_FILED = null;
    private static StringBuilder ALL_ALISA_FILED = null;
    private static String PK_NAME = "";

    /**
     * 生成所有文件入口
     *
     * @param dbTables       数据库表
     * @param entityFile     实体文件
     * @param mapperFiles    映射文件
     * @param serviceFiles   服务文件
     * @param controllerFile 控制器文件
     * @param soAndVoFile    So和Vo文件
     */
    private static void generateFiles(DbTables dbTables, boolean entityFile, boolean mapperFiles, boolean serviceFiles, boolean controllerFile, boolean soAndVoFile) {
        TABLE_NAME = dbTables.getTableName();
        CLASS_NAME = dbTables.getClassName();
        ALL_FILED = new StringBuilder();
        ALL_ALISA_FILED = new StringBuilder();
        connection = getConnections();

        if (CLASS_NAME == null || "".equals(CLASS_NAME.trim())) {
            throw new RuntimeException("文件名不能为空");
        }

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
                        System.out.println(tableCat + /*" - " + tableSchemaName +*/ " - " + tableName2 + " - " + tableType + " - " + remarks);
                        tableRemark = remarks;
                    }
                    //要生成文件的内容
                    List<String> mapperContentList = new ArrayList<>();
                    List<String> mapperXmlContentList = new ArrayList<>();
                    List<String> entityContentList = new ArrayList<>();

                    List<String> serviceInterfaceContentList = new ArrayList<>();
                    List<String> serviceInterfaceImplContentList = new ArrayList<>();
                    List<String> controllerContentList = new ArrayList<>();

                    List<String> soContentList = new ArrayList<>();
                    List<String> voContentList = new ArrayList<>();

                    soContentList.add("package " + SO_PACKAGE_LOCATION + ";\r\n");
                    soContentList.add("\r\n");
                    soContentList.add("import cn.com.zhengya.framework.model.PageSo;\r\n");
                    soContentList.add("import lombok.Data;\r\n");
                    soContentList.add("import lombok.EqualsAndHashCode;\r\n");
                    soContentList.add("\r\n");
                    soContentList.add("/**\r\n");
                    soContentList.add(" * " + tableRemark + " 列表查询参数\r\n");
                    soContentList.add(" * @version [1.0.0, " + getDate() + "] \r\n");
                    soContentList.add(" * @author " + AUTHOR + "\r\n");
                    soContentList.add(" */ \r\n");
                    soContentList.add("@EqualsAndHashCode(callSuper = true)\r\n");
                    soContentList.add("@Data\r\n");
                    soContentList.add("public class " + CLASS_NAME + "So extends PageSo {\r\n");
                    soContentList.add("\r\n");
                    soContentList.add("}");

                    voContentList.add("package " + VO_PACKAGE_LOCATION + ";\r\n");
                    voContentList.add("\r\n");
                    voContentList.add("import io.swagger.annotations.ApiModel;\r\n");
                    voContentList.add("import io.swagger.annotations.ApiModelProperty;\r\n");
                    voContentList.add("import lombok.Data;\r\n");
                    voContentList.add("\r\n");
                    voContentList.add("\r\n");
                    voContentList.add("/**\r\n");
                    voContentList.add(" * " + tableRemark + " Vo\r\n");
                    voContentList.add(" * @version [1.0.0, " + getDate() + "] \r\n");
                    voContentList.add(" * @author " + AUTHOR + "\r\n");
                    voContentList.add(" */ \r\n");
                    voContentList.add("@Data\r\n");
                    voContentList.add("@ApiModel\r\n");
                    voContentList.add("public class " + CLASS_NAME + "Vo {\r\n");


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
                    entityContentList.add("public class " + CLASS_NAME + " implements Serializable{\r\n");
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
                        createVoField(voContentList, typeName, name, remark);
                    }
                    mapperXmlContentList.add("\r\n    </resultMap>");
                    voContentList.add("\r\n");
                    voContentList.add("}");

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
                    mapperXmlContentList.add("    <sql id=\"If_Test_Example\">\r\n");
                    mapperXmlContentList.add("        <set>\r\n");
                    StringBuilder sb = generateIfTestSentence("it.");
                    mapperXmlContentList.add(sb.toString());
                    mapperXmlContentList.add("        </set>\r\n");
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
                    controllerContentList.add(" * " + tableRemark + " Controller\r\n");
                    controllerContentList.add(" * " + getDate() + " " + tableRemark + " 接口\r\n");
                    controllerContentList.add(" * @author " + AUTHOR + "\r\n");
                    controllerContentList.add(" */ \r\n");
                    controllerContentList.add("@Slf4j\r\n");
                    controllerContentList.add("@RestController\r\n");
                    controllerContentList.add("@RequestMapping(\"/"+convertCamelCase2camelCase(CLASS_NAME)+"\")\r\n");
                    controllerContentList.add("@Api(value = \""+tableRemark+"\", tags = \""+tableRemark+"接口\")\r\n");
                    controllerContentList.add("public class " + CLASS_NAME +"Controller {\r\n");
                    controllerContentList.add("    @Autowired\r\n");
                    controllerContentList.add("    private "+CLASS_NAME+"Service "+convertCamelCase2camelCase(CLASS_NAME)+"Service;\r\n");
                    controllerContentList.add("\r\n");
                    controllerContentList.add("}\r\n");

                    if (mapperFiles) {
                        //Mapper.java文件
                        writeMapperInterfaceFile(mapperContentList);
                        //Mapper.xml文件
                        writeMapperXmlFile(mapperXmlContentList);
                    }
                    if (entityFile){
                        //Entity.java 文件
                        writeEntityFile(entityContentList);
                    }
                    if (serviceFiles){
                        //Service.java文件
                        writeServiceInterfaceFile(serviceInterfaceContentList);
                        //ServiceImpl.java文件
                        writeServiceInterfaceImplFile(serviceInterfaceImplContentList);
                    }
                    if (controllerFile){
                        //Controller.java文件
                        writeControllerFile(controllerContentList);
                    }
                    if (soAndVoFile){
                        writeSoFile(soContentList);
                        writeVoFile(voContentList);
                    }

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
        writeFiles(javaDirectory, entityContentList);
    }

    private static void writeMapperXmlFile(List<String> mapperXmlContentList) throws Exception {
        new File(MAPPER_XML_PATH).mkdirs();
        File directory = new File(String.format("%s\\%sMapper.xml", MAPPER_XML_PATH, CLASS_NAME));
        writeFiles(directory, mapperXmlContentList);
    }

    private static void writeMapperInterfaceFile(List<String> mapperContentList) throws Exception {
        new File(MAPPER_INTERFACE_PATH).mkdirs();
        File directory = new File(MAPPER_INTERFACE_PATH + "\\" + CLASS_NAME + "Mapper" + ".java");
        writeFiles(directory, mapperContentList);
    }
    private static void writeServiceInterfaceFile(List<String> serviceInterfaceContentList) throws Exception {
        new File(SERVICE_INTERFACE_PATH).mkdirs();
        File directory = new File(SERVICE_INTERFACE_PATH + "\\" + CLASS_NAME + "Service" + ".java");
        writeFiles(directory, serviceInterfaceContentList);
    }
    private static void writeServiceInterfaceImplFile(List<String> serviceInterfaceImplContentList) throws Exception {
        new File(SERVICE_INTERFACE_IMPL_PATH).mkdirs();
        File directory = new File(SERVICE_INTERFACE_IMPL_PATH + "\\" + CLASS_NAME + "ServiceImpl" + ".java");
        writeFiles(directory, serviceInterfaceImplContentList);
    }

    private static void writeControllerFile(List<String> controllerContentList) throws Exception {
        new File(CONTROLLER_PATH).mkdirs();
        File directory = new File(CONTROLLER_PATH + "\\" + CLASS_NAME + "Controller" + ".java");
        writeFiles(directory, controllerContentList);
    }
    private static void writeSoFile(List<String> soContentList) throws Exception {
        new File(SO_PATH).mkdirs();
        File directory = new File(SO_PATH + "\\" + CLASS_NAME + "So" + ".java");
        writeFiles(directory, soContentList);
    }
    private static void writeVoFile(List<String> voContentList) throws Exception {
        new File(VO_PATH).mkdirs();
        File directory = new File(VO_PATH + "\\" + CLASS_NAME + "Vo" + ".java");
        writeFiles(directory, voContentList);
    }

    private static void writeFiles(File directory, List<String> contentList) throws Exception {
        FileWriter fw = new FileWriter(directory);
        PrintWriter pw = new PrintWriter(fw);
        for (String content : contentList) {
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
     *  大写类名 首字母小写
     *
     * @param filedName 字段名
     * @return 大写类名
     */
    private static String convertCamelCase2camelCase(String filedName) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, filedName);
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
        if ("BigDecimal".equalsIgnoreCase(type)) {
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
        if (!"".equals(PK_NAME) && PK_NAME.equals(name)) {
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
     * 生成属性字段
     */
    private static void createVoField(List<String> voContentList, String typeName, String name, String remark) {
        String type = sqlType2JavaType(typeName);
        if ("BigDecimal".equalsIgnoreCase(type)) {
            if (!voContentList.contains("import java.math.BigDecimal;\r\n")) {
                voContentList.add(6, "import java.math.BigDecimal;\r\n");
            }
        }
        voContentList.add("\r\n");
        voContentList.add("    @ApiModelProperty(\""+remark+"\")\r\n");
        if ("Date".equals(type)) {
            if (!voContentList.contains("import com.fasterxml.jackson.annotation.JsonFormat;\r\n")) {
                voContentList.add(6, "import com.fasterxml.jackson.annotation.JsonFormat;\r\n");
                voContentList.add(6, "import java.util.Date;\r\n");
            }
            voContentList.add("    @JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\r\n");
        }
        voContentList.add("    private " + type + "	" + convertcamelCase(name) + ";\r\n");
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
        if ("bit".equalsIgnoreCase(sqlType)) {
            str = "Boolean";
        } else if ("tinyint".equalsIgnoreCase(sqlType)) {
            str = "byte";
        } else if ("smallint".equalsIgnoreCase(sqlType)) {
            str = "short";
        } else if ("int".equalsIgnoreCase(sqlType)) {
            str = "Integer";
        } else if ("bigint".equalsIgnoreCase(sqlType)) {
            str = "Long";
        } else if ("float".equalsIgnoreCase(sqlType)) {
            str = "float";
        } else if ("numeric".equalsIgnoreCase(sqlType)
                || "real".equalsIgnoreCase(sqlType) || "money".equalsIgnoreCase(sqlType)
                || "smallmoney".equalsIgnoreCase(sqlType) || "double".equalsIgnoreCase(sqlType)) {
            str = "Double";
        } else if ("decimal".equalsIgnoreCase(sqlType)) {
            str = "BigDecimal";
        } else if ("varchar".equalsIgnoreCase(sqlType) || "char".equalsIgnoreCase(sqlType)
                || "nvarchar".equalsIgnoreCase(sqlType) || "nchar".equalsIgnoreCase(sqlType)
                || "text".equalsIgnoreCase(sqlType) || "longtext".equalsIgnoreCase(sqlType)) {
            str = "String";
        } else if ("date".equalsIgnoreCase(sqlType) || "datetime".equalsIgnoreCase(sqlType) || "timestamp".equalsIgnoreCase(sqlType)) {
            str = "Date";
        } else if ("image".equalsIgnoreCase(sqlType)) {
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
        if ("DATETIME".equalsIgnoreCase(typeName)) {
            return "TIMESTAMP";
        }
        if ("INT".equalsIgnoreCase(typeName)) {
            return "INTEGER";
        }
        if ("LONGTEXT".equalsIgnoreCase(typeName)) {
            return "VARCHAR";
        }
        if ("TEXT".equalsIgnoreCase(typeName)) {
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
                        sb.append("            " + iftest);
                    }
                }
            }
            return sb;
        } catch (Exception e) {
            e.printStackTrace();
            return sb;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static class DbTables implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 表名称
         */
        private String tableName;
        /**
         * 类名称
         */
        private String className;

        public DbTables(String tableName, String className) {
            this.tableName = tableName;
            this.className = className;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }
}


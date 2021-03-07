package cn.acyou.scorpio.test.informal;

import cn.acyou.scorpio.system.entity.Student;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author youfang
 * @version [1.0.0, 2021/3/7]
 **/
public class DbUtilsTest {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/scorpio?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&serverTimezone=UTC&useSSL=false";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root123";

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        QueryRunner queryRunner = new QueryRunner();

        //Step 1: Register JDBC driver
        DbUtils.loadDriver(JDBC_DRIVER);

        //Step 2: Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL, USER, PASS);

        //Step 3: Create a ResultSet Handler to handle Employee Beans
        ResultSetHandler<Student> resultHandler = new BeanHandler<>(Student.class);

        try {
            Student stu = queryRunner.query(conn, "SELECT * FROM student WHERE id=?",
                    resultHandler, "9");
            //Display values
            System.out.print("STU: " + stu);
        } finally {
            DbUtils.close(conn);
        }
    }


}

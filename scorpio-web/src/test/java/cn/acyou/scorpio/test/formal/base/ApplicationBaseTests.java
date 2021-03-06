package cn.acyou.scorpio.test.formal.base;

import cn.acyou.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/2]
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unit-test")
@Sql("/sql/initDb.sql")
public class ApplicationBaseTests extends AbstractTransactionalJUnit4SpringContextTests {

}

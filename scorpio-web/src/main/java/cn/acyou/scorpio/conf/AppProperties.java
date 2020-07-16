package cn.acyou.scorpio.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2020/7/16]
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "apps")
public class AppProperties implements Serializable {

    private String normalString;

    private String[] normalArray;

    private Map<String, String> normalMap;

    private Ent ent;

}

class Ent implements Serializable {
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

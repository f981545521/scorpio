package cn.acyou.scorpio.conf;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JianJun.Li
 * @date 2018/11/6 13:58
 **/
@Profile({"dev", "test"})
@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean
    public Docket createRestfulApi() {
        List<String> scanControllerPackageList = new ArrayList<>();
        scanControllerPackageList.add("cn.acyou.scorpio.controller");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //单包扫描
                //.apis(RequestHandlerSelectors.basePackage("cn.acyou.scorpio.controller"))
                //多包扫描
                .apis(multipleBasePackage(scanControllerPackageList))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("企业模块接口规范")
                .description("接口描述")
                .termsOfServiceUrl("termsOfServiceUrl")
                .contact(new Contact("ares", "http://www.acyou.com", "youfang@acyou.cn"))
                .version("1.0")
                .build();
    }


    /**
     * 多个包扫描
     *
     * @param scanControllerPackageList
     * @return
     */
    public static Predicate<RequestHandler> multipleBasePackage(final List<String> scanControllerPackageList) {
        return input -> declaringClass(input).transform(handlerPackage(scanControllerPackageList)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final List<String> scanControllerPackageList) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : scanControllerPackageList) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }


}

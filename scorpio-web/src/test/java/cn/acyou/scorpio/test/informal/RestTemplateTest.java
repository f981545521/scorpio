package cn.acyou.scorpio.test.informal;


import cn.acyou.framework.model.PageData;
import cn.acyou.framework.model.Result;
import cn.acyou.scorpio.system.entity.Student;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-8-27 下午 10:19]
 **/
public class RestTemplateTest {
    private RestTemplate restTemplate;

    @Before
    public void init(){
        restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

    }

    private HttpMessageConverter getStringMessageConvert(){
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    private HttpMessageConverter getJsonMessageConvert(){

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
//        fastMediaTypes.add(MediaType.APPLICATION_XML);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        fastConverter.setFastJsonConfig(fastJsonConfig);
        return fastConverter;
    }
    @Test
    public void testGet1(){
        String url = "http://localhost:8054/rest/test1";
        //get无参
        String forObject = restTemplate.getForObject(url, String.class);
        System.out.println(forObject);
        //get无参
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity.getBody());
        //Get有参
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8054/rest/test2?name={1}", String.class, "knight");
        System.out.println(responseEntity.getBody());

        System.out.println("end");
    }
    @Test
    public void testPost1(){
        String url = "http://localhost:8054/rest/testPost1";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("name", "knight");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        //Post有参
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println(response.getBody());
    }

    @Test
    public void test23(){
        String url = "http://localhost:8054/rest/testPost2";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("name", "knight");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        //Post有参
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println(response.getBody());
        //Post有参
        Result response2 = restTemplate.postForObject( url, request , Result.class );
        System.out.println(response2);

        ResponseEntity<Result> exchange = restTemplate.exchange(url, HttpMethod.POST, request, Result.class);
        System.out.println(exchange.getBody());

        // + 泛型
        ResponseEntity<Result<String>> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, request,
                        new ParameterizedTypeReference<Result<String>>() {
                        });

        System.out.println(responseEntity.getBody());
    }
    @Test
    public void test234(){
        String url = "http://localhost:8054/rest/testPost3";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("name", "knight");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // + 泛型
        ResponseEntity<Result<List<Student>>> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, request,
                        new ParameterizedTypeReference<Result<List<Student>>>() {
                        });

        System.out.println(responseEntity.getBody());
    }


    /**
     * PageData泛型
     */
    @Test
    public void test2345(){
        String url = "http://localhost:8054/rest/testPost4";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("name", "knight");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // + 泛型
        ResponseEntity<Result<PageData<Student>>> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, request,
                        new ParameterizedTypeReference<Result<PageData<Student>>>() {
                        });

        System.out.println(responseEntity.getBody());
    }

}

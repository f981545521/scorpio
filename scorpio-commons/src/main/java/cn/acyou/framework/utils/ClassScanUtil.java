package cn.acyou.framework.utils;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Spring中的注解扫描原理
 *
 * @author youfang
 * @version [1.0.0, 2020/8/10]
 **/
@Component
public class ClassScanUtil extends ClassPathScanningCandidateComponentProvider {

    /**
     * 反射获取指定注解的Class对象集合
     *
     * @param basePackage 扫描包
     * @param annClz      注解clz
     * @return {@link List<Class>}
     */
    public List<Class<?>> getClassesUseSpring(String basePackage, Class<? extends Annotation> annClz ) {
        List<Class<?>> classes = new ArrayList<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String basePackagePath = ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(basePackage));
        try {
            Resource[] resources = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + basePackagePath + "/**/*.class");
            for (Resource resource : resources) {
                MetadataReader metadataReader = super.getMetadataReaderFactory().getMetadataReader(resource);
                AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                if (annClz == null){
                    classes.add(Class.forName(annotationMetadata.getClassName()));
                }else {
                    Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
                    if (annotationTypes.contains(annClz.getName())){
                        classes.add(Class.forName(annotationMetadata.getClassName()));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }


}

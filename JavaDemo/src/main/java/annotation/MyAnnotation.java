package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://www.cnblogs.com/a591378955/p/8350499.html
 */
public class MyAnnotation {

    /**
     * 注解类
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyClassAnnotation{
        String uri();
        String desc();
    }

    /**
     * 构造方法注解
     */
    @Target(ElementType.CONSTRUCTOR)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyConstructionAnnotion{
        String uri();
        String desc();
    }

    /**
     * 方法注解
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyMethodAnnotation{
        String uri();
        String desc();
    }

    /**
     * 字段注解
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyFieldAnnotation{
        String uri();
        String desc();
    }

    /**
     * 应用于类和方法
     */
    @Target({ElementType.TYPE,ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface MyClassAndMethodAnnotation{

        public enum EnumType{util,entity,service,model}

        public EnumType classType() default EnumType.util;

        int[] arr() default {3,7,5};

        String color() default "blue";
    }
}

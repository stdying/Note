package annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@MyAnnotation.MyClassAnnotation(desc = "The Class", uri = "test")
@MyAnnotation.MyClassAndMethodAnnotation(classType = MyAnnotation.MyClassAndMethodAnnotation.EnumType.util)
public class Test {

    @MyAnnotation.MyFieldAnnotation(desc = "The Class Field", uri = "test#id")
    private String id;

    @MyAnnotation.MyConstructionAnnotion(desc = "The Class Constructor", uri = "test#constructor")
    public Test() {
    }

    public String getId() {
        return id;
    }

    @MyAnnotation.MyMethodAnnotation(desc = "The Class Method", uri = "test#setid")
    public void setId(String id) {
        this.id = id;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException {
        Class<Test> clazz = (Class<Test>) Class.forName("annotation.Test");

        //获取注解类
        MyAnnotation.MyClassAnnotation myClassAnnotation = clazz.getAnnotation(MyAnnotation.MyClassAnnotation.class);
        System.out.println(myClassAnnotation.desc() + "+" + myClassAnnotation.uri());

        //获取构造方法注解
        Constructor<Test> constructor = clazz.getConstructor(new Class[]{});//获取构造方法对象
        //构造方法上注解实例
        MyAnnotation.MyConstructionAnnotion myConstructionAnnotion =
                constructor.getAnnotation(MyAnnotation.MyConstructionAnnotion.class);

        System.out.println(myConstructionAnnotion.desc()+" + "+myConstructionAnnotion.uri());

        //方法注解
        Method method = clazz.getMethod("setId", new Class[]{String.class});
        MyAnnotation.MyMethodAnnotation myMethodAnnotation =
                method.getAnnotation(MyAnnotation.MyMethodAnnotation.class);
        System.out.println(myMethodAnnotation.desc()+" + "+myMethodAnnotation.uri());

        //获取字段注解
        Field field = clazz.getDeclaredField("id");//可以获取private修饰变量
        MyAnnotation.MyFieldAnnotation myFieldAnnotation =
                field.getAnnotation(MyAnnotation.MyFieldAnnotation.class);
        System.out.println(myFieldAnnotation.desc()+" + "+myFieldAnnotation.uri());


        MyAnnotation.MyClassAndMethodAnnotation myClassAndMethodAnnotation =
                clazz.getAnnotation(MyAnnotation.MyClassAndMethodAnnotation.class);
        System.out.println(myClassAndMethodAnnotation.classType()+" + "+
                myClassAndMethodAnnotation.color()+" + "+
                myClassAndMethodAnnotation.arr().toString());

    }

}

package test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws Exception {

        //int c = (int) invokeMethod("test.NumAdd", "add2", new Integer[]{1, 2});
        //int c = (int) invokeMethod("test.NumAdd", "add", new Integer[]{1, 2});
        //System.out.println(c);

        HashMap<Integer,String> map = new HashMap<>(12);
        map.put(1,"1");
        System.out.println(map.size());
        List<String> strings = new LinkedList<>();
        //((LinkedList<String>) strings).addLast();
    }

    public static Object invokeMethod(String claName, String methodName, Object[] args) throws Exception {

        //创建claName类实例
        Class ownerClass = Class.forName(claName);
        Object owner = ownerClass.newInstance();

        //获取方法参数的类型
        Class[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        //构建claName类的methodName方法
        Method method = ownerClass.getMethod(methodName, argsClass);

        //method对象invoke委托动态构造claName对象，并执行对应参数的methodName方法
        Object result = method.invoke(null, args);
        return result;
    }
}


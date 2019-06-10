### 步骤

- 创建class对象
- 由方法名和形参类型，创建Method对象
- Method对象的invoke方法委托动态构造对应类型的对象，执行对应参数的方法

### 对象方法
```java
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
    Object result = method.invoke(owner, args);
    return result;
}
```

### 静态方法

```java
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

        //静态方法，invoke第一参数为null
        Object result = method.invoke(null, args);
        return result;
    }
```
package designpattern.proxy.custom;

import org.springframework.util.FileCopyUtils;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * MyProxy的作用就相当于JDK的Proxy。MyProxy做了哪些事情呢？
 * <p>
 * 第一：需要根据interfaces接口构造出动态代理类需要的方法。（其实就是利用反射获取）
 * <p>
 * 第二：把动态生成的代理类（即.java文件）进行编译，生成字节码文件（即.class文件），然后利用类加载进行加载
 * <p>
 * 第三：动态代理类进行加载后，利用反射机制，通过构造方法进行实例化，并在实例化时，初始化业务Hanlder
 *
 * @author duosheng
 * @since 2019/9/16
 */
public class MyProxy {
    private static final String rt = "\r";

    public static Object newProxyInstance(MyClassLoader loader, Class<?> interfaces, MyInvocationHandler h) {
        if (h == null) {
            throw new NullPointerException();
        }
        // 根据接口构造代理类: $MyProxy0
        Method[] methods = interfaces.getMethods();
        StringBuffer proxyClassString = new StringBuffer();
        proxyClassString.append("package ")
                .append(loader.getProxyClassPackage()).append(";").append(rt)
                .append("import java.lang.reflect.Method;").append(rt)
                .append("public class $MyProxy0 implements ").append(interfaces.getName()).append("{").append(rt)
                .append("MyInvocationHandler h;").append(rt)
                .append("public $MyProxy0(MyInvocationHandler h){").append(rt).append("this.h = h;}").append(rt)
                .append(getMethodString(methods, interfaces)).append("}");

        // 写入 JAVA 文件进行编译
        String filename = loader.getDir() + File.separator + "$MyProxy0.java";
        File myProxyFile = new File(filename);
        try {
            compile(proxyClassString, myProxyFile);
            // 利用自定义的 classloader 加载
            Class $myProxy0 = loader.findClass("$MyProxy0");
            // $MyProxy0 初始化
            Constructor constructor = $myProxy0.getConstructor(MyInvocationHandler.class);
            Object o = constructor.newInstance(h);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getMethodString(Method[] methods, Class interfaces) {
        StringBuffer methodStringBuffer = new StringBuffer();
        for (Method method : methods) {
            methodStringBuffer.append("@Override \n").append("public void ").append(method.getName())
                    .append("()").append("throws Throwable{ ")
                    .append("Method method1 = ").append(interfaces.getName())
                    .append(".class.getMethod(\"").append(method.getName())
                    .append("\", new Class[]{});")
                    .append("this.h.invoke(this, method1, null);}").append(rt);
        }
        return methodStringBuffer.toString();
    }

    private static void compile(StringBuffer proxyClassString, File myProxyFile) throws IOException {
        FileCopyUtils.copy(proxyClassString.toString().getBytes(), myProxyFile);
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable javaFileObjects = standardJavaFileManager.getJavaFileObjects(myProxyFile);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, null, null, null, javaFileObjects);
        task.call();
        standardJavaFileManager.close();
    }
}

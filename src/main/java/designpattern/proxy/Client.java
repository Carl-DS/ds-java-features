package designpattern.proxy;

import java.lang.reflect.Proxy;

/**
 * @author duosheng
 * @since 2019/9/16
 */
public class Client {

    /**
     * 在JDK动态代理中涉及如下角色：
     * <p>
     * 业务接口Interface、业务实现类target、业务处理类Handler、JVM在内存中生成的动态代理类$Proxy0
     *
     * 说白了，动态代理的过程是这样的：
     *
     * 第一：Proxy通过传递给它的参数（interfaces/invocationHandler）生成代理类$Proxy0；
     *
     * 第二：Proxy通过传递给它的参数（ClassLoader）来加载生成的代理类$Proxy0的字节码文件；
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        Man man = new Hong();
        ManHandler manHandler = new ManHandler(man);
        Man proxyMan = (Man) Proxy.newProxyInstance(man.getClass().getClassLoader(), new Class[]{Man.class}, manHandler);
        System.out.println(proxyMan.getClass().getName());
        proxyMan.findObject();
    }

}

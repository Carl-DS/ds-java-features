package designpattern.proxy;

/**
 * @author duosheng
 * @since 2019/9/16
 */
public class MyClient {

    public static void main(String[] args) throws Throwable {
        Man man = new Hong();
        MyHandler myHandler = new MyHandler(man);
        Man proxyMan = (Man) MyProxy.newProxyInstance(new MyClassLoader("/home/lds/workspace/IdeaProjects/ds-java-features/src/main/java/designpattern/proxy", "designpattern.proxy"),
                Man.class, myHandler);
        System.out.println(proxyMan.getClass().getName());
        proxyMan.findObject();
    }
}

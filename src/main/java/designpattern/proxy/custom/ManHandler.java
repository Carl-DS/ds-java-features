package designpattern.proxy.custom;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author duosheng
 * @since 2019/9/16
 */
public class ManHandler implements InvocationHandler {

    private Man man;

    public ManHandler(Man man) {
        this.man = man;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(man, null);
        after();
        return null;
    }

    /**
     * 前置
     */
    public void before() {
        System.out.println("找到之前");
    }

    /**
     * 后置
     */
    public void after() {
        System.out.println("找到之后");
    }
}

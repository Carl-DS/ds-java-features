package designpattern.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author duosheng
 * @since 2019/9/17
 */
public class GuitaiA implements InvocationHandler {

    private Object pinPai;

    public GuitaiA(Object pinPai) {
        this.pinPai = pinPai;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("销售开始  柜台是： "+this.getClass().getSimpleName());
        method.invoke(pinPai, args);
        System.out.println("销售结束");
        return null;
    }
}

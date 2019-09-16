package designpattern.proxy;

import java.lang.reflect.Method;

/**
 * @author duosheng
 * @since 2019/9/16
 */
public interface MyInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}

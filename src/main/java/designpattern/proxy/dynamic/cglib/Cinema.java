package designpattern.proxy.dynamic.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理类
 *
 * @author duosheng
 * @since 2019/9/17
 */
public class Cinema implements MethodInterceptor {

    private void guangGao(boolean isStart) {
        if (isStart) {
            System.out.println("电影马上开始了，爆米花、可乐、口香糖9.8折，快来买啊！");
        } else {
            System.out.println("电影马上结束了，爆米花、可乐、口香糖9.8折，买回家吃吧！");
        }
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        guangGao(true);
        methodProxy.invokeSuper(o, objects);
        guangGao(false);
        return null;
    }
}

package designpattern.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author duosheng
 * @since 2019/9/17
 */
public class Test {

    public static void main(String[] args) {
        MaotaiJiu maotaiJiu = new MaotaiJiu();
        Wuliangye wuliangye = new Wuliangye();

        InvocationHandler jingxiao1 = new GuitaiA(maotaiJiu);
        InvocationHandler jingxiao2 = new GuitaiA(wuliangye);

        SellWine dynamicProxy = (SellWine) Proxy.newProxyInstance(MaotaiJiu.class.getClassLoader(), MaotaiJiu.class.getInterfaces(), jingxiao1);
        SellWine dynamicProxy2 = (SellWine) Proxy.newProxyInstance(Wuliangye.class.getClassLoader(), Wuliangye.class.getInterfaces(), jingxiao2);
        dynamicProxy.maiJiu();
        dynamicProxy2.maiJiu();

        DaQianMen daQianMen = new DaQianMen();

        InvocationHandler jingxiao3 = new GuitaiA(daQianMen);

        SellCigarette dynamicProxy3 = (SellCigarette) Proxy.newProxyInstance(DaQianMen.class.getClassLoader(), DaQianMen.class.getInterfaces(), jingxiao3);
        dynamicProxy3.sell();
    }
}

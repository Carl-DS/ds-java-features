package designpattern.proxy.dynamic.jdk;

/**
 * @author duosheng
 * @since 2019/9/17
 */
public class DaQianMen implements SellCigarette {

    @Override
    public void sell() {
        System.out.println("售卖的是正宗的大前门，可以扫描条形码查证。");
    }
}

package designpattern.proxy.dynamic.jdk;

/**
 * @author duosheng
 * @since 2019/9/17
 */
public class Wuliangye implements SellWine {
    @Override
    public void maiJiu() {
        System.out.println("我卖得是五粮液。");
    }
}

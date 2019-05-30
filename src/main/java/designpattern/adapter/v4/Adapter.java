package designpattern.adapter.v4;

/**
 * @author duosheng
 * @since 2019/5/30
 */
public class Adapter implements Target {

    private Adaptee1 adaptee1;
    private Adaptee2 adaptee2;

    public Adapter(Adaptee1 adaptee1, Adaptee2 adaptee2) {
        this.adaptee1 = adaptee1;
        this.adaptee2 = adaptee2;
    }

    /**
     * 目标角色有自己的方法
     */
    @Override
    public void request() {
        adaptee1.doSomething();
        adaptee2.doSomething();
    }
}

package designpattern.adapter.v4;

/**
 * @author duosheng
 * @since 2019/5/30
 */
public class Client {

    public static void main(String[] args) {
        //原有的业务逻辑
        Target target = new ConcreteTarget();
        target.request();
        //现在增加了适配器角色后的业务逻辑
        Adaptee1 adaptee1 = new Adaptee1();
        Adaptee2 adaptee2 = new Adaptee2();
        Target target2 = new Adapter(adaptee1, adaptee2);
        target2.request();
    }
}

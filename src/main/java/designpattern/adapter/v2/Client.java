package designpattern.adapter.v2;

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
        Target target2 = new Adapter();
        target2.request();
    }
}

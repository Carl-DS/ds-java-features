package designpattern.command.v4;

/**
 * @author duosheng
 * @since 2019/5/23
 */
public class ConcreteReceiver1 extends Receiver {

    /**
     * 每个接收者都必须处理一定的业务逻辑
     */
    @Override
    public void doSomething() {
        System.out.println("ConcreteReceiver1 do");
    }
}

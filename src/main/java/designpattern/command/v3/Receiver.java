package designpattern.command.v3;

/**
 * 通用Receiver类
 *
 * @author duosheng
 * @since 2019/5/23
 */
public abstract class Receiver {

    /**
     * 抽象接收者，定义每个接收者都必须完成的业务
     */
    public abstract void doSomething();
}

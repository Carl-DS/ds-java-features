package designpattern.command.v4;

/**
 * @author duosheng
 * @since 2019/5/23
 */
public class ConcreteCommand1 extends Command {

    /**
     * 声明自己的默认接收者
     */
    public ConcreteCommand1() {
        super(new ConcreteReceiver1());
    }

    /**
     * 设置新的接收者
     *
     * @param _receiver
     */
    public ConcreteCommand1(Receiver _receiver) {
        super(_receiver);
    }

    /**
     * 每个命令类都必须有一个执行命令的方法
     */
    @Override
    public void execute() {
        // 业务处理
        super.receiver.doSomething();
    }
}

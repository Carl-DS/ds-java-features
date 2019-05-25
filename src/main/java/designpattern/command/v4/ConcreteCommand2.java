package designpattern.command.v4;

/**
 * @author duosheng
 * @since 2019/5/23
 */
public class ConcreteCommand2 extends Command {

    /**
     * 声明自己的默认接收者
     */
    public ConcreteCommand2() {
        super(new ConcreteReceiver2());
    }

    /**
     * 设置新的接收者
     *
     * @param _receiver
     */
    public ConcreteCommand2(Receiver _receiver) {
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

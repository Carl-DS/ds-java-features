package designpattern.command.v3;

/**
 * @author duosheng
 * @since 2019/5/23
 */
public class ConcreteCommand1 extends Command {

    /**
     * 对哪个Receiver类进行命令处理
     */
    private Receiver receiver;

    /**
     * 构造函数传递接收者
     *
     * @param _receiver
     */
    public ConcreteCommand1(Receiver _receiver) {
        this.receiver = _receiver;
    }

    /**
     * 每个命令类都必须有一个执行命令的方法
     */
    @Override
    public void execute() {
        this.receiver.doSomething();
    }
}

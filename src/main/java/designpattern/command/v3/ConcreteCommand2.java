package designpattern.command.v3;

/**
 * @author duosheng
 * @since 2019/5/23
 */
public class ConcreteCommand2 extends Command {

    /**
     * 对哪个Receiver类进行命令处理
     */
    private Receiver receiver;

    public ConcreteCommand2(Receiver _receiver) {
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

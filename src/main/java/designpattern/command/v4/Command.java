package designpattern.command.v4;

/**
 * 抽象的Command类
 *
 * @author duosheng
 * @since 2019/5/23
 */
public abstract class Command {

    /**
     * 定义一个子类的全局共享变量
     */
    protected final Receiver receiver;

    /**
     * 实现类必须定义一个接收者
     *
     * @param _receiver
     */
    public Command(Receiver _receiver) {
        this.receiver = _receiver;
    }

    /**
     * 每个命令类都必须有一个执行命令的方法
     */
    public abstract void execute();
}

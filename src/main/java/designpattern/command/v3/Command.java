package designpattern.command.v3;

/**
 * 抽象的Command类
 *
 * @author duosheng
 * @since 2019/5/23
 */
public abstract class Command {

    /**
     * 每个命令类都必须有一个执行命令的方法
     */
    public abstract void execute();
}

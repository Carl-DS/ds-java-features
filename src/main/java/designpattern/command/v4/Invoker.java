package designpattern.command.v4;

/**
 * @author duosheng
 * @since 2019/5/23
 */
public class Invoker {
    private Command command;

    /**
     * 受气包，接受命令
     *
     * @param command
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * 执行命令
     */
    public void action() {
        this.command.execute();
    }
}

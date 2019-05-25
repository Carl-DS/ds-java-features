package designpattern.command.v4;

/**
 * @author duosheng
 * @since 2019/5/23
 */
public class Client {

    /**
     * 每个命令完成单一的职责，而不是根据接收者的不同完成不同的职
     * 责。在高层模块的调用时就不用考虑接收者是谁的问题
     *
     * @param args
     */
    public static void main(String[] args) {
        //首先声明调用者Invoker
        Invoker invoker = new Invoker();
        //定义一个发送给接收者的命令
        Command command = new ConcreteCommand1();
        //把命令交给调用者去执行
        invoker.setCommand(command);
        invoker.action();
    }
}

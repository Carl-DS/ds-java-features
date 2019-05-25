package designpattern.command.v2;

/**
 * @author duosheng
 * @since 2019/5/23
 */
public class Client {

    public static void main(String[] args) {
        //定义我们的接头人
        Invoker xiaoSan = new Invoker();
        //客户要求增加一项需求
        System.out.println("------------客户要求增加一项需求---------------");
        //客户给我们下命令来
        // Command command = new AddRequirementCommand();
        Command command = new DeletePageCommand();
        //接头人接收到命令
        xiaoSan.setCommand(command);
        Command cancelCommand = new CancelDeletePageCommand();
        xiaoSan.setCommand(cancelCommand);
        //接头人执行命令
        xiaoSan.action();
    }
}

package designpattern.command.v2;

/**
 * @author duosheng
 * @since 2019/5/24
 */
public class CancelDeletePageCommand extends Command {

    /**
     * 撤销删除一个页面的命令
     */
    @Override
    public void execute() {
        super.pg.rollBack();
    }
}

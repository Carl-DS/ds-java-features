package thread.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author duosheng
 * @since 2019/5/13
 */
public class CommandTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandTest.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        CommandOrder commandPhone = new CommandOrder("手机");
        CommandOrder command = new CommandOrder("电视");

        //阻塞方式执行
        String execute = commandPhone.execute();
        LOGGER.info("execute=[{}]");

        //异步非阻塞方式
        Future<String> queue = command.queue();
        String value = queue.get(200, TimeUnit.MILLISECONDS);
        LOGGER.info("value=[{}]", value);


        CommandUser commandUser = new CommandUser("张三");
        String name = commandUser.execute();
        LOGGER.info("name=[{}]", name);
    }
}

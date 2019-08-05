package concurrent.queues;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author duosheng
 * @since 2019/8/5
 */
public abstract class Worker implements Runnable {
    protected volatile boolean enable = true;
    protected String name;
    protected BlockingQueue<Integer> queue;

    public Worker(String name, BlockingQueue<Integer> queue) {
        this.name = name;
        this.queue = queue;
    }

    public void stop() {
        this.enable = false;
        Logger.getLogger(Worker.class.getName()).log(Level.INFO, "Stop: " + name);
    }
}

package concurrent.locks.v1;

/**
 * 读/写锁的重入
 * 上面实现的读/写锁(ReadWriteLock) 是不可重入的，当一个已经持有写锁的线程再次请求写锁时，就会被阻塞。原因是已经有一个写线程了——就是它自己。此外，考虑下面的例子：
 * <p>
 * Thread 1 获得了读锁
 * Thread 2 请求写锁，但因为Thread 1 持有了读锁，所以写锁请求被阻塞。
 * Thread 1 再想请求一次读锁，但因为Thread 2处于请求写锁的状态，所以想再次获取读锁也会被阻塞。
 * 上面这种情形使用前面的ReadWriteLock就会被锁定——一种类似于死锁的情形。不会再有线程能够成功获取读锁或写锁了。
 *
 * @author duosheng
 * @since 2019/9/3
 */
public class ReadWriteLock {
    private int readers = 0;
    private int writers = 0;
    private int writeRequests = 0;

    public synchronized void lockRead() throws InterruptedException {
        while (writers > 0 || writeRequests > 0) {
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead() {
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequests++;
        while (readers > 0 || writers > 0) {
            wait();
        }
        writeRequests--;
        writers++;
    }

    public synchronized void unlockWrite() {
        writers--;
        notifyAll();
    }
}

package concurrent.locks.v2;

import java.util.HashMap;
import java.util.Map;

/**
 * 读锁重入
 * 为了让ReadWriteLock的读锁可重入，我们要先为读锁重入建立规则：
 * <p>
 * 要保证某个线程中的读锁可重入，要么满足获取读锁的条件（没有写或写请求），要么已经持有读锁（不管是否有写请求）。
 * 要确定一个线程是否已经持有读锁，可以用一个map来存储已经持有读锁的线程以及对应线程获取读锁的次数，当需要判断某个线程能否获得读锁时，就利用map中存储的数据进行判断。
 *
 * @author duosheng
 * @since 2019/9/3
 */
public class ReadWriteLock {
    private Map<Thread, Integer> readingThreads = new HashMap<>();

    private int writerAccesses = 0;
    private int writeRequests = 0;
    private Thread writingThread = null;

    /**
     * 只有在没有线程拥有写锁的情况下才允许读锁的重入。此外，重入的读锁比写锁优先级高。
     *
     * @throws InterruptedException
     */
    public synchronized void lockRead() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (!canGrantReadAccess(callingThread)) {
            wait();
        }
        readingThreads.put(callingThread, (getReadAccessCount(callingThread) + 1));
    }

    public synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        int accessCount = getReadAccessCount(callingThread);
        if (accessCount == 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, (accessCount - 1));
        }
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequests++;
        Thread callingThread = Thread.currentThread();
        while (!canGrantWriteAccess(callingThread)) {
            wait();
        }
        writeRequests--;
        writerAccesses++;
        writingThread = callingThread;
    }

    public synchronized void unlockWrite() {
        writerAccesses--;
        if (writerAccesses == 0) {
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canGrantReadAccess(Thread callingThread) {
        // 写锁降级到读锁
        if (isWriter(callingThread)) return true;
        if (writerAccesses > 0) return false;
        if (isReader(callingThread)) return true;
        if (writeRequests > 0) return false;
        return true;
    }

    private int getReadAccessCount(Thread callingThread) {
        Integer accessCount = readingThreads.get(callingThread);
        if (accessCount == null) return 0;
        return accessCount.intValue();
    }

    private boolean isReader(Thread callingThread) {
        return readingThreads.get(callingThread) != null;
    }

    private boolean canGrantWriteAccess(Thread callingThread) {
        // 从读锁升级到写锁了
        if (isOnlyReader(callingThread)) return true;
        if (hasReaders()) return false;
        if (writingThread == null) return true;
        if (!isWriter(callingThread)) return false;
        return true;
    }

    private boolean hasReaders() {
        return readingThreads.size() > 0;
    }

    private boolean isWriter(Thread callingThread) {
        return writingThread == callingThread;
    }

    private boolean isOnlyReader(Thread callingThread) {
        return readingThreads.size() == 1 && readingThreads.get(callingThread) != null;
    }
}

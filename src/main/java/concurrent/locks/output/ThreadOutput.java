package concurrent.locks.output;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Carl
 */
public class ThreadOutput {

    static Thread t1 = null, t2 = null;

    /**
     * 用两个线程，一个输出字母，一个输出数字，交替输出
     * 1A2B3C4D...26Z
     *
     * @param args
     */
    public static void main(String[] args) {
        char[] cI = "12345".toCharArray();
        char[] cC = "ABCDE".toCharArray();

        t1 = new Thread(() -> {
            for (char c : cI) {
                System.out.print(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : cC) {
                LockSupport.park();
                System.out.print(c);
                LockSupport.unpark(t1);
            }
        }, "t2");

        t1.start();
        t2.start();

    }
}

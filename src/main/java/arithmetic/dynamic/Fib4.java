package arithmetic.dynamic;

public class Fib4 {

    /**
     * 根据斐波那契数列的状态转移方程，当前状态只和之前的两个状态有关，
     * 其实并不需要那么长的一个 DP table 来存储所有的状态，
     * 只要想办法存储之前的两个状态就行了。所以，可以进一步优化，把空间复杂度降为 O(1)：
     * 这个技巧就是所谓的「状态压缩」
     * @param n
     * @return
     */
    public static int fib(int n) {
        if (n == 1 || n == 2) return 1;
        int prev = 1, curr = 1;
        for (int i = 3; i <= n; i++) {
            int sum = prev + curr;
            prev = curr;
            curr = sum;
        }
        return curr;
    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        System.out.println(fib(40));
        long end = System.currentTimeMillis();
        System.out.println("end=>" + (end - begin));
    }
}

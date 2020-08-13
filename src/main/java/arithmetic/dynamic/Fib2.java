package arithmetic.dynamic;

import java.util.HashMap;
import java.util.Map;

public class Fib2 {

    static Map<Integer, Integer> memo = new HashMap<>();

    public static int fib(int n) {
        if (n < 1) {
            return 0;
        }
        return helper(n);
    }

    public static int helper(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        if (memo.get(n) != null) {
            return memo.get(n);
        }
        int r = fib(n - 1) + fib(n - 2);
        memo.put(n, r);
        return memo.get(n);
    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        System.out.println(fib(40));
        long end = System.currentTimeMillis();
        System.out.println("end=>" + (end - begin));
    }
}

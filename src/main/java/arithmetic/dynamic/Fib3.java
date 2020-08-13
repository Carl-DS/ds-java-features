package arithmetic.dynamic;

public class Fib3 {

    public static int fib(int n) {
        int[] dp = new int[n+1];
        // base case
        dp[1] = dp[2] = 1;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        System.out.println(fib(40));
        long end = System.currentTimeMillis();
        System.out.println("end=>" + (end - begin));
    }
}

package arithmetic.dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinChange2 {

    static Map<Integer, Integer> memo = new HashMap<>();

    public static int coinChange(List<Integer> coins, int amount) {
        return dp(coins, amount);
    }

    public static int dp(List<Integer> coins, int n) {
        // 查备忘录，避免重复计算
        if (memo.get(n) != null) return memo.get(n);
        // base case
        if (n == 0) return 0;
        if (n < 0) return -1;
        // 求最小值，所以初始化为正无穷
        int res = Integer.MAX_VALUE;
        for (Integer coin : coins) {
            int subproblem = dp(coins, n - coin);
            // 子问题无解，跳过
            if (subproblem == -1) continue;
            res = Math.min(res, subproblem + 1);
        }
        // 记入备忘录
        memo.put(n, res != Integer.MAX_VALUE ? res : -1);
        return memo.get(n);
    }

    public static void main(String[] args) {
        List<Integer> coins = Arrays.asList(1, 2, 5);
        System.out.println(coinChange(coins, 31));
    }
}

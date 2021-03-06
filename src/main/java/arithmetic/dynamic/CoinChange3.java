package arithmetic.dynamic;

import java.util.Arrays;
import java.util.List;

public class CoinChange3 {


    public static int coinChange(List<Integer> coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        // base case
        dp[0] = 0;
        // 外层 for 循环在遍历所有状态的所有取值
        for (int i = 0; i < dp.length; i++) {
            // 内层 for 循环再求所有选择的最小值
            for (int coin : coins) {
                // 子问题无解，跳过
                if(i - coin < 0) continue;
                dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
            }
        }
        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }


    public static void main(String[] args) {
        List<Integer> coins = Arrays.asList(1, 2, 5);
        System.out.println(coinChange(coins, 31));
    }
}

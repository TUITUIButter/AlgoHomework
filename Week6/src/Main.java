public class Main {
    /**
     * 1. 背包问题(Knapsack Problen)
     * 一个旅行者随身携带一个背包，可以放入背包的物品有n种，每种物
     * 品的重量和价值分別是w_i，v_i，i=1，，n。如果背包的最大容量限制是b，
     * 怎样选择放入背包的物品以使得背包的价值最大？
     */
    public int knapsack(int[] w, int[] v, int b){
        int n = w.length;
        int[][] dp = new int[n][b]; //dp[i][j] 表示从下标为[0-i]的物品里任意取，放进容量为j的背包，价值总和最大是多少。
        for (int i = 0; i < n; i++) { // i表示放入第i个物品
            for (int j = 0; j < b; j++) {
                if (i == 0 || j == 0) { // 初始化第一行
                    continue;
                }
                if (j < w[i]) {
                    dp[i][j] = dp[i - 1][j]; // 当前物品的重量大于背包的容量, 不能放入背包
                } else { // 当前物品的重量小于等于背包的容量, 可以放入背包
                    // dp[i - 1][j - w[i]] + v[i] 表示放入当前物品的价值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i]] + v[i]);
                }
            }
        }
        return dp[n-1][b-1];
    }

    /**
     * 2.投资问题
     * 设有m元钱，口项投资，函数f_i(x)表示将x元钱投入到第i项项目所产生
     * 的效益，i=1,…,n.问：如何分配这n元钱，使得投资的总效益最高？
     */
    public int investment(int[][] f, int m) {
        int n = f.length;
        int[][] dp = new int[n+1][m+1];
        for (int i = 1; i <= n; i++)
        {
            for (int j = 0; j <= m; j++)
            {
                for (int k = 0; k <= j; k++)
                {
                    dp[i][j] = Math.max(dp[i][j], f[i][k] + dp[i - 1][j - k]);
                }
            }
        }
        return dp[n][m];
    }



    public static void main(String[] args) {
        int[] w = {2, 3, 4, 5}; // 物品的重量数组
        int[] v = {3, 4, 5, 6}; // 物品的价值数组
        int b = 10; // 背包的容量

        Main main = new Main();
        System.out.println(main.knapsack(w,v,b));
    }
}
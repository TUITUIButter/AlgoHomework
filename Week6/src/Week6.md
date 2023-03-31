

## 背包问题(Knapsack Problem)
> 
> 一个旅行者随身携带一个背包，可以放入背包的物品有n种，每种物 
> 品的重量和价值分別是w_i，v_i，i=1，，n。如果背包的最大容量限制是b， 
> 怎样选择放入背包的物品以使得背包的价值最大？

### solution 1
解决思路：动态规划

二维DP数组
> dp[i][j] 表示从下标为[0-i]的物品里任意取，放进容量为j的背包，价值总和最大是多少。

递推公式
> dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - w[i]] + v[i]);

先遍历物品，然后遍历背包重量的代码。

```java
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
```

## 投资问题
> 现在有一个01背包的升级问题：投资问题。设有m元钱，口项投资，
> 函数f_i(x)表示将x元钱投入到第i项项目所产生的效益，i=1,…,n.
> 问：如何分配这n元钱，使得投资的总效益最高？
### solution
这是一个典型的01背包的升级问题，可以用类似于01背包的动态规划思想来求解。具体来说，可以用一个二维的dp数组来记录状态，其中dp[i][j]表示前i个项目投资j元钱所能得到的最大效益。则状态转移方程为：

> 当j < x_i时，dp[i][j] = dp[i-1][j]
>
> 当j >= x_i时，dp[i][j] = max{dp[i-1][j], dp[i-1][j-x_i]+f_i(x_i)}
>
> 其中x_i表示第i个项目的投资金额，f_i(x_i)表示将x_i元钱投入到第i项项目所产生的效益。根据状态转移方程，dp[i][j]的值只和dp[i-1][j]和dp[i-1][j-x_i]有关，因此可以使用滚动数组的技巧将空间复杂度优化为O(m)。

最终的答案为dp[n][m]，即前n个项目投资m元钱所能得到的最大效益。

由于每个项目最多只能被投资一次，因此这是一个0-1背包问题，而不是完全背包问题。

```java
public int investment(int[][] f, int m) {
    int n = f.length;
    int[][] dp = new int[n+1][m+1];
    for (int i = 1; i <= n; i++)
    {
        for (int j = 0; j <= m; j++)
        {
            for (int k = 0; k <= j; k++)
            {
                //k表示给第当前i个项目投资k元，给之前的i-1个项目总共投资j-k元的收益，0<=k<=j
                dp[i][j] = Math.max(dp[i][j], f[i][k] + dp[i - 1][j - k]);
            }
        }
    }
    return dp[n][m];
}
```
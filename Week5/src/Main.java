import java.util.List;

public class Main {
    public String longestPalindromeDP(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n]; // dp[i][j] 表示 s[i..j] 是否是回文串
        String ans = "";
        for (int l = 0; l < n; l++) {
            for (int i = 0; i + l < n; i++) {
                int j = i + l;
                if (l == 0) {
                    dp[i][j] = true; // 一个字符一定是回文串
                } else if (l == 1) { // 两个字符的情况
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else { // 大于两个字符的情况
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if (dp[i][j] && l + 1 > ans.length()) { // 记录最长回文串
                    ans = s.substring(i, i + l + 1);
                }
            }
        }
        return ans;
    }

    public String longestPalindrome(String s) {
        int n = s.length();
        String ans = "";
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                String test = s.substring(i, j);
                if (isPalindrome(test) && test.length() > ans.length()) {
                    ans = test;
                }
            }
        }
        return ans;
    }

    public boolean isPalindrome(String s) {
        int n = s.length();
        for (int i = 0; i < n / 2; i++) {
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n]; // dp[i][j] 表示从左上角走到 (i, j) 的最小路径和
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0]; // 初始化第一列
        }
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j]; // 初始化第一行
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]; // 状态转移方程, 只能从上面或者左边走过来
            }
        }
        return dp[m - 1][n - 1];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n]; // dp[i][j] 表示从三角形顶部走到 (i, j) 的最小路径和
        dp[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i - 1][0] + triangle.get(i).get(0); // 初始化第一列
            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i).get(j); // 状态转移方程, 只能从左上角或者上面走过来
            }
            dp[i][i] = dp[i - 1][i - 1] + triangle.get(i).get(i); // 最后一个元素单独处理，只能从左上角走过来
        }
        int minTotal = dp[n - 1][0];
        for (int j = 1; j < n; j++) {
            minTotal = Math.min(minTotal, dp[n - 1][j]);
        }
        return minTotal;
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
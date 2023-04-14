import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Main {

    /**
     * 第 84 题，求柱状图的最大面积
     * @param heights 高度数组
     * @return 最大面积
     */
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];

        Deque<Integer> mono_stack = new ArrayDeque<>();
        // 左边界
        for (int i = 0; i < n; ++i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) { // 出栈高的
                mono_stack.pop();
            }
            left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
            mono_stack.push(i);
        }

        // 右边界
        mono_stack.clear();
        for (int i = n - 1; i >= 0; --i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) { // 出栈高的
                mono_stack.pop();
            }
            right[i] = (mono_stack.isEmpty() ? n : mono_stack.peek());
            mono_stack.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]); // 根据边界计算面积
        }
        return ans;
    }


    /**
     * 85 题
     * @param matrix 矩阵
     * @return 最大面积
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int[] heights = new int[matrix[0].length];
        int maxArea = 0;
        for (int row = 0; row < matrix.length; row++) { // 一层一层调用84题
            //遍历每一列，更新高度
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == '1') {
                    heights[col] += 1;
                } else {
                    heights[col] = 0;
                }
            }
            // 调用第84道题的解法，更新函数
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        return maxArea;
    }

    public int maxProduct(int[] nums) {
        int n = nums.length;
        int[] dpMin = new int[n];
        int[] dpMax = new int[n];

        dpMin[0] = dpMax[0] = nums[0];
        for(int i = 1; i < n; i++){
            dpMin[i] = Math.min(dpMin[i - 1] * nums[i], Math.min(dpMax[i - 1] * nums[i], nums[i]));
            dpMax[i] = Math.max(dpMax[i - 1] * nums[i], Math.max(dpMin[i - 1] * nums[i], nums[i]));
        }
        int res = nums[0];
        for(int r:dpMax){
            res = Math.max(r, res);
        }
        return res;
    }


    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
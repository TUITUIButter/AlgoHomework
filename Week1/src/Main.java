import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            if(map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]),i};
            }
            else{
                map.put(nums[i],i);
            }
        }
        return null;
    }

    public int mySqrt(int x) {
        int left = 0, right = x/2 + 1;
        int res = 0;
        while (left <= right){
            int middle = left + (right - left)/2;
            long power = (long) middle * middle;
            if(power <= x){
                left = middle + 1;
                res = middle;
            }
            if(power > x){
                right = middle - 1;
            }
        }
        return res;
    }

    public int climbStairs(int n) {
        int p, q = 0, r = 1;
        for(int i = 0; i < n; i++){
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[] res1 = main.twoSum(new int[]{3,2,4}, 6);
        System.out.println(Arrays.toString(res1));

        int res2 = main.mySqrt(2147395599);
        System.out.println(res2);

        int res3 = main.climbStairs(45);
        System.out.println(res3);
    }
}
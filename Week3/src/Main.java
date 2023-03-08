public class Main {
    public int threeSumClosest(int[] nums, int target) {
        int len = nums.length;
        int min = Integer.MAX_VALUE;
        int res = 0;
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                if (i == j)
                    continue;
                for(int k = 0; k < len; k++){
                    if (i == k || j == k){
                        continue;
                    }
                    int sum = nums[i] + nums[j] + nums[k];
                    if(Math.abs(target-sum) < min){
                        min = Math.abs(target-sum);
                        res = sum;
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.threeSumClosest(new int[]{1,1,1,0},-100));
    }
}
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;


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

    public int threeSumClosest2(int[] nums, int target) {
        int len = nums.length;
        int min = Integer.MAX_VALUE; // 记录最接近的值
        Arrays.sort(nums); // 排序
        int res = 0;
        for(int i = 0; i <= len - 3; i++){ // a从第一个元素取到倒数第三个元素
            int l = i + 1;
            int r = len - 1;
            while(l < r){ // 双指针遍历
                int sum = nums[i] + nums[l] + nums[r];

                if(Math.abs(target-sum) < min){ // 记录最小值
                    min = Math.abs(target-sum);
                    res = sum;
                }

                if(sum < target)
                    l++; // 比target小
                else if(sum > target)
                    r--; // 比target大
                else
                    return sum;
            }
        }
        return res;
    }

    public List<String> letterCombinations(String digits) {
        // 初始化map
        Map<Integer, char[]> map = new HashMap<>();
        map.put(2, new char[]{'a', 'b', 'c'});
        map.put(3, new char[]{'d', 'e', 'f'});
        map.put(4, new char[]{'g', 'h', 'i'});
        map.put(5, new char[]{'j', 'k', 'l'});
        map.put(6, new char[]{'m', 'n', 'o'});
        map.put(7, new char[]{'p', 'q', 'r', 's'});
        map.put(8, new char[]{'t', 'u', 'v'});
        map.put(9, new char[]{'w', 'x', 'y','z'});


        List<String> list = new ArrayList<>();
        if(digits.isEmpty()){ // 空串返回
            return new ArrayList<>();
        }else{ // 添加第一个数字对应的字符
            int c = digits.charAt(0) - '0';
            char[] chars = map.get(c);
            for(char one : chars){
                list.add(String.valueOf(one));
            }
            
        }

        for(int i = 1; i < digits.length(); i++){ // 遍历所有数字（除了第一位）
            List<String> temp = new ArrayList<>(); // 创建中间结果存储数组
            int c = digits.charAt(i) - '0'; // 获取数字
            char[] chars = map.get(c); // 得到对应字母
            for(char one : chars){ // 遍历所有字符 ，如 a,b,c
                for(String s: list){ // 遍历已有的字符，如w,x,y,z
                    temp.add(s+one); // 拼接字符，如 wa,wb,wc,... 存储在中间结果中
                }
            }
            list = temp; // 获取中间结果
        }

        return list;
    }

    public List<String> letterCombinations2(String digits) {
        // 初始化map
        Map<Integer, char[]> map = new HashMap<>();
        map.put(2, new char[]{'a', 'b', 'c'});
        map.put(3, new char[]{'d', 'e', 'f'});
        map.put(4, new char[]{'g', 'h', 'i'});
        map.put(5, new char[]{'j', 'k', 'l'});
        map.put(6, new char[]{'m', 'n', 'o'});
        map.put(7, new char[]{'p', 'q', 'r', 's'});
        map.put(8, new char[]{'t', 'u', 'v'});
        map.put(9, new char[]{'w', 'x', 'y','z'});


        Queue<String> res = new ArrayDeque<>();
        if(digits.isEmpty()){ // 空串返回
            return new ArrayList<>();
        }else{ // 添加第一个数字对应的字符
            int c = digits.charAt(0) - '0';
            char[] chars = map.get(c);
            for(char one : chars){
                res.add(String.valueOf(one));
            }
            
        }

        for(int i = 1; i < digits.length(); i++){ // 遍历所有数字（除了第一位）
            int c = digits.charAt(i) - '0'; // 获取数字
            char[] chars = map.get(c); // 得到对应字母
            while(res.peek().length() <= i){ // 遍历队列中的长度不够的字符串
                String old = res.poll(); // 取队列头
                for(char one : chars){ // 遍历所有字符 ，如 a,b,c
                    res.add(old+one); // 加入队列尾
                }
            }
            
        }

        return new ArrayList<String>(res);
    }

    List<String> ans = new ArrayList<>();
    StringBuilder t = new StringBuilder();
    // 初始化map
    Map<Character, String> m = new HashMap<>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};

    public List<String> letterCombinations3(String digits) {
        if (digits.length() != 0) dfs(0, digits);
        return ans;
    }

    public void dfs(int k, String digits) {
        if (k == digits.length()) {
            ans.add(t.toString());
            return;
        }
        for (char ch: m.get(digits.charAt(k)).toCharArray()) {
            t.append(ch);
            dfs(k + 1, digits);
            t.deleteCharAt(t.length() - 1);
        }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null)
            return head;

        // 遍历列表长度
        int len = 0;
        ListNode node = head;
        while(node != null){len++; node = node.next;}

        if(len == n){ // 删除的是头
            return head.next;
        }

        ListNode pre = head;
        for(int i = 0; i < len - n - 1; i++){
            pre = pre.next;
        }
        pre.next = pre.next.next;

        return head;
    }

    public ListNode removeNthFromEnd2(ListNode head, int n) {
        if(head == null)
            return head;

        // 遍历列表长度
        ListNode l = head, r = head;
        for(int i = 0; i < n; i++){
            r = r.next;
        }

        if(r == null){ // 加了n之后就是结尾，说明删除的是头结点
            return head.next;
        }

        while (r.next != null){ // l,r同时向右移动，直至r到最后一个节点
            l = l.next;
            r = r.next;
        }

        l.next = l.next.next; // 删除l的下一个节点

        return head;
    }

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.threeSumClosest(new int[]{1,1,1,0},-100));
        System.out.println(main.threeSumClosest2(new int[]{-1,2,1,-4},1));
        System.out.println(main.letterCombinations("23"));
        System.out.println(main.letterCombinations2("23"));
        System.out.println(main.letterCombinations3("23"));

        // 1->2->3->4
        ListNode n4 = new ListNode(4);
        ListNode n3 = new ListNode(3, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(1, n2);

//        ListNode head = main.removeNthFromEnd(n1, 2);
//        head.print();
        ListNode head2 = main.removeNthFromEnd2(n1, 1);
        head2.print();
    }
}
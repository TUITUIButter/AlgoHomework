import java.util.*;

public class Main {
    public int reverse1(int x) {
        int res = 0;
        double max = (Math.pow(2, 31) - 1) / 10.0; // 确定最大值（除以10，在溢出前判断）
        double min = -Math.pow(2, 31) / 10.0; // 确定最小值（除以10，在溢出前判断）
        while(x != 0){ // 在x不为0前循环
            if(res > max || res < min){ // 如果下一步会发生溢出，即可返回0
                return 0;
            }
            res *= 10; // 左移res，单位为10
            res += x % 10; // 将当前x个位上的数添加至res内
            x /= 10; // 右移res，单位为10
        }
        return res;
    }

    public int reverse2(int x) {
        String number = String.valueOf(x);

        StringBuilder builder = new StringBuilder(); // 字符串builder
        int begin = 0; // 标识数字部分起始位置
        if(number.charAt(0) == '-'){
            builder.append('-'); // 添加负号
            begin++;
        }
        for(int index = number.length() - 1; index >= begin; index--){ // 由后向前遍历
            builder.append(number.charAt(index));
        }

        String resString = builder.toString();
        long res = Long.parseLong(resString); // 先转化成long类型
        if(-2147483648 <= res && res <= 2147483647){ // 如果在int区间内返回值
            return (int)res;
        }
        else return 0;
    }

    public int[] plusOne(int[] digits) {
        int len = digits.length;
        digits[len -1] += 1; // 最后一位+1
        int t = digits[len -1] / 10; // 记录进位
        digits[len -1] %= 10; // mod 10

        // 从倒数第二位遍历
        for(int i = len -2 ; i >= 0; i--){
            digits[i] = digits[i] + t; // 加上进位
            t = digits[i] / 10; // 记录新的进位
            digits[i] %= 10; // 计算mod 10
        }

        if(t == 1) { // 如果最后发生进位
            int[] res = new int[len + 1];
            System.arraycopy(digits, 0, res, 1, len); // 复制数组
            res[0] = 1;
            return res;
        }
        return digits;
    }

    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I',1);map.put('V',5);map.put('X',10);map.put('L',50);
        map.put('C',100);map.put('D',500);map.put('M',1000);

        int res = 0;
        for(int i = 0; i < s.length(); i++){ // 遍历整个字符串
            if(s.charAt(i) == 'I' && i+1 < s.length() &&
                    (s.charAt(i+1) == 'V' || s.charAt(i+1) == 'X')){
                res -= 1;
            }
            else if(s.charAt(i) == 'X' && i+1 < s.length() &&
                    (s.charAt(i+1) == 'L' || s.charAt(i+1) == 'C')){
                res -= 10;
            }
            else if(s.charAt(i) == 'C' && i+1 < s.length() &&
                    (s.charAt(i+1) == 'D' || s.charAt(i+1) == 'M')){
                res -= 100;
            }
            else {
                res += map.get(s.charAt(i));
            }
        }
        return res;
    }

    public int romanToInt2(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I',1);map.put('V',5);map.put('X',10);map.put('L',50);
        map.put('C',100);map.put('D',500);map.put('M',1000);

        int res = 0;
        int top = 1; // 记录最大值
        for(int i = s.length() - 1; i >= 0; i--) { // 遍历整个字符串
            int value = map.get(s.charAt(i)); // 获取数字
            if(value >= top){ // 如果当前值比最大值大，更新最大值
                res += value;
                top = value;
            }else { // 遇到了小的值，需要减去当前值
                res -= value;
            }
        }
        return res;
    }


    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.reverse1(-2147483648));
        System.out.println(main.reverse2(120));

        System.out.println(Arrays.toString(main.plusOne(new int[]{9,8,7,6,5,4,3,2,1,0})));

        System.out.println(main.romanToInt("DCXXI"));
        System.out.println(main.romanToInt2("DCXXI"));
    }
}
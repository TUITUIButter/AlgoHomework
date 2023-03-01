public class Main {
    public int reverse(int x) {
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

    public static void main(String[] args) {
        System.out.println(new Main().reverse(-2147483648));
    }
}
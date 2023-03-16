import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        int[][] intervals = new int[][]{{1, 4}, {4, 5}, {8, 17}, {15, 18}};
        int[][] res = main.merge(intervals);
        System.out.println(Arrays.deepToString(res));

        // 1->2->3->4
        ListNode n4 = new ListNode(3);
        ListNode n3 = new ListNode(1, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(4, n2);
        n1.print();

        ListNode sortNode = main.sortList(n1);
        sortNode.print();
    }

    public int[][] merge(int[][] intervals) {
        // 对所有数队进行排序
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);

        // 保存结果
        List<int[]> merged = new ArrayList<>();
        for (int[] one : intervals) { // 对所有输队进行遍历
            // [1,5],[6,7] 无交集。直接添加
            if (merged.size() == 0 || one[0] > merged.get(merged.size() - 1)[1]) {
                merged.add(one);
            }
            // [1,5],[4,～] 遇到有交集的
            else if (one[0] <= merged.get(merged.size() - 1)[1]) {
                int[] replace = merged.get(merged.size() - 1);
                replace[1] = Math.max(replace[1], one[1]); // 右侧边界取最大值
                merged.set(merged.size() - 1, replace); // 替换为新的区间
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    public ListNode sortListBubble(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = new ListNode();
        newHead.next = head;
        int len = 0;
        for (ListNode tmp = newHead.next; tmp != null; tmp = tmp.next) len++;

        // 冒泡排序
        for (int i = 0; i < len; i++) // 外层走一次
        {
            for (ListNode l = newHead.next, pre = newHead; // 定义当前比较节点与前一节点
                 l.next != null;  // 结束条件为l为最后一个节点
                 l = l.next, pre = pre.next) // l与pre同时右移
            {
                if (l.val > l.next.val) {
                    swapFromPreNode(pre); // 交换l与l.next
                    l = pre.next; // 交换之后需要重制l
                }
            }
        }
        return newHead.next;
    }

    private void swapFromPreNode(ListNode preNode) {
        ListNode l = preNode.next;
        if (l == null) return;
        ListNode r = l.next;
        l.next = r.next;
        r.next = l;
        preNode.next = r;
    }

    public ListNode sortList(ListNode head) {
        return sortList(head, null);
    }

    public ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return null;
        }
        if (head.next == tail) { // 遇到单个节点，不需要递归，直接返回
            head.next = null;
            return head;
        }

        ListNode slow = head, fast = head; // 定义快慢指针
        while (fast != tail) { // 寻找中点
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }// 此时slow就是中点

        ListNode mid = slow;
        ListNode list1 = sortList(head, mid); // 递归
        ListNode list2 = sortList(mid, tail);
        return merge(list1, list2);

    }

    /**
     * 合并两个有序链表
     *
     * @param head1 有序链表1
     * @param head2 有序链表2
     * @return 新头节点
     */
    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0); // 假头
        ListNode temp = dummyHead, temp1 = head1, temp2 = head2; // temp为res的尾节点
        while (temp1 != null && temp2 != null) { // 连接l1与l2
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        // 有链表剩余
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }
}
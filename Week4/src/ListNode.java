
public class ListNode {
    int val;
    ListNode next;

    public ListNode() {
    }

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public void print() {
        for (ListNode n = this; n != null; n = n.next) {
            System.out.print(n.val);
            System.out.print("->");
        }
        System.out.println();
    }
}


package class04;

// 测试链接：https://leetcode.com/problems/reverse-nodes-in-k-group/
public class Code04_ReverseNodesInKGroup {

	// 不要提交这个类
	public static class ListNode {
		public int val;
		public ListNode next;
	}

	public static ListNode reverseKGroup(ListNode head, int k) {
		ListNode start = head;
		ListNode end = getKGroupEnd(start, k);
		if (end == null) {
			return head;
		}
		// 第一组凑齐了！
		head = end;
		reverse(start, end);
		// 上一组的结尾节点
		ListNode lastEnd = start;
		while (lastEnd.next != null) {
			start = lastEnd.next;
			end = getKGroupEnd(start, k);
			if (end == null) {
				return head;
			}
			reverse(start, end);
			lastEnd.next = end;
			lastEnd = start;
		}
		return head;
	}

	public static ListNode getKGroupEnd(ListNode start, int k) {
		while (--k != 0 && start != null) {
			start = start.next;
		}
		return start;
	}

	public static void reverse(ListNode start, ListNode end) {
		end = end.next;
		ListNode pre = null;
		ListNode cur = start;
		ListNode next = null;
		while (cur != end) {
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		start.next = end;
	}

	//my code
	public static ListNode MyreverseKGroup(ListNode head, int k){
		int count = 0;
		ListNode tail = null;
		ListNode originalHead = head;
		while(count < k){
			head = head.next;
			count--;
		}
		tail = head.next;
		Myreverse(originalHead, head);
		head.next = MyreverseKGroup(tail, k);
		return head;
	}

	public static void Myreverse(ListNode start, ListNode end){
		ListNode next = null;
		ListNode pre = null;
		while (start != end) {
			next = start.next;
			start.next = pre;
			pre = start;
			start = next;
		}
	}
	//

}

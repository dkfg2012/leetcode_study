package class10;

public class Code01_FindFirstIntersectNode {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getIntersectNode(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node loop1 = getLoopNode(head1);
		Node loop2 = getLoopNode(head2);
		if (loop1 == null && loop2 == null) {
			return noLoop(head1, head2);
		}
		if (loop1 != null && loop2 != null) {
			return bothLoop(head1, loop1, head2, loop2);
		}
		return null;
	}

	// 找到链表第一个入环节点，如果无环，返回null
	public static Node getLoopNode(Node head) {
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		// n1 慢  n2 快
		Node slow = head.next; // n1 -> slow
		Node fast = head.next.next; // n2 -> fast
		while (slow != fast) {
			if (fast.next == null || fast.next.next == null) {
				return null;
			}
			fast = fast.next.next;
			slow = slow.next;
		}
		// slow fast  相遇
		fast = head; // n2 -> walk again from head
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		return slow;
	}

	// 如果两个链表都无环，返回第一个相交节点，如果不想交，返回null
	public static Node noLoop(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node cur1 = head1;
		Node cur2 = head2;
		int n = 0;
		while (cur1.next != null) {
			n++;
			cur1 = cur1.next;
		}
		while (cur2.next != null) {
			n--;
			cur2 = cur2.next;
		}
		if (cur1 != cur2) {
			return null;
		}
		// n  :  链表1长度减去链表2长度的值
		cur1 = n > 0 ? head1 : head2; // 谁长，谁的头变成cur1
		cur2 = cur1 == head1 ? head2 : head1; // 谁短，谁的头变成cur2
		n = Math.abs(n);
		while (n != 0) {
			n--;
			cur1 = cur1.next;
		}
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		return cur1;
	}

	// 两个有环链表，返回第一个相交节点，如果不想交返回null
	public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
		Node cur1 = null;
		Node cur2 = null;
		if (loop1 == loop2) {
			cur1 = head1;
			cur2 = head2;
			int n = 0;
			while (cur1 != loop1) {
				n++;
				cur1 = cur1.next;
			}
			while (cur2 != loop2) {
				n--;
				cur2 = cur2.next;
			}
			cur1 = n > 0 ? head1 : head2;
			cur2 = cur1 == head1 ? head2 : head1;
			n = Math.abs(n);
			while (n != 0) {
				n--;
				cur1 = cur1.next;
			}
			while (cur1 != cur2) {
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			return cur1;
		} else {
			cur1 = loop1.next;
			while (cur1 != loop1) {
				if (cur1 == loop2) {
					return loop1;
				}
				cur1 = cur1.next;
			}
			return null;
		}
	}


	//my code
	public static Node mygetIntersectNode(Node head1, Node head2){
		if(head1 == head2){
			return head1;
		}
		Node head1CycleEntry = cycleEntry(head1);
		Node head2CycleEntry = cycleEntry(head2);
		if(head1CycleEntry == null && head2CycleEntry == null){
			return bothCycleFree(head1, head2);
		}
		if(head1CycleEntry != null && head2CycleEntry != null){
			return bothHaveCycle(head1, head2, head1CycleEntry, head2CycleEntry);
		}
		return null;
	}

	public static Node cycleEntry(Node head){
		Node fastP = head.next.next;
		Node slowP = head.next;
		while(fastP != null && fastP.next != null){
			if(fastP == slowP){
				//contain cycle
				fastP = head;
				while(fastP != slowP){
					fastP = fastP.next;
					slowP = slowP.next;
				}
				return fastP;
			}else{
				fastP = fastP.next.next;
				slowP = slowP.next;
			}
		}
		return null;
	}

	public static Node bothCycleFree(Node head1, Node  head2){
		int N1Length = 1;
		int N2Length = 1;
		Node cur1 = head1;
		Node cur2 = head2;
		while(cur1 != null && cur2 != null){
			cur1 = cur1.next;
			cur2 = cur2.next;
			N1Length++;
			N2Length++;
		}
		while(cur1 != null){
			cur1 = cur1.next;
			N1Length++;
		}
		while(cur2 != null){
			cur2 = cur2.next;
			N2Length++;
		}
		int lendiff = N1Length > N2Length ? N1Length - N2Length : N2Length - N1Length;
		cur1 = head1;
		cur2 = head2;
		if(N1Length > N2Length){
			while(lendiff != 0){
				cur1 = cur1.next;
				lendiff--;
			}
			while(cur1 != cur2){
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			return cur1;
		}else{
			while(lendiff != 0){
				cur2 = cur2.next;
				lendiff--;
			}
			while(cur2 != cur1){
				cur2 = cur2.next;
				cur1 = cur1.next;
			}
			return cur2;
		}
	}

	public static Node support(Node head1, Node  head2, Node entry1, Node entry2){
		int N1Length = 1;
		int N2Length = 1;
		Node cur1 = head1;
		Node cur2 = head2;
		while(cur1 != null && cur2 != null && cur1 != entry1 && cur2 != entry2){
			cur1 = cur1.next;
			cur2 = cur2.next;
			N1Length++;
			N2Length++;
		}
		while(cur1 != null && cur1 != entry1){
			cur1 = cur1.next;
			N1Length++;
		}
		while(cur2 != null && cur2 != entry2){
			cur2 = cur2.next;
			N2Length++;
		}
		int lendiff = N1Length > N2Length ? N1Length - N2Length : N2Length - N1Length;
		cur1 = head1;
		cur2 = head2;
		if(N1Length > N2Length){
			while(lendiff != 0){
				cur1 = cur1.next;
				lendiff--;
			}
			while(cur1 != cur2){
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			return cur1;
		}else{
			while(lendiff != 0){
				cur2 = cur2.next;
				lendiff--;
			}
			while(cur2 != cur1){
				cur2 = cur2.next;
				cur1 = cur1.next;
			}
			return cur2;
		}
	}

	public static Node bothHaveCycle(Node head1, Node head2, Node entry1, Node entry2){
		if(entry1 == entry2){
			// they cross before enter into cycle
			return support(head1, head2, entry1, entry2);
		}else{
			Node cur1 = entry1;
			Node cur2 = entry2;
			cur1 = cur1.next;
			while(cur1 != entry1){
				if(cur1 == cur2){
					return cur2;
				}else{
					cur1 = cur1.next;
				}
			}
			return null;
		}
	}
	//



	public static void main(String[] args) {
		// 1->2->3->4->5->6->7->null
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);

		// 0->9->8->6->7->null
		Node head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);
		System.out.println(mygetIntersectNode(head1, head2).value);
		System.out.println("");

		// 1->2->3->4->5->6->7->4...
		head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);
		head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

		// 0->9->8->2...
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next; // 8->2
		System.out.println(getIntersectNode(head1, head2).value);
		System.out.println(mygetIntersectNode(head1, head2).value);
		System.out.println("");

		// 0->9->8->6->4->5->6..
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);
		System.out.println(mygetIntersectNode(head1, head2).value);
		System.out.println("");

	}

}

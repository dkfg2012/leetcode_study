package class09;

import java.util.Stack;

public class Code02_IsPalindromeList {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	// need n extra space
	public static boolean isPalindrome1(Node head) {
		Stack<Node> stack = new Stack<Node>();
		Node cur = head;
		while (cur != null) {
			stack.push(cur);
			cur = cur.next;
		}
		while (head != null) {
			if (head.value != stack.pop().value) {
				return false;
			}
			head = head.next;
		}
		return true;
	}

	// need n/2 extra space
	public static boolean isPalindrome2(Node head) {
		if (head == null || head.next == null) {
			return true;
		}
		Node right = head.next;
		Node cur = head;
		while (cur.next != null && cur.next.next != null) {
			right = right.next;
			cur = cur.next.next;
		}
		Stack<Node> stack = new Stack<Node>();
		while (right != null) {
			stack.push(right);
			right = right.next;
		}
		while (!stack.isEmpty()) {
			if (head.value != stack.pop().value) {
				return false;
			}
			head = head.next;
		}
		return true;
	}

	// need O(1) extra space
	public static boolean isPalindrome3(Node head) {
		if (head == null || head.next == null) {
			return true;
		}
		Node n1 = head;
		Node n2 = head;
		while (n2.next != null && n2.next.next != null) { // find mid node
			n1 = n1.next; // n1 -> mid
			n2 = n2.next.next; // n2 -> end
		}
		// n1 中点
		
		
		n2 = n1.next; // n2 -> right part first node
		n1.next = null; // mid.next -> null
		Node n3 = null;
		while (n2 != null) { // right part convert
			n3 = n2.next; // n3 -> save next node
			n2.next = n1; // next of right node convert
			n1 = n2; // n1 move
			n2 = n3; // n2 move
		}
		n3 = n1; // n3 -> save last node
		n2 = head;// n2 -> left first node
		boolean res = true;
		while (n1 != null && n2 != null) { // check palindrome
			if (n1.value != n2.value) {
				res = false;
				break;
			}
			n1 = n1.next; // left to mid
			n2 = n2.next; // right to mid
		}
		n1 = n3.next;
		n3.next = null;
		while (n1 != null) { // recover list
			n2 = n1.next;
			n1.next = n3;
			n3 = n1;
			n1 = n2;
		}
		return res;
	}

	public static void printLinkedList(Node node) {
		System.out.print("Linked List: ");
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
		System.out.println();
	}



	//my code
	public static boolean myIsPalindrome(Node head){
		if(head == null){
			return true;
		}else if(head.next == null){
			return true;
		}else if(head.next.next == null){
			return head.value == head.next.value;
		}else{
			Node slowP = head;
			Node fastP = head;
			while(fastP.next != null && fastP.next.next != null){
				slowP = slowP.next;
				fastP = fastP.next.next;
			}
			Stack<Node> s = new Stack<>();
			if(fastP.next == null){
				//double
				slowP = slowP.next;
				while(slowP.next != null){
					s.add(slowP);
					slowP = slowP.next;
				}
				s.add(slowP);
				while(!s.isEmpty()){
					if(head.value != s.pop().value){
						return false;
					}
					head = head.next;
				}
				return true;
			}else{
				//single
				while(slowP.next != null){
					s.add(slowP);
					slowP = slowP.next;
				}
				s.add(slowP);
				while(!s.isEmpty()){
					if(head.value != s.pop().value){
						return false;
					}
					head = head.next;
				}
				return true;
			}
		}
	}

	public static boolean O1IsPalindrome(Node head){
		if(head == null){
			return true;
		}else if(head.next == null){
			return true;
		}else if(head.next.next == null){
			return head.value == head.next.value;
		}
		Node slowP = head;
		Node fastP = head;
		while(fastP.next != null && fastP.next.next != null){
			slowP = slowP.next;
			fastP = fastP.next.next;
		}
		Node Righthead = slowP.next;
		// reverse left part of list
		slowP.next = null;
		Node cur = head;
		Node next = null;
		Node pre = null;

		while(cur != slowP){
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		if(fastP.next == null){
			slowP = slowP.next;
			while(Righthead != null && slowP != null){
				System.out.println(Righthead.value);
				System.out.println(slowP.value);
				System.out.println("");
				if(Righthead.value != slowP.value){
					return false;
				}else{
					Righthead = Righthead.next;
					slowP = slowP.next;
				}
			}
			return true;
		}else{
			while(Righthead != null && slowP != null){
				if(Righthead.value != slowP.value){
					return false;
				}else{
					Righthead = Righthead.next;
					slowP = slowP.next;
				}
			}
			return true;
		}

	}

	public static void main(String[] args) {

		Node head = null;
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(1);
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(1);
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(1);
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(2);
		head.next.next.next = new Node(1);
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(2);
		head.next.next.next.next = new Node(1);
		printLinkedList(head);
//		System.out.print(isPalindrome1(head) + " | ");
		System.out.print(myIsPalindrome(head) + " | ");
//		System.out.print(O1IsPalindrome(head) + " | ");
		System.out.print(isPalindrome2(head) + " | ");
		System.out.println(isPalindrome3(head) + " | ");
		printLinkedList(head);
		System.out.println("=========================");

	}

}

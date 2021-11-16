package class12;

import java.util.ArrayList;

public class Code02_IsBST {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBST1(Node head) {
		if (head == null) {
			return true;
		}
		ArrayList<Node> arr = new ArrayList<>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return false;
			}
		}
		return true;
	}

	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}

	public static boolean isBST2(Node head) {
		if (head == null) {
			return true;
		}
		return process(head).isBST;
	}

	public static class Info {
		public boolean isBST;
		public int max;
		public int min;

		public Info(boolean i, int ma, int mi) {
			isBST = i;
			max = ma;
			min = mi;
		}

	}

	public static Info process(Node x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		int max = x.value;
		if (leftInfo != null) {
			max = Math.max(max, leftInfo.max);
		}
		if (rightInfo != null) {
			max = Math.max(max, rightInfo.max);
		}
		int min = x.value;
		if (leftInfo != null) {
			min = Math.min(min, leftInfo.min);
		}
		if (rightInfo != null) {
			min = Math.min(min, rightInfo.min);
		}
		boolean isBST = true;
		if (leftInfo != null && !leftInfo.isBST) {
			isBST = false;
		}
		if (rightInfo != null && !rightInfo.isBST) {
			isBST = false;
		}
		if (leftInfo != null && leftInfo.max >= x.value) {
			isBST = false;
		}
		if (rightInfo != null && rightInfo.min <= x.value) {
			isBST = false;
		}
		return new Info(isBST, max, min);
	}



	//my code
	public static boolean myisBST(Node head){
		if(head == null){
			return true;
		}
		return myProcess(head).isBST;
	}

	public static class myInfo{
		private boolean isBST;
		private int min;
		private int max;
		public myInfo(boolean isBST, int min, int max){
			this.isBST = isBST;
			this.min = min;
			this.max = max;
		}
	}

	public static myInfo myProcess(Node head){
		myInfo leftInfo = null;
		myInfo rightInfo = null;
		if(head.left != null){
			leftInfo = myProcess(head.left);
		}
		if(head.right != null){
			rightInfo = myProcess(head.right);
		}
		boolean isBST = true;


		int min = head.value;
		int max = head.value;

		if(leftInfo != null){
			if(leftInfo.max >= head.value || leftInfo.isBST == false){
				isBST = false;
			}
			min = Math.min(min, leftInfo.min);
			max = Math.max(max, leftInfo.max);
		}
		if(rightInfo != null){
			if(rightInfo.min <= head.value || rightInfo.isBST == false) {
				isBST = false;
			}
			min = Math.min(min, rightInfo.min);
			max = Math.max(max, rightInfo.max);
		}
		return new myInfo(isBST, min, max);
	}






	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
//			if (isBST1(head) != isBST2(head)) {
			if (myisBST(head) != isBST2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}

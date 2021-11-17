package class13;

import java.util.ArrayList;

public class Code02_MaxSubBSTHead {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static int getBSTSize(Node head) {
		if (head == null) {
			return 0;
		}
		ArrayList<Node> arr = new ArrayList<>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return 0;
			}
		}
		return arr.size();
	}
	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}

	public static Node maxSubBSTHead1(Node head) {
		if (head == null) {
			return null;
		}
		if (getBSTSize(head) != 0) {
			return head;
		}
		Node leftAns = maxSubBSTHead1(head.left);
		Node rightAns = maxSubBSTHead1(head.right);
		return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
	}

	public static Node maxSubBSTHead2(Node head) {
		if (head == null) {
			return null;
		}
		return process(head).maxSubBSTHead;
	}

	// 每一棵子树
	public static class Info {
		public Node maxSubBSTHead;
		public int maxSubBSTSize;
		public int min;
		public int max;

		public Info(Node h, int size, int mi, int ma) {
			maxSubBSTHead = h;
			maxSubBSTSize = size;
			min = mi;
			max = ma;
		}
	}

	public static Info process(Node X) {
		if (X == null) {
			return null;
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);
		int min = X.value;
		int max = X.value;
		Node maxSubBSTHead = null;
		int maxSubBSTSize = 0;
		if (leftInfo != null) {
			min = Math.min(min, leftInfo.min);
			max = Math.max(max, leftInfo.max);
			maxSubBSTHead = leftInfo.maxSubBSTHead;
			maxSubBSTSize = leftInfo.maxSubBSTSize;
		}
		if (rightInfo != null) {
			min = Math.min(min, rightInfo.min);
			max = Math.max(max, rightInfo.max);
			if (rightInfo.maxSubBSTSize > maxSubBSTSize) {
				maxSubBSTHead = rightInfo.maxSubBSTHead;
				maxSubBSTSize = rightInfo.maxSubBSTSize;
			}
		}
		if ((leftInfo == null ? true : (leftInfo.maxSubBSTHead == X.left && leftInfo.max < X.value))
				&& (rightInfo == null ? true : (rightInfo.maxSubBSTHead == X.right && rightInfo.min > X.value))) {
			maxSubBSTHead = X;
			maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
					+ (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
		}
		return new Info(maxSubBSTHead, maxSubBSTSize, min, max);
	}



	//my code
	public static Node myMaxSubBSTHead(Node head){
		if(head == null){
			return null;
		}
		return myProcess(head).maxNode;
	}

	public static class myInfo{
		private int size;
		private boolean isBST;
		private Node maxNode;
		private int max;
		private int min;
		public myInfo(int size, boolean isBST, Node maxNode, int max, int min){
			this.size = size;
			this.isBST = isBST;
			this.maxNode = maxNode;
			this.max = max;
			this.min = min;
		}
	}

	public static myInfo myProcess(Node head){
		if(head == null){
			return new myInfo(0,true, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
		}
		myInfo leftInfo = myProcess(head.left);
		myInfo rightInfo = myProcess(head.right);

		int max = head.value;
		int min = head.value;

		if(leftInfo.isBST && rightInfo.isBST){
			if(leftInfo.max < head.value && rightInfo.min > head.value){
				return new myInfo(leftInfo.size + rightInfo.size + 1, true, head, Math.max(Math.max(leftInfo.max, rightInfo.max), max), Math.min(Math.min(leftInfo.min, rightInfo.min),min));
			}else{
				//include self then not bst
				if(leftInfo.size < rightInfo.size){
					return new myInfo(rightInfo.size, false, rightInfo.maxNode, rightInfo.max, rightInfo.min);
				}else{
					return new myInfo(leftInfo.size, false, leftInfo.maxNode, leftInfo.max, leftInfo.min);
				}
			}
		}
		if(leftInfo.size < rightInfo.size){
			return new myInfo(rightInfo.size, false, rightInfo.maxNode, rightInfo.max, rightInfo.min);
		}else{
			return new myInfo(leftInfo.size, false, leftInfo.maxNode, leftInfo.max, leftInfo.min);
		}


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

	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
//			if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
			if (myMaxSubBSTHead(head) != maxSubBSTHead2(head)) {
				System.out.println("Oops!");
				printTree(head);
				break;
			}
		}
		System.out.println("finish!");
	}

}

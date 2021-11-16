package class12;

public class Code03_IsBalanced {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBalanced1(Node head) {
		boolean[] ans = new boolean[1];
		ans[0] = true;
		process1(head, ans);
		return ans[0];
	}

	public static int process1(Node head, boolean[] ans) {
		if (!ans[0] || head == null) {
			return -1;
		}
		int leftHeight = process1(head.left, ans);
		int rightHeight = process1(head.right, ans);
		if (Math.abs(leftHeight - rightHeight) > 1) {
			ans[0] = false;
		}
		return Math.max(leftHeight, rightHeight) + 1;
	}

	public static boolean isBalanced2(Node head) {
		return process(head).isBalanced;
	}
	
	public static class Info{
		public boolean isBalanced;
		public int height;
		
		public Info(boolean i, int h) {
			isBalanced = i;
			height = h;
		}
	}
	
	public static Info process(Node x) {
		if(x == null) {
			return new Info(true, 0);
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		int height = Math.max(leftInfo.height, rightInfo.height)  + 1;
		boolean isBalanced = true;
		if(!leftInfo.isBalanced) {
			isBalanced = false;
		}
		if(!rightInfo.isBalanced) {
			isBalanced = false;
		}
		if(Math.abs(leftInfo.height - rightInfo.height) > 1) {
			isBalanced = false;
		}
		return new Info(isBalanced, height);
	}





	//my code
	public static boolean isBalanced(Node head){
		if(head == null){
			return true;
		}
		return myProcess(head).isBalanced;
	}


	public static class myInfo{
		boolean isBalanced;
		int height;
		public myInfo(boolean isBalanced, int height){
			this.isBalanced = isBalanced;
			this.height = height;
		}
	}

	public static myInfo myProcess(Node head){
		if(head == null){
			return null;
		}
		myInfo leftTreeInfo = myProcess(head.left);
		myInfo rightTreeInfo = myProcess(head.right);
		int lheight = leftTreeInfo == null ? 0 : leftTreeInfo.height;
		int rheight = rightTreeInfo == null ? 0 : rightTreeInfo.height;
		int height = Math.max(lheight, rheight);

		boolean isBalanced = true;
		if(leftTreeInfo != null){
			if(leftTreeInfo.isBalanced == false){
				isBalanced = false;
			}
		}
		if(rightTreeInfo != null){
			if(rightTreeInfo.isBalanced == false){
				isBalanced = false;
			}
		}
		if(Math.abs(lheight - rheight) > 1){
			isBalanced = false;
		}
		return new myInfo(isBalanced, height + 1);
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
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
//			if (isBalanced1(head) != isBalanced2(head)) {
			if (isBalanced(head) != isBalanced2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}

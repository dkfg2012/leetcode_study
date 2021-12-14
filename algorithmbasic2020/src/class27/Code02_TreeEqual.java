package class27;

import java.util.ArrayList;

public class Code02_TreeEqual {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	public static boolean containsTree1(Node big, Node small) {
		if (small == null) {
			return true;
		}
		if (big == null) {
			return false;
		}
		if (isSameValueStructure(big, small)) {
			return true;
		}
		return containsTree1(big.left, small) || containsTree1(big.right, small);
	}

	public static boolean isSameValueStructure(Node head1, Node head2) {
		if (head1 == null && head2 != null) {
			return false;
		}
		if (head1 != null && head2 == null) {
			return false;
		}
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1.value != head2.value) {
			return false;
		}
		return isSameValueStructure(head1.left, head2.left) 
				&& isSameValueStructure(head1.right, head2.right);
	}

	public static boolean containsTree2(Node big, Node small) {
		if (small == null) {
			return true;
		}
		if (big == null) {
			return false;
		}
		ArrayList<String> b = preSerial(big);
		ArrayList<String> s = preSerial(small);
		String[] str = new String[b.size()];
		for (int i = 0; i < str.length; i++) {
			str[i] = b.get(i);
		}

		String[] match = new String[s.size()];
		for (int i = 0; i < match.length; i++) {
			match[i] = s.get(i);
		}
		return KMP(str, match) != -1;
	}

	public static ArrayList<String> preSerial(Node head) {
		ArrayList<String> ans = new ArrayList<>();
		pres(head, ans);
		return ans;
	}

	public static void pres(Node head, ArrayList<String> ans) {
		if (head == null) {
			ans.add(null);
		} else {
			ans.add(String.valueOf(head.value));
			pres(head.left, ans);
			pres(head.right, ans);
		}
	}

	public static int getIndexOf(String[] str1, String[] str2) {
		if (str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
			return -1;
		}
		int x = 0;
		int y = 0;
		int[] next = getNextArray(str2);
		while (x < str1.length && y < str2.length) {
			if (isEqual(str1[x], str2[y])) {
				x++;
				y++;
			} else if (next[y] == -1) {
				x++;
			} else {
				y = next[y];
			}
		}
		return y == str2.length ? x - y : -1;
	}

	public static int[] getNextArray(String[] ms) {
		if (ms.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[ms.length];
		next[0] = -1;
		next[1] = 0;
		int i = 2;
		int cn = 0;
		while (i < next.length) {
			if (isEqual(ms[i - 1], ms[cn])) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

	public static boolean isEqual(String a, String b) {
		if (a == null && b == null) {
			return true;
		} else {
			if (a == null || b == null) {
				return false;
			} else {
				return a.equals(b);
			}
		}
	}



	//my code
	public static boolean myContainTree(Node big, Node small){
		if (small == null) {
			return true;
		}
		if (big == null) {
			return false;
		}
		ArrayList<String> tree1 = myPostOrder(big);
		String[] t1 = new String[tree1.size()];
		for(int i = 0; i < tree1.size(); i++){
			t1[i] = tree1.get(i);
		}
		ArrayList<String> tree2 = myPostOrder(small);
		String[] t2 = new String[tree2.size()];
		for(int i = 0; i < tree2.size(); i++){
			t2[i] = tree2.get(i);
		}
		return KMP(t1, t2) != -1;
	}

	public static ArrayList<String> myPostOrder(Node node){
		ArrayList<String> r = new ArrayList<>();
		process(node, r);
		return r;
	}

	public static void process(Node head, ArrayList<String> arrayList){
		if(head == null){
			arrayList.add(null);
			return;
		}
		process(head.left, arrayList);
		arrayList.add(String.valueOf(head.value));
		process(head.right, arrayList);
		arrayList.add("E");
		return;
	}

	public static int[] nexts(String[] str){
		if(str.length == 1){
			return new int[] { -1 };
		}
		int[] next = new int[str.length];
		next[0] = -1;
		next[1] = 0;
		int cn = 0;
		for(int index = 2; index < str.length; index++){
			if(isEqual(str[index-1], str[cn])){
				next[index] = cn + 1;
				cn++;
			}else if(cn > 0){
				cn = next[cn];
				index--;
			}else{
				next[index] = 0;
			}
		}
		return next;
	}

	public static int KMP(String[] str1, String[] str2){
		if (str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
			return -1;
		}
		int x = 0;
		int y = 0;
		int[] next = nexts(str2);
		while(x < str1.length && y < str2.length){
			if(isEqual(str1[x], str2[y])){
				x++;
				y++;
			}else if(next[y] == -1){
				x++;
			}else{
				y = next[y];
			}
		}
		return y == str2.length ? x - y : -1;
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

	public static void printPreOrder(Node head){
		if(head == null){
			System.out.print("N");
			return;
		}
		System.out.print(String.valueOf(head.value));
		printPreOrder(head.left);
		printPreOrder(head.right);
		return;
	}

	public static void printInOrder(Node head){
		if(head == null){
			System.out.print("N");
			return;
		}
		printInOrder(head.left);
		System.out.print(String.valueOf(head.value));
		printInOrder(head.right);
		return;
	}

	public static void printPostOrder(Node head){
		if(head == null){
			System.out.print("N");
			return;
		}
		printPostOrder(head.left);
		printPostOrder(head.right);
		System.out.print(String.valueOf(head.value));

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
		int bigTreeLevel = 7;
		int smallTreeLevel = 4;
		int nodeMaxValue = 5;
		int testTimes = 1000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			Node big = generateRandomBST(bigTreeLevel, nodeMaxValue);
			Node small = generateRandomBST(smallTreeLevel, nodeMaxValue);
			boolean ans1 = containsTree1(big, small);
			boolean ans3 = myContainTree(big, small);
			boolean ans2 = containsTree2(big, small);
			if (ans1 != ans2 || ans2 != ans3) {
				System.out.println("Oops!");
				printPreOrder(big);
				System.out.println("");
				printInOrder(big);
				System.out.println("");
				printPostOrder(big);
				System.out.println("");
				printTree(big);
				System.out.println("------");
				printPreOrder(small);
				System.out.println("");
				printInOrder(small);
				System.out.println("");
				printPostOrder(small);
				System.out.println("");
				printTree(small);
				System.out.println("---");
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("");
		System.out.println("test finish!");

	}

}

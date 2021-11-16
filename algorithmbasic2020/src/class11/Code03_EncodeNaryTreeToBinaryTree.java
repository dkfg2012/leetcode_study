package class11;

import java.util.ArrayList;
import java.util.List;

// 本题测试链接：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
public class Code03_EncodeNaryTreeToBinaryTree {

	// 提交时不要提交这个类
	public static class Node {
		public int val;
		public List<Node> children;

		public Node() {
		}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	};

	// 提交时不要提交这个类
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	// 只提交这个类即可
	class Codec {
		// Encodes an n-ary tree to a binary tree.
		public TreeNode encode(Node root) {
			if (root == null) {
				return null;
			}
			TreeNode head = new TreeNode(root.val);
			head.left = en(root.children);
			return head;
		}

		private TreeNode en(List<Node> children) {
			TreeNode head = null;
			TreeNode cur = null;
			for (Node child : children) {
				TreeNode tNode = new TreeNode(child.val);
				if (head == null) {
					head = tNode;
				} else {
					cur.right = tNode;
				}
				cur = tNode;
				cur.left = en(child.children);
			}
			return head;
		}

		// Decodes your binary tree to an n-ary tree.
		public Node decode(TreeNode root) {
			if (root == null) {
				return null;
			}
			return new Node(root.val, de(root.left));
		}

		public List<Node> de(TreeNode root) {
			List<Node> children = new ArrayList<>();
			while (root != null) {
				Node cur = new Node(root.val, de(root.left));
				children.add(cur);
				root = root.right;
			}
			return children;
		}







		//my code
		//to transform a n array tree, we let the leftmost node be the left child of head node
		//and all other children be a linked list, be the right child of head node
		//so all right child will be the same level node of their ancestor
		public TreeNode myEncode(Node root){
			if(root == null){
				return null;
			}
			TreeNode head = new TreeNode(root.val);
			head.left = encodeProcess(root.children);
			return head;
		}

		public TreeNode encodeProcess(List<Node> children){
			TreeNode head = null;
			TreeNode cur = null;
			for(Node child: children){
				TreeNode t = new TreeNode(child.val);
				if(head == null){
					head = t;
				}else{
					cur.right = t;
				}
				cur = t; //handle its child first
				cur.left = encodeProcess(child.children);
			}
			return head;
		}


		public Node myDecode(TreeNode root){
			if(root == null){
				return null;
			}
			Node head = new Node(root.val);
			head.children = decodeProcess(root.left);
			return head;
		}

		public List<Node> decodeProcess(TreeNode child){
			List<Node> l = new ArrayList<>();
//			Node leftMost = new Node(child.val);
//			leftMost.children = decodeProcess(child.left);
//			l.add(leftMost);
//			TreeNode sameL = child.right;
//			while(sameL != null){
//				Node sameLevel = new Node(sameL.val);
//				sameLevel.children = decodeProcess(sameL.left);
//				l.add(sameLevel);
//				sameL = sameL.right;
//			}
			while(child != null){
				Node cur = new Node(child.val);
				cur.children = decodeProcess(child.right);
				l.add(cur);
				child = child.right;
			}
			return l;
		}



		//

	}

}

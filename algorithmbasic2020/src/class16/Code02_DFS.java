package class16;

import java.util.HashSet;
import java.util.Stack;

public class Code02_DFS {

	public static void dfs(Node node) {
		if (node == null) {
			return;
		}
		Stack<Node> stack = new Stack<>();
		HashSet<Node> set = new HashSet<>();
		stack.add(node);
		set.add(node);
		System.out.println(node.value);
		while (!stack.isEmpty()) {
			Node cur = stack.pop();
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					stack.push(cur);
					stack.push(next);
					set.add(next);
					System.out.println(next.value);
					break;
				}
			}
		}
	}
	


	//my code
	public static void myDfs(Node head){
		if(head == null){
			return;
		}
		Stack<Node> st = new Stack<>();
		HashSet<Node> hs = new HashSet<>();
		st.add(head);
		hs.add(head);
		while(!st.isEmpty()){
			Node cur = st.pop();
			System.out.println("do sth");
			for(Node next: cur.nexts){
				if(!hs.contains(next)){
					st.push(cur);
					st.push(next);
					hs.add(next);
					break;
				}
			}
		}
	}

	

}

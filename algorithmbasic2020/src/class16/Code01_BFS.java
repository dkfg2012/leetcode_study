package class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Code01_BFS {

	// 从node出发，进行宽度优先遍历
	public static void bfs(Node start) {
		if (start == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		HashSet<Node> set = new HashSet<>();
		queue.add(start);
		set.add(start);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			System.out.println(cur.value);
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
				}
			}
		}
	}


	//my code
	public static void myBfs(Node head){
		if(head == null){
			return;
		}
		Queue<Node> q = new LinkedList<>();
		HashSet<Node> s = new HashSet<>();
		q.add(head);
		s.add(head);
		while(!q.isEmpty()){
			Node cur = q.poll();
			System.out.println("do something");
			for(Node next: cur.nexts){
				if(!s.contains(next)){
					q.add(next);
					s.add(next);
				}
			}
		}
	}

}

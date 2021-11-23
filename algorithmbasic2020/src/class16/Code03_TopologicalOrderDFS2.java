package class16;

import java.util.*;

// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class Code03_TopologicalOrderDFS2 {

	// 不要提交这个类
	public static class DirectedGraphNode {
		public int label;
		public ArrayList<DirectedGraphNode> neighbors;

		public DirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<DirectedGraphNode>();
		}
	}

	// 提交下面的
	public static class Record {
		public DirectedGraphNode node;
		public long nodes;

		public Record(DirectedGraphNode n, long o) {
			node = n;
			nodes = o;
		}
	}

	public static class MyComparator implements Comparator<Record> {

		@Override
		public int compare(Record o1, Record o2) {
			return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
		}
	}

	public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		HashMap<DirectedGraphNode, Record> order = new HashMap<>();
		for (DirectedGraphNode cur : graph) {
			f(cur, order);
		}
		ArrayList<Record> recordArr = new ArrayList<>();
		for (Record r : order.values()) {
			recordArr.add(r);
		}
		recordArr.sort(new MyComparator());
		ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
		for (Record r : recordArr) {
			ans.add(r.node);
		}
		return ans;
	}

	// 当前来到cur点，请返回cur点所到之处，所有的点次！
	// 返回（cur，点次）
	// 缓存！！！！！order   
	//  key : 某一个点的点次，之前算过了！
	//  value : 点次是多少
	public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
		if (order.containsKey(cur)) {
			return order.get(cur);
		}
		// cur的点次之前没算过！
		long nodes = 0;
		for (DirectedGraphNode next : cur.neighbors) {
			nodes += f(next, order).nodes;
		}
		Record ans = new Record(cur, nodes + 1);
		order.put(cur, ans);
		return ans;
	}




	//my code
	public static ArrayList<DirectedGraphNode> myTopSort(ArrayList<DirectedGraphNode> graph){
		HashMap<DirectedGraphNode, Boolean> visited = new HashMap<>();
		Stack<DirectedGraphNode> st = new Stack<>();

		for(DirectedGraphNode node : graph){
			visited.put(node, false);
		}
		for(DirectedGraphNode node : graph){
			if(visited.get(node) == false){
				helpFunction(node, visited, st);
			}
		}
		ArrayList<DirectedGraphNode> r = new ArrayList<>();
		while(!st.isEmpty()){
			DirectedGraphNode cur = st.pop();
			r.add(cur);
		}
		return r;
	}

	public static void helpFunction(DirectedGraphNode node, HashMap<DirectedGraphNode, Boolean> visited, Stack<DirectedGraphNode> st){
		visited.put(node, true);
		for(DirectedGraphNode next : node.neighbors){
			if(visited.get(next) == false){
				helpFunction(next, visited, st);
			}
		}
		st.push(node);
	}

}

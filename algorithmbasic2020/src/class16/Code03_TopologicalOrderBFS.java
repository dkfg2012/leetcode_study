package class16;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class Code03_TopologicalOrderBFS {

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
	public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
		HashMap<DirectedGraphNode, Integer> indegreeMap = new HashMap<>();
		for (DirectedGraphNode cur : graph) {
			indegreeMap.put(cur, 0);
		}
		for (DirectedGraphNode cur : graph) {
			for (DirectedGraphNode next : cur.neighbors) {
				indegreeMap.put(next, indegreeMap.get(next) + 1);
			}
		}
		Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
		for (DirectedGraphNode cur : indegreeMap.keySet()) {
			if (indegreeMap.get(cur) == 0) {
				zeroQueue.add(cur);
			}
		}
		ArrayList<DirectedGraphNode> ans = new ArrayList<>();
		while (!zeroQueue.isEmpty()) {
			DirectedGraphNode cur = zeroQueue.poll();
			ans.add(cur);
			for (DirectedGraphNode next : cur.neighbors) {
				indegreeMap.put(next, indegreeMap.get(next) - 1);
				if (indegreeMap.get(next) == 0) {
					zeroQueue.offer(next);
				}
			}
		}
		return ans;
	}

	//my code
	public static ArrayList<DirectedGraphNode> myBFSTopSort(ArrayList<DirectedGraphNode> graph){
		HashMap<DirectedGraphNode, Integer> hm = new HashMap<>();
		//initialization
		for(DirectedGraphNode node : graph){
			hm.put(node, 0);
		}
		for(DirectedGraphNode node : graph){
			for(DirectedGraphNode next: node.neighbors){
				hm.put(next, hm.get(next) + 1);
			}
		}
		Queue<DirectedGraphNode> q = new LinkedList<>();
		ArrayList<DirectedGraphNode> r = new ArrayList<>();
		for(DirectedGraphNode node : graph){
			if(hm.get(node) == 0){
				q.offer(node);
			}
		}
		while(!q.isEmpty()){
			DirectedGraphNode cur = q.poll();
			r.add(cur);
			for(DirectedGraphNode node : cur.neighbors){
				int times = hm.get(node) - 1;
				if(times == 0){
					q.offer(node);
				}
				hm.put(node, times);
			}
		}
		return r;
	}

}

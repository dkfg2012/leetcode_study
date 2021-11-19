package class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code05_UnionFind {

	public static class Node<V> {
		V value;

		public Node(V v) {
			value = v;
		}
	}

	public static class UnionFind<V> {
		public HashMap<V, Node<V>> nodes;
		public HashMap<Node<V>, Node<V>> parents;
		public HashMap<Node<V>, Integer> sizeMap;

		public UnionFind(List<V> values) {
			nodes = new HashMap<>();
			parents = new HashMap<>();
			sizeMap = new HashMap<>();
			for (V cur : values) {
				Node<V> node = new Node<>(cur);
				nodes.put(cur, node);
				parents.put(node, node);
				sizeMap.put(node, 1);
			}
		}

		// 给你一个节点，请你往上到不能再往上，把代表返回
		public Node<V> findFather(Node<V> cur) {
			Stack<Node<V>> path = new Stack<>();
			while (cur != parents.get(cur)) {
				path.push(cur);
				cur = parents.get(cur);
			}
			while (!path.isEmpty()) {
				parents.put(path.pop(), cur);
			}
			return cur;
		}

		public boolean isSameSet(V a, V b) {
			return findFather(nodes.get(a)) == findFather(nodes.get(b));
		}

		public void union(V a, V b) {
			Node<V> aHead = findFather(nodes.get(a));
			Node<V> bHead = findFather(nodes.get(b));
			if (aHead != bHead) {
				int aSetSize = sizeMap.get(aHead);
				int bSetSize = sizeMap.get(bHead);
				Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
				Node<V> small = big == aHead ? bHead : aHead;
				parents.put(small, big);
				sizeMap.put(big, aSetSize + bSetSize);
				sizeMap.remove(small);
			}
		}

		public int sets() {
			return sizeMap.size();
		}

	}



	//my code
	public static class myNode<T>{
		T value;
		public myNode(T value){
			this.value = value;
		}
	}


	public static class myUnionFind<V>{
		private HashMap<V, Node<V>> nodes;
		private HashMap<Node<V>, Node<V>> parents;
		private HashMap<Node<V>, Integer> sizeMap;

		public myUnionFind(List<V> values){
			this.nodes = nodes;
			this.parents = parents;
			this.sizeMap = sizeMap;
			for(V value : values){
				Node<V> node = new Node<>(value);
				nodes.put(value, node);
				parents.put(node, node);
				sizeMap.put(node, 1);
			}
		}

		public Node<V> findFather(Node<V> cur){
			Stack<Node<V>> s = new Stack<>();
			while(cur != parents.get(cur)){
				s.push(cur);
				cur = parents.get(cur);
			}
			while(!s.isEmpty()){
				parents.put(s.pop(), cur);
			}
			return cur;
		}

		public boolean isSameSet(V a, V b){
			return findFather(nodes.get(a)) == findFather(nodes.get(b));
		}

		public int sets(){
			return sizeMap.size();
		}

		public void union(V a, V b){
			Node<V> ahead = findFather(nodes.get(a));
			Node<V> bhead = findFather(nodes.get(b));
			if(ahead != bhead){
				int aSize = sizeMap.get(ahead);
				int bSize = sizeMap.get(bhead);
				Node<V> largerhead = aSize > bSize ? ahead : bhead;
				Node<V> smallerhead = largerhead == ahead ? bhead : ahead;
				parents.put(smallerhead, largerhead);
				sizeMap.put(largerhead, aSize + bSize);
				sizeMap.remove(smallerhead);
			}
		}

	}





}

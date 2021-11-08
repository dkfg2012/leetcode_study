package class04;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_LinkedListToQueueAndStack {

	public static class Node<V> {
		public V value;
		public Node<V> next;

		public Node(V v) {
			value = v;
			next = null;
		}
	}

	public static class MEQueue<V>{
		public Node<V> head;
		public Node<V> tail;
		public int size;

		public MEQueue(){
			head = null;
			tail = null;
			size = 0;
		}

		public boolean isEmpty(){
			return size == 0;
		}

		public int size(){
			return size;
		}

		public void offer(V v){
			Node n = new Node(v);
			if(isEmpty()){
				head = n;
				tail = n;
			}else{
				tail.next = n;
				tail = tail.next;
			}
			size++;
		}

		public V poll(){
			V r = null;
			if(! isEmpty()){
				r = head.value;
				head = head.next;
				size--;
			}
			return r;
		}
		public V peek(){
			if(! isEmpty()){
				return head.value;
			}else{
				return null;
			}
		}

	}

	public static class MyQueue<V> {
		private Node<V> head;
		private Node<V> tail;
		private int size;

		public MyQueue() {
			head = null;
			tail = null;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public int size() {
			return size;
		}

		public void offer(V value) {
			Node<V> cur = new Node<V>(value);
			if (tail == null) {
				head = cur;
				tail = cur;
			} else {
				tail.next = cur;
				tail = cur;
			}
			size++;
		}

		// C/C++的同学需要做节点析构的工作
		public V poll() {
			V ans = null;
			if (head != null) {
				ans = head.value;
				head = head.next;
				size--;
			}
			if (head == null) {
				tail = null;
			}
			return ans;
		}

		// C/C++的同学需要做节点析构的工作
		public V peek() {
			V ans = null;
			if (head != null) {
				ans = head.value;
			}
			return ans;
		}

	}


	public static class MEStack<V>{
		public Node<V> tail;
		public int size;

		public MEStack(){
			tail = null;
			size = 0;
		}

		public boolean isEmpty(){
			return size == 0 ? true : false;
		}

		public int size(){
			return size;
		}

		public void push(V value){
			Node<V> n = new Node<>(value);
			if(isEmpty()){
				tail = n;
			}else{
				n.next = tail;
				tail = n;
			}
			size++;
		}

		public V pop(){
			V r = null;
			if(!isEmpty()){
				if(tail.next == null){
					r = tail.value;
					tail = null;
				}else{
					r = tail.value;
					tail = tail.next;
				}
				size--;
			}
			return r;
		}

		public V peek(){
			return tail == null ? null : tail.value;
		}
	}

	public static class MyStack<V> {
		private Node<V> head;
		private int size;

		public MyStack() {
			head = null;
			size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		public int size() {
			return size;
		}

		public void push(V value) {
			Node<V> cur = new Node<>(value);
			if (head == null) {
				head = cur;
			} else {
				cur.next = head;
				head = cur;
			}
			size++;
		}

		public V pop() {
			V ans = null;
			if (head != null) {
				ans = head.value;
				head = head.next;
				size--;
			}
			return ans;
		}

		public V peek() {
			return head != null ? head.value : null;
		}

	}

	public static void testQueue() {
//		MyQueue<Integer> myQueue = new MyQueue<>();
		MEQueue<Integer> myQueue = new MEQueue<>();
		Queue<Integer> test = new LinkedList<>();
		int testTime = 5000000;
		int maxValue = 200000000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			if (myQueue.isEmpty() != test.isEmpty()) {
				System.out.println("Oops!");
			}
			if (myQueue.size() != test.size()) {
				System.out.println("Oops!");
			}
			double decide = Math.random();
			if (decide < 0.33) {
				int num = (int) (Math.random() * maxValue);
				myQueue.offer(num);
				test.offer(num);
			} else if (decide < 0.66) {
				if (!myQueue.isEmpty()) {
					int num1 = myQueue.poll();
					int num2 = test.poll();
					if (num1 != num2) {
						System.out.println("Oops!");
					}
				}
			} else {
				if (!myQueue.isEmpty()) {
					int num1 = myQueue.peek();
					int num2 = test.peek();
					if (num1 != num2) {
						System.out.println("Oops!");
					}
				}
			}
		}
		if (myQueue.size() != test.size()) {
			System.out.println("Oops!");
		}
		while (!myQueue.isEmpty()) {
			int num1 = myQueue.poll();
			int num2 = test.poll();
			if (num1 != num2) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束！");
	}

	public static void testStack() {
//		MyStack<Integer> myStack = new MyStack<>();
		MEStack<Integer> myStack = new MEStack<>();
		Stack<Integer> test = new Stack<>();
		int testTime = 5000000;
		int maxValue = 200000000;
		System.out.println("测试开始！");
		for (int i = 0; i < testTime; i++) {
			if (myStack.isEmpty() != test.isEmpty()) {
				System.out.println("Oops!");
			}
			if (myStack.size() != test.size()) {
				System.out.println("Oops!");
			}
			double decide = Math.random();
			if (decide < 0.33) {
				int num = (int) (Math.random() * maxValue);
				myStack.push(num);
				test.push(num);
			} else if (decide < 0.66) {
				if (!myStack.isEmpty()) {
					int num1 = myStack.pop();
					int num2 = test.pop();
					if (num1 != num2) {
						System.out.println("Oops!");
					}
				}
			} else {
				if (!myStack.isEmpty()) {
					int num1 = myStack.peek();
					int num2 = test.peek();
					if (num1 != num2) {
						System.out.println("Oops!");
					}
				}
			}
		}
		if (myStack.size() != test.size()) {
			System.out.println("Oops!");
		}
		while (!myStack.isEmpty()) {
			int num1 = myStack.pop();
			int num2 = test.pop();
			if (num1 != num2) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束！");
	}

	public static void main(String[] args) {
//		testQueue();
		testStack();
	}

}

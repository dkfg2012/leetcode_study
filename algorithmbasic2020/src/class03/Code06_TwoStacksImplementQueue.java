package class03;

import java.util.Stack;

public class Code06_TwoStacksImplementQueue {

	public static class TwoStacksQueue {
		public Stack<Integer> stackPush;
		public Stack<Integer> stackPop;

		public TwoStacksQueue() {
			stackPush = new Stack<Integer>();
			stackPop = new Stack<Integer>();
		}

		// push栈向pop栈倒入数据
		private void pushToPop() {
			if (stackPop.empty()) {
				while (!stackPush.empty()) {
					stackPop.push(stackPush.pop());
				}
			}
		}

		public void add(int pushInt) {
			stackPush.push(pushInt);
			pushToPop();
		}

		public int poll() {
			if (stackPop.empty() && stackPush.empty()) {
				throw new RuntimeException("Queue is empty!");
			}
			pushToPop();
			return stackPop.pop();
		}

		public int peek() {
			if (stackPop.empty() && stackPush.empty()) {
				throw new RuntimeException("Queue is empty!");
			}
			pushToPop();
			return stackPop.peek();
		}
	}

	//my code
	public static class myTwoStackQueue{
		private Stack<Integer> pushSt;
		private Stack<Integer> pollSt;
		public myTwoStackQueue(){
			this.pushSt = new Stack<Integer>();
			this.pollSt = new Stack<Integer>();
		}

		public void add(int value){
			if(pollSt.isEmpty()){
				while(!pushSt.isEmpty()){
					int t = pushSt.pop();
					pollSt.push(t);
				}
			}else{
				pushSt.push(value);
			}
		}

		public int poll(){
			if(!pollSt.isEmpty()){
				int t = pollSt.pop();
				return t;
			}else{
				while(!pushSt.isEmpty()){
					int t = pushSt.pop();
					pollSt.push(t);
				}
				int t = pollSt.pop();
				return t;
			}
		}

		public int peek(){
			if (pushSt.empty() && pollSt.empty()) {
				throw new RuntimeException("Queue is empty!");
			}
			if(!pollSt.isEmpty()){
				int t = pollSt.peek();
				return t;
			}else{
				while(!pushSt.isEmpty()){
					int t = pushSt.pop();
					pollSt.push(t);
				}
				int t = pollSt.peek();
				return t;
			}
		}
	}

	public static void main(String[] args) {
		myTwoStackQueue test = new myTwoStackQueue();
		test.add(1);
		test.add(2);
		test.add(3);
		System.out.println(test.peek());
		System.out.println(test.poll());
		System.out.println(test.peek());
		System.out.println(test.poll());
		System.out.println(test.peek());
		System.out.println(test.poll());
	}

}

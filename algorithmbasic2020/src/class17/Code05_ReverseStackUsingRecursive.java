package class17;

import java.util.Stack;

public class Code05_ReverseStackUsingRecursive {

	public static void reverse(Stack<Integer> stack) {
		if (stack.isEmpty()) {
			return;
		}
		int i = f(stack);
		reverse(stack);
		stack.push(i);
	}

	// 栈底元素移除掉
	// 上面的元素盖下来
	// 返回移除掉的栈底元素
	public static int f(Stack<Integer> stack) {
		int result = stack.pop();
		if (stack.isEmpty()) {
			return result;
		} else {
			int last = f(stack);
			stack.push(result);
			return last;
		}
	}


	//my code
	//透過遞歸，把stack的最前int拿出來,然後原樣返回，知道全都拿出來了，就再按遞歸的反順序放回去，例如最後拿出5就最先把5放回去
	public static void myReverse(Stack<Integer> stack){
		if(stack.isEmpty()){
			return;
		}
		int i = myProcess(stack);
		myReverse(stack); //first recurse, get 1, second recurse get 2 ...
		stack.push(i);
	}

	public static int myProcess(Stack<Integer> s){
		int i = s.pop();
		if(s.isEmpty()){
			return i;
		}else{
			int j = myProcess(s); //j is i when s.empty, so must be the int that add earliest
			s.push(i);
			return j;
		}
	}

	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		test.push(1);
		test.push(2);
		test.push(3);
		test.push(4);
		test.push(5);
//		reverse(test);
		myReverse(test);
		while (!test.isEmpty()) {
			System.out.println(test.pop());
		}

	}

}

package class24;

import java.util.LinkedList;

public class Code01_SlidingWindowMaxArray {

	// 暴力的对数器方法
	public static int[] right(int[] arr, int w) {
		if (arr == null || w < 1 || arr.length < w) {
			return null;
		}
		int N = arr.length;
		int[] res = new int[N - w + 1];
		int index = 0;
		int L = 0;
		int R = w - 1;
		while (R < N) {
			int max = arr[L];
			for (int i = L + 1; i <= R; i++) {
				max = Math.max(max, arr[i]);

			}
			res[index++] = max;
			L++;
			R++;
		}
		return res;
	}

	public static int[] getMaxWindow(int[] arr, int w) {
		if (arr == null || w < 1 || arr.length < w) {
			return null;
		}
		// qmax 窗口最大值的更新结构
		// 放下标
		LinkedList<Integer> qmax = new LinkedList<Integer>();
		int[] res = new int[arr.length - w + 1];
		int index = 0;
		for (int R = 0; R < arr.length; R++) {
			while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
				qmax.pollLast();
			}
			qmax.addLast(R);
			if (qmax.peekFirst() == R - w) {
				qmax.pollFirst();
			}
			if (R >= w - 1) {
				res[index++] = arr[qmax.peekFirst()];
			}
		}
		return res;
	}


	// my code
	public static int[] myGetMaxWindow(int[] arr, int w){
	 	if (arr == null || w < 1 || arr.length < w) {
			return null;
		}
		LinkedList<Integer> ll = new LinkedList<Integer>(); //a deque
		int[] r = new int[arr.length - w + 1];
		int index = 0;
		for(int rightB = 0; rightB < arr.length; rightB++){
			//keep the deque small to large, and store index only
			while(!ll.isEmpty() && arr[rightB] >= arr[ll.peekLast()]){
				// if new coming element is larger than the last element in deque,
				// popLast until there is an element larger than the newcomer
				// such that deque remain from small to large (we do not add element from head)
				// or if all element is smaller than the newcomer, then we pop all element and
				// add newcomer at the end (and it also become the head)
				ll.pollLast();
			}
			ll.addLast(rightB);
			if(ll.peekFirst() == rightB - w){
				// head index equal to the left boundary of window (right boudary - window size)
				// which means it should no longer include in the window
				// therefore we remove it
				ll.pollFirst();
			}
			if(rightB >= w - 1){
				// when rightB <= w - 1 (w - 1 means the index that the window start moving)
				// for example when w is 3, then the first element add to the return array
				// when right boundary meet 2 (no element should add to the array when right boundary
				// locate at 0 and 1
				r[index] = arr[ll.peekFirst()];
				index++;
			}

		}
		return r;
	}



	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * (maxValue + 1));
		}
		return arr;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		int testTime = 100000;
		int maxSize = 100;
		int maxValue = 100;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int w = (int) (Math.random() * (arr.length + 1));
			int[] ans1 = getMaxWindow(arr, w);
//			int[] ans2 = right(arr, w);
			int[] ans2 = myGetMaxWindow(arr, w);
			if (!isEqual(ans1, ans2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}

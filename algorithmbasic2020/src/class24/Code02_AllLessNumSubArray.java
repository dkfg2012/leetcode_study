package class24;

import java.util.LinkedList;

public class Code02_AllLessNumSubArray {

	// 暴力的对数器方法
	public static int right(int[] arr, int sum) {
		if (arr == null || arr.length == 0 || sum < 0) {
			return 0;
		}
		int N = arr.length;
		int count = 0;
		for (int L = 0; L < N; L++) {
			for (int R = L; R < N; R++) {
				int max = arr[L];
				int min = arr[L];
				for (int i = L + 1; i <= R; i++) {
					max = Math.max(max, arr[i]);
					min = Math.min(min, arr[i]);
				}
				if (max - min <= sum) {
					count++;
				}
			}
		}
		return count;
	}

	public static int num(int[] arr, int sum) {
		if (arr == null || arr.length == 0 || sum < 0) {
			return 0;
		}
		int N = arr.length;
		int count = 0;
		LinkedList<Integer> maxWindow = new LinkedList<>();
		LinkedList<Integer> minWindow = new LinkedList<>();
		int R = 0;
		for (int L = 0; L < N; L++) {
			while (R < N) {
				while (!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= arr[R]) {
					maxWindow.pollLast();
				}
				maxWindow.addLast(R);
				while (!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[R]) {
					minWindow.pollLast();
				}
				minWindow.addLast(R);
				if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > sum) {
					break;
				} else {
					R++;
				}
			}
			count += R - L;
			if (maxWindow.peekFirst() == L) {
				maxWindow.pollFirst();
			}
			if (minWindow.peekFirst() == L) {
				minWindow.pollFirst();
			}
		}
		return count;
	}

	//my code
	public static int myGetNum(int[] arr, int sum){
		if(arr == null || arr.length == 0 || sum < 0){
			return 0;
		}
		int r = 0;
		LinkedList<Integer> minWindow = new LinkedList<>();
		LinkedList<Integer> maxWindow = new LinkedList<>();
		int rightB = 0;
		for(int leftB = 0; leftB < arr.length; leftB++){
			while(rightB < arr.length){
				while(!minWindow.isEmpty() && arr[rightB] <= arr[minWindow.peekLast()]){
					minWindow.pollLast();
				}
				minWindow.addLast(rightB);
				while(!maxWindow.isEmpty() && arr[rightB] >= arr[maxWindow.peekLast()]){
					maxWindow.pollLast();
				}
				maxWindow.addLast(rightB);
				if(arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] <= sum){
					rightB++;
				}else{
					break;
				}
			}
			// the subarray is start from leftB to rightB
			// assume 0 - 2 is a valid subarray, then 0 - 1, 0 - 0 are also valid subarray
			// then there are 3 - 0 subarray (since it is rightB++)
			r += rightB - leftB;
			if(maxWindow.peekFirst() == leftB){
				maxWindow.pollFirst();
			}
			if(minWindow.peekFirst() == leftB){
				minWindow.pollFirst();
			}
		}

		return r;
	}

	// for test
	public static int[] generateRandomArray(int maxLen, int maxValue) {
		int len = (int) (Math.random() * (maxLen + 1));
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int maxLen = 100;
		int maxValue = 200;
		int testTime = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxLen, maxValue);
			int sum = (int) (Math.random() * (maxValue + 1));
			int ans1 = right(arr, sum);
//			int ans2 = num(arr, sum);
			int ans2 = myGetNum(arr, sum);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(sum);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");

	}

}

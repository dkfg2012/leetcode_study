package class29;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Code02_MaxTopK {

	// 时间复杂度O(N*logN)
	// 排序+收集
	public static int[] maxTopK1(int[] arr, int k) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int N = arr.length;
		k = Math.min(N, k);
		Arrays.sort(arr);
		int[] ans = new int[k];
		for (int i = N - 1, j = 0; j < k; i--, j++) {
			ans[j] = arr[i];
		}
		return ans;
	}

	// 方法二，时间复杂度O(N + K*logN)
	// 解释：堆
	public static int[] maxTopK2(int[] arr, int k) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int N = arr.length;
		k = Math.min(N, k);
		// 从底向上建堆，时间复杂度O(N)
		for (int i = N - 1; i >= 0; i--) {
			heapify(arr, i, N);
		}
		// 只把前K个数放在arr末尾，然后收集，O(K*logN)
		int heapSize = N;
		swap(arr, 0, --heapSize);
		int count = 1;
		while (heapSize > 0 && count < k) {
			heapify(arr, 0, heapSize);
			swap(arr, 0, --heapSize);
			count++;
		}
		int[] ans = new int[k];
		for (int i = N - 1, j = 0; j < k; i--, j++) {
			ans[j] = arr[i];
		}
		return ans;
	}

	public static void heapInsert(int[] arr, int index) {
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	public static void heapify(int[] arr, int index, int heapSize) {
		int left = index * 2 + 1;
		while (left < heapSize) {
			int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
			largest = arr[largest] > arr[index] ? largest : index;
			if (largest == index) {
				break;
			}
			swap(arr, largest, index);
			index = largest;
			left = index * 2 + 1;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 方法三，时间复杂度O(n + k * logk)
	public static int[] maxTopK3(int[] arr, int k) {
		if (arr == null || arr.length == 0) {
			return new int[0];
		}
		int N = arr.length;
		k = Math.min(N, k);
		// O(N)
		int num = minKth(arr, N - k);
		int[] ans = new int[k];
		int index = 0;
		for (int i = 0; i < N; i++) {
			if (arr[i] > num) {
				ans[index++] = arr[i];
			}
		}
		for (; index < k; index++) {
			ans[index] = num;
		}
		// O(k*logk)
		Arrays.sort(ans);
		for (int L = 0, R = k - 1; L < R; L++, R--) {
			swap(ans, L, R);
		}
		return ans;
	}

	// 时间复杂度O(N)
	public static int minKth(int[] arr, int index) {
		int L = 0;
		int R = arr.length - 1;
		int pivot = 0;
		int[] range = null;
		while (L < R) {
			pivot = arr[L + (int) (Math.random() * (R - L + 1))];
			range = partition(arr, L, R, pivot);
			if (index < range[0]) {
				R = range[0] - 1;
			} else if (index > range[1]) {
				L = range[1] + 1;
			} else {
				return pivot;
			}
		}
		return arr[L];
	}

	public static int[] partition(int[] arr, int L, int R, int pivot) {
		int less = L - 1;
		int more = R + 1;
		int cur = L;
		while (cur < more) {
			if (arr[cur] < pivot) {
				swap(arr, ++less, cur++);
			} else if (arr[cur] > pivot) {
				swap(arr, cur, --more);
			} else {
				cur++;
			}
		}
		return new int[] { less + 1, more - 1 };
	}


	//my code
	public static int[] myTopKBFPRT(int[] arr, int k){
		if(arr == null || arr.length == 0){
			return new int[0];
		}
		int size = Math.min(arr.length, k);
		int[] r = new int[size];
		int tIndex = Math.min(k, arr.length);
		int pivot = myBFPRT(arr, 0, arr.length - 1, tIndex-1);
		int index = 0;
		for(int i = 0; i < arr.length; i++){
			if(arr[i] > pivot){
				r[index++] = arr[i];
			}
		};
		while(index < r.length){
			r[index++] = pivot;
		}
		return r;
	}

	public static int[] myPartition(int[] arr, int L, int R, int pivot){
		int LargeRightB = L - 1;
		int lessLeftB = R + 1;
		int current = L;
		while(current < lessLeftB){
			if(arr[current] < pivot){
				swap(arr, current, --lessLeftB);
			}else if(arr[current] > pivot){
				swap(arr, current++, ++LargeRightB);
			}else{
				current++;
			}
		}
		return new int[] { LargeRightB + 1, lessLeftB - 1};
	}

	public static int myBFPRT(int[] arr, int L, int R, int index){
		if(L == R){
			return arr[L];
		}
		int pivot = MedianOfMedian(arr, L, R);
		int[] range = myPartition(arr, L, R, pivot);
		if(index >= range[0] && index <= range[1]){
			return arr[index];
		}else if(index > range[1]){
			return myBFPRT(arr, range[1] + 1, R, index);
		}else{
			return myBFPRT(arr, L, range[0] - 1, index);
		}

	}

	public static int MedianOfMedian(int[] arr, int L, int R){
		int size = R - L + 1;
		int offset = size % 5 == 0 ? 0 : 1;
		int[] medianArray = new int[size / 5 + offset];
		for(int i = 0; i < medianArray.length; i++){
			int leftB = L + i * 5;
			medianArray[i] = getMedian(arr, leftB, Math.min(R, leftB + 4));
		}
		return myBFPRT(medianArray, 0, medianArray.length - 1, medianArray.length >> 1);

	}

	public static int getMedian(int[] arr, int L, int R){
		insertionSort(arr, L, R);
		return arr[(L + R) >> 1];
	}

	public static void insertionSort(int[] arr, int L, int R) {
		for (int i = L + 1; i <= R; i++) {
			for (int j = i - 1; j >= L && arr[j] < arr[j + 1]; j--) {
				swap(arr, j, j + 1);
			}
		}
	}


	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			// [-? , +?]
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		int[] a1 = copyArray(arr1);
		Arrays.sort(a1);
		int[] a2 = copyArray(arr2);
		Arrays.sort(a2);
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
//			if (arr1[i] != arr2[i]) {
//				return false;
//			}
			if (a1[i] != a2[i]) {
				return false;
			}
		}
		return true;
	}

	public static boolean contains(final int[] array, final int key) {
		Arrays.sort(array);
		return Arrays.binarySearch(array, key) >= 0;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// 生成随机数组测试
	public static void main(String[] args) {
		int testTime = 500000;
//		int testTime = 20;
		int maxSize = 100;
		int maxValue = 100;
		long time1 = 0;
		long time2 = 0;
		long time3 = 0;
		boolean pass = true;
		System.out.println("测试开始，没有打印出错信息说明测试通过");
		for (int i = 0; i < testTime; i++) {
			int k = (int) (Math.random() * maxSize) + 1;
			int[] arr = generateRandomArray(maxSize, maxValue);

			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);

			long start1 = System.nanoTime();
//			int[] ans1 = maxTopK1(arr1, k);
			int[] ans1 = myTopKBFPRT(arr1, k);
			time1 += System.nanoTime() - start1;

			long start2 = System.nanoTime();
			int[] ans2 = maxTopK2(arr2, k);
			time2 += System.nanoTime() - start2;

			long start3 = System.nanoTime();
			int[] ans3 = maxTopK3(arr3, k);
			time3 += System.nanoTime() - start3;

			if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
				pass = false;
				System.out.println("出错了！");
				printArray(ans1);
				printArray(ans2);
				printArray(ans3);
				break;
			}
		}
		System.out.println(time1 / testTime);
		System.out.println(time2 / testTime);
		System.out.println(time3 / testTime);
		System.out.println("测试结束了，测试了" + testTime + "组，是否所有测试用例都通过？" + (pass ? "是" : "否"));
	}

}

package class04;

public class Code04_BiggerThanRightTwice {

	public static int biggerTwice(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		return process(arr, 0, arr.length - 1);
	}

	public static int process(int[] arr, int l, int r) {
		if (l == r) {
			return 0;
		}
		// l < r
		int mid = l + ((r - l) >> 1);
		int left = process(arr, l, mid);
		int right = process(arr, mid + 1, r);
		int merg = merge(arr, l, mid, r);
//		System.out.println("li");
//		System.out.println(left);
//		System.out.println(right);
//		System.out.println(merg);
		return left + right + merg;
	}

	public static int merge(int[] arr, int L, int m, int r) {
		// [L....M]   [M+1....R]
		
		int ans = 0;
		// 目前囊括进来的数，是从[M+1, windowR)
		int windowR = m + 1;
		for (int i = L; i <= m; i++) {
			while (windowR <= r && arr[i] > (arr[windowR] * 2)) {
				windowR++;
			}
			ans += windowR - m - 1;
		}
		
		
		int[] help = new int[r - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = m + 1;
		while (p1 <= m && p2 <= r) {
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= m) {
			help[i++] = arr[p1++];
		}
		while (p2 <= r) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
		return ans;
	}

	//my code
	public static int myBiggerTwice(int arr[]){
		if(arr == null || arr.length == 0){
			return 0;
		}
		return myProcess(arr, 0, arr.length - 1) ;
	}
	public static int myProcess(int[] arr, int L, int R){
		if(L == R){
			return 0;
		}
		int mid = (L + R) / 2;
		int left = myProcess(arr, L, mid);
		int right = myProcess(arr, mid + 1, R);
		int merg = myMerge(arr, L, mid, R);
//		System.out.println("my");
//		System.out.println(left);
//		System.out.println(right);
//		System.out.println(merg);
		return left + right + merg;
	}

	public static int myMerge(int[] arr, int L, int M, int R){
		int ans = 0;
		int windowR = M + 1;
		for(int j = L; j <= M; j++){
			while(windowR <= R + 1 && arr[j] < (arr[windowR] * 2)){
				windowR++;
			}
			ans += R - windowR + 1;
		}

		int[] help = new int[R - L + 1];
		int leftP = L;
		int middleP = M+1;
		int i = 0;
		while(leftP <= M && middleP <= R){
			if(arr[leftP] > arr[middleP]){
				help[i++] = arr[leftP++];
			}else{
				help[i++] = arr[middleP++];
			}
		}
		while(leftP <= M){
			help[i++] = arr[leftP++];
		}
		while(middleP <= R){
			help[i++] = arr[middleP++];
		}
		for(int k = 0; k < help.length; k++){
			arr[L + k] = help[k];
		}
		return ans;
	}

	// for test
	public static int comparator(int[] arr) {
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] > (arr[j] << 1)) {
					ans++;
				}
			}
		}
		return ans;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) ((maxValue + 1) * Math.random());
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

	// for test
	public static void main(String[] args) {
		int testTime = 1;
		int maxSize = 100;
		int maxValue = 100;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
//			int[] arr1 = {40, 32, -13, -2, 16, -27, 76, -43};
			int[] arr2 = copyArray(arr1);
			int t1 = myBiggerTwice(arr1);
			int t2 = biggerTwice(arr2);
			if (t1 != t2) {
				System.out.println(t1);
				System.out.println(t2);
				System.out.println("Oops!");
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}

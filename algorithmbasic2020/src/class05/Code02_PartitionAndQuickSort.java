package class05;

public class Code02_PartitionAndQuickSort {

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// arr[L..R]上，以arr[R]位置的数做划分值
	// <= X > X
	// <= X X
	public static int partition(int[] arr, int L, int R) {
		if (L > R) {
			return -1;
		}
		if (L == R) {
			return L;
		}
		int lessEqual = L - 1;
		int index = L;
		while (index < R) {
			if (arr[index] <= arr[R]) {
				swap(arr, index, ++lessEqual);
			}
			index++;
		}
		swap(arr, ++lessEqual, R);
		return lessEqual;
	}

	// arr[L...R] 玩荷兰国旗问题的划分，以arr[R]做划分值
	// <arr[R] ==arr[R] > arr[R]
	public static int[] netherlandsFlag(int[] arr, int L, int R) {
		if (L > R) { // L...R L>R
			return new int[] { -1, -1 };
		}
		if (L == R) {
			return new int[] { L, R };
		}
		int less = L - 1; // < 区 右边界
		int more = R; // > 区 左边界
		int index = L;
		while (index < more) { // 当前位置，不能和 >区的左边界撞上
			if (arr[index] == arr[R]) {
				index++;
			} else if (arr[index] < arr[R]) {
//				swap(arr, less + 1, index);
//				less++;
//				index++;						
				swap(arr, index++, ++less);
			} else { // >
				swap(arr, index, --more);
			}
		}
		swap(arr, more, R); // <[R]   =[R]   >[R]
		return new int[] { less + 1, more };
	}

	//my code
	public static int[] myNetherlandFlag(int[] arr, int L, int R){
		if(L > R){
			return new int[] {-1, -1};
		}
		if(L == R){
			return new int[] {L, R};
		}
		int cur = L;
		int less = L - 1;//小於區域的邊界
		int more = R; //取最右格做partition,同時也是大於區域的邊界
		while(cur < more){
			//因為數組是從左往右掃描的，所以可以假設左邊是sorted右邊沒有
			//假設less = 2, cur = 4, 說明小於區域是0-2,等於區域是3開始
			//假設arr[4]小於arr[R],那麼說明4是應該放在小於區域,現在的小於區域是0-3
			//交換前,0-2小於,3相等,4小於,所以3,4交換後變為0-3為小於，4為等於
			if(arr[cur] < arr[R]){
				swap(arr, cur++, ++less);
			}else if(arr[cur] > arr[R]){
				//因為大於區域往左擴張,因為我們並不知道more(或者R)的值,它可能大於等於小於,
				//唯一確定的是cur大於R,但不同於左邊區域是已排的,右邊是還沒排
				//所以cur不能++,必須在交換後拿來再作檢查
				swap(arr, cur, --more);
			}else{
				cur++;
			}
		}
		//R一直作為partition,所以在快排後,L-less是小於區,less+1 - more-1是等於區, more-R-1是大於區,R自己是partition
		//最後這個交換是將作為partition的R和大於區邊界的more交換
		//交換後,less+1 - more(現在在more上的是原先作為partition的R,因為是作為對比的partition,它必然要在等於區)
		//more+1 - R是大於區(原來是more - R-1, 相當於右移一格)
		swap(arr, more, R);
		//最後返回less+1和more是等於區域內的左邊界和右邊界
		return new int[] { less + 1, more };
	}
	//

	public static void quickSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process1(arr, 0, arr.length - 1);
	}

	public static void process1(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		// L..R partition arr[R] [ <=arr[R] arr[R] >arr[R] ]
		int M = partition(arr, L, R);
		process1(arr, L, M - 1);
		process1(arr, M + 1, R);
	}

	
	
	
	
	
	public static void quickSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process2(arr, 0, arr.length - 1);
	}

	// arr[L...R] 排有序，快排2.0方式
	public static void process2(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		// [ equalArea[0]  ,  equalArea[0]]
//		int[] equalArea = netherlandsFlag(arr, L, R);
		int[] equalArea = myNetherlandFlag(arr, L, R);
		process2(arr, L, equalArea[0] - 1); //返回的是等於區的左邊界(在等於區域內)，所以-1才是小於區域的右邊界(回到小於區域)
		process2(arr, equalArea[1] + 1, R); //與上同理, +1才是大於區域的左邊界
	}

	
	
	
	
	
	
	public static void quickSort3(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process3(arr, 0, arr.length - 1);
	}

	public static void process3(int[] arr, int L, int R) {
		if (L >= R) {
			return;
		}
		swap(arr, L + (int) (Math.random() * (R - L + 1)), R); //隨機算位置和最右側交換, 再進行快排
		int[] equalArea = netherlandsFlag(arr, L, R);
		process3(arr, L, equalArea[0] - 1);
		process3(arr, equalArea[1] + 1, R);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
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
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			int[] arr3 = copyArray(arr1);
			quickSort1(arr1);
			quickSort2(arr2);
			quickSort3(arr3);
			if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
				succeed = false;
				printArray(arr2);
				printArray(arr3);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Oops!");

	}

}

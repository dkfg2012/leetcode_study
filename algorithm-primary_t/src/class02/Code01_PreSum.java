package class02;

public class Code01_PreSum {

	public static class RangeSum1 {

		private int[] arr;

		public RangeSum1(int[] array) {
			arr = array;
		}

		public int rangeSum(int L, int R) {
			int sum = 0;
			for (int i = L; i <= R; i++) {
				sum += arr[i];
			}
			return sum;
		}

	}

	public static class RangeSum2 {

		private int[] preSum;

		public RangeSum2(int[] array) {
			int N = array.length;
			preSum = new int[N];
			preSum[0] = array[0];
			for (int i = 1; i < N; i++) {
				preSum[i] = preSum[i - 1] + array[i];
			}
		}

		public int rangeSum(int L, int R) {
			return L == 0 ? preSum[R] : preSum[R] - preSum[L - 1];
		}

	}

	public static class MyRangeSum{
		private int[] preSum;

		public MyRangeSum(int[] array){
			preSum = new int[array.length];
			for(int i = 0; i < array.length; i++){
				if(i == 0){
					preSum[i] = array[0];
				}else{
					preSum[i] = preSum[i-1] + array[i];
				}
			}
		}

		public int rangeSum(int L, int R){
			if(L == 0){
				return preSum[R];
			}else{
				return preSum[R] - preSum[L-1];
			}
		}
	}

	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		if(arr.length < 2){
			arr = new int[10];
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args){
		for(int i = 0; i < 50000; i++){
			int[] arr = generateRandomArray(100, 100);
			int L = (int)(arr.length - 1 * Math.random());
			int R = (int)(arr.length - 1 * Math.random());
			RangeSum2 r2 = new RangeSum2(arr);
			MyRangeSum r3 = new MyRangeSum(arr);
			if(L > R){
				int t = R;
				R = L;
				L = t;
			}
			if(!(r2.rangeSum(L, R) == r3.rangeSum(L, R))){
				printArray(arr);
				System.out.println(r2.rangeSum(L, R));
				System.out.println(r3.rangeSum(L, R));
				System.out.println(L);
				System.out.println(R);
				break;
			}
		}
		System.out.println("done");

	}

}
package class20;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// 题目
// 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
// 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
// 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
// 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
// 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
// 四个参数：arr, n, a, b
// 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
public class Code03_Coffee {

	// 验证的方法
	// 彻底的暴力
	// 很慢但是绝对正确
	public static int right(int[] arr, int n, int a, int b) {
		int[] times = new int[arr.length];
		int[] drink = new int[n];
		return forceMake(arr, times, 0, drink, n, a, b);
	}

	// 每个人暴力尝试用每一个咖啡机给自己做咖啡
	public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
		if (kth == n) {
			int[] drinkSorted = Arrays.copyOf(drink, kth);
			Arrays.sort(drinkSorted);
			return forceWash(drinkSorted, a, b, 0, 0, 0);
		}
		int time = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			int work = arr[i];
			int pre = times[i];
			drink[kth] = pre + work;
			times[i] = pre + work;
			time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
			drink[kth] = 0;
			times[i] = pre;
		}
		return time;
	}

	public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
		if (index == drinks.length) {
			return time;
		}
		// 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
		int wash = Math.max(drinks[index], washLine) + a;
		int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));

		// 选择二：当前index号咖啡杯，选择自然挥发
		int dry = drinks[index] + b;
		int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
		return Math.min(ans1, ans2);
	}

	// 以下为贪心+优良暴力
	public static class Machine {
		public int timePoint;
		public int workTime;

		public Machine(int t, int w) {
			timePoint = t;
			workTime = w;
		}
	}

	public static class MachineComparator implements Comparator<Machine> {

		@Override
		public int compare(Machine o1, Machine o2) {
			return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
		}

	}

	// 优良一点的暴力尝试的方法
	public static int minTime1(int[] arr, int n, int a, int b) {
		PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
		for (int i = 0; i < arr.length; i++) {
			heap.add(new Machine(0, arr[i]));
		}
		int[] drinks = new int[n];
		for (int i = 0; i < n; i++) {
			Machine cur = heap.poll();
			cur.timePoint += cur.workTime;
			drinks[i] = cur.timePoint;
			heap.add(cur);
		}
		return bestTime(drinks, a, b, 0, 0);
	}

	// drinks 所有杯子可以开始洗的时间
	// wash 单杯洗干净的时间（串行）
	// air 挥发干净的时间(并行)
	// free 洗的机器什么时候可用
	// drinks[index.....]都变干净，最早的结束时间（返回）
	public static int bestTime(int[] drinks, int wash, int air, int index, int free) {
		if (index == drinks.length) {
			return 0;
		}
		// index号杯子 决定洗
		int selfClean1 = Math.max(drinks[index], free) + wash;
		int restClean1 = bestTime(drinks, wash, air, index + 1, selfClean1);
		int p1 = Math.max(selfClean1, restClean1);

		// index号杯子 决定挥发
		int selfClean2 = drinks[index] + air;
		int restClean2 = bestTime(drinks, wash, air, index + 1, free);
		int p2 = Math.max(selfClean2, restClean2);
		return Math.min(p1, p2);
	}

	// 贪心+优良尝试改成动态规划
	public static int minTime2(int[] arr, int n, int a, int b) {
		PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
		for (int i = 0; i < arr.length; i++) {
			heap.add(new Machine(0, arr[i]));
		}
		int[] drinks = new int[n];
		for (int i = 0; i < n; i++) {
			Machine cur = heap.poll();
			cur.timePoint += cur.workTime;
			drinks[i] = cur.timePoint;
			heap.add(cur);
		}
		return bestTimeDp(drinks, a, b);
	}

	public static int bestTimeDp(int[] drinks, int wash, int air) {
		int N = drinks.length;
		int maxFree = 0;
		for (int i = 0; i < drinks.length; i++) {
			maxFree = Math.max(maxFree, drinks[i]) + wash;
		}
		int[][] dp = new int[N + 1][maxFree + 1];
		for (int index = N - 1; index >= 0; index--) {
			for (int free = 0; free <= maxFree; free++) {
				int selfClean1 = Math.max(drinks[index], free) + wash;
				if (selfClean1 > maxFree) {
					break; // 因为后面的也都不用填了
				}
				// index号杯子 决定洗
				int restClean1 = dp[index + 1][selfClean1];
				int p1 = Math.max(selfClean1, restClean1);
				// index号杯子 决定挥发
				int selfClean2 = drinks[index] + air;
				int restClean2 = dp[index + 1][free];
				int p2 = Math.max(selfClean2, restClean2);
				dp[index][free] = Math.min(p1, p2);
			}
		}
		return dp[0][0];
	}



	//my code
	public static class myMachine{
		private int currentTime;
		private int makeTime;
		public myMachine(int currentTime, int makeTime){
			this.currentTime = currentTime;
			this.makeTime = makeTime;
		}
	}

	public static class myComparator implements Comparator<myMachine>{
		@Override
		public int compare(myMachine o1, myMachine o2) {
			return (o1.currentTime + o1.makeTime) - (o2.currentTime + o2.makeTime);
		}
	}



	public static int myMinTime(int[] arr, int n, int a, int b){
		PriorityQueue<myMachine> minheap = new PriorityQueue<>(new myComparator());
		for(int makeTime : arr){
			minheap.add(new myMachine(0, makeTime));
		}
		int[] getCoffeeTime = new int[n];
		for(int i = 0; i < n; i++){
			myMachine t = minheap.poll();
			getCoffeeTime[i] = t.makeTime + t.currentTime;
			t.currentTime = getCoffeeTime[i];
			minheap.add(t);

		}
		return process(getCoffeeTime, a, b);
	}

	public static int process(int[] coffeeTime, int wash, int air){
		int maxTimeCLean = 0;
		for(int i = 0; i < coffeeTime.length; i++){
			maxTimeCLean = Math.max(maxTimeCLean, coffeeTime[i]) + wash; //maxTimeClean代表上一個杯子洗完後的時間，對比拿到咖啡的時間，誰更晚拿哪個，然後再加洗的時間，得出喝完這杯咖啡後什麼時候才會洗完
		}
		int[][] dp = new int[coffeeTime.length + 1][maxTimeCLean + 1];
		for(int i = coffeeTime.length-1; i >= 0; i--){
			for(int j = 0; j <= maxTimeCLean; j++){
				//在j分鐘
				int cleanByMachine = Math.max(coffeeTime[i], j) + wash; //杯子用機器洗
				if(cleanByMachine > maxTimeCLean){
					break;
				}
				int nextCoffeeByMachineifthisByMachine = dp[i+1][cleanByMachine]; //這次機器洗，下一個也機器洗
				int t1 = Math.max(cleanByMachine,nextCoffeeByMachineifthisByMachine);

				int cleanByAir = coffeeTime[i] + air; //杯子用空氣洗
				int nextCoffeeByMachineifthisnotMachine = dp[i+1][j]; //這次不用機器洗，那麼下一個杯子可以現在洗
				int t2 = Math.max(cleanByAir, nextCoffeeByMachineifthisnotMachine);
				dp[i][j] = Math.min(t1,t2);
			}
		}
		return dp[0][0];
	}




	// for test
	public static int[] randomArray(int len, int max) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * max) + 1;
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		System.out.print("arr : ");
		for (int j = 0; j < arr.length; j++) {
			System.out.print(arr[j] + ", ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int len = 10;
		int max = 10;
		int testTime = 10;
		System.out.println("测试开始");for (int i = 0; i < testTime; i++) {
			int[] arr = randomArray(len, max);
			int n = (int) (Math.random() * 7) + 1;
			int a = (int) (Math.random() * 7) + 1;
			int b = (int) (Math.random() * 10) + 1;
			int ans1 = right(arr, n, a, b);
			int ans2 = minTime1(arr, n, a, b);
			int ans3 = minTime2(arr, n, a, b);
			int ans4 = myMinTime(arr, n, a, b);
			if (ans1 != ans2 || ans2 != ans3 || ans2 != ans4) {
				printArray(arr);
				System.out.println("n : " + n);
				System.out.println("a : " + a);
				System.out.println("b : " + b);
				System.out.println(ans1 + " , " + ans2 + " , " + ans3 + " , " + ans4);
				System.out.println("===============");
				break;
			}
		}
		System.out.println("测试结束");

	}

}

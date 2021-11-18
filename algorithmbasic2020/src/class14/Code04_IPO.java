package class14;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Code04_IPO {

	// 最多K个项目
	// W是初始资金
	// Profits[] Capital[] 一定等长
	// 返回最终最大的资金
	public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {
		PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
		PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
		for (int i = 0; i < Profits.length; i++) {
			minCostQ.add(new Program(Profits[i], Capital[i]));
		}
		for (int i = 0; i < K; i++) {
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
				maxProfitQ.add(minCostQ.poll());
			}
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			W += maxProfitQ.poll().p;
		}
		return W;
	}

	public static class Program {
		public int p;
		public int c;

		public Program(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}

	public static class MinCostComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.c - o2.c;
		}

	}

	public static class MaxProfitComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o2.p - o1.p;
		}

	}

	public static Program[] generatePrograms(int programSize) {
		Program[] ans = new Program[(int) (Math.random() * (programSize))];
		for (int i = 0; i < ans.length; i++) {
			int C = (int) (Math.random() * (8000));
			int P = (int) (Math.random() * (2000));
			ans[i] = new Program(P, C);
		}
		return ans;
	}


	//my code
	public static class profitComparator implements Comparator<Program>{
		@Override
		public int compare(Program p1, Program p2){
			return p2.p - p1.p;
		}
	}

	public static class costComparator implements Comparator<Program>{
		@Override
		public int compare(Program p1, Program p2){
			return p1.c - p2.c;
		}
	}

	public static int myFindMaxCapital(int K, int W, int[] profits, int[] capitals){
		PriorityQueue<Program> profitMax = new PriorityQueue<>(new profitComparator());
		PriorityQueue<Program> capMin = new PriorityQueue<>(new costComparator());
		for(int i = 0; i < capitals.length; i++){
			Program p = new Program(profits[i], capitals[i]);
			capMin.add(p);
		}
		while(K > 0){
			while(!capMin.isEmpty() && W >= capMin.peek().c){
				Program p = capMin.poll();
				profitMax.add(p);
			}
			if(profitMax.isEmpty()){
				return W;
			}
			W += profitMax.poll().p;
			K--;
		}
		return W;
	}


	//test code
	public static int test(int K, int W, int[] Profits, int[] Capital){
		Program[] programs = new Program[Profits.length];
		for(int i = 0; i < Profits.length; i++){
			Program p = new Program(Profits[i], Capital[i]);
			programs[i] = p;
		}
		int index = 0;
		Arrays.sort(programs, new testComparator());
		HashMap<Integer,Program> h = new HashMap<Integer, Program>();

		while(K > 0 && index < programs.length){
			if(W > programs[index].c && !h.containsKey(index)){
				W += programs[index].p;
				h.put(index, programs[index]);
				K--;
				index = 0;
			}else{
				index++;
			}
		}
		return W;

	}

	public static class testComparator implements Comparator<Program>{
		@Override
		public int compare(Program p1, Program p2){
			return p2.p - p1.p;
		}
	}

	public static void main(String[] args) {
		int K = (int) (Math.random() * 50);
		int M = 10000;
		int programSize = 50;
		int timeTimes = 100;
		long[] testTime = new long[timeTimes];
		long[] tryTime = new long[timeTimes];
		for (int j = 0; j < timeTimes; j++) {
			Program[] programs = generatePrograms(programSize);
			int[] profits = new int[programs.length];
			int[] capitals = new int[programs.length];;
			int index = 0;
			for(int i = 0; i < profits.length; i++){
				profits[index] = programs[index].p;
				capitals[index] = programs[index].c;
				index++;
			}

			long startTime = System.nanoTime();
//			int r = myFindMaxCapital(K, M, profits, capitals);
			int r = findMaximizedCapital(K, M, profits, capitals);
			long endTime  = System.nanoTime();
			tryTime[j] = endTime - startTime;

			startTime = System.nanoTime();
			int t = test(K, M ,profits, capitals);
			endTime   = System.nanoTime();
			testTime[j] = endTime - startTime;

			if(r != t){
				System.out.println(r);
				System.out.println(t);
				System.out.println("Opps");
				break;
			}
		}
		System.out.println("Average try time " + Arrays.stream(tryTime).average());
		System.out.println("Average test time " + Arrays.stream(testTime).average());
		System.out.println("finish!");
	}

}

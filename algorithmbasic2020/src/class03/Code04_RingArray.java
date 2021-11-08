package class03;

public class Code04_RingArray {

	public static class MyQueue {
		private int[] arr;
		private int pushi;// end
		private int polli;// begin
		private int size;
		private final int limit;

		public MyQueue(int limit) {
			arr = new int[limit];
			pushi = 0;
			polli = 0;
			size = 0;
			this.limit = limit;
		}

		public void push(int value) {
			if (size == limit) {
				throw new RuntimeException("队列满了，不能再加了");
			}
			size++;
			arr[pushi] = value;
			pushi = nextIndex(pushi);
		}

		public int pop() {
			if (size == 0) {
				throw new RuntimeException("队列空了，不能再拿了");
			}
			size--;
			int ans = arr[polli];
			polli = nextIndex(polli);
			return ans;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		// 如果现在的下标是i，返回下一个位置
		private int nextIndex(int i) {
			return i < limit - 1 ? i + 1 : 0;
		}

	}

	//my code
	public static class myRingArray{
		private int[] arr;
		private int pushIndex;
		private int pollIndex;
		private int size;
		private int limit;

		public myRingArray(int limit){
			arr = new int[limit];
			pushIndex = 0;
			pollIndex = 0;
			size = 0;
			this.limit = limit;
		}

		public void push(int number){
			if(size == limit){
				throw new RuntimeException("array is full");
			}
			arr[pushIndex] = number;
			size += 1;
			if(pushIndex == limit - 1){
				pushIndex = 0;
			}else{
				pushIndex += 1;
			}
		}

		public int poll(){
			if(size == 0){
				throw new RuntimeException("array is empty");
			}
			size--;
			int r = arr[pollIndex];
			if(pollIndex == limit - 1){
				pollIndex = 0;
			}else{
				pollIndex += 1;
			}
			return r;
		}

		public boolean isEmpty(){
			return size == 0;
		}
	}

}

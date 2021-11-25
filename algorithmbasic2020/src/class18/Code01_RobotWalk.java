package class18;

public class Code01_RobotWalk {

	public static int ways1(int N, int start, int aim, int K) {
		if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
			return -1;
		}
		return process1(start, K, aim, N);
	}

	// 机器人当前来到的位置是cur，
	// 机器人还有rest步需要去走，
	// 最终的目标是aim，
	// 有哪些位置？1~N
	// 返回：机器人从cur出发，走过rest步之后，最终停在aim的方法数，是多少？
	public static int process1(int cur, int rest, int aim, int N) {
		if (rest == 0) { // 如果已经不需要走了，走完了！
			return cur == aim ? 1 : 0;
		}
		// (cur, rest)
		if (cur == 1) { // 1 -> 2
			return process1(2, rest - 1, aim, N);
		}
		// (cur, rest)
		if (cur == N) { // N-1 <- N
			return process1(N - 1, rest - 1, aim, N);
		}
		// (cur, rest)
		return process1(cur - 1, rest - 1, aim, N) + process1(cur + 1, rest - 1, aim, N);
	}

	public static int ways2(int N, int start, int aim, int K) {
		if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
			return -1;
		}
		int[][] dp = new int[N + 1][K + 1];
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= K; j++) {
				dp[i][j] = -1;
			}
		}
		// dp就是缓存表
		// dp[cur][rest] == -1 -> process1(cur, rest)之前没算过！
		// dp[cur][rest] != -1 -> process1(cur, rest)之前算过！返回值，dp[cur][rest]
		// N+1 * K+1
		return process2(start, K, aim, N, dp);
	}

	// cur 范: 1 ~ N
	// rest 范：0 ~ K
	public static int process2(int cur, int rest, int aim, int N, int[][] dp) {
		if (dp[cur][rest] != -1) {
			return dp[cur][rest];
		}
		// 之前没算过！
		int ans = 0;
		if (rest == 0) {
			ans = cur == aim ? 1 : 0;
		} else if (cur == 1) {
			ans = process2(2, rest - 1, aim, N, dp);
		} else if (cur == N) {
			ans = process2(N - 1, rest - 1, aim, N, dp);
		} else {
			ans = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
		}
		dp[cur][rest] = ans;
		return ans;

	}

	public static int ways3(int N, int start, int aim, int K) {
		if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
			return -1;
		}
		int[][] dp = new int[N + 1][K + 1];
		dp[aim][0] = 1;
		for (int rest = 1; rest <= K; rest++) {
			dp[1][rest] = dp[2][rest - 1];
			for (int cur = 2; cur < N; cur++) {
				dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
			}
			dp[N][rest] = dp[N - 1][rest - 1];
		}
		return dp[start][K];
	}



	//my code
	public static int myWay(int N, int start, int aim, int K){
		if(N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1){
			return -1;
		}
		int[][] dp = new int[N+1][K+1];
		dp[aim][K] = 1;
		for(int walks = K-1; walks >= 0; walks--){
			dp[1][walks] = dp[2][walks+1];
			dp[N][walks] = dp[N-1][walks+1];
			for(int locations = 2; locations < N; locations++){
				dp[locations][walks] = dp[locations + 1][walks + 1] + dp[locations - 1][walks + 1];
			}
		}
		return dp[start][0];
	}


	public static int myWay2(int N, int start, int aim, int K){
		if(N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1){
			return -1;
		}
		int[][] dp = new int[N+1][2];
		dp[aim][0] = 1;
		for(int walks = 1; walks <= K; walks++){
			dp[1][1] = dp[2][0];
			dp[N][1] = dp[N-1][0];
			for(int locations = 2; locations < N; locations++){
				dp[locations][1] = dp[locations + 1][0] + dp[locations - 1][0];
			}
			for(int i = 1; i <= N; i++){
				dp[i][0] = dp[i][1];
			}
		}
		return dp[start][0];
	}



	public static void main(String[] args) {
		System.out.println(ways1(10, 2, 4, 5));
		System.out.println(ways2(10, 2, 4, 5));
		System.out.println(ways3(10, 2, 4, 5));
		System.out.println(myWay(10, 2, 4, 5));
		System.out.println(myWay2(10, 2, 4, 5));
	}

}

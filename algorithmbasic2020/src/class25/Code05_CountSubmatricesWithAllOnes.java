package class25;

import java.util.Stack;

// 测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
public class Code05_CountSubmatricesWithAllOnes {

	public static int numSubmat(int[][] mat) {
		if (mat == null || mat.length == 0 || mat[0].length == 0) {
			return 0;
		}
		int nums = 0;
		int[] height = new int[mat[0].length];
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
			}
			nums += countFromBottom(height);
		}
		return nums;

	}

	public static int countFromBottom(int[] height) {
		if (height == null || height.length == 0) {
			return 0;
		}
		int nums = 0;
		int[] stack = new int[height.length];
		int si = -1;
		for (int i = 0; i < height.length; i++) {
			while (si != -1 && height[stack[si]] >= height[i]) {
				int cur = stack[si--];
				if (height[cur] > height[i]) {
					int left = si == -1 ? -1 : stack[si];
					int n = i - left - 1;
					int down = Math.max(left == -1 ? 0 : height[left], height[i]);
					nums += (height[cur] - down) * num(n);
				}

			}
			stack[++si] = i;
		}
		while (si != -1) {
			int cur = stack[si--];
			int left = si == -1  ? -1 : stack[si];
			int n = height.length - left - 1;
			int down = left == -1 ? 0 : height[left];
			nums += (height[cur] - down) * num(n);
		}
		return nums;
	}


	//using stack
	public int mynumSubmat(int[][] mat) {
		if(mat == null || mat.length == 0 || mat[0].length == 0){
			return 0;
		}
		int r = 0;
		int[] heights = new int[mat[0].length];
		for(int i = 0; i < mat.length; i++){
			for(int j = 0; j < mat[0].length; j++){
				heights[j] = mat[i][j] == 0 ? 0 : heights[j] + 1;
			}
			r += process(heights);
		}
		return r;
	}

	public int process(int[] heights){
		if(heights == null || heights.length == 0){
			return 0;
		}
		int nums = 0;
		Stack<Integer> stack = new Stack<>();
		for(int i = 0; i < heights.length; i++){
			while(!stack.isEmpty() && heights[stack.peek()] >= heights[i]){
				int j = stack.pop();
				if(heights[j] > heights[i]){
					int left = stack.isEmpty() ? -1 : stack.peek();
					int n = i - left - 1;
					int down = Math.max(left == -1 ? 0 : heights[left], heights[i]);
					nums += (heights[j] - down) * num(n);
					//for example 131, when the rightmost 1 add to stack, it pop 1 (height 3) out
					//left = 0 (index 0 where height is 1), n = 2-0-1 = 1, which is the base length of index 1
					//heights[j] - down represent the 2 subarray [[1],[1],[1]] at 1 and [1] [[1],[1],[0]] at 1
					//the [[0],[0],[1]] subarray was count in the while loop after for loop was done
					//it count with [[0],[0],[1]] at 0 and at 2 simultaneously
				}
			}
			stack.push(i);
		}
		while(!stack.isEmpty()){
			int j = stack.pop();
			int left = stack.isEmpty() ? -1 : stack.peek();
			int n = heights.length - left - 1;
			int down = left == -1 ? 0 : heights[left];
			nums += (heights[j] - down) * num(n);
		}
		return nums;
	}




	public static int num(int n) {
		return ((n * (1 + n)) >> 1);
	}

}

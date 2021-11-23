package class17;

import java.util.ArrayList;
import java.util.List;

public class Code04_PrintAllPermutations {

	public static List<String> permutation1(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return ans;
		}
		char[] str = s.toCharArray();
		ArrayList<Character> rest = new ArrayList<Character>();
		for (char cha : str) {
			rest.add(cha);
		}
		String path = "";
		f(rest, path, ans);
		return ans;
	}

	public static void f(ArrayList<Character> rest, String path, List<String> ans) {
		if (rest.isEmpty()) {
			ans.add(path);
		} else {
			int N = rest.size();
			for (int i = 0; i < N; i++) {
				char cur = rest.get(i);
				rest.remove(i);
				f(rest, path + cur, ans);
				rest.add(i, cur);
			}
		}
	}

	public static List<String> permutation2(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return ans;
		}
		char[] str = s.toCharArray();
		g1(str, 0, ans);
		return ans;
	}

	public static void g1(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		} else {
			for (int i = index; i < str.length; i++) {
				swap(str, index, i);
				g1(str, index + 1, ans);
				swap(str, index, i);
			}
		}
	}

	public static List<String> permutation3(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() == 0) {
			return ans;
		}
		char[] str = s.toCharArray();
		g2(str, 0, ans);
		return ans;
	}

	public static void g2(char[] str, int index, List<String> ans) {
		if (index == str.length) {
			ans.add(String.valueOf(str));
		} else {
			boolean[] visited = new boolean[256];
			for (int i = index; i < str.length; i++) {
				if (!visited[str[i]]) {
					visited[str[i]] = true;
					swap(str, index, i);
					g2(str, index + 1, ans);
					swap(str, index, i);
				}
			}
		}
	}


	//my code
	public static List<String> myPermutation(String s){
		List<String> r = new ArrayList<>();
		char[] str = s.toCharArray();
		myProcess(str, 0, r);
		return r;
	}

	public static void myProcess(char[] str, int index, List<String> r){
		if(index == str.length){
			r.add(String.valueOf(str));
			return;
		}
		boolean[] visited = new boolean[256];
		for(int i = index; i < str.length; i++){
			if(!visited[str[i]]){
				visited[str[i]] = true;
				//假設輸入abc
				//第一個loop
				//i = index,所以都是原地tp
				//進入遞歸
				//最後input怎麼樣最後怎麼樣,所以首先是abc
				//遞歸時第二個loop
				//二三位交換,成為acb, 再遞歸進入終止條件,加入acb
				//回到a時候的第二loop
				//一二位交換位置,得出bac, 隨後同理
				swap(str, index, i);
				myProcess(str, index+1, r);
				swap(str, i, index);
			}
		}
	}

	public static void swap(char[] chs, int i, int j) {
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}

	public static void main(String[] args) {
		String s = "abc";
		List<String> ans1 = permutation1(s);
		for (String str : ans1) {
			System.out.println(str);
		}
		System.out.println("=======");
		List<String> ans2 = permutation2(s);
		for (String str : ans2) {
			System.out.println(str);
		}
		System.out.println("=======");
		List<String> ans3 = permutation3(s);
		for (String str : ans3) {
			System.out.println(str);
		}
		System.out.println("=======");
		List<String> ans4 = myPermutation(s);
		for (String str : ans4) {
			System.out.println(str);
		}

	}

}

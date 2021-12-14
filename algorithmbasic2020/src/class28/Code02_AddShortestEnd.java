package class28;

public class Code02_AddShortestEnd {

	public static String shortestEnd(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		char[] str = manacherString(s);
		int[] pArr = new int[str.length];
		int C = -1;
		int R = -1;
		int maxContainsEnd = -1;
		for (int i = 0; i != str.length; i++) {
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			if (R == str.length) {
				maxContainsEnd = pArr[i];
				break;
			}
		}
		char[] res = new char[s.length() - maxContainsEnd + 1];
		for (int i = 0; i < res.length; i++) {
			res[res.length - 1 - i] = str[i * 2 + 1];
		}
		return String.valueOf(res);
	}

	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}



	//my code
	public static String myManacher(String s){
		if(s == null || s.length() < 1){
			return null;
		}
		char[] str = myStringProcessing(s);
		int[] pArr = new int[str.length];
		int C = -1;
		int R = -1;
		int maxpArrwithEnd = -1;
		for(int i = 0; i < pArr.length; i++){
			pArr[i] = R > i ? Math.min(R - i, pArr[2 * C - i]) : 1;
			while(i + pArr[i] < str.length && i - pArr[i] > -1){
				if(str[i + pArr[i]] == str[i - pArr[i]]){
					pArr[i]++;
				}else{
					break;
				}
			}
			if(i + pArr[i] > R){
				C = i;
				R = i + pArr[i];
			}
			if(R == str.length){
				maxpArrwithEnd = pArr[i];
				break;
			}
		}

		char[] r = new char[s.length() - maxpArrwithEnd + 1];;
		for (int i = 0; i < r.length; i++) {
			r[r.length - 1 - i] = str[i * 2 + 1];
		}

		return String.valueOf(r);

	}

	public static char[] myStringProcessing(String s){
		char[] charArr = s.toCharArray();
		char[] r = new char[charArr.length * 2 + 1];
		int index = 0;
		for(int i = 0; i < r.length; i++){
			r[i] = (i % 2) == 0 ? '#' : charArr[index++];
		}
		return r;
	}



	public static void main(String[] args) {
		String str1 = "abcd123321";
		System.out.println(shortestEnd(str1));
		System.out.println(myManacher(str1));
	}

}

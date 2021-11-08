package class01;

import java.util.ArrayList;

public class Code01_PrintBinary {

	public static String print(int num) {
		String r = "";
		for (int i = 31; i >= 0; i--) {
			r += (num & (1 << i)) == 0 ? "0" : "1";
		}
		return r;
	}


	public static String MygetByte(int nums){
		String r = "";
		if(nums >= 0){
			r += 0;
		}else{
			r += 1;
		}
		for(int i = 30; i >= 0; i--){
			System.out.println(nums >> i);
			r += 1 & (nums >> i);
		}
		return r;
	}

	public static void main(String[] args) {
//		String standard = print(67185993);
		String my = MygetByte(67185993);
//		System.out.println(standard.equals(my));
//		System.out.println(my);
		int max = Integer.MAX_VALUE;
		int min = Integer.MIN_VALUE;
//		for(int i = 0; i < 50000; i++){
//			int random_int = (int) Math.floor(Math.random()*(max - min+1)+ min);
//			if(! print(random_int).equals(MygetByte(random_int))){
//				System.out.println(random_int);
//				break;
//			}
//		}
		System.out.println("done");

		// 32位
//		int num = 4;
//
//		print(num);
//		
//		
//		int test = 1123123;
//		print(test);
//		print(test<<1);
//		print(test<<2);
//		print(test<<8);
//		
//		
//		int a = Integer.MAX_VALUE;
//		System.out.println(a);

//		print(-1);
//		int a = Integer.MIN_VALUE;
//		print(a);

//		int b = 123823138;
//		int c = ~b;
//		print(b);
//		print(c);

//		print(-5);

//		System.out.println(Integer.MIN_VALUE);
//		System.out.println(Integer.MAX_VALUE);

//		int a = 12319283;
//		int b = 3819283;
//		print(a);
//		print(b);
//		System.out.println("=============");
//		print(a | b);
//		print(a & b);
//		print(a ^ b);

//		int a = Integer.MIN_VALUE;
//		print(a);
//		print(a >> 1);
//		print(a >>> 1);
//		
//		int c = Integer.MIN_VALUE;
//		int d = -c ;
//		
//		print(c);
//		print(d);

	}

}

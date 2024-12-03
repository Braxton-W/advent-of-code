package day1;

import java.util.Scanner;
import java.util.HashMap;

public class part2 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		HashMap<Long, Integer> map = new HashMap<>();
		long[] left = new long[1000];
		long[] right = new long[1000];
		int total = 0;
		
		for(int i = 0; input.hasNextLong() && i < 999; i++) {
			left[i] = input.nextLong();
			right[i] = input.nextLong();
			
			map.put(left[i], 0);
		}
		
		for(int j = 0; j < right.length; j++) {
			if(!map.containsKey(right[j])) {
				continue;
			}
			
			total += right[j];
		}
		
		System.out.println(total);
		
		input.close();
	}
}

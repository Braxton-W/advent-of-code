package day1;

import java.util.Scanner;
import java.util.Arrays;

public class part1 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		long[] left = new long[1000];
		long[] right = new long[1000];
		long dist = 0;
		
		for(int i = 0; input.hasNextLong() && i < 999; i++) {
			left[i] = input.nextLong();
			right[i] = input.nextLong();
		}
		
		Arrays.sort(left);
		Arrays.sort(right);
		
		for(int i = 0; i < left.length; i++) {
			dist += Math.abs(left[i] - right[i]);
		}
		
		System.out.println(dist);
		
		input.close();
	}
}

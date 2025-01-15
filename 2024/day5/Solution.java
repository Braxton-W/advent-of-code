package day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class SolutionTest {
	public static void main(String[] args) {
		HashMap<String, ArrayList<ArrayList<Integer>>> data = fileData();
		ArrayList<ArrayList<Integer>> rules = data.get("rules");
		ArrayList<ArrayList<Integer>> updates = data.get("updates");
	  
		HashMap<String, ArrayList<ArrayList<Integer>>> sortedUpdates = getUpdates(rules, updates);
		ArrayList<ArrayList<Integer>> validUpdates = sortedUpdates.get("valid");
		ArrayList<ArrayList<Integer>> invalidUpdates = sortedUpdates.get("invalid");
		
		System.out.println("The sum of the median values of valid updates is " + sumMedians(validUpdates));
		// System.out.println("The sum of the median values of invalid updates is " + sumMedians(invalidUpdates));
		
		ArrayList<ArrayList<Integer>> orderedUpdates = orderUpdates(rules, invalidUpdates);
		
		System.out.println("The sum of the median values of invalid updates ordered is " + sumMedians(orderedUpdates));
	}
	
	public static HashMap<String, ArrayList<ArrayList<Integer>>> fileData() {
		String fileName = "src/day5/input.txt";
		File file = new File(fileName);
		Scanner inStream = null;
		ArrayList<ArrayList<Integer>> rules = new ArrayList<>();
		ArrayList<ArrayList<Integer>> updates = new ArrayList<>();
		
		try {
			inStream = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found");
			
			System.exit(0);
		}
		
		while(inStream.hasNextLine()) {
			String line = inStream.nextLine();
			
			if(line == "") {
				break;
			}
			
			String[] intsStr = line.split("\\|");
			ArrayList<Integer> ints = new ArrayList<>();
			ints.add(Integer.parseInt(intsStr[0]));
			ints.add(Integer.parseInt(intsStr[1]));
			rules.add(ints);
		}
		
		while(inStream.hasNextLine()) {
			String line = inStream.nextLine();
			
			String[] intsStr = line.split(",");
			ArrayList<Integer> ints = new ArrayList<>();
			
			for(int i = 0; i < intsStr.length; i++) {
				ints.add(Integer.parseInt(intsStr[i]));
			}
			
			updates.add(ints);
		}
		
		inStream.close();
		
		HashMap<String, ArrayList<ArrayList<Integer>>> data = new HashMap<>();
		data.put("rules", rules);
		data.put("updates", updates);
		
		return data;
	}
	
	public static int find(ArrayList<Integer> arr, int x) {
		for(int i = 0; i < arr.size(); i++) {
			if(arr.get(i) != x) {
				continue;
			}
			
			return i;
		}
		
		return -1;
	}
	
	public static boolean isValidUpdate(ArrayList<ArrayList<Integer>> rules, ArrayList<Integer> update) {
		for(int i = 0; i < rules.size(); i++) {
			int firstIndex = find(update, rules.get(i).get(0));
			int secondIndex = find(update, rules.get(i).get(1));
			
			if(firstIndex < 0 || secondIndex < 0) {
				continue;
			}
			
			if(firstIndex > secondIndex) {
				return false;
			}
		}
		
		return true;
	}
	
	public static HashMap<String, ArrayList<ArrayList<Integer>>> getUpdates(ArrayList<ArrayList<Integer>> rules, ArrayList<ArrayList<Integer>> updates) {
		ArrayList<ArrayList<Integer>> validUpdates = new ArrayList<>();
		ArrayList<ArrayList<Integer>> invalidUpdates = new ArrayList<>();
		
		for(int i = 0; i < updates.size(); i++) {
			ArrayList<Integer> cur = updates.get(i);
			
			if(isValidUpdate(rules, cur)) {
				validUpdates.add(cur);
				
				continue;
			}
			
			invalidUpdates.add(cur);
		}
		
		HashMap<String, ArrayList<ArrayList<Integer>>> sortedUpdates = new HashMap<>();
		sortedUpdates.put("valid", validUpdates);
		sortedUpdates.put("invalid", invalidUpdates);
		
		return sortedUpdates;
	}
	
	public static ArrayList<ArrayList<Integer>> orderUpdates(ArrayList<ArrayList<Integer>> rules, ArrayList<ArrayList<Integer>> updates) {
		ArrayList<ArrayList<Integer>> orderedUpdates = new ArrayList<>();
		
		for(int i = 0; i < updates.size(); i++) {			
			@SuppressWarnings("unchecked")
			ArrayList<Integer> orderedUpdate = (ArrayList<Integer>)updates.get(i).clone();
			
			while(!isValidUpdate(rules, orderedUpdate)) {
				for(int j = 0; j < rules.size(); j++) {
					ArrayList<Integer> curRule = rules.get(j);
					int ruleOnePos = find(orderedUpdate, curRule.get(0));
					int ruleTwoPos = find(orderedUpdate, curRule.get(1));
			    					
					if(ruleOnePos == -1 || ruleTwoPos == -1 || ruleOnePos < ruleTwoPos) {						
						continue;
					}
					
					int temp = orderedUpdate.get(ruleOnePos);
					orderedUpdate.set(ruleOnePos, orderedUpdate.get(ruleTwoPos));
					orderedUpdate.set(ruleTwoPos, temp);
				}
			}
			
			orderedUpdates.add(orderedUpdate);
		}
		
		return orderedUpdates;
	}
	
	public static int sumMedians(ArrayList<ArrayList<Integer>> updates) {
		int sum = 0;
		
		for(int i = 0; i < updates.size(); i++) {
			sum += updates.get(i).get(updates.get(i).size() / 2 );
		}		
		return sum;
	}
}

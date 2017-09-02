package uk.co.llblumire.cs2ja16.week2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

import util.Combinations;
import util.Pair;

/**
 * Finds the smallest difference between a set of input values.
 * 
 * @author L. L. Blumire
 *
 */
public class SmallestDifference {
	
	/**
	 * Stores the inputs
	 */
	List<Integer> data;
	
	/**
	 * Stores the smallest difference between inputs after findSmallest()
	 */
	Optional<Integer> smallest;

	/**
	 * Constructs the SmallestDifference class
	 * @param input The numbers to check the smallest input between
	 */
	public SmallestDifference(String input) {
		this.data = Arrays.asList(input.split(" "))
				.stream()
				.map(string -> Integer.parseInt(string))
				.collect(Collectors.toList());
		this.smallest = Optional.empty();
	}
	
	/**
	 * Populates smallest with the smallest difference between two numbers in data
	 */
	void findSmallest() {
		Optional<Integer> smallest = Optional.empty();
		
		Combinations<Integer> c = new Combinations<>(this.data);
		for (Pair<Integer, Integer> testPair : c) {
			int test = Math.abs(testPair.first - testPair.second);
			smallest = Optional.of(smallest.map(current -> (current < test) ? current : test).orElse(test));
		}
		
		this.smallest = smallest;
	}
	
	/** 
	 * Generates an output string from the smallest value. If none has been generated it generates them.
	 * @return The output string
	 */
	String getResult() {
		if (this.smallest.isPresent()) {
			return "The smallest difference is " + this.smallest.get();
		} else {
			this.findSmallest();
			return this.getResult();
		}
	}
	
	/**
	 * Prompt with a dialog box to enter the values space seperated and then show the user the smallest difference
	 */
	public static void main(String[] args) {
		String userIn = JOptionPane.showInputDialog(null, "Enter numbers separated by spaces:");
		SmallestDifference sd = new SmallestDifference(userIn);
		sd.findSmallest();
		JOptionPane.showMessageDialog(null, sd.getResult());
	}

}

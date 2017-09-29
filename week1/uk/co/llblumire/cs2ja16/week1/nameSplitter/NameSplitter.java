package uk.co.llblumire.cs2ja16.week1.nameSplitter;

import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class NameSplitter {
	
	public static void main(String[] args) {
		String names = JOptionPane.showInputDialog(null, "Enter your names:"); // Get input

		String initials = names.chars() // Create a chars iterator
				.mapToObj(i -> (char)i) // Fix java's nonsense and make it actuall a char iterator
				.filter(Character::isUpperCase) // Only take uppercase values 
				.map(String::valueOf) // Turn each element into a string
				.collect(Collectors.joining()); // Join all the strings

		JOptionPane.showMessageDialog(null, initials);
	}
}

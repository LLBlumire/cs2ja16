package uk.co.llblumire.cs2ja16.week2;

import java.util.Arrays;

import javax.swing.JOptionPane;

/**
 * Validates whether two input strings are anagrams (case insensitive, whitespace ignored)
 * 
 * @author L. L. Blumire
 *
 */
public class AnagramCheck {
	
	/**
	 * The first of the two strings being checked 
	 */
	String case1;
	
	/**
	 * The second of the two strings being checked
	 */
	String case2;
	
	/**
	 * Constructor for AnagramCheck
	 * @param case1 The first of the two strings being checked
	 * @param case2 The second of the two strings being checked
	 */
	public AnagramCheck(String case1, String case2) {
		this.case1 = case1;
		this.case2 = case2;
	}
	
	/**
	 * Checks if the two strings are anagrams
	 * @return true if they are, false if they aren't
	 */
	public Boolean checkAnagram() {
		// Convert both cases into char arrays
		char[] chars1 = this.case1.toLowerCase().replaceAll("[^a-z]", "").toCharArray();
		char[] chars2 = this.case2.toLowerCase().replaceAll("[^a-z]", "").toCharArray();
		
		// Sort both char arrays
		Arrays.sort(chars1);
		Arrays.sort(chars2);
		
		return (Arrays.equals(chars1, chars2)); 
	}
	
	/**
	 * Prmopt the user to input '.' separated words, then output if they are anagrams 
	 */
	public static void main(String[] args) {
		String userIn = JOptionPane.showInputDialog(null, "Enter potential anagrams separated by a `.'");
		String[] inputs = userIn.split("\\.");
		AnagramCheck ac = new AnagramCheck(inputs[0], inputs[1]);
		if (ac.checkAnagram()) {
			JOptionPane.showMessageDialog(null, "\"" + inputs[0] + "\" and \"" + inputs[1] + "\" are anagrams");
		} else {
			JOptionPane.showMessageDialog(null, "\"" + inputs[0] + "\" and \"" + inputs[1] + "\" are NOT anagrams");
		}
	}
	
}

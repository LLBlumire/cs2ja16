package uk.co.llblumire.cs2ja16.week2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import util.Combinations;
import util.Pair;

/**
 * Checks multiple words to find anagrams between them
 * 
 * @author L. L. Blumire
 *
 */
public class MultiAnagramCheck {
	
	/**
	 * The string being tested for anagrams
	 */
	String[] input;
	
	/**
	 * Constructer for MultiAnagramCheck
	 * @param input space separted words to check
	 */
	public MultiAnagramCheck(String input) {
		this.input = input.split("\\s");
	}
	
	/**
	 * @return a list of pairs of anagrams from the string being tested
	 */
	public List<Pair<String, String>> getAnagrams() {
		List<Pair<String, String>> results = new ArrayList<Pair<String, String>>();
		Combinations<String> c = new Combinations<String>(Arrays.asList(this.input));
		for (Pair<String, String> pair : c) {
			AnagramCheck ac = new AnagramCheck(pair.first, pair.second);
			if (ac.checkAnagram()) {
				results.add(pair);
			}
		}
		return results;
	}

	/**
	 * Prompt the user to input a list of space separated words, then output the anagrams.
	 */
	public static void main(String[] args) {
		String userIn = JOptionPane.showInputDialog(null, "Enter potential anagrams separated spaces");
		MultiAnagramCheck mac = new MultiAnagramCheck(userIn);
		for (Pair<String, String> pair : mac.getAnagrams()) {
			JOptionPane.showMessageDialog(null, "\"" + pair.first + "\" and \"" + pair.second + "\" are anagrams");
		}
	}

}

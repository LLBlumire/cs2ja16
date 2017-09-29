package uk.co.llblumire.cs2ja16.week1.jOptionPaneGui;

import javax.swing.JOptionPane;

public class SCounter {
	public static void main(String[] args) {
		String input = JOptionPane.showInputDialog(null, "Enter a String:");
		JOptionPane.showMessageDialog(null, String.format("There are %d s in %s", input.chars().filter(ch -> ch == 's').count(), input));
	}
}

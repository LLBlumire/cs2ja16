package uk.co.llblumire.cs2ja16.week1.arrayNameSplitter;

import java.util.stream.Stream;

import javax.swing.JOptionPane;

public class ArrayNameSplitter {
	public static void main(String[] args) {
		String[] names = JOptionPane.showInputDialog(null, "Enter your names:").split(" "); // Get input

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < names.length; i++) {
			sb.append(String.format("%d: %s\n", i, names[i]));
		}
		
		JOptionPane.showMessageDialog(null, sb.toString());
	}
}

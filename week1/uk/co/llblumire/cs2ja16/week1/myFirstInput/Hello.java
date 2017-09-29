package uk.co.llblumire.cs2ja16.week1.myFirstInput;

import javax.swing.JFrame;

/**
 * Advanced "Hello world!" application using GUI
 */
 public class Hello {

	/**
	 *  Pause for a number of seconds.
	 */
	private static void pause(int seconds) {
		long msec = 1000 * seconds;
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		System.out.println("Starting..."); // it prints a string in the console

		JFrame myWindow; // the reference variable of class type JFrame
		myWindow = new JFrame(); // the object: a new instance of JFrame
		myWindow.setSize(300, 200); // set private attributes: width and heigth
		myWindow.setTitle("My First Java Window");
		System.out.println("width = " + myWindow.getWidth() + " and "
				+ "heigth = " + myWindow.getHeight());
		System.out.println("Title = \"" + myWindow.getTitle() + "\"");

		myWindow.setVisible(true); // it makes the windows to show up
		pause(2);
		myWindow.setVisible(false); // it makes the windows to hide

		System.out.println("\nDone.");
	}
}

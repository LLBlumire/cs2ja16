package uk.co.llblumire.cs2ja16.robotconsole;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javax.swing.JFileChooser;

/**
 * An interface for drawing and modifying arenas and robots
 * 
 * @author L. L. Blumire
 *
 */
public class RobotInterface {
	private Scanner s;
	private RobotArena myArena;

	/**
	 * Adds a robot to the arena
	 */
	private void addRobot() {
		this.myArena.addRobot();
	}

	/**
	 * Outputs information about the contents of the arena
	 */
	private void giveInformation() {
		System.out.println(myArena.toString());
	}

	/**
	 * Draws a visual representation of the arena
	 */
	private void displayArena() {
		String buffer = new String();

		// Header
		buffer += "/";
		for (int x = 0; x < this.myArena.getDimX(); ++x) {
			buffer += "-";
		}
		buffer += "\\";

		// Main
		for (int y = 0; y < this.myArena.getDimY(); ++y) {
			buffer += "\n|";
			for (int x = 0; x < this.myArena.getDimX(); ++x) {
				if (this.myArena.getRobotAt(x, y).isPresent()) {
					buffer += "R";
				} else {
					buffer += " ";
				}
			}
			buffer += "|";
		}

		// Footer
		buffer += "\n\\";
		for (int x = 0; x < this.myArena.getDimX(); ++x) {
			buffer += "-";
		}
		buffer += "/";

		System.out.println(buffer);
	}

	/**
	 * Instructs all robots in the arena to move
	 */
	private void moveRobots() {
		this.myArena.moveAllRobots();
	}

	/**
	 * Prompts the user for details and uses them to create a new arena
	 */
	private void newArena() {
		String[] dimensions;
		do {
			System.out.print("Enter the dimensions of the new arena, followed by the number of robtos (x y n): ");
			dimensions = this.s.nextLine().split(" ");
		} while (dimensions.length != 3);

		int[] dimensionInts = new int[3];
		for (int i = 0; i < 3; i++) {
			try {
				dimensionInts[i] = Integer.parseInt(dimensions[i]);
			} catch (NumberFormatException error) {
				this.newArena(); // Attempt to make a new arena again, this code is hacky and uses recursion to
									// solve this
				return;
			}
		}

		this.myArena = new RobotArena(dimensionInts[0], dimensionInts[1], dimensionInts[2]);

	}

	/**
	 * Ask the user for a file name and path with a gui chooser and then saves the
	 * arena to that location
	 * 
	 * @throws IOException
	 *             if the file cannot be written to
	 */
	private void saveArena() throws IOException {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.myArena);
			oos.flush();
			oos.close();
		}
	}

	/**
	 * Ask the user for a file name and path with a gui chooser and then loads the
	 * arena from that location
	 * 
	 * @throws ClassNotFoundException
	 *             if the file cannot be deserialised as an arena
	 * @throws IOException
	 *             if the file cannot be read from
	 */
	private void loadArena() throws ClassNotFoundException, IOException {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
			ObjectInputStream oos = new ObjectInputStream(fis);
			this.myArena = (RobotArena) oos.readObject();
			oos.close();
		}

	}

	/**
	 * Constructor, runs the business logic of the interface
	 */
	public RobotInterface() {
		this.s = new Scanner(System.in);
		this.myArena = new RobotArena(20, 6, 5);

		char ch = ' ';
		do {
			System.out.print(
					"Enter (A)dd robot, give (I)nformation, (D)isplay arena, (M)ove robots, (N)ew arena, (S)ave arena, (L)oad arena, or e(X)it > ");
			ch = this.s.next().charAt(0);
			this.s.nextLine();
			switch (ch) {
			case 'A':
			case 'a':
				this.addRobot();
				break;
			case 'I':
			case 'i':
				this.giveInformation();
				break;
			case 'D':
			case 'd':
				this.displayArena();
				break;
			case 'M':
			case 'm':
				this.moveRobots();
				break;
			case 'N':
			case 'n':
				this.newArena();
				break;
			case 'S':
			case 's':
				try {
					this.saveArena();
					System.out.println("Saved");
				} catch (IOException error) {
					System.out.println("Failed to save");
				}
				break;
			case 'L':
			case 'l':
				try {
					this.loadArena();
					System.out.println("Loaded");
				} catch (ClassNotFoundException error) {
					System.out.println("File selected not arena file");
				} catch (IOException error) {
					System.out.println("Failed to load file");
				}
				break;
			case '#':
				for (int i = 0; i < 10; ++i) {
					this.displayArena();
					this.moveRobots();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				this.displayArena();
				break;
			case 'X':
			case 'x':
				ch = 'X';
				break;
			}
		} while (ch != 'X');
		s.close();
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		RobotInterface ri = new RobotInterface();
	}

}

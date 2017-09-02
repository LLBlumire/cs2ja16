package uk.co.llblumire.cs2ja16.robotconsole;

import java.util.Scanner;

/**
 * Specifies the behaviour of a Robot
 * 
 * @author L. L. Blumire
 *
 */
public class Robot {

	/**
	 * The X position of the robot
	 */
	int x;
	
	/**
	 * The Y position of the robot
	 */
	int y;
	
	/**
	 * The unique identifier of the robot
	 */
	int uid;
	
	/**
	 * The source of the unique identifier of the robot
	 */
	static int uidSource = 0;
	
	/**
	 * Constructor for Robot
	 * @param x The X position of the robot
	 * @param y The Y position of the robot
	 */
	public Robot(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.uid = Robot.getUidSource(); // Assign and postincrement
	}
	
	/**
	 * @param sx The X coordinate to check robot precense against
	 * @param sy The Y coordinate to check robot precense against
	 * @return true if the robot is at the supplied coordinates, else false
	 */
	public boolean isHere(int sx, int sy) {
		return (this.getX() == sx && this.getY() == sy);
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the uid
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * @return the uidSource
	 */
	public static int getUidSource() {
		return uidSource++; // Post increment to ensure uniqueness
	}
	
	@Override
	public String toString() {
		return "" + this.uid + "@" + this.x + "," + this.y + ";";
	}
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.print("How many robots in your world? ");
		int numRobots = s.nextInt();
		Robot[] allRobots = new Robot[numRobots];
		int rx, ry;
		System.out.println("Enter the position of each robot in turn");
		for (int i = 0; i < numRobots; ++i) {
			System.out.print("Enter \"x y\" position for robot number " + i + ": ");
			rx = s.nextInt();
			ry = s.nextInt();
			allRobots[i] = new Robot(rx, ry);
		}
		for (int i = 0; i < numRobots; ++i) {
			System.out.println(allRobots[i].toString());
		}
		System.out.print("Enter a coordinate to check for a robot: ");
		rx = s.nextInt();
		ry = s.nextInt();
		for (Robot robot : allRobots) {
			if (robot.isHere(rx, ry)) {
				System.out.println("Robot " + robot.getUid() + " is at " + robot.getX() + "," + robot.getY());
			} else {
				System.out.println("No robot at specified postition");
			}
		}
		s.close();
	}


}
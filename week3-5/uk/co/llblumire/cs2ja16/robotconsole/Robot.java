package uk.co.llblumire.cs2ja16.robotconsole;

import java.io.Serializable;
import java.util.Scanner;

import util.Pair;

/**
 * Specifies the behaviour of a Robot
 * 
 * @author L. L. Blumire
 *
 */
public class Robot implements Serializable {

	/**
	 * The serial ID of the class
	 */
	private static final long serialVersionUID = 8744362623334486365L;

	/**
	 * The X position of the robot
	 */
	private int x;

	/**
	 * The Y position of the robot
	 */
	private int y;

	/**
	 * The Direction of the robot
	 */
	private Direction direction;

	/**
	 * The unique identifier of the robot
	 */
	private int uid;

	/**
	 * The source of the unique identifier of the robot
	 */
	private static int uidSource = 0;

	/**
	 * Constructor for Robot
	 * 
	 * @param x
	 *            The X position of the robot
	 * @param y
	 *            The Y position of the robot
	 * @param direction
	 *            The direction the robot should face
	 */
	public Robot(int x, int y, Direction direction) {
		this.setX(x);
		this.setY(y);
		this.setDirection(direction);
		this.uid = Robot.getUidSource(); // Assign and postincrement
	}

	/**
	 * @param sx
	 *            The X coordinate to check robot precense against
	 * @param sy
	 *            The Y coordinate to check robot precense against
	 * @return true if the robot is at the supplied coordinates, else false
	 */
	public boolean isHere(int sx, int sy) {
		return (this.getX() == sx && this.getY() == sy);
	}

	/**
	 * @param delta
	 *            The delta to apply (dx, dy)
	 */
	public void applyDelta(Pair<Integer, Integer> delta) {
		this.x += delta.first;
		this.y += delta.second;
	}

	public Pair<Integer, Integer> phantomDelta(Pair<Integer, Integer> delta) {
		return new Pair<Integer, Integer>(this.x + delta.first, this.y + delta.second);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the direction
	 */
	public Direction getDirection() {
		return this.direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * @return the uid
	 */
	public int getUid() {
		return this.uid;
	}

	/**
	 * @return the uidSource
	 */
	public static int getUidSource() {
		return Robot.uidSource++; // Post increment to ensure uniqueness
	}

	@Override
	public String toString() {
		return "" + this.uid + "@" + this.x + "," + this.y + ":" + this.direction.toString() + ";";
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
			allRobots[i] = new Robot(rx, ry, Direction.randomDirection());
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
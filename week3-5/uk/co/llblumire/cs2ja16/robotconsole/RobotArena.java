package uk.co.llblumire.cs2ja16.robotconsole;

import java.io.Serializable;
import java.util.Optional;
import java.util.Random;

import util.Pair;

/**
 * Represents an Arena of robots
 * 
 * @author L. L. Blumire
 *
 */
public class RobotArena implements Serializable {

	/**
	 * The serial ID of the class
	 */
	private static final long serialVersionUID = 2633885840204196191L;

	/**
	 * The X length of the arena
	 */
	private int dimX;

	/**
	 * The Y length of the arena
	 */
	private int dimY;

	/**
	 * The maximum capacity of the arena
	 */
	private int maxCapacity;

	/**
	 * The number of robots currently in the arena
	 */
	private int numRobots;

	/**
	 * The robots in the arena
	 */
	private Robot[] robots;

	/**
	 * Constructor for RobotArena
	 * 
	 * @param dimX
	 *            The X length of the arena
	 * @param dimY
	 *            The Y lenght of the arena
	 * @param maxCapacity
	 *            The maximum capacity of the arena
	 */
	public RobotArena(int dimX, int dimY, int maxCapacity) {
		this.dimX = dimX;
		this.dimY = dimY;
		this.maxCapacity = maxCapacity;
		this.robots = new Robot[maxCapacity];
	}

	/**
	 * Adds a robot to random empty coordinates
	 */
	public void addRobot() {
		if (this.numRobots == this.maxCapacity) {
			return;
		}
		Random rng = new Random();
		int randX;
		int randY;
		do {
			randX = rng.nextInt(this.dimX);
			randY = rng.nextInt(this.dimY);
		} while (this.getRobotAt(randX, randY).isPresent());
		this.robots[this.numRobots++] = new Robot(randX, randY, Direction.randomDirection()); // Postincrement number of
																								// robots
	}

	/**
	 * Attempts to get a robot at given coordinates
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @return Either an optional of the robot or empty if nothing found
	 */
	public Optional<Robot> getRobotAt(int x, int y) {
		for (int i = 0; i < this.numRobots; ++i) {
			Robot robot = this.getRobots()[i];
			if (robot.getX() == x && robot.getY() == y) {
				return Optional.of(robot);
			}
		}
		return Optional.empty();
	}

	/**
	 * Moves a robot in the context of this arena
	 * 
	 * @param robot
	 *            the robot to move
	 * @return whether the move was successful
	 */
	public boolean moveRobot(Robot robot) {
		Pair<Integer, Integer> delta = robot.getDirection().getDelta();
		Pair<Integer, Integer> target = robot.phantomDelta(delta);
		if (target.first >= this.getDimX() || target.first < 0 || target.second >= this.getDimY() || target.second < 0
				|| this.getRobotAt(target.first, target.second).isPresent()) {
			Direction newDirection;
			do {
				newDirection = Direction.randomDirection();
			} while (newDirection == robot.getDirection());
			robot.setDirection(newDirection);
			return false;
		} else {
			robot.applyDelta(delta);
			return true;
		}
	}

	/**
	 * Moves all robots in the arena
	 */
	public void moveAllRobots() {
		for (int i = 0; i < this.numRobots; ++i) {
			this.moveRobot(this.robots[i]);
		}
	}

	/**
	 * @return the dimX
	 */
	public int getDimX() {
		return dimX;
	}

	/**
	 * @return the dimY
	 */
	public int getDimY() {
		return dimY;
	}

	/**
	 * @return the maxCapacity
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * @return the numRobots
	 */
	public int getNumRobots() {
		return numRobots;
	}

	/**
	 * @return the robots
	 */
	public Robot[] getRobots() {
		return robots;
	}

	@Override
	public String toString() {
		String buffer = new String();
		for (int i = 0; i < this.getNumRobots(); ++i) {
			buffer += this.getRobots()[i].toString() + "\n";
		}
		try {
			buffer = buffer.substring(0, buffer.length() - 1);
		} catch (IndexOutOfBoundsException e) {
			buffer = new String();
		}
		return buffer;
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		RobotArena ra = new RobotArena(10, 10, 4);
	}

}

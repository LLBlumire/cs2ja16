package uk.co.llblumire.cs2ja16.robotconsole;

import java.util.Optional;
import java.util.Random;

/**
 * @author Lucille
 *
 */
public class RobotArena {
	
	/**
	 * The X length of the arena
	 */
	int dimX;
	
	/**
	 * The Y length of the arena
	 */
	int dimY;
	
	/**
	 * The maximum capacity of the arena 
	 */
	int maxCapacity;
	
	/**
	 * The number of robots currently in the arena
	 */
	int numRobots;
	
	/**
	 * The robots in the arena
	 */
	Robot[] robots;
	
	/**
	 * Constructor for RobotArena
	 * @param dimX The X length of the arena
	 * @param dimY The Y lenght of the arena
	 * @param maxCapacity The maximum capacity of the arena
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
		Random rng = new Random();
		int randX; 
		int randY;
		do  {
			randX = rng.nextInt(this.dimX);
			randY = rng.nextInt(this.dimY);
		} while (this.getRobotAt(randX, randY).isPresent());
		this.robots[this.numRobots++] = new Robot(randX, randY); // Postincrement number of robots
	}
	
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
			buffer = buffer.substring(0, buffer.length()-1);
		} catch (IndexOutOfBoundsException e) {
			buffer = new String();
		}
		return buffer;
	}

	public static void main(String[] args) {
		RobotArena ra = new RobotArena(10, 10, 4);
	}
	
}

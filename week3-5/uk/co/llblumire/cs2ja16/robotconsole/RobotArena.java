/**
 * 
 */
package uk.co.llblumire.cs2ja16.robotconsole;

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RobotArena ra = new RobotArena(10, 10, 4);
	}
	
}

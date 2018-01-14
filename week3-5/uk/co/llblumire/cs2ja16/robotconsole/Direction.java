package uk.co.llblumire.cs2ja16.robotconsole;

import java.io.Serializable;
import java.util.Random;

import util.Pair;

/**
 * Represents the possible cardinal directions
 * 
 * @author L. L. Blumire
 *
 */
public enum Direction implements Serializable {
	North, East, South, West;

	public static Direction randomDirection() {
		Random rng = new Random();
		Direction[] directions = Direction.class.getEnumConstants();
		return directions[rng.nextInt(directions.length)];
	}

	/**
	 * Returns the change in x and y coordinates implied by the Direction
	 * 
	 * @return (dx, dy)
	 */
	public Pair<Integer, Integer> getDelta() {
		switch (this) {
		case North:
			return new Pair<Integer, Integer>(0, -1);
		case East:
			return new Pair<Integer, Integer>(1, 0);
		case South:
			return new Pair<Integer, Integer>(0, 1);
		case West:
			return new Pair<Integer, Integer>(-1, 0);
		}
		throw new Error("Unreachable");
	}

	@Override
	public String toString() {
		return this.name().substring(0, 1);
	}
}

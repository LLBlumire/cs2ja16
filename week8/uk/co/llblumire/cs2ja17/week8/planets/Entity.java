package uk.co.llblumire.cs2ja17.week8.planets;

import javafx.scene.image.Image;

abstract public class Entity {
	/**
	 * The x coordinate of the entity
	 */
	protected double x;
	
	/**
	 * The y coordinate of the entity
	 */
	protected double y;
	
	/**
	 * The width of the entity
	 */
	protected double width;
	
	/**
	 * The height of the entity
	 */
	protected double height;
	
	/**
	 * The graphic of the entity
	 */
    protected Image display;

	public abstract void update(double dt);
}

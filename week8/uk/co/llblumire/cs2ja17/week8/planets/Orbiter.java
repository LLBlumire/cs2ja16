package uk.co.llblumire.cs2ja17.week8.planets;

import javafx.scene.image.Image;

public class Orbiter extends Entity {
	/**
	 * The mean distance the planet is from the sun in millions of km.
	 */
	protected double distanceFromOrbitee;
	
	/**
	 * The period of revolution of the planet in days.
	 */
	protected double periodOfRevolution;
	
	/**
	 * The entitee being orbited about.
	 */
	protected Entity orbitee;

	public Entity getOrbitee() {
		return orbitee;
	}


	/**
	 * How much the entity has progressed along it's arc.
	 */
	protected double arcProgress;
	
	/**
	 * Constructs a new instance of Orbiter
	 * @param distanceFromOrbitee
	 * @param periodOfRevolution
	 * @param orbitee
	 */
	public Orbiter(double distanceFromOrbitee, double periodOfRevolution, double width, double height, Entity orbitee, Image display) {
		this.distanceFromOrbitee = distanceFromOrbitee;
		this.periodOfRevolution = periodOfRevolution;
		this.orbitee = orbitee;
		this.y = orbitee.y;
		this.x = orbitee.x + this.distanceFromOrbitee;
		this.width = width;
		this.height = height;
		this.display = display;
	}


	@Override
	public void update(double dt) {
		this.arcProgress += dt / this.periodOfRevolution;
		
		this.x = orbitee.x + this.distanceFromOrbitee * Math.cos(this.arcProgress);
		this.y = orbitee.y + this.distanceFromOrbitee * Math.sin(this.arcProgress);
	}
	
}

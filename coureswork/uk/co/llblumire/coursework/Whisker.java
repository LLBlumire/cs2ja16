package uk.co.llblumire.coursework;

import javafx.scene.paint.Color;

/**
 * A whisker as used by WhiskerRobot to sense it's surroundings.
 * 
 * @author L. L. Blumire
 *
 */
public final class Whisker implements Entity {
	
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -8022023005126811148L;

	/** 
	 * The starting x coordinate of the whisker.
	 */
	double x;
	
	/**
	 * The change in the x coordinate of the whisker from start to finish.
	 */
	double dx; 
	
	/**
	 * The starting y coordinate of the whisker.
	 */
	double y;
	
	/**
	 * The change in the y coordinate of the whisker from start to finish.
	 */
	double dy;
	
	/**
	 * The thickness of the whisker.
	 */
	double thickness;
	
	public Whisker() {
		this.x = 0;
		this.dx = 0;
		this.y = 0;
		this.dy = 0;
		this.thickness = 0;
	}

	@Override
	public Collider collider() {
		Collider collider = new Collider.LineCollider(x, dx, y, dy);
		collider.hardCollision = false;
		return collider;
	}

	@Override
	public Renderer renderer() {
		return new LineRenderer(this.x, this.y, this.dx, this.dy, Color.GREEN, this.thickness);
	}

	@Override
	public Updater updater() {
		return new NoUpdater();
	}

	@Override
	public String info() {
		return String.format("Whisker\n\to=(%.0f, %.0f)\n\td=(%.0f, %.0f)\n\tt=%.2f", this.x, this.y, this.dx, this.dy, this.thickness);
	}

}

package uk.co.llblumire.coursework;

import javafx.scene.paint.Color;

/**
 * Walls represent a point to point barrier in an environment.
 * 
 * @author L. L. Blumire
 *
 */
public final class Wall implements Entity {

	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -3015999431867773496L;
	
	/**
	 * The initial x coordinate of the wall. 
	 */
	private double x;
	/**
	 * The initial y coordinate of the wall.
	 */
	private double y;
	/**
	 * The change in the x coordinate of the wall from start to finish.
	 */
	private double dx;
	
	/**
	 * The change in the y coordinate of the wall from start to finish.
	 */
	private double dy;
	
	/**
	 * The graphical thickness of the wall (no collision effect)
	 */
	private double thicknkess;
	
	/**
	 * Constructs a new Wall from it's fields.
	 */
	public Wall(double x, double y, double dx, double dy, double thickness) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.thicknkess = thickness;
	}
	
	@Override
	public Collider collider() {
		return new Collider.LineCollider(x, dx, y, dy);
	}

	@Override
	public Renderer renderer() {
		return new LineRenderer(this.x, this.y, this.dx, this.dy, Color.BLACK, this.thicknkess);
	}

	@Override
	public Updater updater() {
		return new NoUpdater();
	}

	@Override
	public String info() {
		return String.format("Wall\n\to=(%.0f, %.0f)\n\td=(%.0f, %.0f)\n\tt=%.2f", this.x, this.y, this.dx, this.dy, this.thicknkess);
	}

}

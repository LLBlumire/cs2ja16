package uk.co.llblumire.coursework;

/**
 * An invisible immutable entity that has only a LineCollider.  
 * 
 * @author L. L. Blumire
 *
 */
public final class LineColliderEntity implements Entity {
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -6513483465912994076L;

	/**
	 * The initial x coordinate of the line.
	 */
	private double x;
	
	/**
	 * The change in the x position of the line from start to finish.
	 */
	private double dx;
	
	/**
	 * The initial y coordinate of the line.
	 */
	private double y;
	
	/**
	 * The change in the y position of the line from start to finish.
	 */
	private double dy;
	
	/**
	 * Constructs a new LineColliderEntity from it's fields.
	 */
	public LineColliderEntity(double x, double dx, double y, double dy) {
		this.x = x;
		this.dx = dx;
		this.y = y;
		this.dy = dy;
	}

	@Override
	public Collider collider() {
		return new Collider.LineCollider(this.x, this.dx, this.y, this.dy);
	}

	@Override
	public Renderer renderer() {
		return new NoRenderer();
	}

	@Override
	public Updater updater() {
		return new NoUpdater();
	}
	
	@Override
	public String info() {
		return String.format("LineColliderEntity\n\to=(%.0f, %.0f)\n\td=(%.0f, %.0f)", this.x, this.y, this.dx, this.dy);
	}

}

package uk.co.llblumire.coursework;

import javafx.scene.paint.Color;

/**
 * An unmoving block.
 * 
 * @author L. L. Blumire
 *
 */
public final class StaticBlock implements Entity {

	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -116976020618021251L;
	
	/**
	 * The X coordinate of the top left of the block.
	 */
	private double x;
	
	/**
	 * The Y coordinate of the top left of the block.
	 */
	private double y;
	
	/**
	 * The width of the block.
	 */
	private double width;
	
	/**
	 * The height of the block.
	 */
	private double height;
	
	/**
	 * Constructs a StaticBlock from it's fields.
	 * @param x The X coordinate of the top left of the block.
	 * @param y The Y coordinate of the top left of the block.
	 * @param width The width of the block.
	 * @param height The height of the block.
	 */
	public StaticBlock(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Collider collider() {
		return new Collider.BoxCollider(x, y, width, height);
	}

	@Override
	public Renderer renderer() {
		return new RectangleRenderer(x, y, width, height, Color.BLACK);
	}

	@Override
	public Updater updater() {
		return new NoUpdater();
	}

	@Override
	public String info() {
		return String.format("StaticBlock\n\to=(%.0f, %.0f)\n\td=(%.0f, %.0f)", this.x, this.y, this.width, this.height);
	}

}

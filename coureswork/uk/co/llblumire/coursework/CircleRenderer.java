package uk.co.llblumire.coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * A Renderer for drawing circles.
 * 
 * @author L. L. Blumire
 *
 */
public final class CircleRenderer implements Renderer {

	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 989852890871403140L;

	/**
	 * The color of the circle.
	 */
	private RGBA color;
	/**
	 * The X coordinate of the centre of the circle.
	 */
	private double x;
	/**
	 * The Y coordinate of the centre of the circle.
	 */
	private double y;
	/**
	 * The radius of the circle
	 */
	private double rad;

	/**
	 * Constructs a new CircleRenderer from it's fields.
	 * 
	 * @param x
	 *            The X coordinate of the centre of the circle.
	 * @param y
	 *            The Y coordinate of the centre of the circle.
	 * @param rad
	 *            The radius of the circle
	 * @param color
	 *            The color of the circle.
	 */
	public CircleRenderer(double x, double y, double rad, Color color) {
		this.color = new RGBA(color);
		this.x = x;
		this.y = y;
		this.rad = rad;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(this.color.toColor());
		gc.fillArc(x, y, rad * 2, rad * 2, 0, 360, ArcType.ROUND);
	}

}

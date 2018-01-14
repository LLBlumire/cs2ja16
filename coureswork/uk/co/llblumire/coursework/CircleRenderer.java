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
	 * The color of the circle.
	 */
	private Color color;
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
	 */
	public CircleRenderer(double x, double y, double rad, Color color) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.rad = rad;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(this.color);
		gc.fillArc(x, y, rad*2, rad*2, 0, 360, ArcType.ROUND);
	}

}

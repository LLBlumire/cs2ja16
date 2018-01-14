package uk.co.llblumire.coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A Renderer for drawing lines.
 * 
 * @author L. L. Blumire
 *
 */
public final class LineRenderer implements Renderer {
	/**
	 * The color of the line.
	 */
	private Color color;

	/**
	 * The starting x coordinate of the line.
	 */
	private double x;

	/**
	 * The change in the x position of the line from start to finish.
	 */
	private double dx;

	/**
	 * The starting y coordinate of the line.
	 */
	private double y;

	/**
	 * The change in the y position of the line from start to finish.
	 */
	private double dy;
	
	/**
	 * The thickness of the line.
	 */
	private double thickness;

	/**
	 * Constructs a LineRenderer from it's fields.
	 */
	public LineRenderer(double x, double y, double dx, double dy, Color color, double thickness) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.thickness = thickness;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setStroke(this.color);
		gc.setLineWidth(this.thickness);
		gc.strokeLine(x, y, x + dx, y + dy);
	}

}

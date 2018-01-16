package uk.co.llblumire.coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Renderer that draws a rectangle to a graphics context.
 *  
 * @author L. L. Blumire
 *
 */
public final class RectangleRenderer implements Renderer {

	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 6331126009007142913L;
	
	/**
	 * The top right X coordinate of the rectangle.
	 */
	private double x;
	
	/**
	 * The top right Y coordinate of the rectangle.
	 */
	private double y;
	
	/**
	 * The width of the rectangle.
	 */
	private double width;
	
	/**
	 * The height of the rectangle.
	 */
	private double height;
	
	/**
	 * The colour to fill the rectangle with.
	 */
	private Color color;
	
	/**
	 * Construct a RectangleRenderer from it's fields.
	 */
	public RectangleRenderer(double x, double y, double width, double height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(this.color);
		gc.fillRect(this.x, this.y, this.width, this.height);
	}

}

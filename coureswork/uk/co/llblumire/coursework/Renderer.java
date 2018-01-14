package uk.co.llblumire.coursework;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface implemented by classes that facilitate rendering something to a
 * graphics context.
 * 
 * @author L. L. Blumire
 *
 */
public interface Renderer {
	/**
	 * Render the class to the GraphicsContext
	 */
	public abstract void render(GraphicsContext gc);
}

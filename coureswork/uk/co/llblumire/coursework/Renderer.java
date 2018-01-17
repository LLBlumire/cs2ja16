package uk.co.llblumire.coursework;

import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface implemented by classes that facilitate rendering something to a
 * graphics context.
 * 
 * @author L. L. Blumire
 *
 */
public interface Renderer extends Serializable {
	/**
	 * Render the class to the GraphicsContext
	 * 
	 * @param gc
	 *            The graphics context to render on.
	 */
	public abstract void render(GraphicsContext gc);

	/**
	 * Valency. Higher Valency things are rendered later. Default valency is 0.
	 * 
	 * @return The valency of the renderer. Higher values are rendered after earlier
	 *         values.
	 */
	public default int valency() {
		return 0;
	}
}

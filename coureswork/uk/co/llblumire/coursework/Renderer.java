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
	 */
	public abstract void render(GraphicsContext gc);
	
	/**
	 * Valency. Higher Valency things are rendered later. Default valency is 0.
	 */
	public default int valency() {
		return 0;
	}
}

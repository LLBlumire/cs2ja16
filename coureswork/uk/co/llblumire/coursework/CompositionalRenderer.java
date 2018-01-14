package uk.co.llblumire.coursework;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.canvas.GraphicsContext;

/**
 * Allows composing multiple Renderers into one Renderer.
 * 
 * @author L. L. Blumire
 *
 */
public final class CompositionalRenderer implements Renderer {

	/**
	 * The renderers being composed.
	 */
	private ArrayList<Renderer> renderers;

	/**
	 * Trivial constructor. Contains no composed elements.
	 */
	public CompositionalRenderer() {
		renderers = new ArrayList<Renderer>();
	}

	/**
	 * Compose constructor. Creates a CompositionalRenderer composing all Renderers
	 * passed as input.
	 * 
	 * @param renderers
	 */
	public CompositionalRenderer(Renderer... renderers) {
		this.renderers = new ArrayList<Renderer>(Arrays.asList(renderers));
	}

	@Override
	public void render(GraphicsContext gc) {
		this.renderers.forEach((renderer) -> renderer.render(gc));
	}

}

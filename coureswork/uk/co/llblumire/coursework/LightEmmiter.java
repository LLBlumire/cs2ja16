package uk.co.llblumire.coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LightEmmiter implements Entity {

	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -1207362449320376890L;

	/**
	 * The x coordinate of the centre of the light.
	 */
	private double x;

	/**
	 * The y coordinate of the centre of the light.
	 */
	private double y;

	/**
	 * The radius of the light.
	 */
	private double rad;

	/**
	 * The brightness of the light.
	 */
	private double brightness;

	/**
	 * Constructs a LightEmmiter from it's fields.
	 */
	public LightEmmiter(double x, double y, double rad, double brightness) {
		this.x = x;
		this.y = y;
		this.rad = rad;
		this.brightness = brightness;
	}
	
	/**
	 * Getter for the brightness of the light.
	 */
	public double getBrightness() {
		return this.brightness;
	}

	@Override
	public Collider collider() {
		Collider collider = new Collider.BoxCollider(x-rad, y-rad, rad*2, rad*2);
		collider.hardCollision = false;
		return collider;
	}

	@Override
	public Renderer renderer() {
		// TODO Auto-generated method stub
		double brightness = this.brightness;
		return new Renderer() {
			/**
			 * Serialisation ID
			 */
			private static final long serialVersionUID = 7700448228101028346L;

			@Override
			public void render(GraphicsContext gc) {
				new RectangleRenderer(x-rad, y-rad, rad*2, rad*2, new Color(1.0, 1.0, 0.0, (1.0 - (1.0 / (1.0 + brightness))))).render(gc);
			}
			
			@Override
			public int valency() {
				return 10;
			}
		};
	}

	@Override
	public Updater updater() {
		return new NoUpdater();
	}

	@Override
	public String info() {
		return String.format("LightEmmiter\n\to=(%.0f, %.0f)\n\tr=%.2f\n\tb=%.2f", this.x, this.y, this.rad,
				this.brightness);
	}

}

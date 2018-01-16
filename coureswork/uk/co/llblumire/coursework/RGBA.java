package uk.co.llblumire.coursework;

import java.io.Serializable;

import javafx.scene.paint.Color;

public class RGBA implements Serializable {
	
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -7742781395880894083L;
	public double red;
	public double green;
	public double blue;
	public double alpha;
	
	public RGBA(Color color) {
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
		this.alpha = color.getOpacity();
	}
	
	public Color toColor() {
		return new Color(this.red, this.green, this.blue, this.alpha);
	}
}

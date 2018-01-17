package uk.co.llblumire.coursework;

import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * Serializable Color Representation
 * 
 * @author L. L. Blumire
 *
 */
public class RGBA implements Serializable {
	
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -7742781395880894083L;
	
	/**
	 * Red Color Channel
	 */
	public double red;
	
	/**
	 * Green Color Channel
	 */
	public double green;
	
	/**
	 * Blue Color Channel
	 */
	public double blue;
	
	/**
	 * Alpha Color Channel
	 */
	public double alpha;
	
	public RGBA(Color color) {
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
		this.alpha = color.getOpacity();
	}
	
	/**
	 * Converts the RGBA to a JavaFX Color
	 */
	public Color toColor() {
		return new Color(this.red, this.green, this.blue, this.alpha);
	}
}

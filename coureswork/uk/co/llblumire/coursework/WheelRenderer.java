package uk.co.llblumire.coursework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WheelRenderer implements Renderer {

	LineRenderer leftWheel;
	LineRenderer rightWheel;
	
	public WheelRenderer(double x, double y, double rad, double angle) {
		double lsx = rad * (Math.cos(angle - (Math.PI/2.0)) + Math.cos(angle)) + x;
		double lsy = rad * (Math.sin(angle - (Math.PI/2.0)) + Math.sin(angle)) + y;
		double lex = rad * (Math.cos(angle - (Math.PI/2.0)) - Math.cos(angle)) + x;
		double ley = rad * (Math.sin(angle - (Math.PI/2.0)) - Math.sin(angle)) + y;
		leftWheel = new LineRenderer(lsx, lsy, lex-lsx, ley-lsy, Color.BLACK, rad/5);
		
		double rsx = rad * (Math.cos(angle + (Math.PI/2.0)) + Math.cos(angle)) + x;
		double rsy = rad * (Math.sin(angle + (Math.PI/2.0)) + Math.sin(angle)) + y;
		double rex = rad * (Math.cos(angle + (Math.PI/2.0)) - Math.cos(angle)) + x;
		double rey = rad * (Math.sin(angle + (Math.PI/2.0)) - Math.sin(angle)) + y;
		rightWheel = new LineRenderer(rsx, rsy, rex-rsx, rey-rsy, Color.BLACK, rad/5);
	}
	
	@Override
	public void render(GraphicsContext gc) {
		leftWheel.render(gc);
		rightWheel.render(gc);
	}

}


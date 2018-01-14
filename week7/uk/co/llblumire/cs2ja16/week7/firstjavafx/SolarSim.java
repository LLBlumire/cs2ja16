package uk.co.llblumire.cs2ja16.week7.firstjavafx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SolarSim extends Application {
	private int canvasSize = 512;				// constants for relevant sizes
	private double orbitSize = canvasSize / 4;
	private double sunSize = 80;
	private double earthSize = 30;
	private double marsSize = 30;
	private GraphicsContext gc; 
    private Image earth = new Image(getClass().getResourceAsStream("earth.png"));
    private Image sun = new Image(getClass().getResourceAsStream("sun.png"));
    private Image mars = new Image(getClass().getResourceAsStream("mars.png"));
			// note loading of images, which should be in package

    /**
     * drawIt ... draws object defined by given image at position and size
     * @param i
     * @param x
     * @param y
     * @param sz
     */
	public void drawIt (Image i, double x, double y, double sz) {
		gc.drawImage(i, x - sz/2, y - sz/2, sz, sz );
	}
	/**
	 *  draw system, with Earth at x,y
	 * @param earthX
	 * @param earthY
	 */
	private void drawSystem(double earthX, double earthY, double marsX, double marsY) {
		// now clear canvas and draw sun and moon
		gc.clearRect(0,  0,  canvasSize,  canvasSize);		// clear canvas
		drawIt( sun, canvasSize/2, canvasSize/2, sunSize );	// draw Sun
		drawIt( earth, earthX, earthY, earthSize );					// draw Earth
		drawIt( mars, marsX, marsY, marsSize );
	}

	/**
	 * draw system with Earth at specified angle
	 * @param t
	 */
	private void drawSystem(double t) {
		double ex = canvasSize/2 + orbitSize * Math.cos(t);	// calc x coord
		double ey = canvasSize/2 + orbitSize * Math.sin(t);	// and y
		double mx = canvasSize/2 + orbitSize * 1.5 * Math.cos(0.5*t);
		double my = canvasSize/2 + orbitSize * 1.5 * Math.sin(0.5*t);
		drawSystem(ex, ey, mx, my);									// and draw system
	}
	
	/**
	 * main function ... sets up canvas and timer
	 */
	@Override
	public void start(Stage stagePrimary) throws Exception {
		stagePrimary.setTitle("Solar System");	// set title
		
	    Group root = new Group();				// create group of what is to be shown
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    										// create canvas to draw on
	    root.getChildren().add( canvas );		// add canvas to root
	    gc = canvas.getGraphicsContext2D();
	    										// remember context of canvas drawn on
	    Scene scene = new Scene( root );		// put root	 in a scene
	    stagePrimary.setScene( scene );			// apply the scene to the stage
		
	    final long startNanoTime = System.nanoTime();
												// for animation, note start time

	    new AnimationTimer()					// create timer
	    	{
	    		public void handle(long currentNanoTime) {
	    				// define handle for what do at this time
	    			double t = (currentNanoTime - startNanoTime) / 1000000000.0; 
	    										// calculate time
	    			drawSystem(t);				// draw system with Earth at this time
	    		}
	    	}.start();							// start timer
	    
		stagePrimary.show();					// show scene
	}
	
	public static void main(String[] args) {
		Application.launch(args);			// launch the GUI
	}

}

package uk.co.llblumire.cs2ja17.week8.planets;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SolarSystem extends Application {
	private int canvasSize = 512;
	private GraphicsContext gc;
	private ArrayList<Entity> entities;
	private boolean showSatelites;
	private long previousNanoTime = System.nanoTime();
	Entity sun;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.showSatelites = false;
		this.entities = new ArrayList<Entity>();
		this.sun = new StaticEntity(canvasSize / 2, canvasSize / 2, 30, 30,
				new Image(getClass().getResourceAsStream("sun.png")));
		Entity mercury = new Orbiter(57.9, 88, 30, 30, sun, new Image(getClass().getResourceAsStream("mercury.png")));
		Entity venus = new Orbiter(108.2, 224.7, 30, 30, sun, new Image(getClass().getResourceAsStream("venus.png")));
		Entity earth = new Orbiter(149.6, 365.2, 30, 30, sun, new Image(getClass().getResourceAsStream("earth.png")));
		Entity mars = new Orbiter(227.9, 687, 30, 30, sun, new Image(getClass().getResourceAsStream("mars.png")));

		Entity moon = new Orbiter(30, 27, 10, 10, earth, new Image(getClass().getResourceAsStream("satelite.png")));
		Entity phobos = new Orbiter(30, 0.34, 10, 10, mars, new Image(getClass().getResourceAsStream("satelite.png")));
		Entity deimos = new Orbiter(30, 1.25, 10, 10, mars, new Image(getClass().getResourceAsStream("satelite.png")));

		this.entities.add(sun);
		this.entities.add(mercury);
		this.entities.add(venus);
		this.entities.add(earth);
		this.entities.add(mars);
		this.entities.add(moon);
		this.entities.add(phobos);
		this.entities.add(deimos);

		primaryStage.setTitle("Solar System"); // set title
		
		Group root = new Group(); // create group of what is to be shown
		Canvas canvas = new Canvas(this.canvasSize, this.canvasSize);
		// create canvas to draw on
		root.getChildren().add(canvas); // add canvas to root
		this.gc = canvas.getGraphicsContext2D();
		// remember context of canvas drawn on
		Scene scene = new Scene(root); // put root in a scene

		primaryStage.setScene(scene); // apply the scene to the stage		
		
		previousNanoTime = System.nanoTime();
		// for animation, note start time

		new AnimationTimer() // create timer
		{
			public void handle(long currentNanoTime) {
				// define handle for what do at this time
				double deltaTime = (currentNanoTime - previousNanoTime) / 5000000.0;
				previousNanoTime = currentNanoTime;
				// calculate time
				updateAndRenderSystem(deltaTime); // draw system with Earth at this time
			}

		}.start(); // start timer

		primaryStage.show(); // show scene
	}

	private void updateAndRenderSystem(double dt) {
		gc.clearRect(0, 0, this.canvasSize, this.canvasSize);
		for (Entity entity : this.entities) {
			if (!this.showSatelites) {
				if (entity instanceof Orbiter) {
					if (((Orbiter) entity).getOrbitee() != sun) {
						continue;
					}
				}
			}
			entity.update(dt);
			gc.drawImage(entity.display, entity.x - (entity.width / 2), entity.y - (entity.height / 2), entity.width,
					entity.height);
		}
	}

	public static void main(String[] args) {
		Application.launch(args); // launch the GUI
	}

}

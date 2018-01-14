package uk.co.llblumire.coursework;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gui extends Application {
	/**
	 * The Environment being rendered.
	 */
	private Environment environment;

	/**
	 * A random number generator.
	 */
	private Random rng;

	@Override
	public void start(Stage primaryStage) throws Exception {
		environment = new Environment(500, 500);
		rng = new Random();

		primaryStage.setTitle("Robot Arena");
		BorderPane bp = new BorderPane();
		bp.setTop(this.menu(primaryStage));
		Canvas canvas = new Canvas(500, 500);
		canvas.getGraphicsContext2D();
		bp.setCenter(canvas);
		Scene scene = new Scene(bp, 800, 600);
		primaryStage.setScene(scene);
		animationTimer(canvas).start();
		primaryStage.show();
	}

	/**
	 * Creates the menu of the application.
	 * 
	 * @param stage
	 * @return
	 */
	MenuBar menu(Stage stage) {
		// gui is used in interface definitions later in the function.
		@SuppressWarnings("unused")
		Gui gui = this;

		MenuBar mb = new MenuBar();
		mb.setStyle("-fx-font-size: 14");

		Menu mFile = new Menu("File");
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});
		mFile.getItems().addAll(mExit);

		Menu mHelp = new Menu("Help");
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("About Robot Arena");
				alert.setHeaderText(null);
				alert.setContentText("Robot Arena by L. L. Blumire 2017");
				alert.showAndWait();
			}
		});
		mHelp.getItems().addAll(mAbout);

		Menu mEdit = new Menu("Edit");
		MenuItem mAddSimpleRobot = new MenuItem("Add Simple Robot");
		mAddSimpleRobot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int key = -1;
				do {
					gui.environment.removeEntity(key);
					key = gui.environment.addEntity(new SimpleRobot(gui.rng.nextDouble() * gui.environment.getWidth(),
							gui.rng.nextDouble() * gui.environment.getHeight(),
							(gui.rng.nextDouble()
									* (Math.min(gui.environment.getWidth(), gui.environment.getHeight()) * 0.05))
									+ (Math.min(gui.environment.getWidth(), gui.environment.getHeight()) * 0.025),
							(gui.rng.nextDouble()
									* (Math.min(gui.environment.getWidth(), gui.environment.getHeight()) * 0.025))
									+ (Math.min(gui.environment.getWidth(), gui.environment.getHeight()) * 0.025),
							gui.rng.nextDouble() * Math.PI * 2.0,
							(gui.rng.nextDouble() * Math.PI * 2.0 * 0.1 + (Math.PI * 2.0 * 0.1))
									* (rng.nextBoolean() ? 1 : -1)));
				} while (gui.environment.checkCollisionOf(key));
			}
		});
		mEdit.getItems().addAll(mAddSimpleRobot);

		mb.getMenus().addAll(mFile, mEdit, mHelp);
		return mb;
	}

	/**
	 * Creates an AnimationTimer that Animates the canvas. Triggering updates and
	 * renders.
	 * 
	 * @return The canvas AnimationTime.
	 */
	AnimationTimer animationTimer(Canvas canvas) {
		Gui gui = this;
		return new AnimationTimer() {
			long lastTime = System.nanoTime();

			@Override
			public void handle(long now) {
				long dt = now - lastTime;

				gui.render(canvas);
				environment.update((double) dt / 100000000.0);

				this.lastTime = System.nanoTime();
			}
		};
	}

	/**
	 * Renders the GUI
	 */
	void render(Canvas canvas) {
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		graphicsContext.setFill(Color.WHITE);
		graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		environment.render(graphicsContext);
	}

	/**
	 * Initialises the program.
	 */
	public static void main(String[] args) {
		Gui.launch(args);
	}

}

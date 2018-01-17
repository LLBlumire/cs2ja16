package uk.co.llblumire.coursework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Gui extends Application {

	private final int DEFAULT_WIDTH = 800;
	private final int DEFAULT_HEIGHT = 600;
	private final int DEFAULT_ENV_WIDTH = 500;
	private final int DEFAULT_ENV_HEIGHT = 500;

	/**
	 * The Environment being rendered.
	 */
	private Environment environment;

	/**
	 * A random number generator.
	 */
	private Random rng;

	/**
	 * Simulation Speed, a factor that is applied to delta time.
	 */
	private double simulationSpeed;

	/**
	 * The simulationSpeed of the game after resuming.
	 */
	private double resumeSpeed;

	/**
	 * The text body of the information panel.
	 */
	private Text info;

	@Override
	public void start(Stage primaryStage) throws Exception {
		environment = new Environment(DEFAULT_ENV_WIDTH, DEFAULT_ENV_HEIGHT);
		rng = new Random();
		simulationSpeed = 1.0;
		info = new Text();

		primaryStage.setTitle("Robot Arena");
		BorderPane bp = new BorderPane();
		bp.setTop(this.menu(primaryStage));
		Canvas canvas = new Canvas(DEFAULT_ENV_WIDTH, DEFAULT_ENV_HEIGHT);
		canvas.getGraphicsContext2D();
		bp.setCenter(canvas);
		ScrollPane infoScroll = new ScrollPane(this.info);
		infoScroll.setPrefWidth(DEFAULT_ENV_WIDTH * 0.3125);
		bp.setRight(infoScroll);

		Scene scene = new Scene(bp, DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
	private MenuBar menu(Stage stage) {
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
		MenuItem mPauseResume = new MenuItem("Pause/Resume");
		mPauseResume.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (gui.simulationSpeed > 0.0) {
					gui.resumeSpeed = gui.simulationSpeed;
					gui.simulationSpeed = 0.0;
				} else {
					gui.simulationSpeed = gui.resumeSpeed;
				}
			}
		});
		MenuItem mClear = new MenuItem("Clear");
		mClear.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				gui.environment = new Environment(DEFAULT_ENV_WIDTH, DEFAULT_ENV_HEIGHT);
			}
		});
		MenuItem mSave = new MenuItem("Save");
		mSave.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Java Robot Map files (*.jarm)",
						"*.jarm");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showSaveDialog(null);

				if (file != null) {
					try {
						FileOutputStream fileOut = new FileOutputStream(file);
						ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
						objOut.writeObject(gui.environment);
						objOut.close();
						fileOut.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		});
		MenuItem mOpen = new MenuItem("Open");
		mOpen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Java Robot Map files (*.jarm)",
						"*.jarm");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showOpenDialog(null);

				if (file != null) {
					try {
						FileInputStream fileIn = new FileInputStream(file);
						ObjectInputStream objIn = new ObjectInputStream(fileIn);
						gui.environment = (Environment)objIn.readObject();
						objIn.close();
						fileIn.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					} catch (ClassNotFoundException cnfe) {
						cnfe.printStackTrace();
					}
				}
			}
		});
		mFile.getItems().addAll(mSave, mOpen, mPauseResume, mClear, mExit);

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
					key = gui.environment.addEntity(gui.randomSimpleRobot());
				} while (gui.environment.checkCollisionOf(key));
			}
		});
		MenuItem mAddWhiskerRobot = new MenuItem("Add Whisker Robot");
		mAddWhiskerRobot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int key = -1;
				int lkey = -1;
				int rkey = -1;
				do {
					gui.environment.removeEntity(key);
					gui.environment.removeEntity(lkey);
					gui.environment.removeEntity(rkey);
					lkey = gui.environment.addEntity(new Whisker());
					rkey = gui.environment.addEntity(new Whisker());
					key = gui.environment.addEntity(gui.randomWhiskerRobot(lkey, rkey));
				} while (gui.environment.checkCollisionOf(key));
			}
		});
		MenuItem mAddWall = new MenuItem("Add Wall");
		mAddWall.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int key = -1;
				do {
					gui.environment.removeEntity(key);
					key = gui.environment.addEntity(gui.randomWall());
				} while (gui.environment.checkCollisionOf(key));
			}
		});
		MenuItem mAddStaticBlock = new MenuItem("Add Static Block");
		mAddStaticBlock.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int key = -1;
				do {
					gui.environment.removeEntity(key);
					key = gui.environment.addEntity(gui.randomStaticBlock());
				} while (gui.environment.checkCollisionOf(key));
			}
		});
		MenuItem mAddLightEmmiter = new MenuItem("Add Light Emmiter");
		mAddLightEmmiter.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				gui.environment.addEntity(gui.randomLightEmmiter());
			}
		});
		MenuItem mAddLightSenserRobot = new MenuItem("Add Light Sensing Robot");
		mAddLightSenserRobot.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				int key = -1;
				do {
					gui.environment.removeEntity(key);
					key = gui.environment.addEntity(gui.randomLightSenserRobot());
				} while (gui.environment.checkCollisionOf(key));
			}
		});
		mEdit.getItems().addAll(mAddSimpleRobot, mAddWhiskerRobot, mAddWall, mAddStaticBlock, mAddLightEmmiter,
				mAddLightSenserRobot);
		mb.getMenus().addAll(mFile, mEdit, mHelp);
		return mb;
	}

	/**
	 * Generates a new SimpleRobot.
	 */
	private Entity randomSimpleRobot() {
		double x = this.rng.nextDouble() * this.environment.getWidth();
		double y = this.rng.nextDouble() * this.environment.getHeight();
		double speed = (this.rng.nextDouble()
				* (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.05))
				+ (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025);
		double rad = (this.rng.nextDouble()
				* (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025))
				+ (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025);
		double angle = this.rng.nextDouble() * Math.PI * 2.0;
		double turnSpeed = (this.rng.nextDouble() * Math.PI * 2.0 * 0.1 + (Math.PI * 2.0 * 0.1))
				* (rng.nextBoolean() ? 1 : -1);
		return new SimpleRobot(x, y, speed, rad, angle, turnSpeed);
	}

	/**
	 * Generates a new WhiskerRobot.
	 */
	private Entity randomWhiskerRobot(Integer... keys) {
		double x = this.rng.nextDouble() * this.environment.getWidth();
		double y = this.rng.nextDouble() * this.environment.getHeight();
		double speed = (this.rng.nextDouble()
				* (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.05))
				+ (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025);
		double rad = (this.rng.nextDouble()
				* (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025))
				+ (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025);
		double angle = this.rng.nextDouble() * Math.PI * 2.0;
		double turnSpeed = (this.rng.nextDouble() * Math.PI * 2.0 * 0.1 + (Math.PI * 2.0 * 0.1))
				* (rng.nextBoolean() ? 1 : -1);
		double whiskerLength = rad * 2.0;
		double whiskerInteriorAngle = Math.PI / 4;
		double initalWhiskerAngle = -Math.PI / 8;
		return new WhiskerRobot(x, y, speed, rad, angle, turnSpeed, whiskerLength, whiskerInteriorAngle,
				initalWhiskerAngle, keys);
	}

	/**
	 * Generates a new Wall.
	 */
	private Entity randomWall() {
		double x = this.rng.nextDouble() * this.environment.getWidth();
		double y = this.rng.nextDouble() * this.environment.getHeight();
		double dx = this.rng.nextDouble() * this.environment.getWidth() - x;
		double dy = this.rng.nextDouble() * this.environment.getHeight() - y;
		double thickness = Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.01;
		return new Wall(x, y, dx, dy, thickness);
	}

	/**
	 * Generates a new StaticBlock.
	 */
	private Entity randomStaticBlock() {
		double x1 = this.rng.nextDouble() * this.environment.getWidth();
		double y1 = this.rng.nextDouble() * this.environment.getHeight();
		double x2 = this.rng.nextDouble() * this.environment.getWidth();
		double y2 = this.rng.nextDouble() * this.environment.getHeight();
		return new StaticBlock(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	}

	/**
	 * Generates a random LightEmmiter.
	 */
	public Entity randomLightEmmiter() {
		double rad = 0.5 * this.rng.nextDouble() * Math.min(this.environment.getWidth(), this.environment.getHeight());
		double x = this.rng.nextDouble() * this.environment.getWidth();
		double y = this.rng.nextDouble() * this.environment.getHeight();
		double brightness = this.rng.nextDouble();
		return new LightEmmiter(x, y, rad, brightness);
	}

	/**
	 * Generates a new LightSenserRobot.
	 */
	private Entity randomLightSenserRobot() {
		double x = this.rng.nextDouble() * this.environment.getWidth();
		double y = this.rng.nextDouble() * this.environment.getHeight();
		double speed = (this.rng.nextDouble()
				* (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.05))
				+ (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025);
		double rad = (this.rng.nextDouble()
				* (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025))
				+ (Math.min(this.environment.getWidth(), this.environment.getHeight()) * 0.025);
		double angle = this.rng.nextDouble() * Math.PI * 2.0;
		double turnSpeed = (this.rng.nextDouble() * Math.PI * 2.0 * 0.1 + (Math.PI * 2.0 * 0.1))
				* (rng.nextBoolean() ? 1 : -1);
		return new LightSensorRobot(x, y, speed, rad, angle, turnSpeed);
	}

	/**
	 * Creates an AnimationTimer that Animates the canvas. Triggering updates and
	 * renders.
	 * 
	 * @return The canvas AnimationTime.
	 */
	private AnimationTimer animationTimer(Canvas canvas) {
		Gui gui = this;
		return new AnimationTimer() {

			long lastTime = System.nanoTime();

			@Override
			public void handle(long now) {
				gui.info.setText(gui.environment.getInfo());
				long dt = now - lastTime;
				double msdt = (double) dt / 100000000.0;
				msdt *= simulationSpeed;

				gui.render(canvas);
				environment.update(msdt);

				this.lastTime = System.nanoTime();
			}
		};
	}

	/**
	 * Renders the GUI
	 */
	private void render(Canvas canvas) {
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

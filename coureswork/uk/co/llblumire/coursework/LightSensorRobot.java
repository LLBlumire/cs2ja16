package uk.co.llblumire.coursework;

import javafx.scene.paint.Color;

/**
 * A robot that moves as a simple robot until it detects a decrease in light
 * levels, at which point it begins to turn to return to the light.
 * 
 * @author L. L. Blumire
 *
 */
public final class LightSensorRobot extends SimpleRobot {

	/**
	 * Serialisation ID.
	 */
	private static final long serialVersionUID = 3214993770042563611L;

	/**
	 * The last light level seen by the Robot.
	 */
	private double lastLight;

	/**
	 * How much the robot is turning by to look for the light.
	 */
	private double turnConstant;

	/**
	 * Construct a LightSenserRobot from it's fields.
	 */
	public LightSensorRobot(double x, double y, double speed, double rad, double angle, double turnSpeed) {
		super(x, y, speed, rad, angle, turnSpeed);
		this.lastLight = 0.0;
		this.turnConstant = 0.0;
		this.color = new RGBA(Color.BLUE);
	}

	@Override
	public String info() {
		StringBuffer buffer = new StringBuffer(super.info().replaceAll("SimpleRobot", "LightSenserRobot"));
		buffer.append(String.format("\n\tc=%.2f\n\tl=%.2f", this.turnConstant, this.lastLight));
		return buffer.toString();
	}

	@Override
	public Updater updater() {
		LightSensorRobot self = this;
		Updater superUpdater = super.updater();
		return new Updater() {
			private static final long serialVersionUID = 2046591705732672105L;

			@Override
			public void update(double dt, Environment environment, int id) {
				superUpdater.update(dt, environment, id);
				Double light = environment.forEachEntity((eid) -> (entity) -> (state) -> {
					double lightLevel = state;
					if (entity instanceof LightEmmiter) {
						LightEmmiter lightEmmiter = (LightEmmiter) entity;
						if (lightEmmiter.collider().collidesWith(self.collider()).collides()) {
							lightLevel += 1 / Math.pow(lightEmmiter.getBrightness(), 2);
						}
					}
					return lightLevel;
				}, 0.0);

				if (self.lastLight < light) {
					self.turnConstant = 0.0;
				} else if (self.lastLight > light) {
					self.turnConstant = self.turnSpeed * 0.5;
				}

				self.lastLight = light;

				self.angle += self.turnConstant * dt;
			}
		};

	}

}

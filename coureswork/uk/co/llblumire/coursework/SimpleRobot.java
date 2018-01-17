package uk.co.llblumire.coursework;

import javafx.scene.paint.Color;

/**
 * A simple robot that moves in straight lines around the Arena, if it detects
 * it has collided with something, it will rotate until and try to move until it
 * no longer collides with anything.
 * 
 * @author L. L. Blumire
 *
 */
public class SimpleRobot implements Entity {
	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = -8951189071453093040L;

	/**
	 * The x coordinate of the centre of the robot.
	 */
	protected double x;

	/**
	 * The y coordinate of the centre of the robot.
	 */
	protected double y;

	/**
	 * The speed of the robot.
	 */
	protected double speed;

	/**
	 * The radius of the robot.
	 */
	protected double rad;

	/**
	 * The angle the robot is facing, in radians.
	 */
	protected double angle;

	/**
	 * The speed at which the robot rotates about it's centre.
	 */
	protected double turnSpeed;
	
	/**
	 * The color of the robot.
	 */
	protected RGBA color;

	/**
	 * Constructs a SimpleRobot from it's fields.
	 */
	public SimpleRobot(double x, double y, double speed, double rad, double angle, double turnSpeed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.rad = rad;
		this.angle = angle;
		this.turnSpeed = turnSpeed;
		this.color = new RGBA(Color.RED);
	}

	@Override
	public Collider collider() {
		return new Collider.BoxCollider(this.x - this.rad, this.y - this.rad, this.rad * 2, this.rad * 2);
	}

	@Override
	public Renderer renderer() {
		return new CompositionalRenderer(new CircleRenderer(this.x - this.rad, this.y - this.rad, this.rad, this.color.toColor()),
				new WheelRenderer(this.x, this.y, this.rad, this.angle));
	}

	@Override
	public Updater updater() {
		SimpleRobot self = this;
		return new Updater() {
			private static final long serialVersionUID = -5295428573149262062L;

			@Override
			public void update(double dt, Environment e, int id) {
				self.x += Math.cos(self.angle) * self.speed * dt;
				self.y += Math.sin(self.angle) * self.speed * dt;
				if (e.checkCollisionOf(id)) {
					self.x -= Math.cos(self.angle) * self.speed * dt;
					self.y -= Math.sin(self.angle) * self.speed * dt;
					self.angle += self.turnSpeed * dt;
				}
			}
		};
	}
	
	@Override
	public String info() {
		return String.format("SimpleRobot\n\to=(%.0f, %.0f)\n\tr=%.2f\n\ta=%.2f\n\ts=%.2f\n\tt=%.2f", this.x, this.y, this.rad, this.angle, this.speed, this.turnSpeed);
	}

}

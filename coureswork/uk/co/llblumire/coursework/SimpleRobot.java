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
	 * Constructs a SimpleRobot from it's fields.
	 */
	public SimpleRobot(double x, double y, double speed, double rad, double angle, double turnSpeed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.rad = rad;
		this.angle = angle;
		this.turnSpeed = turnSpeed;
	}

	@Override
	public Collider collider() {
		return new Collider.BoxCollider(this.x - this.rad, this.y - this.rad, this.rad * 2, this.rad * 2);
	}

	@Override
	public Renderer renderer() {
		return new CompositionalRenderer(new CircleRenderer(this.x - this.rad, this.y - this.rad, this.rad, Color.RED),
				new WheelRenderer(this.x, this.y, this.rad, this.angle));
	}

	@Override
	public Updater updater() {
		SimpleRobot self = this;
		return new Updater() {
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

}

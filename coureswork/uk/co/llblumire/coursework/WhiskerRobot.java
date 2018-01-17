package uk.co.llblumire.coursework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.scene.paint.Color;

/**
 * A robot with whiskers, if the whiskers feel a collision they will cause the robot to turn away.
 * 
 * @author L. L. Blumire
 *
 */
public final class WhiskerRobot extends SimpleRobot {

	/**
	 * Serialisation ID
	 */
	private static final long serialVersionUID = 3686031327205336480L;
	
	/**
	 * The length of each whisker.
	 */
	private double whiskerLength;
	
	/**
	 * The IDs of the connected whiskers.
	 */
	private ArrayList<Integer> whiskerIds;
	
	/**
	 * The angle between each whisker.
	 */
	private double whiskerInteriorAngle;
	
	/**
	 * The angle offset of the first whisker.
	 */
	private double initialWhiskerAngle;
	
	/**
	 * The direction the robot should turn, -1 or 1.
	 */
	private double turnMode;
	
	/**
	 * Construct a WhiskerRobot from it's fields.
	 */
	public WhiskerRobot(double x, double y, double speed, double rad, double angle, double turnSpeed, double whiskerLength, double whiskerInteriorAngle, double initialWhiskerAngle, Integer... whiskerIds) {
		super(x, y, speed, rad, angle, turnSpeed);
		this.whiskerLength = whiskerLength;
		this.whiskerIds = new ArrayList<Integer>(Arrays.asList(whiskerIds));
		this.whiskerInteriorAngle = whiskerInteriorAngle;
		this.initialWhiskerAngle = initialWhiskerAngle;
		super.color = new RGBA(Color.GREEN);
		turnMode = Math.signum(this.turnSpeed);
	}

	/**
	 * Synchronises the whiskers position to the WhiskerRobot.
	 * 
	 * @param onEach (whiskerId) -> (whiskerAngle) -> (whisker) -> { }
	 */
	private void syncWhiskersOnEach(Environment environment, Function<Integer, Function<Double, Consumer<Whisker>>> onEach) {
		double whiskerAngle = this.initialWhiskerAngle;
		for (int whiskerId : this.whiskerIds) {
			Entity whiskerEntity = environment.getEntity(whiskerId);
			if (whiskerEntity instanceof Whisker) {
				Whisker whisker = (Whisker)whiskerEntity;
				whisker.x = this.x + (this.whiskerLength * Math.cos(this.angle + whiskerAngle));
				whisker.y = this.y + (this.whiskerLength * Math.sin(this.angle + whiskerAngle));
				whisker.dx = this.whiskerLength * Math.cos(this.angle + whiskerAngle);
				whisker.dy = this.whiskerLength * Math.sin(this.angle + whiskerAngle);
				whisker.thickness = this.rad * 0.05;
				onEach.apply(whiskerId).apply(whiskerAngle).accept(whisker);
			}
			whiskerAngle += this.whiskerInteriorAngle;
		}
			
	}
	
	@Override
	public Updater updater() {
		WhiskerRobot self = this;
		
		return new Updater() {
			private static final long serialVersionUID = 7021464126471165374L;

			@Override
			public void update(double dt, Environment environment, int id) {
				
				self.x += Math.cos(self.angle) * self.speed * dt;
				self.y += Math.sin(self.angle) * self.speed * dt;
				if (environment.checkCollisionOf(id)) {
					self.x -= Math.cos(self.angle) * self.speed * dt;
					self.y -= Math.sin(self.angle) * self.speed * dt;
					self.angle += self.turnMode * Math.abs(self.turnSpeed * dt);
					self.syncWhiskersOnEach(environment, (_wid) -> (_wa) -> (_w) -> {});
					return;
				}

				self.syncWhiskersOnEach(environment, (whiskerId) -> (whiskerAngle) -> (whisker) -> {
					if (environment.checkCollisionOf(whiskerId)) {
						self.x -= Math.cos(self.angle) * self.speed * dt;
						self.y -= Math.sin(self.angle) * self.speed * dt;
						if (self.angle + whiskerAngle > self.angle) {
							self.turnMode = -1.0;
							self.angle += self.turnMode * Math.abs(self.turnSpeed * dt);
						} else {
							self.turnMode = 1.0;
							self.angle += self.turnMode * Math.abs(self.turnSpeed * dt);
						}
						self.syncWhiskersOnEach(environment, (_wid) -> (_wa) -> (_w) -> {});;
					}
				});
			}
		};
	}
	
	@Override
	public String info() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("WhiskerRObot\n\to=(%.0f, %.0f)\n\tr=%.2f\n\ta=%.2f\n\ts=%.2f\n\tt=%.2f\n\tl=%.2f\n\tw=[", this.x, this.y, this.rad, this.angle, this.speed, this.turnSpeed, this.whiskerLength));
		this.whiskerIds.forEach((id) -> {
			sb.append(String.format("%d,", id));
		});
		sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		sb.append(String.format("\n\tm=%.0f", this.turnMode));
		return sb.toString(); 
	}


}

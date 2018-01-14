package uk.co.llblumire.coursework;

/**
 * Interface implemented by classes that facilitate being updated with respect
 * to an entity in an Environment, and the passage of time.
 * 
 * @author L. L. Blumire
 *
 */
public interface Updater {
	/**
	 * Updates the class.
	 * 
	 * @param dt
	 *            The time since the previous update.
	 * @param environment
	 *            The environment the class is updated with respect too.
	 * @param id
	 *            The ID of the Entity the class is updated with respect too.
	 */
	public abstract void update(double dt, Environment environment, int id);
}

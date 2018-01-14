package uk.co.llblumire.coursework;

/**
 * Interface implemented by Entities.
 *  
 * Entities are defined as any object that can be rendered, collided with, and
 * updated.
 * 
 * @author L. L. Blumire
 *
 */
public interface Entity {
	/**
	 * Gets the collision model of the object.
	 * @return
	 */
	public abstract Collider collider();

	/**
	 * Gets the rendering model of the object.
	 * @return
	 */
	public abstract Renderer renderer();
	
	/**
	 * Gets the updating model of the object.
	 * @return
	 */
	public abstract Updater updater();
}

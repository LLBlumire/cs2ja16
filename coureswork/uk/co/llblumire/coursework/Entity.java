package uk.co.llblumire.coursework;

import java.io.Serializable;

/**
 * Interface implemented by Entities.
 * 
 * Entities are defined as any object that can be rendered, collided with, and
 * updated.
 * 
 * @author L. L. Blumire
 *
 */
public interface Entity extends Serializable {
	/**
	 * Gets the collision model of the object.
	 * 
	 * @return The collision model of the Entity.
	 */
	public abstract Collider collider();

	/**
	 * Gets the rendering model of the object.
	 * 
	 * @return The rendering model of the Entity.
	 */
	public abstract Renderer renderer();

	/**
	 * Gets the updating model of the object.
	 * 
	 * @return A method of updating the Entity with respect to DeltaTime, the
	 *         Environment, and The ID of the Entity being updated.
	 */
	public abstract Updater updater();

	/**
	 * Gets the information of the entity.
	 * 
	 * @return Information about the Entity.
	 */
	public abstract String info();
}

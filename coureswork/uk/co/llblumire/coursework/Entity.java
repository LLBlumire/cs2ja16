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
	 */
	public abstract Collider collider();

	/**
	 * Gets the rendering model of the object.
	 */
	public abstract Renderer renderer();
	
	/**
	 * Gets the updating model of the object.
	 */
	public abstract Updater updater();
	
	/**
	 * Gets the information of the entity.
	 */
	public abstract String info();
}

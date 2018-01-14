package uk.co.llblumire.coursework;

import java.util.ArrayList;
import java.util.TreeMap;

import javafx.scene.canvas.GraphicsContext;

/**
 * An environment in which Entities can be kept.
 * 
 * @author L. L. Blumire
 */
public class Environment {
	/**
	 * The entities kept within the environment.
	 */
	private TreeMap<Integer, Entity> entities;

	/**
	 * The keys that have been released from usage.
	 */
	private ArrayList<Integer> freeKeys;

	/**
	 * The next key to use if there are no free keys available.
	 */
	private int nextKey;

	/**
	 * The width of the environment.
	 */
	private double width;

	/**
	 * The height of the environment.
	 */
	private double height;

	/**
	 * Constructs the environment.
	 */
	public Environment(double width, double height) {
		this.entities = new TreeMap<Integer, Entity>();
		this.freeKeys = new ArrayList<Integer>();
		this.nextKey = 0;
		this.width = width;
		this.height = height;

		this.addEntity(new LineColliderEntity(0, this.width, 0, 0));
		this.addEntity(new LineColliderEntity(this.width, 0, 0, this.height));
		this.addEntity(new LineColliderEntity(this.width, -this.width, this.height, 0));
		this.addEntity(new LineColliderEntity(0, 0, this.height, -this.height));
	}

	/**
	 * The accessed key must be used, or will be invalidated.
	 * 
	 * @return the next key to access.
	 */
	private int nextKey() {
		if (this.freeKeys.size() > 0) {
			return this.freeKeys.remove(0);
		} else {
			return this.nextKey++;
		}
	}

	/**
	 * Marks a key as free, removing the entity if one is associated. A key of -1 is
	 * guaranteed to do nothing.
	 *
	 * @return
	 */
	public void removeEntity(int key) {
		if (key == -1) {
			return;
		}
		if (this.entities.containsKey(key)) {
			this.entities.remove(key);
		}
		this.freeKeys.add(key);
	}

	/**
	 * Adds an entity to the Environment.
	 * 
	 * @return key The key of the added Entity.
	 */
	public int addEntity(Entity entity) {
		int key = this.nextKey();
		this.entities.put(key, entity);
		return key;
	}

	/**
	 * Checks the collision of a specific Entity.
	 * 
	 * @param id
	 *            The ID of the Entity to check the collision of.
	 * @return true if collision occurs, else false.
	 */
	public boolean checkCollisionOf(int id) {
		Entity self = this.entities.get(id);
		if (self == null) {
			return false;
		}
		for (Entity other : this.entities.values()) {
			if (other != self) {
				if (other.collider().collidesWith(self.collider())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Renders the Environment.
	 * @param gc The graphics context to draw on.
	 */
	public void render(GraphicsContext gc) {
		this.entities.forEach((id, entity) -> {
			entity.renderer().render(gc);
		});
	}

	/**
	 * Updates the environment.
	 * @param dt The time since the previous update.
	 */
	public void update(double dt) {
		this.entities.forEach((id, entity) -> {
			entity.updater().update(dt, this, id);
		});
	}

	/**
	 * Getter for the width.
	 * @return The width.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Getter for the height.
	 * @return The height.
	 */
	public double getHeight() {
		return height;
	}

}

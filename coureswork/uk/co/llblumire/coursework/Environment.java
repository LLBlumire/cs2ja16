package uk.co.llblumire.coursework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.function.Function;

import javafx.scene.canvas.GraphicsContext;

/**
 * An environment in which Entities can be kept.
 * 
 * @author L. L. Blumire
 */
public class Environment implements Serializable {
	/**
	 * Serial ID for Serialisation
	 */
	private static final long serialVersionUID = 8589107584206747115L;

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
	 * Returns the entity of a specific ID.
	 * 
	 * Returns null if there is no entity for a given key.
	 */
	public Entity getEntity(int key) {
		return this.entities.get(key);
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
				if (self.collider().collidesWith(other.collider()).collides()) {
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
		ArrayList<Renderer> renderers = new ArrayList<Renderer>();
		this.entities.forEach((id, entity) -> {
			renderers.add(entity.renderer());
		});
		renderers.sort((a, b) -> a.valency() < b.valency()? -1 : 1);
		renderers.forEach((renderer) -> renderer.render(gc));
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

	public String getInfo() {
		StringBuffer buf = new StringBuffer();
		this.entities.forEach((id, entity) -> {
			buf.append(String.format("%d :: %s\n", id, entity.info()));
		});
		return buf.toString();
	}

	public void clear() {
		this.entities.clear();
	}
	
	public <T> T forEachEntity(Function<Integer, Function<Entity, Function<T, T>>> forEach, T initial) {
		T state = initial;
		for (Integer id : this.entities.keySet()) {
			Entity entity = this.entities.get(id);
			state = forEach.apply(id).apply(entity).apply(initial);
		}
		return state;
	}

}

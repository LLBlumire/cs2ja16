package uk.co.llblumire.coursework;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Collider provides a number of different types of collider to check if
 * arbitrary bounds are colliding.
 * 
 * This class behaves as a closed union type and should be treated as externally
 * private and therefore should NOT be subclassed.
 * 
 * It does this by assigning each Collider a valency. Every Collider handles
 * collision with all other colliders of lower valency.
 * 
 * @author L. L. Blumire
 *
 */
public abstract class Collider {

	/**
	 * The universal collider is special, and is assumed to be a special case. It
	 * should probably never be used.
	 * 
	 * @param c
	 *            The collider to check collision with.
	 * @return The result of a collision check.
	 */
	public abstract CollisionStatus collidesWith(Collider c);

	/**
	 * Whether the collider should be treated as hard (collider) or soft (senser)
	 */
	public boolean hardCollision = true;

	/**
	 * Construct the universal collider, always collides.
	 */
	private Collider() {
	}

	/**
	 * The valency of a collider determines who handles computation of intersection.
	 * The highest valency collider always handles collision.
	 * 
	 * Any unknown subclass will have Integer.MAX_VALUE valency and therefore will
	 * be expected to handle collision with all other possible types of collider.
	 * 
	 * @param c
	 *            the Collider to check the valency of.
	 * @return the valency of the collider.
	 */
	final public static double valency(Collider c) {
		if (c instanceof NoCollider) {
			return 0;
		} else if (c instanceof BoxCollider) {
			return 1;
		} else if (c instanceof LineCollider) {
			return 2;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	/**
	 * The status of a collision
	 * 
	 * @author L. L. Blumire
	 *
	 */
	public static enum CollisionStatus {
		/**
		 * Both colliders are hard and collide.
		 */
		Hard,
		/**
		 * The calling collider is hard and not the target and collide.
		 */
		SoftSelf,
		/**
		 * The target collider is hard and not the caller and collide.
		 */
		SoftOther,
		/**
		 * Both the target and the calling collider are not hard and collide.
		 */
		Soft,
		/**
		 * No collision occurred.
		 */
		None;

		/**
		 * Swaps SoftSelf and SoftOther, useful when a valency inverted call is made.
		 * 
		 * @return A CollisionStatus with SoftSelf and SoftOther swapped.
		 */
		public CollisionStatus selfToOther() {
			if (this == CollisionStatus.SoftSelf) {
				return CollisionStatus.SoftOther;
			} else if (this == CollisionStatus.SoftOther) {
				return CollisionStatus.SoftSelf;
			} else {
				return this;
			}
		}

		/**
		 * Checks for hard collision of a target.
		 * 
		 * @return true if collision occured.
		 */
		public boolean collides() {
			switch (this) {
			case Hard:
			case SoftSelf:
				return true;
			case SoftOther:
			case Soft:
			case None:
				return false;
			}
			return false;
		}

		/**
		 * Assuming collision, turns two boolean hardness properties into a collision
		 * status.
		 * 
		 * @param selfHard
		 *            The hardness property of the caller.
		 * @param otherHard
		 *            The hardness property of the collision target.
		 * @return A CollisionStatus assuming collision with respect to the hardness of
		 *         parameters.
		 */
		public static CollisionStatus fromHardness(boolean selfHard, boolean otherHard) {
			if (selfHard && otherHard) {
				return CollisionStatus.Hard;
			} else if (selfHard && !otherHard) {
				return CollisionStatus.SoftOther;
			} else if (!selfHard && otherHard) {
				return CollisionStatus.SoftSelf;
			} else if (!selfHard && !otherHard) {
				return CollisionStatus.Soft;
			} else {
				// Should not run
				return CollisionStatus.None;
			}
		}
	}

	/**
	 * Collides with nothing.
	 * 
	 * @author L. L. Blumire
	 *
	 */
	public final static class NoCollider extends Collider {
		/**
		 * Trivial Constructor for NoCollider
		 */
		public NoCollider() {
		}

		@Override
		public CollisionStatus collidesWith(Collider c) {
			return CollisionStatus.None;
		}
	}

	/**
	 * Collides with anything that intersects it's bounding box.
	 * 
	 * @author L. L. Blumire
	 *
	 */
	public final static class BoxCollider extends Collider {
		/**
		 * The width of the box.
		 */
		private double width;

		/**
		 * The height of the box.
		 */
		private double height;

		/**
		 * The x coordinate of the left of the box.
		 */
		private double x;

		/**
		 * The y coordinate of the top of the box.
		 */
		private double y;

		/**
		 * A new box collider from it's x, y, width, and height.
		 * 
		 * @param x
		 *            The x coordinate of the left of the box.
		 * @param y
		 *            The y coordinate of the top of the box.
		 * @param width
		 *            The width of the box.
		 * @param height
		 *            The height of the box.
		 */
		public BoxCollider(double x, double y, double width, double height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		@Override
		public CollisionStatus collidesWith(Collider c) {
			if (valency(c) > valency(this)) {
				return c.collidesWith(this).selfToOther();
			} else {
				if (c instanceof NoCollider) {
					return CollisionStatus.None;
				} else if (c instanceof BoxCollider) {
					BoxCollider other = (BoxCollider) c;
					if (new Rectangle2D.Double(this.x, this.y, this.width, this.height).intersects(other.x, other.y,
							other.width, other.height)) {
						return CollisionStatus.fromHardness(this.hardCollision, other.hardCollision);
					}
					;
				} else {
					return CollisionStatus.None;
				}
			}
			return CollisionStatus.None;
		}
	}

	/**
	 * Collides with anything that intersects the line segment.
	 * 
	 * @author L. L. Blumire
	 *
	 */
	public final static class LineCollider extends Collider {
		/**
		 * The starting x coordinate of the line.
		 */
		private double x;
		/**
		 * The change in the x position of the line from start to finish.
		 */
		private double dx;
		/**
		 * The starting y coordinate of the line.
		 */
		private double y;
		/**
		 * The change in the y position of the line from start to finish.
		 */
		private double dy;

		/**
		 * A line collider from it's x, y, and bounding box.
		 * 
		 * @param x
		 *            The starting x coordinate of the line.
		 * @param dx
		 *            The change in the x position of the line from start to finish.
		 * @param y
		 *            The starting y coordinate of the line.
		 * @param dy
		 *            The change in the y position of the line from start to finish.
		 */
		public LineCollider(double x, double dx, double y, double dy) {
			this.x = x;
			this.dx = dx;
			this.y = y;
			this.dy = dy;
		}

		@Override
		public CollisionStatus collidesWith(Collider c) {
			if (valency(c) > valency(this)) {
				return c.collidesWith(this).selfToOther();
			} else {
				if (c instanceof NoCollider) {
					return CollisionStatus.None;
				} else if (c instanceof BoxCollider) {
					BoxCollider other = (BoxCollider) c;
					if (new Line2D.Double(this.x, this.y, this.x + this.dx, this.y + this.dy)
							.intersects(new Rectangle2D.Double(other.x, other.y, other.width, other.height))) {
						return CollisionStatus.fromHardness(this.hardCollision, other.hardCollision);
					}
				} else if (c instanceof LineCollider) {
					LineCollider other = (LineCollider) c;
					if (new Line2D.Double(this.x, this.y, this.x + this.dx, this.y + this.dy).intersectsLine(other.x,
							other.y, other.x + other.dx, other.y + other.dy)) {
						return CollisionStatus.fromHardness(this.hardCollision, other.hardCollision);
					}
				} else {
					return CollisionStatus.None;
				}
			}
			return CollisionStatus.None;
		}
	}

}
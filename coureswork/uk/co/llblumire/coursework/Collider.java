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
	 * Construct the universal collider, always collides.
	 */
	private Collider() {
	}

	/**
	 * The universal collider is special, and is assumed to be a special case. It
	 * should probably never be used.
	 * 
	 * @return true.
	 */
	public abstract boolean collidesWith(Collider c);

	/**
	 * The valency of a collider determines who handles computation of intersection.
	 * The highest valency collider always handles collision.
	 * 
	 * Any unknown subclass will have Integer.MAX_VALUE valency and therefore will
	 * be expected to handle collision with all other possible types of collider.
	 * 
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
	 * Collides with nothing.
	 * 
	 * @author L. L. Blumire
	 *
	 */
	public final static class NoCollider extends Collider {
		@Override
		public boolean collidesWith(Collider c) {
			return false;
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
		 */
		public BoxCollider(double x, double y, double width, double height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		@Override
		public boolean collidesWith(Collider c) {
			if (valency(c) > valency(this)) {
				return c.collidesWith(this);
			} else {
				if (c instanceof NoCollider) {
					return false;
				} else if (c instanceof BoxCollider) {
					BoxCollider other = (BoxCollider) c;
					return new Rectangle2D.Double(this.x, this.y, this.width, this.height).intersects(other.x, other.y,
							other.width, other.height);
				} else {
					return false;
				}
			}
		}
	}

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
		 */
		public LineCollider(double x, double dx, double y, double dy) {
			this.x = x;
			this.dx = dx;
			this.y = y;
			this.dy = dy;
		}

		@Override
		public boolean collidesWith(Collider c) {
			if (valency(c) > valency(this)) {
				return c.collidesWith(this);
			} else {
				if (c instanceof NoCollider) {
					return false;
				} else if (c instanceof BoxCollider) {
					BoxCollider other = (BoxCollider) c;
					return new Line2D.Double(this.x, this.y, this.x + this.dx, this.y + this.dy)
							.intersects(new Rectangle2D.Double(other.x, other.y, other.width, other.height));
				} else if (c instanceof LineCollider) {
					LineCollider other = (LineCollider) c;
					return new Line2D.Double(this.x, this.y, this.x + this.dx, this.y + this.dy).intersectsLine(other.x,
							other.y, other.x + other.dx, other.y + other.dy);
				} else {
					return false;
				}
			}
		}
	}
}
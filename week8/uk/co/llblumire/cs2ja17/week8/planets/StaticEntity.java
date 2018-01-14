package uk.co.llblumire.cs2ja17.week8.planets;

import javafx.scene.image.Image;

public class StaticEntity extends Entity {

	public StaticEntity(float x, float y, float width, float height, Image display) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.display = display;
	}

	@Override
	public void update(double dt) {}
	
}

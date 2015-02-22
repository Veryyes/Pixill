package pl;

import java.awt.Graphics;

public abstract class Actor extends Entity {
	int hp;
	float speed;
	int width;
	int height;
	public Actor(float x, float y) {
		super(x, y);
	}
}

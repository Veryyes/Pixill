package pl;

public abstract class Enemy extends Actor {
	boolean active;
	public Enemy(float x, float y) {
		super(x, y);
		active = false;
	}

}

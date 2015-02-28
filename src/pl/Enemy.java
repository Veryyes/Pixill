package pl;

public abstract class Enemy extends Actor {
	boolean active;
	char color;
	public Enemy(float x, float y) {
		super(x, y);
		active = false;
	}
	public void directMove(Entity other){
		double magnitude = distance(other);
		x+=(((other.x-x)/magnitude)*speed);
		y+=(((other.y-y)/magnitude)*speed);
	}
}

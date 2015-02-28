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
	public int isHit() {
		for(int i = 0; i < Global.projectiles.size(); i++) {
			if(isColliding(Global.projectiles.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
}

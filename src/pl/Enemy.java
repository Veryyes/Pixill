package pl;

public abstract class Enemy extends Actor {
	boolean active;
	String color;
	int r, g, b;
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
			if(!(Global.projectiles.get(i).outOfBounds())) {
				if(isColliding(Global.projectiles.get(i))) {
					return i;
				}
			}
		}
		return -1;
	}
	public void updateColor(){
		switch(r){
		case 0:
			switch(g){
			case 0:
				switch(b){
				case 0:
					color="Blk";
					break;
				case 255:
					color="B";
					break;
				}
				break;
			case 255:
				switch(b){
				case 0:
					color="G";
					break;
				case 255:
					color="C";
					break;
				}
				break;
			}
			break;
		case 255:
			switch(g){
			case 0:
				switch(b){
				case 0:
					color="R";
					break;
				case 255:
					color="M";
					break;
				}
				break;
			case 255:
				switch(b){
				case 0:
					color="Y";
					break;
				case 255:
					color="W";
					break;
				}
				break;
			}
			break;
		}
	}
	
}

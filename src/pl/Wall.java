package pl;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Wall extends Entity {
	Rectangle2D.Double hitBox;
	public Wall(float x, float y) {
		super(x, y);
		hitBox=new Rectangle2D.Double(this.x, this.y, 128, 128);
	}
	public Wall(float x, float y, int width, int height){
		super(x, y);
		hitBox=new Rectangle2D.Double(this.x, this.y, width, height);
	}
	@Override
	public void update() {
		
	}

	@Override
	public void paint(Graphics g) {
	
	}

}

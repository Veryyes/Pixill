package pl;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Path extends Entity {	//Represents walkable space
	/*
	 *  Not sure if will use
	 *  only if i cant come up with a better solution with walls, other wise just ignore this
	 */
	Rectangle2D.Double hitBox;
	public Path(float x, float y) {
		super(x, y);
		hitBox = new Rectangle2D.Double(this.x, this.y, 128,128);
	}
	public Path(float x, float y, int width, int height){
		super(x, y);
		hitBox=new Rectangle2D.Double(this.x, this.y, width, height);
	}
	public void update() {

	}
	public void paint(Graphics g) {

	}

}

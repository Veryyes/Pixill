package pl;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Actor extends Entity {
	int hp;
	float speed;
	Rectangle2D.Double hitBox;
	public Actor(float x, float y) {
		super(x, y);
	}
	
	public boolean isDead(){
		return(hp<0);
	}
	/*For 2 rects
	 * 	true
	 * 		intersecting
	 * 		inside each other
	 * false
	 * 		share a side
	 * 	
	 * For 1 rec one line
	 * 	true
	 * 		intersecting
	 * 		line sits on a side of rectangle
	 * 		inside of rectangle
	 * 
	 * For 2 lines
	 * 	true
	 * 		intersecting
	 * 		on top of each other
	 */
}

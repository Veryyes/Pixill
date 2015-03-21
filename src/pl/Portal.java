package pl;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Portal extends Entity {

	public Portal(float x, float y) {
		super(x, y);
		hitBox = new Rectangle2D.Double(x,y,128,128);
		//setImage("");
	}

	@Override
	public void update() {
		hitBox = new Rectangle2D.Double(x,y,128,128);
	}

	@Override
	public void paint(Graphics g) {
		

	}

}

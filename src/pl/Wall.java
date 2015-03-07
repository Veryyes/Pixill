package pl;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Wall extends Entity {
	public Wall(float x, float y) {
		super(x, y);
		hitBox=new Rectangle2D.Double(x, y, 128, 128);
		//setImage("res/Test Tile.png");
	}
	@Override
	public void update() {
		hitBox=new Rectangle2D.Double(x, y, 128, 128);
	}

	@Override
	public void paint(Graphics g) {
		//g.drawImage(img,(int)x,(int)y,null);
	}

}

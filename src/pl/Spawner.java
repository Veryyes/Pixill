package pl;

import java.awt.Graphics;

public class Spawner extends Enemy {

	public Spawner(float x, float y, char c) {
		super(x, y);
		this.color = c;
		setImage("res/enemies/"+c+"Spawn/Spawn.png");
	}

	@Override
	public void update() {

	}

	@Override
	public void paint(Graphics g) {
		
		g.drawImage(img,(int)x,(int)y,null);

	}

}

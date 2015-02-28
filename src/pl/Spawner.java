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
	int proj;
	public void changeColor() {
		proj = isHit();
		if(proj != -1) {
			color = Global.projectiles.get(proj).color;
			Global.projectiles.get(proj).remove = true;
		}
	}

}

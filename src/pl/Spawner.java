package pl;

import java.awt.Graphics;

public class Spawner extends Enemy {

	public Spawner(float x, float y, char c) {
		super(x, y);
		this.color = c;
		setImage("res/enemies/"+c+"Spawn/Spawner.png");
	}
	int timer;
	@Override
	public void update() {
		timer +=1;
		if((distance(Global.player) < 300 && (timer/Global.FPS) >= 15) || ((timer/Global.FPS) >= 30)) {
			Global.enemies.add(new Crawler(x,y,color));
			timer = 0;
		}

	}

	@Override
	public void paint(Graphics g) {
		changeColor();
		setImage("res/enemies/"+color+"Spawn/Spawner.png");
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

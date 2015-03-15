package pl;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spawner extends Enemy {
	BufferedImage[] animation = new BufferedImage[3];
	int timer;
	float animate = 0;
	float animationSpeed=1;
	int cc;
	public Spawner(float x, float y, int r, int g, int b){
		super(x,y);
		this.r=r;
		this.g=g;
		this.b=b;
		updateColor();
		for(int i=0;i<animation.length;i++){
			try {
				animation[i] = ImageIO.read(new File("res/enemies/"+color+"Spawn/"+color+"Spawner"+i+".png"));
			} catch (IOException e) {
				System.out.println("[WARNING] Missing Image - res/enemies/"+color+"Spawn/"+color+"Spawner"+i+".png");
			}
		}
	}
	
	@Override
	public void update() {
		timer +=1;
		if((distance(Global.player) < 1200 && (timer/Global.FPS) >= 15) || ((timer/Global.FPS) >= 30)) {
			Global.enemies.add(new Crawler(x,y,r,g,b));
			timer = 0;
		}

	}
	

	@Override
	public void paint (Graphics g) {
		//changeColor();
		animate+=animationSpeed;
		animate=animate%animation.length;
		g.drawImage(animation[(int)animate],(int)x,(int)y,null);

	}
	//int proj;
	/*
	public void changeColor() {
		proj = isHit();
		if(proj != -1) {
			color = Global.projectiles.get(proj).color;
			Global.projectiles.get(proj).remove = true;
		}
	}*/
}

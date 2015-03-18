package pl;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spawner extends Enemy {
	int timer;
	float animate = 0;
	float animationSpeed=1;
	public Spawner(float x, float y, int r, int g, int b){
		super(x,y);
		this.r=r;
		this.g=g;
		this.b=b;
		animation = new BufferedImage[3];
		updateColor();
		for(int i=0;i<animation.length;i++){
			try {
				animation[i] = ImageIO.read(new File("res/enemies/"+color+"Spawn/"+color+"Spawner"+i+".png"));
			} catch (IOException e) {
				System.out.println("[WARNING] Missing Image - res/enemies/"+color+"Spawn/"+color+"Spawner"+i+".png");
			}
		}
		hitBox = new Rectangle2D.Double(x,y,128,128);
	}
	
	public void update() {
		if(r+g+b==0)
			dead=true;
		timer +=1;
		if((Global.enemies.size()<20)&&(distance(Global.player) < 1200 && (timer/Global.FPS) >= 15) || ((timer/Global.FPS) >= 30)) {
			Global.enemies.add(new Crawler(x,y,r,g,b));
			timer = 0;
		}
		hitBox = new Rectangle2D.Double(x,y,128,128);
		updateProjectileCollisions();
	}
	
	public void paint (Graphics g) {
		//changeColor();
		animate+=animationSpeed;
		animate=animate%animation.length;
		g.drawImage(animation[(int)animate],(int)x,(int)y,null);

	}
}

package pl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spawner extends Enemy {

	public Spawner(float x, float y, char c) throws IOException {
		super(x, y);
		this.color = c;
		setImage("res/enemies/"+c+"Spawn/"+color+"Spawner0.png");

		animation[0][0] = ImageIO.read(new File("res/enemies/RSpawn/Rspawner0.png"));
		animation[0][0] = ImageIO.read(new File("res/enemies/RSpawn/Rspawner1.png"));
		animation[0][0] = ImageIO.read(new File("res/enemies/RSpawn/Rspawner2.png"));
		
		animation[1][0] = ImageIO.read(new File("res/enemies/GSpawn/Gspawner0.png"));
		animation[1][1] = ImageIO.read(new File("res/enemies/GSpawn/Gspawner1.png"));
		animation[1][2] = ImageIO.read(new File("res/enemies/GSpawn/Gspawner2.png"));
		
		animation[2][0] = ImageIO.read(new File("res/enemies/BSpawn/Bspawner0.png"));
		animation[2][1] = ImageIO.read(new File("res/enemies/BSpawn/Bspawner1.png"));
		animation[2][2] = ImageIO.read(new File("res/enemies/BSpawn/Bspawner2.png"));
	}
	
	BufferedImage[][] animation = new BufferedImage[3][3];
	int timer;
	@Override
	public void update() {
		timer +=1;
		if((distance(Global.player) < 1200 && (timer/Global.FPS) >= 15) || ((timer/Global.FPS) >= 30)) {
			Global.enemies.add(new Crawler(x,y,color));
			timer = 0;
		}

	}
	
	float animate = 0;
	int cc;

	@Override
	public void paint (Graphics g) {
		changeColor();
		animate++;
		if(animate > 2) animate = 0;
		switch(color) {
		case 'R':
			cc= 0;
			break;
		case 'G':
			cc=1;
			break;
		case 'B':
			cc=2;
			break;
		}
		setImage(animation[cc][(int)animate]);
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

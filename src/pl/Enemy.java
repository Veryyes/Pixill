package pl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Enemy extends Actor {
	boolean dead;
	String color;
	BufferedImage[] animation;
	int r, g, b;
	public Enemy(float x, float y) {
		super(x, y);
		dead = false;
	}
	public void directMove(Entity other){
		double magnitude = distance(other);
		x+=(((other.x-x)/magnitude)*speed);
		y+=(((other.y-y)/magnitude)*speed);
	}
	public void updateProjectileCollisions() {
		for(int i = 0; i < Global.projectiles.size(); i++) {
			if(!(Global.projectiles.get(i).outOfBounds())) {
				if(isColliding(Global.projectiles.get(i))) {
					Global.projectiles.get(i).remove=true;
					if(Global.projectiles.get(i).color=='R'){
						r=0;
						updateColor();
						updateImages();
					}else if(Global.projectiles.get(i).color=='G'){
						g=0;
						updateColor();
						updateImages();
					}else {// Blue
						b=0;
						updateColor();
						updateImages();
					}
					if(r+g+b==0)
						dead=true;
				}
			}
		}
	}
	public void updateImages(){
		if(this instanceof Crawler){
			for(int i=0;i<animation.length;i++){
				try {
					animation[i] = ImageIO.read(new File("res/enemies/"+color+"Mob/"+color+"MobWalking/"+color+"MobWalk"+i+".png"));
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("[WARNING] Missing Image - res/enemies/"+color+"Mob/"+color+"MobWalking/"+color+"MobWalk"+i+".png");
				}
			}
		}
		else if(this instanceof Spawner){
			for(int i=0;i<animation.length;i++){
				try {
					animation[i] = ImageIO.read(new File("res/enemies/"+color+"Spawn/"+color+"Spawner"+i+".png"));
				} catch (IOException e) {
					System.out.println("[WARNING] Missing Image - res/enemies/"+color+"Spawn/"+color+"Spawner"+i+".png");
				}
			}
		}
	}
	public void updateColor(){
		switch(r){
		case 0:
			switch(g){
			case 0:
				switch(b){
				case 0:
					color="Blk";
					break;
				case 255:
					color="B";
					break;
				}
				break;
			case 255:
				switch(b){
				case 0:
					color="G";
					break;
				case 255:
					color="C";
					break;
				}
				break;
			}
			break;
		case 255:
			switch(g){
			case 0:
				switch(b){
				case 0:
					color="R";
					break;
				case 255:
					color="M";
					break;
				}
				break;
			case 255:
				switch(b){
				case 0:
					color="Y";
					break;
				case 255:
					color="W";
					break;
				}
				break;
			}
			break;
		}
	}
	
}

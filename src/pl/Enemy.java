package pl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public abstract class Enemy extends Actor {
	boolean dead;
	boolean remove;
	boolean aggro;
	String color;
	BufferedImage[] animation;
	int r, g, b;

	public Enemy(float x, float y) {
		super(x, y);
		dead = false;
		remove=false;
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
					if(Global.projectiles.get(i).remove)
						return;
					Global.projectiles.get(i).remove=true;
					
					if(Global.projectiles.get(i).color=='R'){
						if(r>0){
							if(this instanceof Crawler){
								this.x+=(float) Global.projectiles.get(i).xVel*2.5;
								this.y+=(float) Global.projectiles.get(i).yVel*2.5;
								aggro=true;
							}
							if(b+g!=0)
								playPhaseShiftSound();
						}
						r=0;
						updateColor();
						updateImages();
					}else if(Global.projectiles.get(i).color=='G'){
						if(g>0){
							if(this instanceof Crawler){
								this.x+=(float) Global.projectiles.get(i).xVel*2.5;
								this.y+=(float) Global.projectiles.get(i).yVel*2.5;
								aggro=true;
							}
							if(r+b!=0)
								playPhaseShiftSound();
						}
						g=0;
						updateColor();
						updateImages();
					}else {// Blue
						if(b>0){
							if(this instanceof Crawler){
								this.x+=(float) Global.projectiles.get(i).xVel*2.5;
								this.y+=(float) Global.projectiles.get(i).yVel*2.5;
								aggro=true;
							}
							if(r+g!=0)
								playPhaseShiftSound();
						}
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
					speed=(float)(Player.speed*(1.1)*1.2);
					break;
				case 255:
					color="B";
					speed=(float)(Player.speed*(1.1)*1.2);
					//playPhaseShiftSound();
					break;
				}
				break;
			case 255:
				switch(b){
				case 0:
					color="G";
					speed=(float)(Player.speed*(1.1)*1.2);
					//playPhaseShiftSound();
					break;
				case 255:
					color="C";
					speed=(float)(Player.speed*(2.2/3)*1.2);
					//playPhaseShiftSound();
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
					speed=(float)(Player.speed*(1.1)*1.2);
					//playPhaseShiftSound();
					break;
				case 255:
					color="M";
					speed=(float)(Player.speed*(2.2/3)*1.2);
					//playPhaseShiftSound();
					break;
				}
				break;
			case 255:
				switch(b){
				case 0:
					color="Y";
					speed=(float)(Player.speed*(2.2/3)*1.2);
					//playPhaseShiftSound();
					break;
				case 255:
					color="W";
					speed=(float) (Player.speed*(1.1/3)*1.2);
					//playPhaseShiftSound();
					break;
				}
				break;
			}
			break;
		}
	}
	
	private void playPhaseShiftSound(){
		try {
			Camera.playSound("res/sound/Mob/PhaseShift.wav");
		} catch (UnsupportedAudioFileException | IOException
				| LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}

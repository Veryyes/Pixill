package pl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Crawler extends Enemy {
		float animationCount;
		float animationSpeed;
		Line2D.Double topLine,botLine,leftLine,rightLine;
		double xVel;
		double yVel;
		boolean canAttack;
		float attackTimer;
	public Crawler(float x, float y, int r, int g, int b ){
		super(x,y);
		this.r=r;
		this.g=g;
		this.b=b;
		updateColor();
		setImage("res/enemies/"+color+"Mob/"+color+"MobTD.png");
		animation = new BufferedImage[12];
		for(int i=0;i<animation.length;i++){
			try {
				animation[i] = ImageIO.read(new File("res/enemies/"+color+"Mob/"+color+"MobWalking/"+color+"MobWalk"+i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[WARNING] Missing Image - res/enemies/"+color+"Mob/"+color+"MobWalking/"+color+"MobWalk"+i+".png");
			}
		}
		hitBox = new Rectangle2D.Double(x,y,98,128);
		theta=(float) (Math.random()*2*Math.PI);
		animationCount=0;
		animationSpeed=.35f;
		speed = (float) (Player.speed*1.1);
		canAttack=true;
		attackTimer=0; //150 max
	}

	public void directMove(Entity other){
		double magnitude = distance(other);
		xVel=(((other.x-x)/magnitude)*speed);
		yVel=(((other.y-y)/magnitude)*speed);
		for(int i=0;i<Global.enemies.size();i++){
			if((hitBox.intersects(Global.enemies.get(i).hitBox))&&(Global.enemies.get(i)!=this)){
				if(x>Global.enemies.get(i).x&&xVel<0)
					xVel=0;
				else if(x<Global.enemies.get(i).x&&xVel>0)
					xVel=0;
				if(y>Global.enemies.get(i).y&&yVel<0)
					yVel=0;
				else if(y<Global.enemies.get(i).y&&yVel>0)
					yVel=0;
			}
		}
		x+=xVel;
		y+=yVel;
	}
	@Override
	public void update() {
		if(!canAttack){
			attackTimer++;
			if(attackTimer>=75){
				attackTimer=0;
				canAttack=true;
			}
		}
		if(distance(Global.player)<=100){//Attacking Player
			if(canAttack){
				Global.player.hp--;
				if(Global.player.hp<1){
					Global.player.hp=5;
					Global.loading=true;
					Global.level=-1;
					Canvas.map=new Map(Global.level);
					Global.loading=false;
				}
					
				Global.camera.setCurrentEffect(new Color(r,g,b,60));
				try {
					Camera.playSound("res/sound/Mob/MobAttack.wav");
					Camera.playSound("res/sound/Player/playerhurt"+(int)(3*Math.random())+".wav");
				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e) {
					e.printStackTrace();
				}
				canAttack=false;
			}
		}
		else if(distance(Global.player)<=200)//Following Player
			directMove(Global.player); //run to player
		else if(distance(Global.player)<=300)//Entered Aggro Range
			directMove(Global.player); //run to player
		hitBox = new Rectangle2D.Double(x,y,98,128);
		updateProjectileCollisions();
	}
	public void paint(Graphics g) {//Oh god this needs some cleaning up
		if(distance(Global.player)<=100){//Attacking Player
			setImage("res/enemies/"+color+"Mob/"+color+"MobTDA.png");
			if(InputListener.directionKeyPressed())
				animate();
			else
				animationCount=0;
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
			//Legs
			bodyRotation = AffineTransform.getRotateInstance(theta,animation[(int)animationCount].getWidth()/2,animation[(int)animationCount].getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(animation[(int)animationCount], null),(int)x,(int)y,null);
			//Torso
			bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(img, null),(int)x-img.getWidth()/2+animation[(int)animationCount].getWidth()/2,(int)y-img.getHeight()/2+animation[(int)animationCount].getHeight()/2,null);
		}
		else if(distance(Global.player)<=200){//Following Player
			setImage("res/enemies/"+color+"Mob/"+color+"MobTDA.png");
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
			//Legs
			bodyRotation = AffineTransform.getRotateInstance(theta,animation[(int)animationCount].getWidth()/2,animation[(int)animationCount].getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(animation[(int)animationCount], null),(int)x,(int)y,null);
			//Torso
			bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(img, null),(int)x-img.getWidth()/2+animation[(int)animationCount].getWidth()/2,(int)y-img.getHeight()/2+animation[(int)animationCount].getHeight()/2,null);
			animate();
		}
		else if(distance(Global.player)<=300){//Entered Aggro Range
			setImage("res/enemies/"+color+"Mob/"+color+"MobTD.png");
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
			//Legs
			bodyRotation = AffineTransform.getRotateInstance(theta,animation[(int)animationCount].getWidth()/2,animation[(int)animationCount].getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(animation[(int)animationCount], null),(int)x,(int)y,null);
			//Torso
			bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(img, null),(int)x,(int)y,null);
			animate();
		}else{//Idle
			animationCount=0;
			setImage("res/enemies/"+color+"Mob/"+color+"MobTD.png");
			//Legs
			bodyRotation = AffineTransform.getRotateInstance(theta,animation[(int)animationCount].getWidth()/2,animation[(int)animationCount].getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(animation[(int)animationCount], null),(int)x,(int)y,null);
			//Torso
			bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(img, null),(int)x,(int)y,null);
		}
	
	}
	private void animate(){
		animationCount+=animationSpeed;
		animationCount=animationCount%animation.length;
	}
}

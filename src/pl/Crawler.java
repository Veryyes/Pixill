package pl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Crawler extends Enemy {
		float animationCount;
		float animationSpeed;
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
		theta=(float) (Math.random()*2*Math.PI);
		animationCount=0;
		animationSpeed=.35f;
		speed = (float) (Player.speed*1);
	}
	/*
	public Crawler(float x, float y, char c) {
		super(x, y);
		setImage("res/enemies/"+c+"Mob/"+c+"MobTD.png");
		animation = new BufferedImage[12];
		for(int i=0;i<animation.length;i++){
			try {
				animation[i] = ImageIO.read(new File("res/enemies/"+c+"Mob/"+c+"MobWalking/"+c+"MobWalk"+i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[WARNING] Missing Image - res/enemies/"+c+"Mob/"+c+"MobWalking/"+c+"MobWalk"+i+".png");
			}
		}
		theta=(float) (Math.random()*2*Math.PI);
		animationCount=0;
		animationSpeed=.35f;
		color = c;
		speed = (float) (Player.speed*1.1);
	}*/
	@Override
	public void update() {
		//TODO Move crawler movement to here from paint method.
		if(distance(Global.player)<=100){//Attacking Player
			//Dont move
			}
		else if(distance(Global.player)<=200)//Following Player
			directMove(Global.player); //run to player
		else if(distance(Global.player)<=300)//Entered Aggro Range
			directMove(Global.player); //run to player
	}
	public void paint(Graphics g) {//Oh god this needs some cleaning up
		Global.camera.setCurrentEffect(new Color(0,0,0,0));
		if(distance(Global.player)<=150){//Attacking Player
			setImage("res/enemies/"+color+"Mob/"+color+"MobTDA.png");
			Global.player.hp--;
			if(InputListener.directionKeyPressed())
				animate();
			else
				animationCount=0;
			//TODO make this flash w/ hp drop //Global.camera.setCurrentEffect(new Color(255,0,0,20));
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

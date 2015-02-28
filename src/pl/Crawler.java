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
		BufferedImage[] legsImage;
		float animationCount;
		float animationSpeed;
	public Crawler(float x, float y, char c) {
		super(x, y);
		setImage("res/enemies/"+c+"Mob/"+c+"MobTD.png");
		legsImage = new BufferedImage[12];
		for(int i=0;i<legsImage.length;i++){
			try {
				legsImage[i] = ImageIO.read(new File("res/enemies/"+c+"Mob/"+c+"MobWalking/"+c+"MobWalk"+i+".png"));
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
	}
	@Override
	public void update() {
		
	}
	public void paint(Graphics g) {//Oh god this needs some cleaning up
		Global.camera.setCurrentEffect(new Color(0,0,0,0));
		if(distance(Global.player)<=100){
			setImage("res/enemies/"+color+"Mob/"+color+"MobTDA.png");
			Global.player.hp--;
			animationCount=0;
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
		}
		else if(distance(Global.player)<=200){
			setImage("res/enemies/"+color+"Mob/"+color+"MobTDA.png");
			directMove(Global.player); //run to player
			animate();
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
		}
		else if(distance(Global.player)<=300){
			active=true;
			setImage("res/enemies/"+color+"Mob/"+color+"MobTD.png");
			directMove(Global.player); //run to player
			animate();
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
		}
		else{
			active=false;
			animationCount=0;
			setImage("res/enemies/"+color+"Mob/"+color+"MobTD.png");
		}
		//Legs
		bodyRotation = AffineTransform.getRotateInstance(theta,legsImage[(int)animationCount].getWidth()/2,legsImage[(int)animationCount].getHeight()/2);
		bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(bodyRotationOp.filter(legsImage[(int)animationCount], null),(int)x,(int)y,null);
		//Torso
		bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
		bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(bodyRotationOp.filter(img, null),(int)x-img.getWidth()/2+legsImage[(int)animationCount].getWidth()/2,(int)y-img.getHeight()/2+legsImage[(int)animationCount].getHeight()/2,null);

		/*
		if(distance(Global.player)<=100){//Attacking Player
			setImage("res/enemies/"+color+"Mob/"+color+"MobTDA.png");
			Global.player.hp--;
			animationCount=0;
			//TODO make this flash w/ hp drop //Global.camera.setCurrentEffect(new Color(255,0,0,20));
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
			//Legs
			bodyRotation = AffineTransform.getRotateInstance(theta,legsImage[(int)animationCount].getWidth()/2,legsImage[(int)animationCount].getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(legsImage[(int)animationCount], null),(int)x,(int)y,null);
			//Torso
			bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(img, null),(int)x-img.getWidth()/2+legsImage[(int)animationCount].getWidth()/2,(int)y-img.getHeight()/2+legsImage[(int)animationCount].getHeight()/2,null);
		}
		else if(distance(Global.player)<=200){//Following Player
			setImage("res/enemies/"+color+"Mob/"+color+"MobTDA.png");
			directMove(Global.player); //run to player
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
			//Legs
			bodyRotation = AffineTransform.getRotateInstance(theta,legsImage[(int)animationCount].getWidth()/2,legsImage[(int)animationCount].getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(legsImage[(int)animationCount], null),(int)x,(int)y,null);
			//Torso
			bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(img, null),(int)x-img.getWidth()/2+legsImage[(int)animationCount].getWidth()/2,(int)y-img.getHeight()/2+legsImage[(int)animationCount].getHeight()/2,null);
			animate();
		}
		else if(distance(Global.player)<=300){//Entered Aggro Range
			active=true;
			setImage("res/enemies/"+color+"Mob/"+color+"MobTD.png");
			directMove(Global.player); //run to player
			theta = (float) Math.atan((Global.player.y-y)/(Global.player.x-x));
			if(Global.player.x<x)
				theta+=Math.PI;
			//Legs
			bodyRotation = AffineTransform.getRotateInstance(theta,legsImage[(int)animationCount].getWidth()/2,legsImage[(int)animationCount].getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(legsImage[(int)animationCount], null),(int)x,(int)y,null);
			//Torso
			bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(img, null),(int)x,(int)y,null);
			animate();
		}else{//Idle
			active=false;
			animationCount=0;
			setImage("res/enemies/"+color+"Mob/"+color+"MobTD.png");
			//Legs
			bodyRotation = AffineTransform.getRotateInstance(theta,legsImage[(int)animationCount].getWidth()/2,legsImage[(int)animationCount].getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(legsImage[(int)animationCount], null),(int)x,(int)y,null);
			//Torso
			bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(bodyRotationOp.filter(img, null),(int)x,(int)y,null);
		}
		*/
	
	}
	private void animate(){
		animationCount+=animationSpeed;
		animationCount=animationCount%legsImage.length;
	}
}

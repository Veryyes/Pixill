package pl;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Actor implements MouseListener{
	public static boolean canMoveUp;
	public static boolean canMoveDown;
	public static boolean canMoveLeft;
	public static boolean canMoveRight;
	public static float speed;
	public BufferedImage[] legsImage;
	public BufferedImage[] attackImage;
	public float animationCount;
	public float animationAttackCount;
	public float animationSpeed;
	private AffineTransform legRotation;
	private AffineTransformOp legRotationOp;
	private double legTheta;
	public char color;
	public boolean isAttacking;
	public Player() {
		super(0,0);
		setImage("res/player/PlayerTD.png");
		legsImage = new BufferedImage[12];
		for(int i=0;i<legsImage.length;i++){
			try {
				legsImage[i] = ImageIO.read(new File("res/player/walking/playerwalk"+i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[WARNING] Missing Image - res/Front With Walking/"+i+".png");
			}
		}
		attackImage = new BufferedImage[3];
		for(int i=0;i<attackImage.length;i++){
			try {
				attackImage[i] = ImageIO.read(new File("res/player/arm/PlayerTDA"+i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[WARNING] Missing Image - res/player/arm/PlayerTDA"+i+".png");
			}
		}
		legRotation = AffineTransform.getRotateInstance(0,legsImage[(int)animationCount].getWidth()/2,legsImage[(int)animationCount].getHeight()/2);
		legRotationOp = new AffineTransformOp(legRotation,AffineTransformOp.TYPE_BILINEAR);
		bodyRotation = AffineTransform.getRotateInstance(0,img.getWidth()/2,img.getHeight()/2);
		bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
		legTheta=0;
		theta = 0;
		setCenter(Global.frameWidth/2,Global.frameHeight/2);
		animationCount=0;
		animationAttackCount=0;
		animationSpeed=.35f;
		canMoveUp=true;
		canMoveDown=true;
		canMoveLeft=true;
		canMoveRight=true;
		speed=4;
		color = 'R';
		isAttacking=false;
	}
	@Override
	public void update() {
		if(InputListener.isPressed('1')){//28,29,30
			color='R';
		}
		else if(InputListener.isPressed('2')){
			color='G';
		}
		else if(InputListener.isPressed('3')){
			color='B';
		}
		if(InputListener.isPressed('E'))
			speed=8;
		else speed=4;
	}
	@Override
	public void paint(Graphics g) {
		if(InputListener.isPressed('W')&&canMoveUp){
			legTheta=0;
			if(InputListener.isPressed('A')&&canMoveLeft)
				legTheta=7*Math.PI/4;
			else if(InputListener.isPressed('D')&&canMoveRight)
				legTheta=Math.PI/4;
			animate();
		}
		else if(InputListener.isPressed('S')&&canMoveDown){
			legTheta=Math.PI;
			if(InputListener.isPressed('A')&&canMoveLeft)
				legTheta=5*Math.PI/4;
			else if(InputListener.isPressed('D')&&canMoveRight)
				legTheta=3*Math.PI/4;
			animate();
		}
		else if(InputListener.isPressed('A')&&canMoveLeft){
			legTheta=3*Math.PI/2;
			animate();
		}
		else if(InputListener.isPressed('D')&&canMoveRight){
			legTheta=Math.PI/2;
			animate();
		}
		else{
			animationCount=0;
		}
		legRotation = AffineTransform.getRotateInstance(legTheta,legsImage[(int)animationCount].getWidth()/2,legsImage[(int)animationCount].getHeight()/2);
		legRotationOp = new AffineTransformOp(legRotation,AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(legRotationOp.filter(legsImage[(int)animationCount],null), (int)x, (int)y,null);
		Point mouse = Global.frame.getMousePosition();
		if(mouse!=null){
		try{
			theta = (float) Math.atan((float)(mouse.y-getCenterY())/(float)(mouse.x-getCenterX()));
		}catch(Exception e){
			System.out.println(e);
		}
			if(mouse.x<getCenterX())
				theta+=Math.PI;
			if(!isAttacking){
				bodyRotation = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
				bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
				g.drawImage(bodyRotationOp.filter(img, null),(int)x,(int)y,null);
			}else{
				bodyRotation = AffineTransform.getRotateInstance(theta,attackImage[(int)animationAttackCount].getWidth()/2,attackImage[(int)animationAttackCount].getHeight()/2);
				bodyRotationOp = new AffineTransformOp(bodyRotation,AffineTransformOp.TYPE_BILINEAR);
				g.drawImage(bodyRotationOp.filter(attackImage[(int)animationAttackCount], null),
						(int)x-(attackImage[(int)animationAttackCount].getWidth()/2)+img.getWidth()/2,(int)y-attackImage[(int)animationAttackCount].getHeight()/2+img.getHeight()/2,null);
				animateAttack();
			}
		}
		else
			if(!isAttacking)
				g.drawImage(img,(int)x,(int)y,null);
			else{
				g.drawImage(attackImage[(int)animationAttackCount],(int)x-(attackImage[(int)animationAttackCount].getWidth()/2)+img.getWidth()/2,(int)y-attackImage[(int)animationAttackCount].getHeight()/2+img.getHeight()/2,null);
				animateAttack();
			}
		//TODO make him not be able to rotate in impossible directions;
			//Flip body if facing the opposite way, and make him walk backwards?
			//clamp the rotation?
	}
	private void animate(){
		animationCount+=animationSpeed;
		animationCount=animationCount%legsImage.length;
	}
	private void animateAttack(){
		animationAttackCount+=animationSpeed;
		if(animationAttackCount>=attackImage.length){
			isAttacking=false;
			animationAttackCount=0;
		}
	}
	private void clampValue(float value, float max, float min){
		if(value>max)
			value=max;
		else if(value<min)
			value=min;
	}
	public void mouseClicked(MouseEvent e) {	
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {

	}
	public void mousePressed(MouseEvent e) {
		if(!isAttacking){
			isAttacking=true;
			Point mouse = Global.frame.getMousePosition();
			Projectile bullet = new Projectile(this.getCenterX(),this.getCenterY(),mouse.x,mouse.y,15,"res/player/laser/laser"+color+".png");
			bullet.setCenter(this.getCenterX(), this.getCenterY());
			bullet.applyRotation(theta);
			bullet.color=this.color;
			Global.projectiles.add(bullet);
		}
	}

	public void mouseReleased(MouseEvent e) {
		
		
	}
	private float[] getRotatedBoundings(float theta){//I dont even...
		//http://stackoverflow.com/questions/622140/calculate-bounding-box-coordinates-from-a-rotated-rectangle-picture-inside
		float[] box = new float[4];
		float[] xCorners = new float[4];
		xCorners[0]=(float) (getCenterX()+(x-getCenterX())*Math.cos(theta)+(y-getCenterY())*Math.sin(theta));
		xCorners[1]=(float) (getCenterX()+(x+img.getWidth()-getCenterX())*Math.cos(theta)+(y-getCenterY())*Math.sin(theta));
		xCorners[2]=(float) (getCenterX()+(x-getCenterX())*Math.cos(theta)+(y+img.getHeight()-getCenterY())*Math.sin(theta));
		xCorners[3]=(float) (getCenterX()+(x+img.getWidth()-getCenterX())*Math.cos(theta)+(y+img.getHeight()-getCenterY())*Math.sin(theta));
		float[] yCorners = new float[4];
		yCorners[0]=(float) (getCenterY()-(x-getCenterX())*Math.sin(theta)+(y-getCenterY())*Math.cos(theta));
		yCorners[1]=(float) (getCenterY()-(x+img.getWidth()-getCenterX())*Math.sin(theta)+(y-getCenterY())*Math.cos(theta));
		yCorners[2]=(float) (getCenterY()-(x-getCenterX())*Math.sin(theta)+(y+img.getHeight()-getCenterY())*Math.cos(theta));
		yCorners[3]=(float) (getCenterY()-(x+img.getWidth()-getCenterX())*Math.sin(theta)+(y+img.getHeight()-getCenterY())*Math.cos(theta));
		float maxChecker = xCorners[0];
		float minChecker = xCorners[0];
		for(int i=0;i<xCorners.length;i++){
			if(maxChecker < xCorners[i])
				maxChecker = xCorners[i];
			if(minChecker > xCorners[i])
				minChecker = xCorners[i];
		}
		box[0]=minChecker;
		box[2]=maxChecker-minChecker;
		maxChecker = yCorners[0];
		minChecker = yCorners[0];
		for(int i=0;i<yCorners.length;i++){
			if(maxChecker < yCorners[i])
				maxChecker = yCorners[i];
			if(minChecker > yCorners[i])
				minChecker = yCorners[i];
		}
		box[1]=minChecker;
		box[3]=maxChecker-minChecker;
		return box;
	}
}

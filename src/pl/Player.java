package pl;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
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
	public float animationCount;
	public float animationSpeed;
	public Player() {
		super(0,0);
		setImage("res/arrow.png");
		legsImage = new BufferedImage[6];
		for(int i=0;i<legsImage.length;i++){
			try {
				legsImage[i] = ImageIO.read(new File("res/Front With Walking/"+i+".png"));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[WARNING] Missing Image - res/Front With Walking/"+i+".png");
			}
		}
		setCenter(Global.frameWidth/2,Global.frameHeight/2);
		animationCount=0;
		animationSpeed=.35f;
		canMoveUp=true;
		canMoveDown=true;
		canMoveLeft=true;
		canMoveRight=true;
		speed=4;
	}
	@Override
	public void update() {
		
	}
	@Override
	public void paint(Graphics g) {
		//TODO if use 2d array to fit animations for each direction?
		g.drawImage(legsImage[(int)animationCount], (int)x, (int)y,null);
		if(InputListener.isPressed('W')){
			animationCount+=animationSpeed;
			animationCount=animationCount%legsImage.length;
		}
		else
			animationCount=0;
		Point mouse = Global.frame.getMousePosition();
		if(mouse!=null){
			float theta = (float) Math.atan((float)(mouse.y-getCenterY())/(float)(mouse.x-getCenterX()));
			if(mouse.x<getCenterX())
				theta+=Math.PI;
			AffineTransform tx = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2);
			AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
			BufferedImage imgRotated = op.filter(img, null);
			g.drawImage(imgRotated,(int)x,(int)y,null);
			//http://stackoverflow.com/questions/622140/calculate-bounding-box-coordinates-from-a-rotated-rectangle-picture-inside
		}
		else
			g.drawImage(img,(int)x,(int)y,null);
		
	}
	public void mouseClicked(MouseEvent e) {
		Point mouse = Global.frame.getMousePosition();
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	private float[] getRotatedBoundings(float theta){//I dont even...
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

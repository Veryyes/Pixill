package pl;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Player extends Actor implements MouseListener{
	public static boolean canMoveUp;
	public static boolean canMoveDown;
	public static boolean canMoveLeft;
	public static boolean canMoveRight;
	public static float speed;
	public Player() {
		super(0,0);
		setImage("res/Test Tile.png");
		setCenter(Global.frameWidth/2,Global.frameHeight/2);
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
		Point mouse = Global.frame.getMousePosition();
		if(mouse!=null){
			double theta = Math.atan((float)(mouse.y-getCenterY())/(float)(mouse.x-getCenterX()));
			System.out.println(theta);
			AffineTransform tx = AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight());
			//AffineTransform tx = new AffineTransform();
			//tx.rotate(Math.atan((float)(mouse.y-getCenterY())/(float)(mouse.x-getCenterX())));
			AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
			g.drawImage(op.filter(img, null),(int)x,(int)y,null);
		}
		else
			g.drawImage(img,(int)x,(int)y,null);
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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

}

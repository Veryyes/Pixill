package pl;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;

public class Projectile extends Entity{
	double xVel;
	double yVel;
	int damage;
	Entity owner; //TODO When null, damages both player&enemies (world dmg)
	public char color;
	public boolean remove = false;
	public Projectile(int x, int y) {
		super(x, y);
	}
	//All these constructors could use some cleaning up;
	public Projectile(float x, float y, float x2, float y2, float speed, String filepath){
		//Creates a projectile at a start point and a target point with given speed;
		super(x,y);
		double magnitude = Math.sqrt(Math.pow(y2-y,2)+Math.pow(x2-x,2));
		xVel = (((x2-x)/magnitude)*speed);
		yVel = (((y2-y)/magnitude)*speed);
		setImage(filepath);
		owner=null;
		color='R';
		updateHitBox();
	}
	public Projectile(Entity owner, float x2, float y2, float speed, String filepath){
		//Creates a projectile at a start point and a target point with given speed;
		super(owner.x,owner.y);
		double magnitude = Math.sqrt(Math.pow(y2-y,2)+Math.pow(x2-x,2));
		xVel = (((x2-owner.x)/magnitude)*speed);
		yVel = (((y2-owner.y)/magnitude)*speed);
		setImage(filepath);
		this.owner=owner;
		color='R';
		updateHitBox();
	}
	public Projectile(float x, float y, float x2, float y2, float speed, Entity owner,String filepath){
		//Creates a projectile at a start point and a target point with given speed;
		super(x,y);
		double magnitude = Math.sqrt(Math.pow(y2-y,2)+Math.pow(x2-x,2));
		xVel = (((x2-x)/magnitude)*speed);
		yVel = (((y2-y)/magnitude)*speed);
		setImage(filepath);
		this.owner=owner;
		color='R';
		updateHitBox();
	}
	public Projectile(Entity owner, Entity target, float speed,String filepath){
		//Creates a projectile at a start point and a target point with given speed;
		super(owner.x,owner.y);
		double magnitude = Math.sqrt(Math.pow(target.y-owner.y,2)+Math.pow(target.x-owner.x,2));
		xVel = (((target.x-owner.x)/magnitude)*speed);
		yVel = (((target.y-owner.y)/magnitude)*speed);
		setImage(filepath);
		this.owner=owner;
		color='R';
		updateHitBox();
	}
	private void updateHitBox(){
		hitBox = new Rectangle2D.Double(x+12,y+12,48,48);
	}
	public void applyRotation(double theta){
		AffineTransformOp op = new AffineTransformOp(AffineTransform.getRotateInstance(theta,img.getWidth()/2,img.getHeight()/2),AffineTransformOp.TYPE_BILINEAR);
		img = op.filter(img, null);
	}
	public void update() {
		x+=xVel;
		y+=yVel;
		updateHitBox();
		for(int i=0;i<Global.walls.size();i++){
			if (hitBox.intersects(Global.walls.get(i).hitBox))
				remove=true;
		}
	}
	public void paint(Graphics g) {
		g.drawImage(img,(int)x,(int)y, null);
	}
}

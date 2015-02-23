package pl;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Projectile extends Entity{
	double xVel;
	double yVel;
	int damage;
	Entity owner; //When null, damages both player&enemies (world dmg)
	public Projectile(int x, int y) {
		super(x, y);
	}
	public Projectile(float x, float y, float x2, float y2, float speed){
		//Creates a projectile at a start point and a target point with given speed;
		super(x,y);
		double magnitude = Math.sqrt(Math.pow(y2-y,2)+Math.pow(x2-x,2));
		xVel = (((x2-x)/magnitude)*speed);
		yVel = (((y2-y)/magnitude)*speed);
		setImage("res/pellet.png");
		owner=null;
	}
	public Projectile(float x, float y, float x2, float y2, float speed, Entity owner){
		//Creates a projectile at a start point and a target point with given speed;
		super(x,y);
		double magnitude = Math.sqrt(Math.pow(y2-y,2)+Math.pow(x2-x,2));
		xVel = (((x2-x)/magnitude)*speed);
		yVel = (((y2-y)/magnitude)*speed);
		setImage("res/pellet.png");
		this.owner=owner;
	}
	public Projectile(Entity owner, Entity target, float speed){
		//Creates a projectile at a start point and a target point with given speed;
		super(owner.x,owner.y);
		double magnitude = Math.sqrt(Math.pow(target.y-owner.y,2)+Math.pow(target.x-owner.x,2));
		xVel = (((target.x-owner.x)/magnitude)*speed);
		yVel = (((target.y-owner.y)/magnitude)*speed);
		setImage("res/pellet.png");
		this.owner=owner;
	}
	public void update() {
		x+=xVel;
		y+=yVel;
	}
	public void paint(Graphics g) {
		g.drawImage(img,(int)x,(int)y, null);
	}
}

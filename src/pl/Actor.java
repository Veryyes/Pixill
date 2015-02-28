package pl;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;

public abstract class Actor extends Entity {
	int hp;
	float speed;
	float theta;
	protected AffineTransform bodyRotation;
	protected AffineTransformOp bodyRotationOp;
	
	public Actor(float x, float y) {
		super(x, y);
	}
	public boolean isDead(){
		return(hp<0);
	}
	
	
}

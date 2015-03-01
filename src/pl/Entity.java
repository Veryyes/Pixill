package pl;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Entity {
	float x,y;
	BufferedImage img;
	Rectangle2D.Double hitBox;
	public Entity(float x, float y){
		this.x=x;
		this.y=y;
	}
	abstract public void update();			//Non-graphical stuff goes here
	abstract public void paint(Graphics g);	//Anything to do with graphics, images, animation or any kind goes here
	public void setImage(String s) {	//sets the image of the entity
		try {
			img = ImageIO.read(new File(s));
		} catch (IOException e) {
			System.out.println("[WARNING] Could not find file \""+s+"\"");
		}
	}
	public void setImage(BufferedImage img){
		this.img = img;
	}
	public float distance(float x1, float y1, float x2, float y2){	//distance formula
		return (float)Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
	}
	public float distance(Entity e){	//distance between this entity and the specified one
		return (float)Math.sqrt(Math.pow(x-e.x,2)+Math.pow(y-e.y,2));
	}
	public boolean outOfBounds(){	//checks if entity is out of the world bounds
		return (x>Global.frameWidth||x<0||y<0||y>Global.frameHeight);
	}
	public float getCenterX(){
		try{
			return (x+(img.getWidth()/2));
		}
		catch(Exception e){
			System.out.println(e);
			return x;
		}
	}
	public float getCenterY(){
		try{
			return (y+(img.getHeight()/2));
		}
		catch(Exception e){
			System.out.println(e);
			return y;
		}
	}
	public float getCenterX(BufferedImage image){
		try{
			return (x+(image.getWidth()/2));
		}
		catch(Exception e){
			System.out.println(e);
			return x;
		}
	}
	public float getCenterY(BufferedImage image){
		try{
			return (y+(image.getHeight()/2));
		}
		catch(Exception e){
			System.out.println(e);
			return y;
		}
	}
	public void setCenter(float f, float g){
		try{
			x=f-(img.getWidth()/2);
			y=g-(img.getHeight()/2);
		}
		catch(Exception e){
			System.out.println(e+"\n[WARNING] Probably missing an image");
		}
	}
	/*For 2 rects
	 * 	true
	 * 		intersecting
	 * 		inside each other
	 * false
	 * 		share a side
	 */
	public boolean isColliding (Entity other) {
		try {
			return hitBox.intersects(other.hitBox);
		}
		catch (Exception e) {
			return false;
		}
	} 
	 /* For 1 rec one line
	 * 	true
	 * 		intersecting
	 * 		line sits on a side of rectangle
	 * 		inside of rectangle
	 *
	* For 2 lines
	 * 	true
	 * 		intersecting
	 * 		on top of each other
	 */
}

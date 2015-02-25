package pl;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Entity {
	float x,y;
	BufferedImage img;
	public Entity(float x, float y){
		this.x=x;
		this.y=y;
	}
	abstract public void update();
	abstract public void paint(Graphics g);
	public void setImage(String s) {	//sets the image of the entity
		try {
			img = ImageIO.read(new File(s));
		} catch (IOException e) {
			System.out.println("[WARNING] Could not find file \""+s+"\"");
		}
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
	public void setCenter(int x1, int y1){
		try{
			x=x1-(img.getWidth()/2);
			y=y1-(img.getHeight()/2);
		}
		catch(Exception e){
			System.out.println(e+"\n[WARNING] Probably missing an image");
		}
	}
}

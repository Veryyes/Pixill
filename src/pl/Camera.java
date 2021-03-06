package pl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Camera {
	Color currentEffect;
	double xVel;
	double yVel;
	int fade;
	BufferedImage[] healthBar;
	public Camera() {
		currentEffect=new Color(0,0,0,0);
		xVel=0;
		yVel=0;
		fade=60;
	}
	public void update(){
		xVel=0;
		yVel=0;
		if(InputListener.isPressed('W')&&Player.canMoveUp&&InputListener.isPressed('A')&&Player.canMoveLeft){
			xVel=Player.speed*Math.sin(Math.PI/4);
			yVel=Player.speed*Math.sin(Math.PI/4);
		}
		else if(InputListener.isPressed('W')&&Player.canMoveUp&&InputListener.isPressed('D')&&Player.canMoveRight){
			yVel=Player.speed*Math.sin(Math.PI/4);
			xVel=-Player.speed*Math.sin(Math.PI/4);
		}
		else if(InputListener.isPressed('S')&&Player.canMoveDown&&InputListener.isPressed('A')&&Player.canMoveLeft){
			yVel=-Player.speed*Math.sin(Math.PI/4);
			xVel=Player.speed*Math.sin(Math.PI/4);
		}
		else if(InputListener.isPressed('S')&&Player.canMoveDown&&InputListener.isPressed('D')&&Player.canMoveRight){
			yVel=-Player.speed*Math.sin(Math.PI/4);
			xVel=-Player.speed*Math.sin(Math.PI/4);
		}
		//Cardinals
		else if(InputListener.isPressed('W')&&Player.canMoveUp){
			yVel=Player.speed;
		}
		else if(InputListener.isPressed('S')&&Player.canMoveDown){
			yVel=-Player.speed;
		}
		else if(InputListener.isPressed('A')&&Player.canMoveLeft){
			xVel=Player.speed;
		}
		else if(InputListener.isPressed('D')&&Player.canMoveRight){
			xVel=-Player.speed;
		}
		for(int i=0;i<Global.walls.size();i++){
			if(!Global.walls.get(i).outOfBounds()){
				if(new Line2D.Double(Player.topLine.x1-xVel,Player.topLine.y1-yVel,Player.topLine.x2+-xVel,Player.topLine.y2-yVel).intersects(Global.walls.get(i).hitBox)&&(yVel>0)){
					yVel=0;
				}
				if(new Line2D.Double(Player.botLine.x1-xVel,Player.botLine.y1-yVel,Player.botLine.x2+-xVel,Player.botLine.y2-yVel).intersects(Global.walls.get(i).hitBox)&&(yVel<0)){
					yVel=0;
				}
				if(new Line2D.Double(Player.leftLine.x1-xVel,Player.leftLine.y1-yVel,Player.leftLine.x2-xVel,Player.leftLine.y2-yVel).intersects(Global.walls.get(i).hitBox)&&(xVel>0)){
					xVel=0;
				}
				if(new Line2D.Double(Player.rightLine.x1-xVel,Player.rightLine.y1-yVel,Player.rightLine.x2+-xVel,Player.rightLine.y2-yVel).intersects(Global.walls.get(i).hitBox)&&(xVel<0)){
					xVel=0;
				}
			}
		}
		//Translating Things on the Screen
		Map.y+=yVel;
		Map.x+=xVel;
		for(int i=0;i<Global.projectiles.size();i++){
			Global.projectiles.get(i).y+=yVel;
			Global.projectiles.get(i).x+=xVel;
		}
		for(int i=0;i<Global.enemies.size();i++){
			Global.enemies.get(i).y+=yVel;
			Global.enemies.get(i).x+=xVel;
		}
		for(int i=0;i<Global.walls.size();i++){
			Global.walls.get(i).y+=yVel;
			Global.walls.get(i).x+=xVel;
		}
		for(int i=0;i<Global.spawners.size();i++){
			Global.spawners.get(i).y+=yVel;
			Global.spawners.get(i).x+=xVel;
		}
		Global.portal.y+=yVel;
		Global.portal.x+=xVel;
		Global.portal.update();
	}
	public void setCurrentEffect(Color c){
		currentEffect = c;
	}
	public void paintEffect(Graphics g){
		g.setColor(currentEffect);
		g.fillRect(0, 0, Global.frameWidth, Global.frameHeight);
		fade=currentEffect.getAlpha()-5;
		if(fade<0)
			fade=0;
		setCurrentEffect(new Color(currentEffect.getRed(),currentEffect.getGreen(),currentEffect.getBlue(),fade));
	}
	public static void playSound(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		Clip clip = AudioSystem.getClip();
		AudioInputStream ain = AudioSystem.getAudioInputStream(new File(filepath));
		clip.open(ain);
		clip.start();
		
	}
}

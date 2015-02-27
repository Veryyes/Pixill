package pl;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Camera {
	Color currentEffect;
	public Camera() {
		currentEffect=new Color(0,0,0,0);
	}
	public void update(){
	//Diagonals
		if(InputListener.isPressed('W')&&Player.canMoveUp&&InputListener.isPressed('A')&&Player.canMoveLeft){
			Map.y+=Player.speed*Math.sin(Math.PI/4);
			Map.x+=Player.speed*Math.sin(Math.PI/4);
			for(int i=0;i<Global.projectiles.size();i++){
				Global.projectiles.get(i).y+=Player.speed*Math.sin(Math.PI/4);
				Global.projectiles.get(i).x+=Player.speed*Math.sin(Math.PI/4);
			}
			for(int i=0;i<Global.enemies.size();i++){
				Global.enemies.get(i).y+=Player.speed*Math.sin(Math.PI/4);
				Global.enemies.get(i).x+=Player.speed*Math.sin(Math.PI/4);
			}
			for(int i=0;i<Global.walls.size();i++){
				Global.walls.get(i).y+=Player.speed*Math.sin(Math.PI/4);
				Global.walls.get(i).x+=Player.speed*Math.sin(Math.PI/4);
			}
		}
		else if(InputListener.isPressed('W')&&Player.canMoveUp&&InputListener.isPressed('D')&&Player.canMoveRight){
			Map.y+=Player.speed*Math.sin(Math.PI/4);
			Map.x+=-Player.speed*Math.sin(Math.PI/4);
			for(int i=0;i<Global.projectiles.size();i++){
				Global.projectiles.get(i).y+=Player.speed*Math.sin(Math.PI/4);
				Global.projectiles.get(i).x+=-Player.speed*Math.sin(Math.PI/4);
			}
			for(int i=0;i<Global.enemies.size();i++){
				Global.enemies.get(i).y+=Player.speed*Math.sin(Math.PI/4);
				Global.enemies.get(i).x+=-Player.speed*Math.sin(Math.PI/4);
			}
			for(int i=0;i<Global.walls.size();i++){
				Global.walls.get(i).y+=Player.speed*Math.sin(Math.PI/4);
				Global.walls.get(i).x+=-Player.speed*Math.sin(Math.PI/4);
			}
		}
		else if(InputListener.isPressed('S')&&Player.canMoveDown&&InputListener.isPressed('A')&&Player.canMoveLeft){
			Map.y+=-Player.speed*Math.sin(Math.PI/4);
			Map.x+=Player.speed*Math.sin(Math.PI/4);
			for(int i=0;i<Global.projectiles.size();i++){
				Global.projectiles.get(i).y+=-Player.speed*Math.sin(Math.PI/4);
				Global.projectiles.get(i).x+=Player.speed*Math.sin(Math.PI/4);
			}
			for(int i=0;i<Global.enemies.size();i++){
				Global.enemies.get(i).y+=-Player.speed*Math.sin(Math.PI/4);
				Global.enemies.get(i).x+=Player.speed*Math.sin(Math.PI/4);
			}
			for(int i=0;i<Global.walls.size();i++){
				Global.walls.get(i).y+=-Player.speed*Math.sin(Math.PI/4);
				Global.walls.get(i).x+=Player.speed*Math.sin(Math.PI/4);
			}
		}
		else if(InputListener.isPressed('S')&&Player.canMoveDown&&InputListener.isPressed('D')&&Player.canMoveRight){
			Map.y+=-Player.speed*Math.sin(Math.PI/4);
			Map.x+=-Player.speed*Math.sin(Math.PI/4);
			for(int i=0;i<Global.projectiles.size();i++){
				Global.projectiles.get(i).y+=-Player.speed*Math.sin(Math.PI/4);
				Global.projectiles.get(i).x+=-Player.speed*Math.sin(Math.PI/4);
			}
			for(int i=0;i<Global.enemies.size();i++){
				Global.enemies.get(i).y+=-Player.speed*Math.sin(Math.PI/4);
				Global.enemies.get(i).x+=-Player.speed*Math.sin(Math.PI/4);
			}
			for(int i=0;i<Global.walls.size();i++){
				Global.walls.get(i).y+=Player.speed*Math.sin(Math.PI/4);
				Global.walls.get(i).x+=-Player.speed*Math.sin(Math.PI/4);
			}
		}
		//Cardinals
		else if(InputListener.isPressed('W')&&Player.canMoveUp){
			Map.y+=Player.speed;
			for(int i=0;i<Global.projectiles.size();i++)
				Global.projectiles.get(i).y+=Player.speed;
			for(int i=0;i<Global.enemies.size();i++)
				Global.enemies.get(i).y+=Player.speed;
			for(int i=0;i<Global.walls.size();i++)
				Global.walls.get(i).y+=Player.speed;
		}
		else if(InputListener.isPressed('S')&&Player.canMoveDown){
			Map.y+=-Player.speed;
			for(int i=0;i<Global.projectiles.size();i++)
				Global.projectiles.get(i).y+=-Player.speed;
			for(int i=0;i<Global.enemies.size();i++)
				Global.enemies.get(i).y+=-Player.speed;
			for(int i=0;i<Global.walls.size();i++)
				Global.walls.get(i).y+=-Player.speed;
		}
		else if(InputListener.isPressed('A')&&Player.canMoveLeft){
			Map.x+=Player.speed;
			for(int i=0;i<Global.projectiles.size();i++)
				Global.projectiles.get(i).x+=Player.speed;
			for(int i=0;i<Global.enemies.size();i++)
				Global.enemies.get(i).x+=Player.speed;
			for(int i=0;i<Global.walls.size();i++)
				Global.walls.get(i).x+=Player.speed;
		}
		else if(InputListener.isPressed('D')&&Player.canMoveRight){
			Map.x+=-Player.speed;
			for(int i=0;i<Global.projectiles.size();i++)
				Global.projectiles.get(i).x+=-Player.speed;
			for(int i=0;i<Global.enemies.size();i++)
				Global.enemies.get(i).x+=-Player.speed;
			for(int i=0;i<Global.walls.size();i++)
				Global.walls.get(i).x+=-Player.speed;
		}
		
		/*
		if(InputListener.isPressed('W')){
			if(Player.canMoveUp){
				for(int i=0;i<Global.projectiles.size();i++)
					Global.projectiles.get(i).y+=Player.speed;
				for(int i=0;i<Global.enemies.size();i++)
					Global.enemies.get(i).y+=Player.speed;
				for(int i=0;i<Global.walls.size();i++)
					Global.walls.get(i).y+=Player.speed;
			}
		}
		
		if(InputListener.isPressed('S')){
			if(Player.canMoveDown){
				for(int i=0;i<Global.projectiles.size();i++)
					Global.projectiles.get(i).y+=-Player.speed;
				for(int i=0;i<Global.enemies.size();i++)
					Global.enemies.get(i).y+=-Player.speed;
				for(int i=0;i<Global.walls.size();i++)
					Global.walls.get(i).y+=-Player.speed;
			}
		}
		
		if(InputListener.isPressed('A')){
			if(Player.canMoveLeft){
				for(int i=0;i<Global.projectiles.size();i++)
					Global.projectiles.get(i).x+=Player.speed;
				for(int i=0;i<Global.enemies.size();i++)
					Global.enemies.get(i).x+=Player.speed;
				for(int i=0;i<Global.walls.size();i++)
					Global.walls.get(i).x+=Player.speed;
			}
		}
	
		if(InputListener.isPressed('D')){
			if(Player.canMoveRight){
				for(int i=0;i<Global.projectiles.size();i++)
					Global.projectiles.get(i).x+=-Player.speed;
				for(int i=0;i<Global.enemies.size();i++)
					Global.enemies.get(i).x+=-Player.speed;
				for(int i=0;i<Global.walls.size();i++)
					Global.walls.get(i).x+=-Player.speed;
			}
		}
		*/
	}
	public void setCurrentEffect(Color c){
		currentEffect = c;
	}
	public void paintEffect(Graphics g){
		g.setColor(currentEffect);
		g.fillRect(0, 0, Global.frameWidth, Global.frameHeight);
	}
	public static void playSound(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		Clip clip = AudioSystem.getClip();
		AudioInputStream ain = AudioSystem.getAudioInputStream(new File(filepath));
		clip.open(ain);
		clip.start();
		
	}
}

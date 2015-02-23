package pl;

import java.awt.Color;
import java.awt.Graphics;

public class Camera {
	int xShift;
	int yShift;
	Color currentEffect;
	public Camera() {
		xShift=0;
		yShift=0;
		currentEffect=new Color(0,0,0,0);
	}
	public void update(){
		if(InputListener.isPressed('W')){
			if(Player.canMoveUp)
				yShift+=Player.speed;
		}
		if(InputListener.isPressed('S')){
			if(Player.canMoveDown)
				yShift+=-Player.speed;
		}
		if(InputListener.isPressed('A')){
			if(Player.canMoveLeft)
				xShift+=Player.speed;
		}
		if(InputListener.isPressed('D')){
			if(Player.canMoveRight)
				xShift+=-Player.speed;
		}
	}
	public void setCurrentEffect(Color c){
		currentEffect = c;
	}
	public void paintEffect(Graphics g){
		g.setColor(currentEffect);
		g.fillRect(0, 0, Global.frameWidth, Global.frameHeight);
	}
}

package pl;

public class Camera {
	int xShift;
	int yShift;
	public Camera() {
		xShift=0;
		yShift=0;
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
}

package pl;

public class Camera {
	int xShift;
	int yShift;
	public Camera() {
		xShift=0;
		yShift=0;
	}
	public void update(){
		if(InputListener.isPressed('W'))
			yShift+=1;
	}
}

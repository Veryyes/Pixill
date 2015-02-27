package pl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputListener implements KeyListener {
	public InputListener() {
		System.out.println("[INFO] InputListener Initiated");
		/*
		 * This only works for letters A->Z and Numbers 0->4 Inclusive;
		 * Haven't tested other things, not sure how much it will screw up;
		 * 		->> 6 and 9 make the player move left and right b/c code
		 */
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()<58&&e.getKeyCode()>47)	
			Global.pressedKeys|=(1<<e.getKeyCode()-21);
		else
			Global.pressedKeys|=(1<<e.getKeyCode()-64);
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()<58&&e.getKeyCode()>47)
			Global.pressedKeys&=~(1<<e.getKeyChar()-21);
		else
			Global.pressedKeys&=~(1<<e.getKeyChar()-64);
	}
	public void keyTyped(KeyEvent e) {
		
	}
	public static boolean isPressed(char key){
		if(key<58&&key>47)
			return ((Global.pressedKeys&(1<<(int)key-21))!=0);
		return ((Global.pressedKeys&(1<<(int)key))!=0);
	}

}

package testJump;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Character { //implements Collision{
	private String imageString = "images/character.png";
	private int x;//, initialX;
	private int y;//, initialY;
	private int ix; //inputed x
	private Image image;
	private Physics jump;

	public Character(int startX, int startY) {
		jump = new Physics(75, 2, -.3); //set Physics (angle, gravity, Vxo)//-0.06
		ImageIcon icon = new ImageIcon(this.getClass().getResource(imageString));//this.getClass works with any class name
		image = icon.getImage(); //sets image to imageString
		x = startX;
		y = startY;
	}

	public void move() { //called every tick
		//if(jump.round(jump.getVo()) == 0) {
		if(jump.getVyo()==0) {
			//jump.setT(0);
			x += ix;
		}

		else {
			jump.calculatePosition();
			//x += jump.getXp();
			//y -= jump.getYp();
			x = jump.getXp();
			y = -1*jump.getYp();
			jump.addTime();

			//if(jump.round(jump.getV()) == -1 * jump.getVo()) {
			if(-1*jump.getVy() > jump.getVyo()) {//-1*
				jump.zeroAll();
				y-=1;
			}
		}
		System.out.println(x+" " + y+" "+jump.getVy() +" "+ jump.getVyo()+ " "+jump.getT());
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); //gets the code of the key preseed

		if(key == KeyEvent.VK_LEFT) {
			ix = -2;//1
		}
		if(key == KeyEvent.VK_RIGHT) {
			ix = 2;//1
		}
		if(key == KeyEvent.VK_UP) {
			//jump.setVo(7.72741);//initial Velocity
			jump.calcInitialVelocities();
			jump.setInitialPosition(x, -1*y);
			//initialX = x;
			//initialY = y;
		}
	}

	public void keyReleased(KeyEvent e) { //add: or if one of the others is pressed
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT) {
				ix = 0;
			}
		if(key == KeyEvent.VK_RIGHT) {
				ix = 0;
			}
		/*if(key == KeyEvent.VK_UP) { //for varied jump height
			setVo(0);
		}*/
	}


	//Reference classes
	public int getX(){ //used in Main
		return x;
	}

	public int getY(){
		return y;
	}

	public Image getImage() { //used in Main
		return image;
	}
}
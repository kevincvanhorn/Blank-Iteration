import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
//import javax.swing.ImageIcon;
import java.awt.Rectangle;

import java.awt.Graphics; //basic rendering information
import java.awt.Graphics2D; //subclass of Graphics

public class Character { //implements Collision{
	private String imageString = "images/spritesheet_character.png";
	private int x, y;
	private int ix; //inputed x
	private BufferedImage image;
	private Physics jump;
	private SpriteSheet sprite;
	private Collision collision;
	private RectangleTest recTest;
	private boolean isFacingRight = true;
	private Rectangle platRect;
	private boolean isMoving, up, grounded;
	private int speed, mod; //2default

	public Character(int startX, int startY) {
		jump = new Physics(-.25);//75, 2, -.3); //set Physics (angle, Vxo, gravity)//-0.06//grav-.3
		sprite = new SpriteSheet(imageString);
		platRect = new Rectangle(254, 389, 169, 23);
		collision = new Collision(x, y);
		x = startX;
		y = startY;
		speed = 2;
	}

	public void move(boolean facingRight) { //called every tick
		//System.out.println(isFacingRight + " "+facingRight);
		if(isFacingRight() == true)//ix == 2)
			image = sprite.getRightFacing(); //quickfix
		else if(isFacingRight() == false)//ix == -2)
			image = sprite.getLeftFacing();

		collision.setBounds(x,y);
		collision.calcBounds(x,y);
		System.out.println(collision.isTouchingBot()+" "+x+" "+y);
		//System.out.println(collision.getXRect());

		if(jump.getVyo()==0){ //&& grounded) {
			x += ix;
			//isMoving = true;
		}
		/*if(grounded == true){
			x += ix;
		}*/

		else {
			//System.out.println(jump.printdxy());
			//System.out.println(jump.toString());

			/*if (collision.isTouchingBot()){
				//grounded = true;
				jump.zeroAll();
			}*/
			/*else if(!collision.isTouchingBot()){
				grounded = false;
			}*/
			if(-1*jump.getVy() >= jump.getVyo()) {
					jump.zeroAll();
					//y -= mod; //quickfix
					//grounded = true;
				}
			else{
				jump.calculatePosition();
				x = jump.getXp();
				y = -1*jump.getYp();
				jump.addTime();
				if(up == false)
					y -= 2;
				//grounded = false;
			}

		}
		//System.out.println(isFacingRight + " "+facingRight);
		//System.out.println(jump.toString());
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); //gets the code of the key preseed

		if(key == KeyEvent.VK_LEFT) {
			ix = -speed;//-2
			isFacingRight = false;
			isMoving = true;
		}
		if(key == KeyEvent.VK_RIGHT) {
			ix = speed;//2
			isFacingRight = true;
			isMoving = true;
		}
		if((key == KeyEvent.VK_UP) && (isMoving == true) && (isFacingRight() == true) && (jump.getVy() == 0.0)) { //
			jump.setVxo(speed);//2
			jump.setAngle(75);
			jump.calcInitialVelocities("Vxo");
			jump.setInitialPosition(x, -1*y);
			up=false;
		}
		if((key == KeyEvent.VK_UP) && (isMoving == true) &&(isFacingRight() == false)&& (jump.getVy() == 0.0)) {//
			jump.setVxo(-speed);
			jump.setAngle(-75);
			jump.calcInitialVelocities("Vxo");
			jump.setInitialPosition(x, -1*y);
			up = false;
		}
		if((key == KeyEvent.VK_UP) && (isMoving == false) && (jump.getVy() == 0.0)){
			jump.setVyo(speed*3.75);//*3
			jump.setAngle(90);
			jump.calcInitialVelocities("Vyo");
			jump.setInitialPosition(x, -1*y);
			up = true;
		}
	}

	public void keyReleased(KeyEvent e) { //add: or if one of the others is pressed
		int key = e.getKeyCode();
		if((key == KeyEvent.VK_LEFT)&&(ix != speed)) {
				ix = 0;
				isMoving = false;
			}
		if((key == KeyEvent.VK_RIGHT)&&(ix != -speed)) {
				ix = 0;
				isMoving = false;
			}
	}

	public boolean isFacingRight(){
		return isFacingRight;
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
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
//import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.util.*;

import java.awt.Graphics; //basic rendering information
import java.awt.Graphics2D; //subclass of Graphics

public class Character { //implements Collision{
	private String imageString = "images/spritesheet_character.png";
	private int x, y;
	private int ix; //inputed x
	private BufferedImage image;
	private Physics jump;
	private SpriteSheet sprite;
	private Collision collision, floor;
	private RectangleTest recTest;
	private boolean isFacingRight = true;
	private Rectangle platRect, floorRect;
	private boolean isMoving, up, grounded, onPlat;
	private int speed; //2default
	private Stack<Integer> xStack, yStack;

	public Character(int startX, int startY) {
		xStack = new Stack();
		yStack = new Stack();
		jump = new Physics(-.25);//75, 2, -.3); //set Physics (angle, Vxo, gravity)//-0.06//grav-.3
		sprite = new SpriteSheet(imageString);
		platRect = new Rectangle(300, 375, 200, 25);//300, 375, 200, 25
		floorRect = new Rectangle(0, 450, 600, 50);//0, 450, 600, 50
		collision = new Collision(x, y, platRect);
		floor = new Collision(x, y, floorRect);
		x = startX;
		y = startY;
		speed = 2;
		grounded = true;
		onPlat = false;
	}

	public void move(boolean facingRight, boolean isGrounded) { //called every tick
		if(facingRight)//ix == 2)//isFacingRight()
			image = sprite.getRightFacing(); //quickfix
		else if(!facingRight)//ix == -2) //isFacingRight()
			image = sprite.getLeftFacing();

		//collision.setBounds(x,y);//shorten thsee 2 into 1
		//floor.setBounds(x,y);

		System.out.println(isGrounded+" "+floor.isTouchingBot()+" "+collision.isTouchingBot()+" "+x+" "+y);
		//System.out.println(isGrounded+" "+jump.toString()+" "+y);
		/*if(jump.getVyo()==0){ //&& grounded) {
			x += ix;
			//isMoving = true;
		}*/
		if(isGrounded){ // && collision.isTouchingBot()) || (isGrounded && floor.isTouchingBot())
			x += ix;
			grounded = true;
			collision.setBounds(x,y);
			floor.setBounds(x,y);

			if(onPlat && collision.isOffBounds())//(!collision.isTouchingBot()) && !floor.isTouchingBot()
			{
				onPlat = false;
				grounded = false;
				if(!facingRight)
					jump.setVxo(-1*speed);//2
				else if(facingRight)
					jump.setVxo(speed);
				jump.setInitialPosition(x, -1*y);
				//System.out.println(" HEY");
			}
		}

		else {//try an approach where I can return early and halt the program
				jump.calculatePosition("");
					x = jump.getXp();
					y = -1*jump.getYp();
					xStack.push(x);
					yStack.push(y);
					jump.addTime();

				collision.setBounds(x,y);
				floor.setBounds(x,y);
				//System.out.println(collision.isTouchingBot());

			if (collision.isTouchingBot() || floor.isTouchingBot()){
				if (collision.isTouchingBot())
					onPlat = true;
				grounded = true;
				xStack.pop();
				yStack.pop();
				if(!xStack.isEmpty() && !yStack.isEmpty()){
					x = xStack.pop();
					y = yStack.pop();
				}
				jump.zeroAll();
				//y -= 2;
				/*if((jump.getVy() == 0) && (!collision.isTouchingBot() || !floor.isTouchingBot())){
					freefalling = true;
					grounded = false;
					}*/
				//if(up == false)
				//	y -= 7;
			}
			else{
				grounded = false;
			}
			//else if(!collision.isTouchingBot()){
			//	grounded = false;
				/*jump.calculatePosition();
					x = jump.getXp();
					y = -1*jump.getYp();
					jump.addTime();*/
			}
			/*if(-1*jump.getVy() >= jump.getVyo()) {
					jump.zeroAll();
					//y -= mod; //quickfix
					//grounded = true;
				}*/
				/*if(!isGrounded){
					jump.calculatePosition();
					x = jump.getXp();
					y = -1*jump.getYp();
					jump.addTime();
					//if(y == 400)//temporary before overhaul
					//	grounded = true;
				}*/

				//if(up == false)
				//	y -= 2;
				//grounded = false;


		//}
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
			grounded = false;
		}
		if((key == KeyEvent.VK_UP) && (isMoving == true) &&(isFacingRight() == false)&& (jump.getVy() == 0.0)) {//
			jump.setVxo(-speed);
			jump.setAngle(-75);
			jump.calcInitialVelocities("Vxo");
			jump.setInitialPosition(x, -1*y);
			up = false;
			grounded = false;
		}
		if((key == KeyEvent.VK_UP) && (isMoving == false) && (jump.getVy() == 0.0)){
			jump.setVyo(speed*3.75);//*3
			jump.setAngle(90);
			jump.calcInitialVelocities("Vyo");
			jump.setInitialPosition(x, -1*y);
			up = true;
			grounded = false;
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
	public boolean isGrounded(){
		return grounded;
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
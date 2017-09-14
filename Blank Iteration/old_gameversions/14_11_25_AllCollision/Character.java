import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
//import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.util.*;

import java.awt.Graphics; //basic rendering information
import java.awt.Graphics2D; //subclass of Graphics
import javax.swing.Timer;

public class Character { //implements Collision{
	private String imageString = "images/spritesheet_character.png";
	private int x, y, speed;
	private int ix; //inputed x
	private BufferedImage image;
	private Physics jump;
	private SpriteSheet sprite;
	private Collision collision, floor;
	private RectangleTest recTest;
	private boolean isFacingRight = true;
	private Rectangle platRect, floorRect;
	private boolean isMoving, up, grounded, onPlat, mod=false;
	private Stack<Integer> xStack, yStack;
	private Timer timer;

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

		else
			splice();

	}

	public void splice(){
		for(int i = 0; i<=5 && !grounded; i++){
			if(collision.isTouchingTop()){
				calcTop();
				System.out.println("top");
			}
			else if(collision.isTouchingBot() || floor.isTouchingBot()){
				calcBot();
				System.out.println("bot");
			}
			else if(collision.isTouchingRight() || collision.isTouchingLeft()){
				calcSides();
				System.out.println("right");
			}
			else{
				grounded = false;
					jump.calculatePosition("");
					x = jump.getXp();
					y = -1*jump.getYp();
					jump.addTime(.2);
			}
			collision.setBounds(x,y);
			floor.setBounds(x,y);
			xStack.push(x);
			yStack.push(y);
			//System.out.println(x+" "+y+" "+grounded+" "+jump.toString());
			//System.out.println(collision.isTouchingBot()+" "+collision.isTouchingRight()+" "+collision.isTouchingRight()+" "+jump.toString());
		}
	}

	public void calcSides(){
			jump.setAngle(90);
			jump.setVxo(0);
			jump.setVyo(jump.getVy());
			//jump.calcInitialVelocities("Vxo");
			jump.setT(0);
			if(!xStack.isEmpty() && !yStack.isEmpty()){
					x = xStack.pop();
					//y = yStack.pop();
					if(!xStack.isEmpty() && !yStack.isEmpty()){
						x = xStack.pop();
						//y = yStack.pop();
					}
				}
			jump.setInitialPosition(x,-1*y);//Problem here
			//System.out.println(x+" "+y+" "+grounded+" "+jump.toString());
			jump.calculatePosition("");
			//System.out.println(x+" "+y+" "+grounded+" "+jump.toString());
		}

	public void calcBot(){
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
	}

	public void calcTop(){
		jump.zeroAll();
			//jump.setVy(0);
			if(!xStack.isEmpty() && !yStack.isEmpty()){
					x = xStack.pop();
					y = yStack.pop();
					if(!xStack.isEmpty() && !yStack.isEmpty()){
						x = xStack.pop();
						y = yStack.pop();
					}
			}
			//jump.setInitialPosition(x,y);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); //gets the code of the key preseed

		if(key == KeyEvent.VK_LEFT) {
			ix = -1*speed;//-2
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
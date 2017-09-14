import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
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
	private boolean isFacingRight = true;
	private Rectangle platRect, floorRect, rect3, rect4;
	private boolean isMoving, up, grounded, onPlat, mod=false;
	private Stack<Integer> xStack, yStack;
	private Timer timer;
	private PlatCollision collision;
	private ArrayList<Rectangle> platList;

	public Character(int startX, int startY) {
		xStack = new Stack();
		yStack = new Stack();
		jump = new Physics(-.25);
		sprite = new SpriteSheet(imageString);

		platList = new ArrayList<Rectangle>();
		makePlatList();
		collision = new PlatCollision(x, y, platList);
		x = startX;
		y = startY;
		speed = 2;
		grounded = true;
		onPlat = false;
	}

	public void makePlatList(){
		platRect = new Rectangle(300, 375, 200, 25);
		floorRect = new Rectangle(-100, 450, 800, 50);//(0, 450, 600, 50)
		rect3 = new Rectangle(198, 328, 30, 25);
		rect4 = new Rectangle(84, 297, 30, 25);
		platList.add(platRect);
		platList.add(floorRect);
		platList.add(rect3);
		platList.add(rect4);
	}

	public void move(boolean facingRight, boolean isGrounded) { //called every tick
		if(facingRight)//ix == 2)//isFacingRight()
			image = sprite.getRightFacing(); //quickfix
		else if(!facingRight)//ix == -2) //isFacingRight()
			image = sprite.getLeftFacing();

		if(isGrounded){
			x += ix;
			grounded = true;
			collision.setBounds(x,y);

			if(onPlat && collision.isOffBounds())//(!collision.isTouchingBot()) && !floor.isTouchingBot()
			{
				onPlat = false;
				grounded = false;
				if(isMoving){
					if(!facingRight)
						jump.setVxo(-1*speed);//2
					else if(facingRight)
						jump.setVxo(speed);
				}
				else
					jump.setVxo(0);

				jump.setInitialPosition(x, -1*y);
			}
		}

		else
			splice();

	}

	public void splice(){
		for(int i = 0; i<=5 && !grounded; i++){
			if(collision.isTouching("top")){
				calcTop();
				//System.out.println("top");
			}
			else if(collision.isTouching("bot")){// || floor.isTouchingBot()
				calcBot();
				//System.out.println("bot");
			}
			else if(collision.isTouching("right") || collision.isTouching("left")){
				calcSides();
				//System.out.println("right");
			}
			else{
				grounded = false;
					jump.calculatePosition("");
					x = jump.getXp();
					y = -1*jump.getYp();
					jump.addTime(.2);
			}
			collision.setBounds(x,y);
			//floor.setBounds(x,y);
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
					yStack.pop();
					if(!xStack.isEmpty() && !yStack.isEmpty()){
						x = xStack.pop();
						yStack.pop();
					}
				}
			jump.setInitialPosition(x,-1*y);
			jump.calculatePosition("");
		}

	public void calcBot(){
		if (collision.isTouching("bot"))
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
		//jump.zeroAll();
			//jump.setVy(0);
			if(!xStack.isEmpty() && !yStack.isEmpty()){
					xStack.pop();
					y = yStack.pop();
					if(!xStack.isEmpty() && !yStack.isEmpty()){
						xStack.pop();
						y = yStack.pop();
					}
			}
			jump.setInitialPosition(x,-1*y);
			jump.setVyo(0);
			jump.setT(0);
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
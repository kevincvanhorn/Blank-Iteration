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
	//private String imageString = "images/spritesheet_character.png";
	private int x, y, speed;
	private int ix; //inputed x
	private BufferedImage image;
	private Physics jump;
	private SpriteSheet sprite;
	private boolean isFacingRight = true;
	//private Rectangle platRect, floorRect, rect3, rect4, rect5;
	private boolean isMoving, up, grounded, onPlat=true, mod=false;
	private Stack<Integer> xStack, yStack;
	private Timer timer;
	private PlatCollision collision;
	private ArrayList<Rectangle> platList;

	public Character(int startX, int startY) {
		xStack = new Stack();
		yStack = new Stack();
		jump = new Physics(-.25);
		sprite = new SpriteSheet(50, 50, "images/spritesheet_robo.png");

		platList = new ArrayList<Rectangle>();
		makePlatList();
		collision = new PlatCollision(21, 50, 13, x, y, platList);
		x = startX;
		y = startY;
		speed = 2;
		grounded = true;
		onPlat = false;
	}

	public void makePlatList(){
		platList.add(new Rectangle(300, 373, 200, 25));
		platList.add(new Rectangle(0, 449, 841, 52));
		platList.add(new Rectangle(523, 268, 30, 25));
		platList.add(new Rectangle(412, 188, 30, 25));
		platList.add(new Rectangle(273, 177, 30, 25));
		platList.add(new Rectangle(138, 148, 30, 25));
		platList.add(new Rectangle(9, 62, 78, 25));
		platList.add(new Rectangle(0, 1048, 1600, 27));
		platList.add(new Rectangle(-10, 0, 10, 1200));//leftbound
		platList.add(new Rectangle(1600, 0, 10, 1200));//rightbound
	}

	public void move(int milliseconds, boolean facingRight, boolean isGrounded) { //called every tick

		if(facingRight){
				if(jump.getVy() > 0 && jump.getAngle()!=90)
					image = sprite.getSprite(12);
				//else if(jump.getVy() < 0 && jump.getAngle()!=90){
					//sprite.addMs20();//delay of 20 sec
					//image = sprite.downSide("right");//getSprite(13);
				//}
				else if(jump.getVy() > 0)
					image = sprite.getSprite(10);
				else if(jump.getVy() < 0){
					image = sprite.getSprite(11);
				}

				else if(isMoving)
					image = sprite.getSprite(6);//moveRight();//
			else{
				sprite.reTime();
					image = sprite.stillRight();
			}

		}

		else if(!facingRight){
				if(jump.getVy() > 0 && jump.getAngle()!=90)
					image = sprite.getSprite(14);
				//else if(jump.getVy() < 0 && jump.getAngle()!=90){
				//	sprite.addMs20();
				//	image = sprite.downSide("left");//getSprite(15);
				//}
				else if(jump.getVy() > 0)
					image = sprite.getSprite(16);
				else if(jump.getVy() < 0)
					image = sprite.getSprite(17);
				else if(isMoving)
					image = sprite.getSprite(8);//moveLeft();//
			else{
				//sprite.reTime();
				image = sprite.stillLeft();
			}
		}


		if(isGrounded){
			while(!xStack.isEmpty()){//Empties stacks when grounded
			xStack.pop();
			}
			while(!yStack.isEmpty()){
				yStack.pop();
			}

			xStack.push(x);
			x += ix;
			grounded = true;
			collision.setBounds(x,y);
			if(collision.isTouching("right") || collision.isTouching("left")){
				x = xStack.pop();
			}
				//System.out.println("onPlat= "+onPlat+"Off Bounds = "+collision.isOffBounds());
			if(collision.isOffBounds())//onPlat &&//(!collision.isTouchingBot()) && !floor.isTouchingBot()
			{
				System.out.println("fall");
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

		else{
			splice();
		}

		//System.out.println(grounded+" "+x+" "+y);
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
					jump.addTime(.2);//.2
			}
			collision.setBounds(x,y);
			//floor.setBounds(x,y);
			xStack.push(x);
			yStack.push(y);
			//System.out.println(x+" "+y+" "+grounded+" "+jump.toString());
			//System.out.println(collision.isTouching("bot")+" "+collision.isTouching("right")+" "+collision.isTouching("left")+" "+jump.toString());
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
				if(!xStack.isEmpty() && !yStack.isEmpty()){
					xStack.pop();
					yStack.pop();
				}
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
	public boolean isMoving(){
		return isMoving;
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
	public void addMs(){
		sprite.addMs30();
	}
}
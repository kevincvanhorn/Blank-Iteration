import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.util.*;
import java.awt.*;

public class Character extends Entity{
	//vertical = up
	private int x, y, speed, moveX;//ADD VARIABLES
	private boolean grounded, onPlat, isMoving, vertical, mRight, mLeft, facingRight;
	private Stack<Integer> xStack, yStack;
	//private SpriteSheet sprites;
	private Physics physics;
	private boolean isFacingRight = true;
	private Collision collision;

	private BufferedImage testImage;

	//Constructor
	//Put platList in Loop, SpriteList in Loop
	public Character(int spawnX, int spawnY, ArrayList<Rectangle> platList, SpriteSheet sprites){//ArrayList<BufferedImage> spriteList
		super(spawnX, spawnY, sprites);

		setImage(sprites.getSprite(0));
		xStack = new Stack();
		yStack = new Stack();
		physics = new Physics(-.25);
		collision = new Collision("space", 13, 21, 50, spawnX, spawnY, platList);
		x = spawnX;
		y = spawnY;
		speed = 2;
		grounded = true;
		onPlat = false;
	}

	//Called every update of GameLoop
	public void move(int milliseconds, boolean facingRight, boolean isGrounded) {
		this.facingRight = facingRight;
		setSprites();
		//If touching the ground
		if(isGrounded){
			//Empties stacks when grounded to avoid overflow
			while(!xStack.isEmpty()){
			xStack.pop();
			}
			while(!yStack.isEmpty()){
				yStack.pop();
			}
			//Lateral Movement
			x += moveX;
			xStack.push(x);
			grounded = true;
			collision.setBounds(x,y, "space");
			if(collision.isTouching("right") || collision.isTouching("left")){
				x = xStack.pop();
			}
			//If Character is off the side of a platform
			if(collision.isOffBounds())
			{
				onPlat = false;
				grounded = false;
				if(isMoving){
					if(!facingRight)
						physics.setVxo(-1*speed-.5);
					else if(facingRight)
						physics.setVxo(speed-.5);
				}
				else
					physics.setVxo(0);
				vertical = true;
				physics.setInitialPosition(x, -1*y);
			}
		}
		//If in the air
		else{
			splice();
		}
		//System.out.println(grounded+" "+x+" "+y);
	}

	//Splices seconds into fifths for better collision detection
	public void splice(){
		for(int i = 0; i<=5 && !grounded; i++){
			if(collision.isTouching("top")){
				calcTop();
				vertical = false;
			}
			else if(collision.isTouching("bot")){
				calcBot();
				vertical = false;
			}
			else if(collision.isTouching("right") || collision.isTouching("left")){
				calcSides();
				vertical = false;
			}
			else{
				grounded = false;
				if(vertical){
					System.out.println(vertical);
					if(mRight)
						physics.addXo(.2);
					if(mLeft)
						physics.addXo(-.2);
				}
					physics.calculatePosition("");
					x = physics.getXp();
					y = -1*physics.getYp();
					physics.addTime(.2);
			}
			collision.setBounds(x,y, "space");
			xStack.push(x);
			yStack.push(y);
		}
	}

	//If Left or Right Bounds collide with an Entity
	public void calcSides(){
			physics.setAngle(90);
			physics.setVxo(0);
			physics.setVyo(physics.getVy());
			physics.setT(0);
			if(!xStack.isEmpty() && !yStack.isEmpty()){
					x = xStack.pop();
					yStack.pop();
					if(!xStack.isEmpty() && !yStack.isEmpty()){
						x = xStack.pop();
						yStack.pop();
					}
				}
			physics.setInitialPosition(x,-1*y);
			physics.calculatePosition("");
		}

	//If Top Bounds collides with an Entity
	public void calcTop(){
			if(!xStack.isEmpty() && !yStack.isEmpty()){
					xStack.pop();
					y = yStack.pop();
					if(!xStack.isEmpty() && !yStack.isEmpty()){
						xStack.pop();
						y = yStack.pop();
					}
			}
			physics.setInitialPosition(x,-1*y);
			physics.setVyo(0);
			physics.setT(0);
	}

	//If Bot Bounds collides with an Entity
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
				physics.zeroAll();
	}

	//Actions for when a key is pressed
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_LEFT) {
			moveX = -1*speed;
			isFacingRight = false;
			isMoving = true;
			mLeft = true;
		}
		if(key == KeyEvent.VK_RIGHT) {
			moveX = speed;
			isFacingRight = true;
			isMoving = true;
			mRight = true;
		}
		if((key == KeyEvent.VK_UP) && (isMoving == true) && (isFacingRight() == true) && (physics.getVy() == 0.0)) {
			physics.setVxo(speed);//2
			physics.setAngle(75);
			physics.calcInitialVelocities("Vxo");
			physics.setInitialPosition(x, -1*y);
			vertical=false;
			grounded = false;
		}
		if((key == KeyEvent.VK_UP) && (isMoving == true) &&(isFacingRight() == false)&& (physics.getVy() == 0.0)) {
			physics.setVxo(-speed);
			physics.setAngle(-75);
			physics.calcInitialVelocities("Vxo");
			physics.setInitialPosition(x, -1*y);
			vertical = false;
			grounded = false;
		}
		if((key == KeyEvent.VK_UP) && (isMoving == false) && (physics.getVy() == 0.0)){
			physics.setVyo(speed*3.75);
			physics.setAngle(90);
			physics.calcInitialVelocities("Vyo");
			physics.setInitialPosition(x, -1*y);
			vertical = true;
			grounded = false;
		}
	}

	//Actions for if a key is released
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT){
			mLeft = false;
		}
		if(key == KeyEvent.VK_RIGHT){
			mRight = false;
		}
		if((key == KeyEvent.VK_LEFT)&&(moveX != speed)) {
				moveX = 0;
				isMoving = false;
			}
		if((key == KeyEvent.VK_RIGHT)&&(moveX != -speed)) {
				moveX = 0;
				isMoving = false;
			}
	}

	//Accessor Classes
	public boolean isFacingRight(){
		return isFacingRight;
	}
	public boolean isGrounded(){
		return grounded;
	}
	public boolean isMoving(){
		return isMoving;
	}
	public int getX(){ //used in Main
		return x;
	}

	public int getY(){
		return y;
	}

	//Called every millisecond for sprites
	public void addMs(){
		cycle30(3);
	}

	//Sprites- Determine images for Character
	public void setSprites(){
		if(facingRight){
				if(physics.getVy() > 0 && physics.getAngle()!=90)
					setSprite(12);

				else if(physics.getVy() > 0)
					setSprite(10);
				else if(physics.getVy() < 0){
					setSprite(11);
				}

				else if(isMoving)
					setSprite(6);
			else{
				//sprite.reTime();
					setCycle(30, 0);//still Right
			}

		}

		else if(!facingRight){
				if(physics.getVy() > 0 && physics.getAngle()!=90)
					setSprite(14);
				else if(physics.getVy() > 0)
					setSprite(16);
				else if(physics.getVy() < 0)
					setSprite(17);
				else if(isMoving)
					setSprite(8);
			else{
				setCycle(30, 3);//still Left
			}
		}
	}

}

import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Character { //implements Collision{
	private String imageString = "images/spritesheet_character.png";
	private int x, y;
	private int ix; //inputed x
	private BufferedImage image;
	private Physics jump;
	private SpriteSheet sprite;
	private boolean isFacingRight;

	public Character(int startX, int startY) {
		jump = new Physics(75, 2, -.3); //set Physics (angle, gravity, Vxo)//-0.06
		//ImageIcon icon = new ImageIcon(this.getClass().getResource(imageString));//this.getClass works with any class name
		//image = icon.getImage(); //sets image to imageString
		sprite = new SpriteSheet(imageString);
		//image = sprite.getRightFacing();
		x = startX;
		y = startY;
	}

	public void move(boolean facingRight) { //called every tick
		//System.out.println(isFacingRight + " "+facingRight);
		if(isFacingRight() == true)//ix == 2)
			image = sprite.getRightFacing();
		else if(isFacingRight() == false)//ix == -2)
			image = sprite.getLeftFacing();

		if(jump.getVyo()==0) {
			x += ix;
			/*if(ix == 2)
				image = sprite.getRightFacing();
			else if(ix == -2)
				image = sprite.getLeftFacing();*/
		}

		else {
			jump.calculatePosition();
			x = jump.getXp();
			y = -1*jump.getYp();
			jump.addTime();

			if(-1*jump.getVy() > jump.getVyo()) {
				jump.zeroAll();
				y -= 1; //quickfix
			}
		}

		System.out.println(isFacingRight + " "+facingRight);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode(); //gets the code of the key preseed

		if(key == KeyEvent.VK_LEFT) {
			ix = -2;//1
			isFacingRight = false;
		}
		if(key == KeyEvent.VK_RIGHT) {
			ix = 2;//1
			isFacingRight = true;
		}
		if(key == KeyEvent.VK_UP) {
			jump.calcInitialVelocities();
			jump.setInitialPosition(x, -1*y);
		}
	}

	public void keyReleased(KeyEvent e) { //add: or if one of the others is pressed
		int key = e.getKeyCode();
		if((key == KeyEvent.VK_LEFT)&&(ix != 2)) {
				ix = 0;
			}
		if((key == KeyEvent.VK_RIGHT)&&(ix != -2)) {
				ix = 0;
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
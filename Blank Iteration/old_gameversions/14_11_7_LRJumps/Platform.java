package testJump;

import java.awt.Image;
//import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Platform(){
	private String imageString = "images/character.png";
	private int x;
	private int y;
	private Image image;

	public Platform(int startX, int startY){
		ImageIcon icon = new ImageIcon(this.getClass().getResource(imageString));
		image = icon.getImage(); //sets image to imageString
		x = startX;
		y = startY;
	}
}


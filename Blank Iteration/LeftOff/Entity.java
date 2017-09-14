import java.awt.image.BufferedImage;
import java.util.*;

public class Entity{
	//Instance Variables
	private int x, y;
	private BufferedImage image;
	//private ArrayList<BufferedImage> spriteList;
	private SpriteSheet sprites;

	//Constructor
	public Entity(int ix, int iy, SpriteSheet sprites){//ArrayList<BufferedImage> spriteList
		x = ix;
		y = iy;
		//this.spriteList = spriteList;
	    this.sprites = sprites;
		//this.type = type;
	}
	//Accessor Classes
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

	public BufferedImage getImage(){
		return image;
	}


	//Mutator Classes
	public void setImage(BufferedImage imagein){
		image = imagein;
	}
	public void setSprite(int i){
		image = sprites.getSprite(i);
	}
	public void setCycle(int type, int loopStart){
		image = sprites.getCycle(type, loopStart);
	}
	public void cycle30(int i){
		sprites.cycle30(i);
	}
}
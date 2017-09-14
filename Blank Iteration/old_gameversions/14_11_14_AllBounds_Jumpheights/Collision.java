//http://www.edu4java.com/en/game/game6.html//notused

//import java.awt.image.BufferedImage;
import java.awt.Rectangle;
//import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;

public class Collision{ //for a Rectangle
	//private boolean isCollidable = true;
	//private int x, y, width, height;
	//private int xPlat, yPlat, wPlat, hPlat;
	private Rectangle allBounds;//, botBounds, topBounds, leftBounds, rightBounds;
	//private Rectangle2D platform;
	private Rectangle platform;
	private Area area;

	public Collision(int ix, int iy){//int ix, int iy, int w, int h, Rectangle plat){
		//width = w;//image.getWidth();
		//height = h;//image.getHeight();
		allBounds = new Rectangle(ix, iy, 50, 50);
		platform = new Rectangle(300, 375, 200, 25);//(0, 400, 200, 50);

		//platform = plat;
		//calcArea();
		area = new Area(platform);
		//calcBounds();
	}

/*	public void calcBounds(){
		Rectangle botBounds = new Rectangle(x, y+height-1, width, 1);//
		Rectangle topBounds = new Rectangle(x, y, width, 1);
		Rectangle leftBounds = new Rectangle(x, y, 1, height);
		Rectangle rightBounds = new Rectangle(x+width-1, y, 1, height);//
	}*/

	/*public void calcArea(){//add exceptions
		xPlat = (int)platform.getX() -1;
		yPlat = (int)platform.getY() -1;
		wPlat = (int)platform.getWidth() + 1;
		hPlat = (int)platform.getHeight() +1;
		Rectangle outRect = new Rectangle(xPlat, yPlat, wPlat, hPlat);
		Area area = new Area(outRect);
	}*/


	//Reference Classes
	public boolean isTouching(){
		return area.intersects(allBounds);//.contains
	}

	/*public boolean isTouchingBot(){
		if(area.contains(botBounds) == true)
			return true;
		else
			return false;
	}

	public boolean isTouchingTop(){
		if(area.contains(topBounds) == true)
			return true;
		else
			return false;
	}

	public boolean isTouchingLeft(){
		if(area.contains(leftBounds) == true)
			return true;
		else
			return false;
	}

	public boolean isTouchingRight(){
		if(area.contains(rightBounds) == true)
			return true;
		else
			return false;
	}*/

	public void setBounds(int ix, int iy){
		allBounds.setLocation(ix,iy);
	}

	//Extra
	public String getXRect(){
		return allBounds.getX() + "";
	}
}

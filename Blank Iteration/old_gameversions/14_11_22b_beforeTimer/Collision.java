//http://www.edu4java.com/en/game/game6.html//notused

//import java.awt.image.BufferedImage;
import java.awt.Rectangle;
//import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;

public class Collision{ //for a Rectangle
	//private boolean isCollidable = true;
	private int x, y, width, height, xChar, yChar;
	private int xPlat, yPlat, wPlat, hPlat, maxLeft, maxRight;
	private Rectangle allBounds, botBounds, topBounds, leftBounds, rightBounds;
	//private Rectangle2D platform;
	private Rectangle platform;
	private Area area;
	private boolean off;

	public Collision(int ix, int iy, Rectangle inRect){//int ix, int iy, int w, int h, Rectangle plat){
		width = 50;//image.getWidth();
		height = 50;//image.getHeight();
		allBounds = new Rectangle(ix, iy, 50, 50);
		xPlat = (int) inRect.getX();
		yPlat = (int) inRect.getY();
		wPlat = (int) inRect.getWidth();
		hPlat = (int) inRect.getHeight();

		platform = new Rectangle(xPlat, yPlat, wPlat, hPlat);//(0, 400, 200, 50);

		//platform = plat;
		//calcArea();
		area = new Area(platform);
		makeBounds();
	}

	public void makeBounds(){//make more elegant by new Rectangle(null/default)
		x = (int) allBounds.getY();
		y = (int) allBounds.getX();
		botBounds = new Rectangle(x, y+height-1, width, 1);//
		topBounds = new Rectangle(x, y, width, 1);
		leftBounds = new Rectangle(x, y, 1, height);
		rightBounds = new Rectangle(x+width-1, y, 1, height);//
	}

	public void calcBounds(int x, int y){
		botBounds = new Rectangle(x, y+height-1, width, 1);//
		topBounds = new Rectangle(x, y, width, 1);
		leftBounds = new Rectangle(x, y, 1, height);
		rightBounds = new Rectangle(x+width-1, y, 1, height);//
		stepsOff(x);
	}

	/*public void calcArea(){//add exceptions
		xPlat = (int)platform.getX() -1;
		yPlat = (int)platform.getY() -1;
		wPlat = (int)platform.getWidth() + 1;
		hPlat = (int)platform.getHeight() +1;
		Rectangle outRect = new Rectangle(xPlat, yPlat, wPlat, hPlat);
		Area area = new Area(outRect);
	}*/
	public void stepsOff(int x){
		maxLeft = xPlat;
		maxRight = xPlat + wPlat;
		if((x < maxLeft-50) || (x > maxRight))
			off = true;
		else
			off = false;
	}

	//Reference Classes
	public boolean isOffBounds(){
		return off;
	}
	public boolean isTouching(){
		return area.intersects(allBounds);//.contains
	}

	public boolean isTouchingBot(){
		return area.intersects(botBounds);
	}

	public boolean isTouchingTop(){
		return area.intersects(topBounds);
	}

	public boolean isTouchingLeft(){
		return area.intersects(leftBounds);
	}

	public boolean isTouchingRight(){
		return area.intersects(rightBounds);
	}

	public void setBounds(int ix, int iy){
		allBounds.setLocation(ix,iy);
		calcBounds(ix, iy);
	}

	//Extra
	/*public String getXRect(){
		return allBounds.getX() + "";
	}*/
}

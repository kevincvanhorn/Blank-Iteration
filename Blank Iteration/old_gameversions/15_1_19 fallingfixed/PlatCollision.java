//http://www.edu4java.com/en/game/game6.html//notused

//import java.awt.image.BufferedImage;
import java.awt.Rectangle;
//import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;
import java.awt.*;
import java.util.*;

public class PlatCollision{ //for a Rectangle
	//private boolean isCollidable = true;
	private int x, y, wChar, hChar, xChar, yChar, space;
	private int xPlat = 0, yPlat, wPlat = 841, hPlat, maxLeft, maxRight;
	private Rectangle allBounds, botBounds, topBounds, leftBounds, rightBounds;
	//private Rectangle2D platform;
	private Rectangle platform;
	private Area area;
	private boolean off;
	private ArrayList<Rectangle> platList;

	public PlatCollision(int w, int h, int space, int ix, int iy, ArrayList<Rectangle> platListIn){//int ix, int iy, int w, int h, Rectangle plat){
		this.space = space;
		wChar = w;//image.getWidth();
		hChar = h;//image.getHeight();
		allBounds = new Rectangle(ix, iy, wChar, hChar);
		//xPlat = (int) inRect.getX();
		//yPlat = (int) inRect.getY();
		//wPlat = (int) inRect.getWidth();
		//hPlat = (int) inRect.getHeight();

		platList = new ArrayList<Rectangle>();
		platList = platListIn;
		//platform = new Rectangle(xPlat, yPlat, wPlat, hPlat);//(0, 400, 200, 50);
		//platform = plat;
		//calcArea();
		//area = new Area(platform);

		//makeBounds();
	}

	/*public void makeBounds(){//make more elegant by new Rectangle(null/default)
		x = (int) allBounds.getY();
		y = (int) allBounds.getX();
		botBounds = new Rectangle(x, y+height-1, width, 1);//
		topBounds = new Rectangle(x, y, width, 1);
		leftBounds = new Rectangle(x, y, 1, height);
		rightBounds = new Rectangle(x+width-1, y, 1, height);//
	}*/

	public void calcBounds(int x, int y){//faster- dont create rectangle here, only update it
		botBounds = new Rectangle(x+space+1, y+ hChar-1, wChar-2, 1);//(x+space, y+ hChar-1, wChar, 1);//(x+1, y+height-1, width-2, 1);
		topBounds = new Rectangle(x+space+1, y, wChar-2, 1);//(x+space, y, wChar, 1);//(x+1, y, width-2, 1);
		leftBounds = new Rectangle(x+space, y, 1, hChar);//(x+space, y+1, 1, hChar-2);//(x, y, 1, height-2);
		rightBounds = new Rectangle(x+space+wChar-1, y, 1, hChar);//(x+space+wChar-1, y+1, 1, hChar-2);//(x+width-1, y+1, 1, height-2);//
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
		maxLeft = xPlat;//(int) inRect.getX();
		maxRight = xPlat + wPlat;//(int) inRect.getWidth();
		if((x+space+wChar < maxLeft) || (x+space > maxRight)){
			off = true;
			//System.out.println("Steps Off");
		}
		else
			off = false;
	}

	//Reference Classes
	public boolean isOffBounds(){
		return off;
	}
	public boolean isTouching(String str){
		if(str.equals("bot")){
			for(int i = 0; i<platList.size(); i++){
				area = new Area(platList.get(i));
				if(area.intersects(botBounds)){
					xPlat = (int) platList.get(i).getX();
					wPlat = (int) platList.get(i).getWidth();
					return true;
				}

			}
			return false;
		}
		else if(str.equals("right")){
			for(int i = 0; i<platList.size(); i++){
				area = new Area(platList.get(i));
				if(area.intersects(rightBounds))
					return true;
			}
			return false;
		}
		else if(str.equals("left")){
			for(int i = 0; i<platList.size(); i++){
				area = new Area(platList.get(i));
				if(area.intersects(leftBounds))
					return true;
			}
			return false;
		}
		else if(str.equals("top")){
			for(int i = 0; i<platList.size(); i++){
				area = new Area(platList.get(i));
				if(area.intersects(topBounds))
					return true;
			}
			return false;
		}
		else{
			for(int i = 0; i<platList.size(); i++){
				area = new Area(platList.get(i));
				if(area.intersects(allBounds))
					return true;
			}
			return false;
			}
		//return area.intersects(allBounds);//.contains
	}

	/*public boolean isTouchingBot(){
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
	}*/

	public void setBounds(int ix, int iy){
		allBounds.setLocation(ix,iy);
		calcBounds(ix, iy);
	}

	//Extra
	/*public String getXRect(){
		return allBounds.getX() + "";
	}*/
}

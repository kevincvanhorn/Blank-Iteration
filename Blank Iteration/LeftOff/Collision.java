//Specifies a Collider and its Collidables
//Der = Collider || ables = Collidables
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.*;
import java.util.*;

public class Collision{
	private String type;
	private int x, y, wChar, hChar, xChar, yChar, space, maxLeft, maxRight, xPlat, wPlat;
	private ArrayList<Rectangle> ableList;
	private Rectangle botBounds, topBounds, leftBounds, rightBounds, allBounds;
	private Area area;
	private boolean off;

	//Constructor
	//Specify "space," space, Charwidth, Charheight, CharX, CharY, Colliable RectangleList
	public Collision(String type, int space, int w, int h, int ix, int iy, ArrayList<Rectangle> ableList){
			allBounds = new Rectangle();
			botBounds = new Rectangle();
			topBounds = new Rectangle();
			leftBounds = new Rectangle();
			rightBounds = new Rectangle();
			this.type = type;
			x = ix;
			y = iy;
			hChar = h;
			wChar = w;
			this.space = space;
			this.ableList = ableList;
			calcBoundsChar(type);
	}

	//Calculate bounds of Collider
	public void calcBoundsChar(String type){
		if(type.equals("space")){
			botBounds.setBounds(x+space+1, y+ hChar-1, wChar-2, 1);
			topBounds.setBounds(x+space+1, y, wChar-2, 1);
			leftBounds.setBounds(x+space, y, 1, hChar);
			rightBounds.setBounds(x+space+wChar-1, y, 1, hChar);
			allBounds.setBounds(x+space, y, wChar, hChar);
		}
		else{//WORKING
			botBounds.setBounds(x+1, y+ hChar-1, wChar-2, 1);
			topBounds.setBounds(x+1, y, wChar-2, 1);
			leftBounds.setBounds(x, y, 1, hChar-2);
			rightBounds.setBounds(x+wChar-1, y+1, 1, hChar-2);
			allBounds.setBounds(x, y, wChar, hChar);
		}
		stepsOff(x);
	}

	//Updates bounds of Collider
	public void setBounds(int ix, int iy, String str){
		x = ix;
		y = iy;
		calcBoundsChar(str);
	}

	//Special Case: Character steps off a platform
	public void stepsOff(int x){
		maxLeft = xPlat;//(int) inRect.getX();
		maxRight = xPlat + wPlat;//(int) inRect.getWidth();
		if((x+space+wChar < maxLeft) || (x+space > maxRight)){
			off = true;
		}
		else
			off = false;
	}
	public boolean isOffBounds(){
		return off;
	}

	//Checks if Each side of Collider is touching Collidables
	public boolean isTouching(String str){
		if(str.equals("bot")){
			for(int i = 0; i<ableList.size(); i++){
				area = new Area(ableList.get(i));
				if(area.intersects(botBounds)){
					xPlat = (int) ableList.get(i).getX();
					wPlat = (int) ableList.get(i).getWidth();
					return true;
				}

			}
			return false;
		}
		else if(str.equals("right")){
			for(int i = 0; i<ableList.size(); i++){
				area = new Area(ableList.get(i));
				if(area.intersects(rightBounds))
					return true;
			}
			return false;
		}
		else if(str.equals("left")){
			for(int i = 0; i<ableList.size(); i++){
				area = new Area(ableList.get(i));
				if(area.intersects(leftBounds))
					return true;
			}
			return false;
		}
		else if(str.equals("top")){
			for(int i = 0; i<ableList.size(); i++){
				area = new Area(ableList.get(i));
				if(area.intersects(topBounds))
					return true;
			}
			return false;
		}
		else{
			for(int i = 0; i<ableList.size(); i++){
				area = new Area(ableList.get(i));
				if(area.intersects(allBounds))
					return true;
			}
			return false;
			}
	}
}
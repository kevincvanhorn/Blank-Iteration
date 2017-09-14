public class Camera{
	//Instance Variables
	private int Xcam, Ycam, charXoff, charYoff, charX, charY, diffX, diffY;
	private int pastX, pastY, midX, midY, height, width, spawnX, spawnY, levelWidth, windowWidth;
	//Constructor
	public Camera(int w, int h, int X, int Y, int levelWidth, int windowWidth){
		pastX = 0;
		pastY = 0;
		Xcam = 0;
		Ycam = 0;
		spawnX = X;
		spawnY = Y;
		charX= spawnX;
		charY = spawnY;
		charXoff = spawnX;
		charYoff = spawnY;
		width = w;
		height = h;
		this.levelWidth = levelWidth;
		this.windowWidth = windowWidth;
	}

	//Updates Camera with Character Movement
	public void update(int x, int y, boolean isFacingRight){
		charX = x;
		charY = y;
		midX = (int)(width/2);
		midY = (int)(height/2);
		if(charX < midX){
			charXoff = charX;
			Xcam = 0;
		}
		else if(Xcam >= levelWidth - windowWidth - 1){ //1000
			charXoff = charX - levelWidth - windowWidth;
			if(charXoff < midX)
				Xcam --;
		}
		else{
			diffX = charX - pastX;
			Xcam += diffX;
		}

		/*if(charY <= spawnY +1){
			charYoff = charY;
		}
		else{
			diffY = charY - pastY;
			Ycam +=diffY;
		}*/

		/*if(charY >= Ycam + midY){
			diffY = Ycam + midY - charY;
			Ycam += diffY;
		}*/

		pastX = charX;
		pastY = charY;
		System.out.println("charX = "+charX+" charY = "+charY+" charXoff = "+charXoff+" charYoff = "+charYoff);
	}

	//Accessor Classes
	public int getX(){
		return charXoff;
	}
	public int getY(){
		return charYoff;
	}
	public int getXcam(){
		return Xcam;
	}
	public int getYcam(){
		return Ycam;
	}
}
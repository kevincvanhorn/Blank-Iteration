public class Camera{
	private int Xcam, Ycam, charXoff, charYoff, charX, charY, diffX, diffY;
	private int pastX, pastY, midX, midY, height, width, spawnX, spawnY;
	public Camera(int w, int h, int X, int Y){//add spawn in constructor
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
	}

	public void update(int x, int y, boolean isFacingRight){
		charX = x;
		charY = y;
		midX = (int)(width/2);
		midY = (int)(height/2);
		if(charX < midX){//takes care of initial exception
			charXoff = charX;
		}
		else if(Xcam >= 1000 - 1){ //&& charXoff > midX){
			charXoff = charX - 1000 - 1;
			if(charXoff < midX)
				Xcam --;
		}
		else{
			diffX = charX - pastX;
			Xcam += diffX;//-
		}

		if(charY <= spawnY +1){
			charYoff = charY;
		}
		else{
			diffY = charY - pastY;
			Ycam +=diffY;//-
		}
		pastX = charX;
		pastY = charY;
		//System.out.println(charX+" "+charY+" off "+charXoff+" "+charYoff+" "+diffX+" midX "+midX );
		//System.out.println(Xcam+" "+Ycam);
	}

	public int getX(){
		return charXoff;//charXoff
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
	/*public BufferedImage getImage(){
		return new Buffered Image
	}*/
	//make sure everything else is global
}
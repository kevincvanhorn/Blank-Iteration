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

	public void update(int x, int y){
		charX = x;
		charY = y;
		midX = (int)(width/2);
		midY = (int)(height/2);
		if(charX >= 0){
			diffX = charX - pastX;
			if(charXoff > midX){
				charXoff -= diffX;
				Xcam -= diffX;
			}

			else if(charXoff <= midX && charX >midX){
				charXoff += diffX;
				Xcam-= diffX;
			}
			else
				charXoff = charX;

		}
		/*if(charY > spawnY && charY<height){
			diffY = charY - pastY;
			charYoff -= diffY;
			Ycam += diffY;
			/*if(charYoff <= midY){
				charYoff +=diffY;
				Ycam-= diffY;
			}
		}*/
		pastX = charX;
		pastY = charY;
		System.out.println(charX+" "+charY+" off "+charXoff+" "+charYoff+" "+diffX+" midX "+midX );
	}

	public int getX(){
		return charXoff;
	}
	public int getY(){
		return charY;
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
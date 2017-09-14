import java.awt.image.BufferedImage;
public class Camera{
	private int camX, camY, localWidth, localHeight;
	private BufferedImage localImage, globalImage;

	public Camera(int w, int h){
		localWidth = w;
		localHeight = h;
		camX = 0;
		camY = 0;
	}

	public void update(BufferedImage buffer){//, int charX, int charY){
		globalImage = buffer;
		localImage = globalImage.getSubimage(0,0, localWidth, localHeight);
	}

	public BufferedImage localImage(){
		return localImage;
	}
}
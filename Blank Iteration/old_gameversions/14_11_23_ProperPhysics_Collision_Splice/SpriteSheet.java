import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;

public class SpriteSheet{
	//private Image sheet;
	private BufferedImage sheet; //BufferedImage(int width, int height, int imageType)
	private int sheetHeight, sheetWidth;

	public SpriteSheet(String imageString){// throws IOException{ //if file is not there
		//bufferedImage = ImageIO.read(file);
		//Image sheet = new Image();

		//File file = new File(imageString);
		//BufferedImage sheet = ImageIO.read(imageString);

		try{
			sheet = ImageIO.read(new File(imageString));//new File(imageString));
		} catch (IOException e){
			System.out.println(e);
				}



		//sheetHeight = sheet.getHeight();
		//sheetWidth = sheet.getWidth();
	}

	public BufferedImage getRightFacing(){ //throws IOException{
		return sheet.getSubimage(0, 0, 50, 50);
	}

	public BufferedImage getLeftFacing(){ //throws IOException{
		return sheet.getSubimage(50, 0, 50, 50);
	}

	/*public BufferedImage subImage(int x, int y, int w, int h){
		return sheet.getSubimage(x, y, w, h);
	}*/

	/*public static void main(String[] args)
  {
    new SpriteSheet();
  }*/
}
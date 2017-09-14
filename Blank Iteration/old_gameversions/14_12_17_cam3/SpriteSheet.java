import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet{
	//private Image sheet;
	private BufferedImage sheet; //BufferedImage(int width, int height, int imageType)
	private int spriteHeight, spriteWidth, sheetHeight, sheetWidth, cnt20, cnt30, index20=0, index30;
	private ArrayList<BufferedImage> arrayList;

	public SpriteSheet(int w, int h, String imageString){// throws IOException{ //if file is not there
		arrayList = new ArrayList<BufferedImage>();

		try{
			sheet = ImageIO.read(new File(imageString));//new File(imageString));
		} catch (IOException e){
			System.out.println(e);
				}

		sheetHeight = sheet.getHeight();
		sheetWidth = sheet.getWidth();
		spriteHeight = h;
		spriteWidth = w;
			for(int cpixel = 0; cpixel < sheetHeight;cpixel+=spriteHeight){
				for(int rpixel = 0; rpixel < sheetWidth; rpixel+=spriteWidth){
					arrayList.add(sheet.getSubimage(rpixel, cpixel, spriteWidth, spriteHeight));
				}

			}
	}
	public BufferedImage getSprite(int i){
		return arrayList.get(i);
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

  	public BufferedImage stillRight(){
		return getSprite(index30);
	}
	public BufferedImage stillLeft(){
		return getSprite(index30+3);
	}
	/*public BufferedImage moveRight(){
		return getSprite(6+index20);
	}
	public BufferedImage moveLeft(){
		return getSprite(8+index20);
	}*/
	public BufferedImage downSide(String str){
		if(str.equals("right"))
			return getSprite(12+index20);
		else
			return getSprite(14+index20);
	}
	public void addMs30(){
		//ms+=10;
		cnt30++;
		if(cnt30==30){
			index30++;
			if(index30 == 3)
				index30 = 0;
			cnt30 = 0;
		}
	}
	/*public void addMs20(){
		//ms+=10;
		cnt20++;
		if(cnt20 >= 10 && cnt20 )//switch back to middle
			index20 = -2;
		else if()
			index20 = 1;
		else
			index20 = 0;

	}*/
	public void reTime(){
		cnt20 = 0;
	}

}
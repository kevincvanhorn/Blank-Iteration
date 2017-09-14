//in GameLoop getList return ArrayList<BufferedImage>
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet{
	private BufferedImage sheet;
	private int spriteHeight, spriteWidth, sheetHeight, sheetWidth, cnt30 =0;
	private int index30 =0;
	private ArrayList<BufferedImage> imageList;

	//Constructor: spriteWidth, spriteHeight, File Name
	public SpriteSheet(int w, int h, String imageString){
	imageList = new ArrayList<BufferedImage>();

		//Checks if File Exists
		try{
			sheet = ImageIO.read(new File(imageString));
			} catch (IOException e){
			System.out.println(e);
		}

		sheetHeight = sheet.getHeight();
		sheetWidth = sheet.getWidth();
		spriteHeight = h;
		spriteWidth = w;

		//adds sprites to imageList based on sprite Height and Width
		for(int cpixel = 0; cpixel < sheetHeight;cpixel+=spriteHeight){
			for(int rpixel = 0; rpixel < sheetWidth; rpixel+=spriteWidth){
				imageList.add(sheet.getSubimage(rpixel, cpixel, spriteWidth, spriteHeight));
				//System.out.println(imageList);
			}//WORKING
		}
	}

	//Cycle Sprite Classes
	public void cycle30(int length){
		//int cnt = 0;
		cnt30++;
		if(cnt30 == 30){
			index30++;
			if(index30 == length)
				index30 = 0;
			cnt30 = 0;
		}

	}

	public BufferedImage getCycle(int type, int loopStart){
		if(type == 30){
			return getSprite(loopStart+ index30);
		}
		else
			return null;
	}

	public void reTime(){
		index30 = 0;
	}


	//Accessor Classes
	public ArrayList<BufferedImage> getList(){
		return imageList;
	}
	public BufferedImage getSprite(int i){
		return imageList.get(i);
	}
}
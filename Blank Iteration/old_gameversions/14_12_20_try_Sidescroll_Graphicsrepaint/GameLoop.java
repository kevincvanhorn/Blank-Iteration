//http://obviam.net/index.php/android-gameloop-measuring-fps/
//http://www.java-gaming.org/topics/game-loops/24220/view.html

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameLoop extends JPanel implements ActionListener {
	private final static int MAX_FPS = 30;
	private final static int MAX_FRAME_SKIPS = 5;
	private final static int    FRAME_PERIOD = 10;//1000 / MAX_FPS; //every 10 ms
	private boolean isRunning = true;
	private long timeStart, timeDiff;
	private int timeSleep, framesSkipped, milliseconds, seconds, prevms, cntSec;

	public Character character;
	private Camera camera;
	private boolean running = false;
	private boolean paused = false;
	//private int fps = 60;
	//private int frameCount = 0;
	private BufferedImage background, buffer;
	private int charX, charY;

	public GameLoop(){
		addKeyListener(new adapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		character = new Character(0,400);

		try{
			background = ImageIO.read(new File("images/background_rework_level.png"));
		} catch (IOException e){}
		buffer = new BufferedImage(background.getWidth(), background.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
		//buffer = background;
		camera = new Camera(600, 500);//checkdees
		buffer = background;
		runGameLoop();
	}

	public void runGameLoop(){
		Thread loop = new Thread()
	      {
	         public void run()
	         {
	            gameLoop();
	         }
	      };
	      loop.start();


	}
	private void gameLoop(){
		while(isRunning){
			if(!paused){
				timeStart = System.currentTimeMillis();//System.nanoTime()
				framesSkipped = 0;
				update();
				display();
				timeDiff = System.currentTimeMillis() - timeStart;
				timeSleep = (int)(FRAME_PERIOD - timeDiff);

				if(timeSleep > 0){
					try {
							Thread.sleep(timeSleep);
						} catch (InterruptedException e) {}
				}

				while(timeSleep < 0 && framesSkipped < MAX_FRAME_SKIPS){
					update();
					timeSleep += FRAME_PERIOD;
					framesSkipped++;
				}
				/*milliseconds++;
				if(milliseconds - cntSec == 100){
					seconds ++;
					cntSec= milliseconds;
				}
				//if(milliseconds - cnt30 == 30){
				//	character.cnt300ms();
				//}
				System.out.println(seconds+" "+milliseconds);
				character.addMs();
				if(seconds == 10){
					try{
					ImageIO.write(buffer, "png", new File ( "output.png" ));
					} catch (IOException e){}
					seconds++;
				}*/

			}

		}
	}

	public void update(){
		character.move(milliseconds, character.isFacingRight(), character.isGrounded());
		//if(character.isMoving())
			//camera.update(character.getX(), character.getY());
	}

	public void display(){
		repaint();
	}
	public void actionPerformed(ActionEvent e) { //called every 5 ms

    }

    public void mergeGlobalBuffer(String str){
    	charX = character.getX();
    	charY = character.getY();
    	Graphics2D gBuffer = buffer.createGraphics();//make private
		    //gBuffer.drawImage(buffer, 0, 0, null);
		    //gBuffer.drawImage(background, 0, 0, null);
		if(str.equals("draw"))
		    gBuffer.drawImage(character.getImage(), charX, charY, null);
		else if(str.equals("reset"))
			gBuffer.drawImage(background, 0, 0, null);
    }

	public void paint(Graphics g) { //using Graphics object g//try paintComponent (cant be overriden, more specific)
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g; //creates Graphics2D
		//g2d.drawImage(background, 0, 0, this);
		//g2d.drawImage(character.getImage(), character.getX(), character.getY(), this); //(image, x, y, ?) ||---
		//buffer.setData(background);//Raster r
		mergeGlobalBuffer("draw");
		camera.update(buffer);
		g2d.drawImage(camera.localImage(), 0, 0, this);
		Toolkit.getDefaultToolkit().sync(); //||-----------
		g.dispose(); //Component resources destroyed and out of memory ||----
		mergeGlobalBuffer("reset");
	}

	private class adapter extends KeyAdapter { //recieves keyboard events //called in constructor

		public void keyPressed(KeyEvent e){
			character.keyPressed(e);
		}

		public void keyReleased(KeyEvent e){ //from character
			character.keyReleased(e);
		}
		public void keyTyped(KeyEvent e){
			int key = e.getKeyCode();
			if(key == 80){
				paused = !paused;
			}

		}
	}
}
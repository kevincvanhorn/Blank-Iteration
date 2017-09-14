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
	private int timeSleep, framesSkipped, milliseconds, seconds, prevms;
	private int xFrame = 0, yFrame = 0, midFrameX, midFrameY, heightFrame, widthFrame, widthLevel, heightLevel;
	private int pastXo, pastYo, pastMidXo, pastMidYo, factorX, factorY, speed = 2, charXFrame, charYFrame;
	private int spawnX, spawnY, diffX, diffY;
	public Character character;
	private boolean running = false;
	private boolean paused = false;
	//private int fps = 60;
	//private int frameCount = 0;
	private BufferedImage background, sectImage;

	public GameLoop(){
		addKeyListener(new adapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		character = new Character(0,400);
		spawnX = 0;
		spawnY = 400;
		charXFrame = 0;
		charYFrame = 400;
		xFrame = 0;
		yFrame = 0;
		try{
			background = ImageIO.read(new File("images/background_level.png"));
		} catch (IOException e){}
		heightLevel = background.getHeight();
		widthLevel = background.getWidth();
		heightFrame = 500;
		widthFrame = 600;
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
		//System.out.println(character.groundRect());
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
				if(milliseconds - cnt30 == 30){
					character.cnt300ms();
				}
				System.out.println(seconds+" "+milliseconds);*/
				character.addMs();
			}

		}
	}

	public void update(){
		character.move(milliseconds, character.isFacingRight(), character.isGrounded());
		calcFrame();
		//System.out.println(diff+" "+pastMidYo+" "+pastMidXo+" "+xFrame +" "+ yFrame +" "+character.getX()+" "+character.getY() + " "+charXFrame +" "+charYFrame);
		//System.out.println(diff);
	}

	public void display(){
		repaint();
	}
	public void actionPerformed(ActionEvent e) { //called every 5 ms
    }

    public void calcFrame(){
    	midFrameX = xFrame + ((int) widthFrame / 2);

    	if(character.isMoving()){
	    		if(character.isFacingRight()){
	    			System.out.print(character.isFacingRight()+" 1right "+diffX+" ");
	    			if((charXFrame > midFrameX) && (xFrame < (widthLevel - widthFrame))){//--->
			    		diffX = charXFrame - midFrameX;
			    		xFrame += diffX;
			    		character.updateCollision(-1*diffX, 0);
			    		System.out.print("2right "+diffX+" = "+charXFrame +" - "+midFrameX+" ");
		    		}
	    		}
	    		else{//(!character.isFacingRight()){
	    			System.out.print(character.isFacingRight()+"1left "+diffX);
	    			if((charXFrame < midFrameX) && (xFrame> 0)){//<-----
			    		diffX = midFrameX - charXFrame;
			    		xFrame -= diffX;
			    		character.updateCollision(diffX, 0);
			    		System.out.print(character.isFacingRight()+"2left "+diffX);
			    	}
	    		}
    		}

	    	charXFrame = character.getX() - xFrame;
	    	System.out.println(charXFrame+" = "+character.getX()+" - "+xFrame);

    }


	public void paint(Graphics g) { //using Graphics object g//try paintComponent (cant be overriden, more specific)
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g; //creates Graphics2D
		sectImage = background.getSubimage(xFrame, yFrame, widthFrame, heightFrame);
		g2d.drawImage(sectImage, 0, 0, this);
		g2d.drawImage(character.getImage(), charXFrame, character.getY(), this); //(image, x, y, ?) ||---
		Toolkit.getDefaultToolkit().sync(); //||-----------
		g.dispose(); //Component resources destroyed and out of memory ||----
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
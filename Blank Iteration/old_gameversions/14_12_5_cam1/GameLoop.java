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
	private int pastXo, pastYo, pastMidXo, pastMidYo, factorX, factorY, speed = 2, diff, charXFrame, charYFrame;
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
		pastXo = 0;
		pastYo = 0;//400;
		charXFrame = 0;
		charYFrame = 400;
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
		System.out.println(diff+" "+pastMidYo+" "+pastMidXo+" "+xFrame +" "+ yFrame +" "+character.getX()+" "+character.getY() + " "+charXFrame +" "+charYFrame);
		//System.out.println(diff);
	}

	public void display(){
		repaint();
	}
	public void actionPerformed(ActionEvent e) { //called every 5 ms
    }

    public void calcFrame(){
    	factorX = 0;//(int) widthFrame /4;
    	factorY = 0;//(int) heightFrame / 2;
    	midFrameX = pastMidXo + xFrame + ((int) widthFrame / 2);
    	midFrameY = pastMidYo + yFrame+ ((int) heightFrame / 2);
    	if(character.isMoving()){
	    	if((charXFrame > midFrameX +factorX) && (xFrame<(widthLevel - widthFrame))){
	    		diff = charXFrame - (midFrameX + factorX);//character.getX()
	    		xFrame+=diff;//should be with speed
	    		pastMidXo +=diff;
	    		character.updateCollision(-1*diff, 0);
	    		///charXFrame = character.getX() + pastXo;
	    		System.out.println("HEY X");
	    	}
	    	else if((charXFrame < midFrameX -factorX) && (xFrame>0)){//character.getX()
	    		diff = (midFrameX -factorX) - charXFrame;
	    		xFrame-=diff;
	    		pastMidXo-=diff;
	    		character.updateCollision(diff, 0);
	    		//charXFrame = character.getX() + pastXo;
	    		System.out.println("HEY X2");
	    	}
    	}


    	if((charYFrame > midFrameY+factorY) && (yFrame<(heightLevel-heightFrame))){//character.getY()
    		diff = charYFrame - (midFrameY+factorY);
    		yFrame += diff;
    		pastMidYo += diff;
    		character.updateCollision(0, -1*diff);
    		//charYFrame = character.getY() + pastYo;
    		System.out.println("HEY 1");
    		//System.out.println(character.groundRect());
    	}
    	else if((charYFrame < midFrameY -factorY) && (yFrame>0) &&(int)character.getVy() != 0){//character.getY()
    		diff = (midFrameY -factorY) - charYFrame;
    		yFrame -=diff;
    		pastMidYo -=diff;
    		character.updateCollision(0, diff);
    		//charYFrame = character.getY() + pastYo;
    		System.out.println("HEY 2");
    	}
    		charXFrame = character.getX();// - pastMidXo;
    		charYFrame = character.getY() - pastMidYo;
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
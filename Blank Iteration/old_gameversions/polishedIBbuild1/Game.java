//Game Loop heavily influenced by articles:
//http://obviam.net/index.php/android-gameloop-measuring-fps/
//http://www.java-gaming.org/topics/game-loops/24220/view.html
//Initial code(highly modified) from:
//http://zetcode.com/tutorials/javagamestutorial/movingsprites/

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements ActionListener {
	private final static int MAX_FPS = 30;
	private final static int MAX_FRAME_SKIPS = 5;
	private final static int    FRAME_PERIOD = 10;//every 10 ms
	private boolean isRunning = true;
	private long timeStart, timeDiff;
	private int timeSleep, framesSkipped, milliseconds, seconds, prevms;

	public Character character;
	private boolean running = false;
	private boolean paused = false;
	private BufferedImage background;

	public Game(){
		addKeyListener(new adapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		character = new Character(0,400);
		try{
			background = ImageIO.read(new File("images/background.png"));
		} catch (IOException e){}

		runGameLoop();
	}

	public void runGameLoop(){		//is this necessary?
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
				character.addMs();
			}

		}
	}

	public void update(){
		character.move(milliseconds, character.isFacingRight(), character.isGrounded());
	}

	public void display(){
		repaint();
	}
	public void actionPerformed(ActionEvent e) {

    }

	public void paint(Graphics g) { //using Graphics object g//try paintComponent (cant be overriden, more specific)
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g; //creates Graphics2D
		g2d.drawImage(background, 0, 0, this);
		g2d.drawImage(character.getImage(), character.getX(), character.getY(), this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose(); //Component resources destroyed and out of memory
	}

	private class adapter extends KeyAdapter { //recieves keyboard events //called in constructor

		public void keyPressed(KeyEvent e){
			character.keyPressed(e);
		}

		public void keyReleased(KeyEvent e){ //From character
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
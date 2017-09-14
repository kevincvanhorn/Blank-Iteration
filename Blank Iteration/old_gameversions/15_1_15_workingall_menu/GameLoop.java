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

	public Character character;
	private Camera camera;
	private boolean running = false;
	private boolean paused = false;
	//private int fps = 60;
	//private int frameCount = 0;
	private BufferedImage background;

	public GameLoop(){//CardLayout cards
		this.addComponentListener( new ComponentAdapter() {
	        @Override
	        public void componentShown( ComponentEvent e ) {
	            GameLoop.this.requestFocusInWindow();
	        }
	    });
		//setVisible(true);
		//setLayout(cards);
		setLayout(null);
		addKeyListener(new adapter());
		setFocusable(true);
		setRequestFocusEnabled(true);//
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		character = new Character(0,398);
		try{
			background = ImageIO.read(new File("images/background_rework_level.png"));
		} catch (IOException e){}

		camera = new Camera(600, 500, 0, 400, 1600);//checkdees
		//runGameLoop();
	}

	public void start(){
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
				if(milliseconds - cnt30 == 30){
					character.cnt300ms();
				}
				System.out.println(seconds+" "+milliseconds);*/
				character.addMs();
			}
			else
				System.out.println("Paused");

			//System.out.println("yo");

		}
	}

	public void update(){
		character.move(milliseconds, character.isFacingRight(), character.isGrounded());
		//if(character.isMoving())
			camera.update(character.getX(), character.getY(), character.isFacingRight());
	}

	public void display(){
		repaint();
	}
	public void actionPerformed(ActionEvent e) { //called every 5 ms

    }

	public void paint(Graphics g) { //using Graphics object g//try paintComponent (cant be overriden, more specific)
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g; //creates Graphics2D
		//g2d.drawImage(background, camera.getXcam(), camera.getYcam(), this);//
		g2d.drawImage(background.getSubimage(camera.getXcam(), camera.getYcam(), 600, 500), 0, 0, this);
		//do while on camera
		g2d.drawImage(character.getImage(), camera.getX(), camera.getY(), this); //(image, x, y, ?) ||---
		Toolkit.getDefaultToolkit().sync(); //||-----------
		g.dispose(); //Component resources destroyed and out of memory ||----
	}

	public boolean isRunning(){
		return isRunning;
	}

	public void run(){
		isRunning = true;
	}

	private class adapter extends KeyAdapter { //recieves keyboard events //called in constructor

		public void keyPressed(KeyEvent e){
			character.keyPressed(e);
			int key = e.getKeyCode();
			if(key == 80){
				paused = !paused;
			}

		}

		public void keyReleased(KeyEvent e){ //from character
			character.keyReleased(e);
		}
	}
}
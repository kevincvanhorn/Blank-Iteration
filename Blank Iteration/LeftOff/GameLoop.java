import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameLoop extends JPanel implements ActionListener {
	private final static int MAX_FPS = 30, MAX_FRAME_SKIPS = 5, FRAME_PERIOD = 10;;
	private boolean isRunning = true, paused = false;
	private long timeStart, timeDiff;
	private int timeSleep, framesSkipped, milliseconds, seconds, prevms, windowWidth, windowHeight, levelWidth;
	private Character character;
	private Camera camera;
	private BufferedImage background;
	private ArrayList<Rectangle> platList;
	private ArrayList<BufferedImage> spriteList;
	private SpriteSheet spriteSheetChar;

	//Constructor
	public GameLoop(int screenWidth, int screenHeight){
		//Get Window Focus
		this.addComponentListener( new ComponentAdapter() {
	        @Override
	        public void componentShown( ComponentEvent e ) {
	            GameLoop.this.requestFocusInWindow();
	        }
	    });
	    //Set GUI
		setLayout(null);
		setFocusable(true);
		setRequestFocusEnabled(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		addKeyListener(new adapter());
		try{
			background = ImageIO.read(new File("images/level1_X2_4_smaller.png"));//background_rework_level_buffer.png
		} catch (IOException e){}

		platList = new ArrayList<Rectangle>();
		makeSpriteSheets();
		makePlatList();
		//Add Entities
		windowHeight = screenHeight;
		windowWidth = screenWidth;
		levelWidth = background.getWidth();
		camera = new Camera(windowWidth, windowHeight, 0, 950, levelWidth, windowWidth);
		character = new Character(0,950, platList, spriteSheetChar);//WORKING//spriteSheetChar.getList()
	}

	//Creates Platlist
	public void makePlatList(){
		platList.add(new Rectangle(0, 1000, 2763, 100));
		platList.add(new Rectangle(1052, 800, 52, 200));
		platList.add(new Rectangle(1052, 140, 52, 513));
		platList.add(new Rectangle(0, 97, 1216, 44));
		platList.add(new Rectangle(1399, 97, 693, 44));
		platList.add(new Rectangle(1104, 451, 101, 10));
		platList.add(new Rectangle(1222, 244, 101, 10));
		platList.add(new Rectangle(1409, 455, 52, 25));
		/*platList.add(new Rectangle(300, 373, 200, 25));
		platList.add(new Rectangle(0, 449, 841, 52));
		platList.add(new Rectangle(523, 268, 30, 25));
		platList.add(new Rectangle(412, 188, 30, 25));
		platList.add(new Rectangle(273, 177, 30, 25));
		platList.add(new Rectangle(138, 148, 30, 25));
		platList.add(new Rectangle(9, 62, 78, 25));
		platList.add(new Rectangle(0, 1048, 1600, 27));
		platList.add(new Rectangle(-10, 0, 10, 1200));
		platList.add(new Rectangle(1600, 0, 10, 1200));*/
	}

	//Creates Sprites
	public void makeSpriteSheets(){//WORKING
		spriteSheetChar = new SpriteSheet(50, 50, "images/spritesheet_robo.png");//spritesheet_robo
	}

	//Creates a Thread to run the loop
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

	//Starts Thread
	public void start(){
		runGameLoop();
	}

	//Main Loop
	private void gameLoop(){
		while(isRunning){
			if(!paused){
				timeStart = System.currentTimeMillis();
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
			else
				System.out.println("Paused");
		}
	}

	//Updates Character and Camera
	public void update(){
		character.move(milliseconds, character.isFacingRight(), character.isGrounded());
		camera.update(character.getX(), character.getY(), character.isFacingRight());
	}

	//Updates Display
	public void display(){
		repaint();
	}

	//For Keyboard input
	public void actionPerformed(ActionEvent e) {
    }

	//Paints to Graphics
    public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(background.getSubimage(camera.getXcam()+6, camera.getYcam()+6, windowWidth, windowHeight), 0, 0, this);//+6 +6
		g2d.drawImage(character.getImage(), camera.getX(), camera.getY(), this); //(image, x, y, ?) ||---
		//g2d.drawImage(mob.getImage(), mob.getX()-camera.getXcam(), mob.getY() - camera.getYcam(), this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	//Running
	public boolean isRunning(){
		return isRunning;
	}
	public void run(){
		isRunning = true;
	}

	//Recieves keyboard events
	private class adapter extends KeyAdapter {

		public void keyPressed(KeyEvent e){
			character.keyPressed(e);
			int key = e.getKeyCode();
			if(key == 80){
				paused = !paused;
			}

		}
		public void keyReleased(KeyEvent e){
			character.keyReleased(e);
		}
	}
}
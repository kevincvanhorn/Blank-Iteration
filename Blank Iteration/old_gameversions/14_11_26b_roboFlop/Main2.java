import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;

import java.awt.Color;
import java.awt.Graphics; //basic rendering information
import java.awt.Graphics2D; //subclass of Graphics
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;//
import java.awt.event.KeyEvent;//

import javax.swing.JPanel;//
import javax.swing.Timer;
import java.util.TimerTask;
import java.lang.Long;

public class Main extends JPanel implements ActionListener {

	private int cnt = 0;
	private Timer timer;
	private TimerTask timerTask;
	private Character character;
	private BufferedImage background;

	public Main(){
		addKeyListener(new adapter());
		setFocusable(true); //for Listener focus
		setBackground(Color.BLACK);

		try{
			background = ImageIO.read(new File("background.png"));
		} catch (IOException e){}

		setDoubleBuffered(true); //used for painting images

		character = new Character(0,400);
		//Timer timer = new Timer();
		timer.schedule(timerTask, Long.valueOf(0), Long.valueOf(2));//every 2 ms 0 sec delay
		timer.start();
	}

	public void run(){
		character.move(character.isFacingRight(), character.isGrounded());
		if(cnt == 15){
			repaint();
			cnt = 0;
		}
		cnt++;
	}

	public void paint(Graphics g) { //using Graphics object g//try paintComponent (cant be overriden, more specific)
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g; //creates Graphics2D
		g2d.drawImage(background, 0, 0, this);
		g2d.drawImage(character.getImage(), character.getX(), character.getY(), this); //(image, x, y, ?) ||---
		Toolkit.getDefaultToolkit().sync(); //||-----------
		g.dispose(); //Component resources destroyed and out of memory ||----
	}

	public void actionPerformed(ActionEvent e) {
		character.move(character.isFacingRight(), character.isGrounded()); //forced retention of boolean
		repaint();
	}

	private class adapter extends KeyAdapter { //recieves keyboard events //called in constructor

		public void keyPressed(KeyEvent e){
			character.keyPressed(e);
		}

		public void keyReleased(KeyEvent e){ //from character
			character.keyReleased(e);
		}
	}
}
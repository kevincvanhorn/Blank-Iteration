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

public class Main extends JPanel implements ActionListener {

	private Character character;
	private BufferedImage background;

	public Main(){
		addKeyListener(new adapter());
		setFocusable(true); //for Listener focus
		setBackground(Color.BLACK);

		try{
			background = ImageIO.read(new File("images/background.png"));
		} catch (IOException e){}

		setDoubleBuffered(true); //used for painting images

		character = new Character(0,400);
		Timer timerPaint = new Timer(8, this); //every 8 ms (delay of 8ms)
		timerPaint.start();
	}

	public void paint(Graphics g) { //using Graphics object g//try paintComponent (cant be overriden, more specific)
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g; //creates Graphics2D
		g2d.drawImage(background, 0, 0, this);
		g2d.drawImage(character.getImage(), character.getX(), character.getY(), this); //(image, x, y, ?) ||---
		Toolkit.getDefaultToolkit().sync(); //||-----------
		g.dispose(); //Component resources destroyed and out of memory ||----
	}

	public void actionPerformed(ActionEvent e) { //called every timer tick
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
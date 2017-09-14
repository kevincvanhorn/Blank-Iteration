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

	private Timer timer;
	private Character character;

	public Main(){
		addKeyListener(new adapter());
		setFocusable(true); //for Listener focus
		setBackground(Color.BLACK);
		setDoubleBuffered(true); //used for painting images

		character = new Character(0,400); //new character at x, y ||off(should be in bot corner)----------
		Timer timer = new Timer(8, this); //every 8 ms
		timer.start();
	}

	public void paint(Graphics g) { //using Graphics object g//try paintComponent (cant be overriden, more specific)
		super.paint(g);

		Graphics2D g2d = (Graphics2D)g; //creates Graphics2D
		g2d.drawImage(character.getImage(), character.getX(), character.getY(), this); //(image, x, y, ?) ||---
		Toolkit.getDefaultToolkit().sync(); //||-----------
		g.dispose(); //Component resources destroyed and out of memory ||----
	}

	public void actionPerformed(ActionEvent e) { //called every timer tick
		character.move(character.isFacingRight()); //forced retention of boolean
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
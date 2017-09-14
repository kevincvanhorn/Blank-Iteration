import javax.swing.JFrame;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;

public class MenuCard extends JPanel implements ActionListener{
	private JButton startButton;
	private CardData menuData;
	//private JPanel cardPanel;
	private CardLayout cards;
	private JFrame frame;
	private BufferedImage background;//make imageicon

	public MenuCard(JFrame frame){
		//setFocusable(true);
		//setBackground(Color.BLACK);
		//setVisible(true);
		try{
			background = ImageIO.read(new File("images/Menu.png"));//new File(imageString));
		} catch (IOException e){
			System.out.println(e);
				}
		this.frame = frame;


		//add(background);

		startButton = new JButton("Start");
		add(startButton);
		//startButton.setLocation(200, 400);//217, 380
		//startButton.setSize(100, 25);//150x49
		setLayout(null);
		//startButton.setVisible(false);
		startButton.setOpaque(false);
		startButton.setContentAreaFilled(false);
		//startButton.setBorderPainted(false);
		startButton.setText(null);

		startButton.setBounds(217, 380, 150, 49);
		add(startButton, BorderLayout.PAGE_START);//getContentPane(). //BorderLayout.CENTER
		startButton.addActionListener(this);

		//this.frame = frame;
	}

	public void paintComponent(Graphics g)
		  {
		    g.drawImage(background, 0, 0, null);
		    repaint();
		  }

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == startButton){
			//removeAll();
			//add(new GameLoop());
			//remove(startButton);
			//setVisible(false);
			//System.out.println("hey");
			//menuData.setString("GameCard");//make this
			((Run)frame).flip("GameCard");
			//cards.show(cardPanel, "GameCard");
		}
	}

}
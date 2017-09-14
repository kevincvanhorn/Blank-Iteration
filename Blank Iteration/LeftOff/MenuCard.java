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
	private CardLayout cards;
	private JFrame frame;
	private BufferedImage background;

	public MenuCard(JFrame frame){
		try{
			background = ImageIO.read(new File("images/Menu.png"));
		} catch (IOException e){
			System.out.println(e);
				}
		this.frame = frame;

		startButton = new JButton("Start");
		add(startButton);
		setLayout(null);
		startButton.setOpaque(false);
		startButton.setContentAreaFilled(false);
		startButton.setText(null);

		startButton.setBounds(217, 380, 150, 49);
		add(startButton, BorderLayout.PAGE_START);
		startButton.addActionListener(this);
	}

	public void paintComponent(Graphics g)
		  {
		    g.drawImage(background, 0, 0, null);
		    repaint();
		  }

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == startButton){
			((Run)frame).flip("GameCard");
		}
	}

}
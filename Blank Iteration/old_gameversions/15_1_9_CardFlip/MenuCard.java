import javax.swing.JFrame;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuCard extends JPanel implements ActionListener{
	private JButton startButton;
	private CardData menuData;
	private JPanel cardPanel;
	private CardLayout cards;
	private JFrame frame;

	public MenuCard(JFrame frame){
		//setFocusable(true);
		//setBackground(Color.BLACK);
		//setVisible(true);

		startButton = new JButton("Start");
		startButton.setLocation(200, 400);
		startButton.setSize(100, 25);
		add(startButton);//getContentPane().
		startButton.addActionListener(this);

		this.frame = frame;
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
import javax.swing.JFrame;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Run extends JFrame {
	private CardLayout cards;
	private JPanel cardPanel;
	private MenuCard menuCard;
	private GameLoop gameCard;
	private CardData cardData;

	public Run(){
		//setLayout(new CardLayout());
		//add(new Main());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null); //centers window
		setTitle("Run");
		setResizable(false);
		setVisible(true);

		cardData = new CardData();
		cards = new CardLayout();
		menuCard = new MenuCard(this);
		gameCard = new GameLoop();//cards);
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);

		JPanel card1 = menuCard;
		JPanel card2 = gameCard;

		cards.addLayoutComponent(card1, "MenuCard");
		cards.addLayoutComponent(card2, "GameCard");
		cardPanel.add(card1);
		cardPanel.add(card2);
		//MenuCard
		add(cardPanel);
		cards.show(cardPanel, "MenuCard");
		//cards.show(cardPanel, "GameCard");
	}

	public void flip(String str){
		if(str.equals("GameCard")){
			//gameCard.requestFocus();
			gameCard.start();
			//gameCard.requestFocusInWindow();
			//gameCard.grabFocus();
		}
		cards.show(cardPanel, str);//
	}
	public static void main(String[] args) {
		new Run();
	}
}
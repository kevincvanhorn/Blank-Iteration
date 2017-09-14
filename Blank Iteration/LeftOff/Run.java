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
	private int screenWidth;
	private int screenHeight;
	private int screenResolution;


	public Run(){
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		screenWidth = toolkit.getScreenSize().width;
		screenHeight = toolkit.getScreenSize().height;
		screenResolution = toolkit.getScreenResolution();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screenWidth, screenHeight);//1000 //1200,800
		setLocationRelativeTo(null);
		setTitle("Run");
		setResizable(false);
		setVisible(true);

		cards = new CardLayout();
		menuCard = new MenuCard(this);
		gameCard = new GameLoop(screenWidth, screenHeight);
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);

		JPanel card1 = menuCard;
		JPanel card2 = gameCard;

		cards.addLayoutComponent(card1, "MenuCard");
		cards.addLayoutComponent(card2, "GameCard");
		cardPanel.add(card1);
		cardPanel.add(card2);
		add(cardPanel);
		cards.show(cardPanel, "MenuCard");
	}

	public void flip(String str){
		if(str.equals("GameCard")){
			gameCard.start();
		}
		cards.show(cardPanel, str);
	}
	public static void main(String[] args) {
		new Run();
	}
}
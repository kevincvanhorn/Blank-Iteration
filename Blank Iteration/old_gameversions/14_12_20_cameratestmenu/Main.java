import javax.swing.JFrame;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel implements ActionListener{
	private JButton startButton;

	public Main(){
		setLayout(null);
		setFocusable(true);
		//setBackground(Color.BLACK);
		setVisible(true);

		startButton = new JButton("Start");
		startButton.setLocation(200, 400);
		startButton.setSize(100, 25);
		add(startButton);//getContentPane().
		startButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == startButton){
			removeAll();
			add(new GameLoop());
			//remove(startButton);
			//setVisible(false);
			System.out.println("hey");
		}
	}

}
import javax.swing.JFrame;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener{
	private JButton startButton;

	public Menu(){
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null); //centers window
		setTitle("testMove_1");
		setResizable(false);
		setVisible(true);
		setFocusable(true);
		setBackground(Color.BLACK);

		startButton = new JButton("Start");
		startButton.setLocation(200, 400);
		startButton.setSize(100, 25);
		getContentPane().add(startButton);//
		startButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == startButton){
			add(new GameLoop());
			System.out.println("hey");
		}
	}

	public static void main(String[] args) {
		new Menu();
	}
}
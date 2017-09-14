package testJump;

import javax.swing.JFrame;

public class Run extends JFrame {//DOESHL:FKSD:LKFJSDL:FJK

	public Run(){ //why isn't timer here?

		add(new Main()); //creates an instance of Main()

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLocationRelativeTo(null); //centers window
		setTitle("testMove_1");
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Run();
	}
}

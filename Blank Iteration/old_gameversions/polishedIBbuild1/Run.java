//Initial GUI code from:
//http://zetcode.com/tutorials/javagamestutorial/movingsprites/
import javax.swing.JFrame;

public class Run extends JFrame {

	public Run(){
		add(new Game());

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

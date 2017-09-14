import java.awt.Dimension;
import java.awt.Rectangle;

public class RectangleTest{
	private Rectangle allbounds;
	private String str;

	public RectangleTest(int ix, int iy){
		allbounds = new Rectangle(ix, iy, 50, 50);
	}

	public void setBounds(int x, int y){
		allbounds.setLocation(x,y);
	}

	//Test
	public String getString(){
		return str;
	}
	public void setString(String in){
		str = in;
	}
}
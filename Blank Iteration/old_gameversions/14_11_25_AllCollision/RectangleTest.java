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

/*//pulse bot collision
if(!pulse){
			if(y == 400)//temporary before overhaul
			grounded = true;
			else if(collision.isTouchingBot()){
				grounded = true;
				jump.zeroAll();
			}
			else
				grounded = false;

			if(grounded){
				x+= ix;
			}
		}
		else{//pulse
			jump.calculatePosition();
			x = jump.getXp();
			y = -1*jump.getYp();
			jump.addTime();

			if(grounded){
				pulse = false;
				jump.zeroAll();
				if(up == false)
					y -= 2;
			}

			if(collision.isTouchingBot())
				grounded = true;
			else if(y == 400)
				grounded = true;
		}
*/
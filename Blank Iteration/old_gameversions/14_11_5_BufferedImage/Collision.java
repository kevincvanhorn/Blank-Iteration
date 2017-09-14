//http://www.edu4java.com/en/game/game6.html
public interface Collision{
	private boolean isCollidable = true;
	private int x, y, length, width;

	public boolean Collision(){
		return object.getBounds().intersects(getBounds())
	}

	public Rectangle getBounds(){
		return new Rectangle(x, y, length, width)
	}


}
import java.lang.Math;

public class Physics{
	private int angle = 0, Yo, X =0, Y =0;
	private double Vo = 0, Vxo= 0, Vyo = 0, g = 0, V = 0,Vx = 0, Vy = 0, t = 0, Xo = 0;
	private double dx, dy;
	private String component = "";

	//Constructor takes in gravity
	public Physics(double gravity){
		g = gravity;
	}

	//Calculates Position in a projectile motion by time and initial velocities
	public void calculatePosition(String str) {
		if(str.equals("static")){
			X = (int) (Xo+((g * (t*t))/2));
			Y = (int) (Yo + (Vxo*t)+ ((g * (t*t))/2));
		}
		dx = round((Xo + (Vxo * t)));
		X = (int)Math.floor(dx);

		dy = round((Yo + (Vyo * t) + ((g * (t*t))/2)));
		Y = (int)Math.floor(dy);
		Vy = Vyo + (g*t);
	}

	//Reset everything
	public void zeroAll() {
		t = 0;
		Vo = 0;
		Vy = 0;
		Vyo = 0;
		V = 0;
		X = 0;
		Y =0;
		angle = 0;
	}

	//Calculates Initial Velocites from a vertical or horizontal component of velocity
	public void calcInitialVelocities(String input){
		if(input.equals("Vxo")){
			Vo = Vxo / Math.cos(Math.toRadians(angle));
			Vyo = Vo * Math.sin(Math.toRadians(angle));
		}

		else if(input.equals("Vyo")){
			Vo = Vyo / Math.sin(Math.toRadians(angle));
			Vxo = Vo * Math.cos(Math.toRadians(angle));
		}

	}

	//increase time by a double
	public void addTime(double in) {
		t+= in;
	}

	//Rounds to certain decimals
	public double round2(double val) { //Rounds to 2 Decimals
		val = val*100;
		val = (double)((int) val);
		val = val /100;
		return val;
	}

	public double round(double val) { //Rounds to 5 Decimals
		val = val*100000;
		val = (double)((int) val);
		val = val /100000;
		return val;
	}

	//Accesor Classes
	public String printdxy(){
		return X+ " " +Y+" "+dx+" "+dy+"";
	}
	public double getAngle(){
		return angle;
	}
	public double getG(){
		return g;
	}
	public double getT(){
		return t;
	}
	public double getV(){
		return V;
	}
	public int getXp(){
		return X;
	}

	public int getYp(){
		return Y;
	}

	public double getVo(){
		return round(Vo);
	}

	public double getVxo(){
		return Vxo;
	}

	public double getVy(){
		return round(Vy);
	}
	public double getVyo(){
		return Vyo;
	}

	public String toString(){
		return ""+angle+" "+round2(Vxo) + " "+ round2(Vy) + " " + round2(Vyo);
	}

	//Mutator Classes
	public void addXo(double in){
		Xo += in;
	}
	public void setVyo(double input){
		Vyo = input;
	}
	public void setVxo(double input){
		Vxo = input;
	}
	public void setVy(double input){
		Vy = input;
	}
	public void setVx(double input){
		Vx = input;
	}

	public void setAngle(int an){
		angle = an;
	}

	public void setVo(double initialVel){
		Vo = initialVel;
	}

	public void setT(double time) {
		t = time;
	}

	public void setInitialPosition(int x, int y){
		Xo = x;
		Yo = y;
	}
}

import java.lang.Math;

public class Physics{
	private int angle = 0;
	private double Vo = 0, Vxo= 0, Vyo = 0, g = 0, V = 0,Vx = 0, Vy = 0, t = 0; //Vo = initial velocity
	private int Xo = 0, Yo, X =0, Y =0;
	private String component = "";

	public Physics(int a, double Vxinitial, double gravity){
		angle = a;
		g = gravity;
		Vxo = Vxinitial;
	}

	public void calculatePosition() { //Jump method
		calcInitialVelocities();
		X = (int) (Xo + (Vxo * t));
		Y = (int) (Yo + (Vyo * t) + ((g * (t*t))/2));
		Vy = Vyo + (g*t);
	}

	public void zeroAll() {
		t = 0;
		Vo = 0;
		Vyo = 0;
		V = 0;
		X = 0;
		Y =0;
	}

	public void calcInitialVelocities(){
		Vo = Vxo / Math.cos(Math.toRadians(angle));
		Vxo = round(Vxo);

		Vyo = Vo * Math.sin(Math.toRadians(angle));
		Vyo = round(Vyo);
	}

	public void addTime() {
		t++;
	}

	//Get Reference Classes
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

	//Set Reference Classes
	public void setVxo(double input){
		Vxo = input;
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

	//Additional
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
}
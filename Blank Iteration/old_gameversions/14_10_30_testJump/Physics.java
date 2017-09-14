package testJump;

import java.lang.Math; //name x -> X

		//x = (int) (Vxo * t);
		//y = (int) ((Vyo * t) + (g * (t*t))/2);
		//v = Vo + (g*t);

public class Physics{
	private int angle = 0;
	private double Vo = 0, Vxo= 0, Vyo = 0, g = 0, V = 0,Vx = 0, Vy = 0, t = 0; //Vo = initial velocity
	private int Xo = 0, Yo, x =0, y =0;
	private String component = "";

	public Physics(int a, double Vxinitial, double gravity){
		angle = a;
		g = gravity;
		Vxo = Vxinitial;
	}


	public void calculatePosition() { //Jump method
		calcInitialVelocities();
		//Vx = Vxo;
		//Vy = Vyo;
		x = (int) (Xo + (Vxo * t));
		y = (int) (Yo + (Vyo * t) + ((g * (t*t))/2));
		//V = Math.sqrt(Math.pow(Vo,2)+(2*g*(x - Xo)));
		//Vy = Math.sqrt(Math.pow(Vyo,2)+(2*g*(y - Yo)));
		Vy = Vyo + (g*t);
		/*if(V < -Vo)
			zeroAll();*/
	}

	public void zeroAll() {
		t = 0;
		Vo = 0;
		Vyo = 0;
		V = 0;
		x = 0;
		y =0;
	}

	public void calcInitialVelocities(){
		//Vxo = (double)Vo * Math.cos(angle);
		//Vxo = 35;
		Vo = Vxo / Math.cos(Math.toRadians(angle));
		Vxo = round(Vxo);

		Vyo = Vo * Math.sin(Math.toRadians(angle));//(double)
		Vyo = round(Vyo);
	}

	public void addTime() {
		//t += .008;
		t++;
	}

	/*public boolean isMaxTime() {
		if(V == Vo){
			V = 0;
			return true;
		}

		else
			return false;
	}*/

	/*public boolean velocityIsZero() {
		if(V ==0)
			return true;
		else
			return false;
	}*/


	/*public double getInitialVelocities(String component){
		if(component.equals("Vox"))
			return Vox;
		else
			return Voy;
	}*/

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
		return x;
	}

	public int getYp(){
		return y;
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
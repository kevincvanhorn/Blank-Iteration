
import java.lang.Math;

public class Physics{
	private int angle = 0;
	private double Vo = 0, Vxo= 0, Vyo = 0, g = 0, V = 0,Vx = 0, Vy = 0, t = 0; //Vo = initial velocity
	private int Xo = 0, Yo, X =0, Y =0;
	private String component = "";
	private double dx, dy;

	public Physics(double gravity){ //int a, double Vxinitial,
		//angle = a;
		g = gravity;
		//Vxo = Vxinitial;
	}

	public void calculatePosition(String str) { //Jump method
		//calcInitialVelocities("Vxo");
		if(str.equals("static")){
			X = (int) (Xo+((g * (t*t))/2));
			Y = (int) (Yo + (Vxo*t)+ ((g * (t*t))/2));
		}
		//X = (int) (Xo + (Vxo * t));
		dx = round((Xo + (Vxo * t)));
		X = (int)Math.floor(dx);
		//Y = (int) (Yo + (Vyo * t) + ((g * (t*t))/2));
		dy = round((Yo + (Vyo * t) + ((g * (t*t))/2)));
		Y = (int)Math.floor(dy);
		Vy = Vyo + (g*t);
	}

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

	public void calcInitialVelocities(String input){

		if(input.equals("Vxo")){
			Vo = Vxo / Math.cos(Math.toRadians(angle));
			//Vxo = round(Vxo);

			Vyo = Vo * Math.sin(Math.toRadians(angle));
			//Vyo = round(Vyo);
		}

		else if(input.equals("Vyo")){
			Vo = Vyo / Math.sin(Math.toRadians(angle));
			//Vyo = round(Vyo);

			Vxo = Vo * Math.cos(Math.toRadians(angle));
			//Vxo = round(Vxo);
		}

	}

	public void addTime(double in) {
		t+= in;
	}

	/*public int roundUp(double in){//553.8 //so inefficient, use split
		String str = Double.toString(in);
		String sNum="", sNext="", sNew ="", sPrev ="";
		for(int i=0; i<str.length();i++){
			sNum = str.substring(i,i+1);
			sPrev = str.substring(i-1,i);

			if(sNum.equals(".")){
				sNext = str.substring(i+1,i+2);
				if(Integer.parseInt(sNext) >= 5){
					sNum = sNext;
				}
			}

			sNew += sNum;
		}

		return (int)Double.parseDouble(sNew);
	}*/

	//Get Reference Classes
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
		//return ""+X+" "+Y+" "+round2(Vy);// +" "+dx+" "+dy;
	}

	//Set Reference Classes
	public void setVyo(double input){
		Vyo = input;
	}
	public void setVxo(double input){
		Vxo = input;
	}
	public void setVy(double input){//not supposed to be there
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
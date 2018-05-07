package simulator;

public class Robot {
	private Actuator a, b, c;
	private Sensor s1, s2, s3, s4;
	public MotorR motorRight;
	public MotorL motorLeft;
	private String motorR, motorL;
	
	public LightR lightRight;
	public LightL lightLeft;
	private String lightR, lightL;
	
	
	public boolean isOn;
	
	public Main main;
	
	public Robot(Main main){
		this.main = main;
		a = b = c = null;
		s1 = s2 = s3 = s4 = null;
		motorRight = null;
		motorLeft = null;
		motorR = motorL = null;
		
		lightRight = null;
		lightLeft = null;
		lightR = lightL = null;
		
		isOn = false;
	}
	
	
	/*public Robot(Actuator a, Actuator b, Actuator c, Sensor s1, Sensor s2, Sensor s3, Sensor s4){
		this.a = a;
		this.b = b;
		this.c = c;
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.s4 = s4;
	}*/
	
	public Actuator getActuator(char character){
		if(character=='a'){
			return a;
		}else if(character=='b'){
			return b;
		}else if(character=='c'){
			return c;
		}else{
			return null;
		}
	}
	
	public Sensor getSensor(char character){
		if(character=='1'){
			return s1;
		}else if(character=='2'){
			return s2;
		}else if(character=='3'){
			return s3;
		}else if(character=='4'){
			return s4;
		}else{
			return null;
		}
	}
	
	
	public void update(){
		if(motorRight.isOn() || motorLeft.isOn()){
			isOn = true;
		}
		
	}
	
	private void setMotorRL(String ABC, Actuator a){
		if(a.getType().equals("MotorRight")){
			motorRight = (MotorR) a;
			motorR = ABC;
		}else if(a.getType().equals("MotorLeft")){
			motorLeft = (MotorL) a;
			motorL = ABC;
		}
	}
	
	private void setLightRL(String sensor1234, Sensor s){
		if(s.getType().equals("LightR")){
			lightRight = (LightR) s;
			lightR = sensor1234;
		}else if(s.getType().equals("LightL")){
			lightLeft = (LightL) s;
			lightL = sensor1234;
		}
	}
	
	
	
	public void setMotor(char character, int snelheid){
		/*if(character==null || character!='A' || character!='B' || character!='C'){
			throw new Error("Robot:\tsetMotor()");
		}
		*/
		if(character == 'A'){
			if(a.getType().equals("MotorLeft")){
				((MotorL) a).setSpeed(snelheid);
				((MotorL) a).open();
			}else if(a.getType().equals("MotorRight")){
				((MotorR) a).setSpeed(snelheid);
				((MotorR) a).open();
			}else{
				System.out.println("Robot:\t"+character+" is not from type MotorLeft/MotorRight");
			}
			
		}else if(character == 'B'){
			if(b.getType().equals("MotorLeft")){
				((MotorL) b).setSpeed(snelheid);
				((MotorL) b).open();
			}else if(b.getType().equals("MotorRight")){
				((MotorR) b).setSpeed(snelheid);
				((MotorR) b).open();
			}else{
				System.out.println("Robot:\t"+character+" is not from type MotorLeft/MotorRight");
			}
			
			
		}else if(character == 'C'){
			if(c.getType().equals("MotorLeft")){
				((MotorL) c).setSpeed(snelheid);
				((MotorL) c).open();
			}else if(c.getType().equals("MotorRight")){
				((MotorR) c).setSpeed(snelheid);
				((MotorR) c).open();
			}else{
				System.out.println("Robot:\t"+character+" is not from type MotorLeft/MotorRight");
			}
			
			
		}else{
			System.out.println("Robot:\tThere is no sensor " + character);
		}
		
		
		update();
	}
	
	
	public void stopMotor(char character){
		if(character == 'A'){
			if(a.getType().equals("MotorLeft")){
				MotorL temp = (MotorL) a;
				temp.close();
			}else if(a.getType().equals("MotorRight")){
				MotorR temp = (MotorR) a;
				temp.close();
			}else{
				System.out.println("Robot:\t"+character+" is not from type MotorLeft/MotorRight");
			}
			
		}else if(character == 'B'){
			if(b.getType().equals("MotorLeft")){
				MotorL temp = (MotorL) b;
				temp.close();
			}else if(b.getType().equals("MotorRight")){
				MotorR temp = (MotorR) b;
				temp.close();
			}else{
				System.out.println("Robot:\t"+character+" is not from type MotorLeft/MotorRight");
			}
			
			
		}else if(character == 'C'){
			if(c.getType().equals("MotorLeft")){
				MotorL temp = (MotorL) c;
				temp.close();
			}else if(c.getType().equals("MotorRight")){
				MotorR temp = (MotorR) c;
				temp.close();
			}else{
				System.out.println("Robot:\t"+character+" is not from type MotorLeft/MotorRight");
			}
			
			
		}else{
			System.out.println("Robot:\tThere is no sensor " + character);
		}
		
		
		update();
	}
	
	
	
	
	
	public MotorR getMotorRight(){
		return motorRight;
	}
	
	public MotorL getMotorLeft(){
		return motorLeft;
	}
	
	public LightR getLightRight(){
		return lightRight;
	}
	
	public LightL getLightLeft(){
		return lightLeft;
	}
	
	
	public void setA(Actuator a){
		if(a!=null){
			setMotorRL("A", a);
		}
		
		this.a = a;
	}
	
	public Actuator getA(){
		return a;
	}
	
	public void setB(Actuator b){
		if(b!=null){
			setMotorRL("B", b);
		}
		this.b = b;
	}
	
	public Actuator getB(){
		return b;
	}
	
	public void setC(Actuator c){
		if(c!=null){
			setMotorRL("C", c);
		}
		this.c = c;
	}
	
	public Actuator getC(){
		return c;
	}
	
	public void set1(Sensor s){
		if(s!=null){
			setLightRL("1", s);
		}
		this.s1 = s;
	}
	
	public Sensor get1(){
		return s1;
	}
	
	public void set2(Sensor s){
		if(s!=null){
			setLightRL("2", s);
		}
		this.s2 = s;
	}
	
	public Sensor get2(){
		return s2;
	}
	
	public void set3(Sensor s){
		if(s!=null){
			setLightRL("3", s);
		}
		this.s3 = s;
	}
	
	public Sensor get3(){
		return s3;
	}
	
	public void set4(Sensor s){
		if(s!=null){
			setLightRL("4", s);
		}
		this.s4 = s;
	}
	
	public Sensor get4(){
		return s4;
	}
	
	
	private String getTypeVerbeterd(String type){
		if(type.equals("MotorLeft")){
			return "motor (left)";
		}else if(type.equals("MotorRight")){
			return "motor (right)";
		}else if(type.equals("LED")){
			return "LED";
		}else if(type.equals("LightL")){
			return "light sensor (left)";
		}else if(type.equals("LightR")){
			return "light sensor (right)";
		}else if(type.equals("Ultrasonic")){
			return "ultrasonic sensor";
		}else{
			return "unknown";
		}
		
	}
	
	
	public String toString(){
		String temp = "";
		
		//A, B en C
		if(a!=null){
			temp += "Port A is connected with " + getTypeVerbeterd(a.getType()) + "\n";
		}
		if(b!=null){
			temp += "Port B is connected with " + getTypeVerbeterd(b.getType()) + "\n";
		}
		if(c!=null){
			temp += "Port C is connected with " + getTypeVerbeterd(c.getType()) + "\n";
		}
		
		//1, 2, 3 en 4
		if(s1!=null){
			temp += "Port 1 is connected with " + getTypeVerbeterd(s1.getType()) + "\n";
		}
		if(s2!=null){
			temp += "Port 2 is connected with " + getTypeVerbeterd(s2.getType()) + "\n";
		}
		if(s3!=null){
			temp += "Port 3 is connected with " + getTypeVerbeterd(s3.getType()) + "\n";
		}
		if(s4!=null){
			temp += "Port 4 is connected with " + getTypeVerbeterd(s4.getType()) + "\n";
		}
		
		/*
		//motor right/left
		if(motorRight!=null){
			temp += "motor right is connected with " + r + "\n";
		}
		if(motorLeft!=null){
			temp += "motor left is connected with " + l + "\n";
		}*/
		
		
		
		//no connection
		if(temp.equals("")){
			temp += "None of the ports is connected.\n";
		}
		
		return temp;
	}
	
	/*
	public String toString(){
		String temp = "";
		
		if(a==null){
			temp += "A is not connected\n";
		}else{
			temp += "A is connected with " + a.getType() + "\n";
		}
		if(b==null){
			temp += "B is not connected\n";
		}else{
			temp += "B is connected with " + b.getType() + "\n";
		}
		if(c==null){
			temp += "C is not connected\n";
		}else{
			temp += "C is connected with " + c.getType() + "\n";
		}
		
		if(s1==null){
			temp += "1 is not connected\n";
		}else{
			temp += "1 is connected with " + s1.getType() + "\n";
		}
		if(s2==null){
			temp += "2 is not connected\n";
		}else{
			temp += "2 is connected with " + s2.getType() + "\n";
		}
		if(s3==null){
			temp += "3 is not connected\n";
		}else{
			temp += "3 is connected with " + s3.getType() + "\n";
		}
		if(s4==null){
			temp += "4 is not connected\n";
		}else{
			temp += "4 is connected with " + s4.getType() + "\n";
		}
		
		return temp;
	}
	*/
	
	
	public void print(){
		if(a==null){
			System.out.println("A\tnull");
		}else{
			System.out.println("A\t"+a.getType());
		}
		if(b==null){
			System.out.println("B\tnull");
		}else{
			System.out.println("B\t"+b.getType());
		}
		if(c==null){
			System.out.println("C\tnull");
		}else{
			System.out.println("C\t"+c.getType());
		}
		
		if(s1==null){
			System.out.println("1\tnull");
		}else{
			System.out.println("1\t"+s1.getType());
		}
		if(s2==null){
			System.out.println("2\tnull");
		}else{
			System.out.println("2\t"+s2.getType());
		}
		if(s3==null){
			System.out.println("3\tnull");
		}else{
			System.out.println("3\t"+s3.getType());
		}
		if(s4==null){
			System.out.println("4\tnull");
		}else{
			System.out.println("4\t"+s4.getType());
		}
		
		
	}
	
	
	public void open(String abc1234){
		if(abc1234.equals("A")){
			a.open();
		}else if(abc1234.equals("B")){
			b.open();
		}else if(abc1234.equals("C")){
			c.open();
		}else if(abc1234.equals("1")){
			s1.open();
		}else if(abc1234.equals("2")){
			s2.open();
		}else if(abc1234.equals("3")){
			s3.open();
		}else if(abc1234.equals("4")){
			s4.open();
		}else{
			System.out.println("Robot:\tError: Cannot open actuator/sensor '"+abc1234+"'");
		}
	}
	
	
	public void close(String abc1234){
		if(abc1234.equals("A")){
			a.close();
		}else if(abc1234.equals("B")){
			b.close();
		}else if(abc1234.equals("C")){
			c.close();
		}else if(abc1234.equals("1")){
			s1.close();
		}else if(abc1234.equals("2")){
			s2.close();
		}else if(abc1234.equals("3")){
			s3.close();
		}else if(abc1234.equals("4")){
			s4.close();
		}else{
			System.out.println("Robot:\tError: Cannot close actuator/sensor '"+abc1234+"'");
		}
	}
	
	public String getDigitsLED(){
		String digits = "";
		
		
		if(a!=null && a.getType().equals("LED") && a.isOn()){
			digits += "1";
		}else{
			digits += "0";
		}
		
		
		if(b!=null && b.getType().equals("LED") && b.isOn()){
			digits += "1";
		}else{
			digits += "0";
		}
		
		
		if(c!=null && c.getType().equals("LED") && c.isOn()){
			digits += "1";
		}else{
			digits += "0";
		}
		
		
		return digits;
	}
	
	public boolean hasLightL(){
		return lightLeft != null;
	}
	
	public boolean hasLightR(){
		return lightRight != null;
	}
	
	public boolean hasUltra(){
		if(s1!=null && s1.getType().equals("Ultrasonic")){
			return true;
		}
		if(s2!=null && s2.getType().equals("Ultrasonic")){
			return true;
		}
		if(s3!=null && s3.getType().equals("Ultrasonic")){
			return true;
		}
		if(s4!=null && s4.getType().equals("Ultrasonic")){
			return true;
		}
		return false;
	}
}

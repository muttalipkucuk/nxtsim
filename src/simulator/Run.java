package simulator;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Run {
	
	public Variabele v;
	
	public Main main;
	
	public int valueUltra;
	
	public int valueLightL;
	public int valueLightR;
	
	public final int TAB = 4;
	
	public boolean stopRun;
	
	public LoopSet loopSet;
	
	public double pause;
	public int pauseLine, pauseEnd;
	public boolean pauseFound;
	
	public Vector vector;
	
	public int regelRunning;
	
	public boolean breakFound;
	public int breakLine;
	
	
	public Run(Main main){
		this.main = main;
		
		vector = new Vector();
		
		loopSet = new LoopSet(main.simulator.code.getSize());
		
		stopRun = false;
		
		v = new Variabele();
		
		valueUltra = main.simulator.graphicsPanel.gd.ultraDistance;
		valueLightL = main.simulator.graphicsPanel.gd.lightL;
		valueLightR = main.simulator.graphicsPanel.gd.lightR;
		
		pause = 0.0;
		pauseLine = -1;
		pauseEnd = -1;
		pauseFound = false;
		
		regelRunning = -1;
		
		breakFound = false;
		breakLine = -1;
		
		runMATLAB();
		
	}
	
	public void runMATLAB(){

		read(0, this.main.simulator.code.getSize()-1);
	}
	
	/*
	private boolean hasMessage(){
		return !message.equals("");
	}
	
	
	private void verifyBegin(){
		
		String line1 = Simulator.code.get(0);
		String line2 = Simulator.code.get(1);
		String line3 = Simulator.code.get(2);
		String line4 = Simulator.code.get(3);
		
		
		String error;
		
		if(!line1.contains("COM_CloseNXT")){
			error = "Line 1 should be: \tCOM_CloseNXT('all')";
			System.out.println("Run:\t" + error);
			message += error + "\n";
			this.main.simulator.print(message);
			return;
			
		}
		
		if(!line2.contains("close all")){
			error = "Line 2 should be: \tclose all";
			System.out.println("Run:\t" + error);
			message += error + "\n";
			this.main.simulator.print(message);
			return;
			
		}
		
		if(!line3.contains("COM_OpenNXT()")){
			error = "Line 3 should be: \th = COM_OpenNXT()";
			System.out.println("Run:\t" + error);
			message += error + "\n";
			this.main.simulator.print(message);
			return;
			
		}
		if(!line4.contains("COM_SetDefaultNXT(")){
			error = "Line 4 should be: \tCOM_SetDefaultNXT(h)";
			System.out.println("Run:\t" + error);
			message += error + "\n";
			this.main.simulator.print(message);
			return;
			
		}
		
	}
	
	private void verifyEnd(){
		
		String lineLast = this.main.simulator.code.get(this.main.simulator.code.getSize()-1);
		
		String error;
		
		if(!lineLast.contains("COM_CLoseNXT(")){
			error = "Line "+(this.main.simulator.code.getSize())+" should be: \tCOM_CLoseNXT(COM_GetDefaultNXT())";
			System.out.println("Run:\t" + error);
			message += error + "\n";
			this.main.simulator.print(message);
			return;
			
		}
		
		
	}
	
	*/
	
	public void read(int start, int end){
		System.out.println("read("+start+","+end+")");
		
		for(int i = start; i <= end; i++){
			String lineI = this.main.simulator.code.get(i);
			main.simulator.outputPanel.setMidden("Running line "+i+": "+lineI+"\n");
			
			
			//System.out.println("for(i="+i+", end="+end+", i<=end == "+(i<=end)+")");
			
			/*
			if(hasMessage()){
				System.out.println(message);
				return;
			}
			*/
			
			
			
			String usedMethod = "";
			
			//verwijder ; of % (comment)
			if(lineI.length()>0){
				if(lineI.contains(";")){
					lineI = lineI.substring(0, lineI.indexOf(";"));
				}else if(lineI.contains("%")){
					lineI = lineI.substring(0, lineI.indexOf("%"));
				}
			}
			
			//space
			if(this.main.simulator.code.isSpace(i)){
				usedMethod = "isSpace";
			
			//verify begin or end
			}else if(lineI.contains("COM_CloseNXT") || lineI.contains("close all") || lineI.contains("COM_OpenNXT()") || lineI.contains("COM_SetDefaultNXT(") || lineI.contains("COM_CLoseNXT(")){
				usedMethod = "verify begin or end";
				
			
			//for	
			}else if(lineI.contains("for")){
				
				usedMethod = "for";
				int regelEnd = this.main.simulator.code.regelEnd(i+1, this.main.simulator.code.numberOfSpace(i));
				
				if(regelEnd == -1){
					main.simulator.outputPanel.print("Er is geen END voor FOR op line " + (i+1));
				}else{
					Loop loop = new Loop("for", lineI, i, regelEnd);
					boolean b = forLoop(loop);
					if(b){
						
						return;
					}else{
						//read(loop.end+1, main.simulator.code.getSize()-1);
						//i = loop.end;
						return;
					}
				}
				
			//while
			}else if(lineI.contains("while")){
				//System.out.println("#while");
				usedMethod = "while";
				int regelEnd = main.simulator.code.regelEnd(i, this.main.simulator.code.numberOfSpace(i));
				
				//System.out.println("#regelEnd = "+regelEnd);
				
				if(regelEnd == -1){
					main.simulator.outputPanel.print("Er is geen END voor WHILE op line " + i);
				}else{
					Loop loop = new Loop("while", lineI, i, regelEnd);
					//System.out.println("lineI (code)= "+lineI);
					//System.out.println("i = "+i);
					//System.out.println("regelEnd = "+regelEnd);
					
					boolean b = whileLoop(loop);
					if(b){
						return;
					}else{
						//read(loop.end+1, main.simulator.code.getSize()-1);
						//i = loop.end;
						return;
					}
				}
			//if	
			}else if(lineI.contains("if")){
				usedMethod = "if";
				System.out.println("#\tif");
				
				boolean statement = berekenBoolean(lineI);
				int volgende = main.simulator.code.regelEnd(i, main.simulator.code.numberOfSpace(i));
				int endd = main.simulator.code.regelNummerEnd(i);
				
				System.out.println("#\tstatement = "+statement);
				System.out.println("#\tvolgende = "+volgende);
				System.out.println("#\tendd = "+endd);
				
				if(statement){
					
					read(i+1, volgende-1);
					read(endd+1, end);
					return;
					//read(endd, end);
					
					//i = main.simulator.code.regelNummerEnd(i);
				}else{
					read(volgende, end);
					return;
					//read(volgende, end);
					//i=volgende-1;
					//i = main.simulator.code.numberOfSpace(i)-1;
				}
			
				//System.out.println();
			
			
			//assignment, bijv:		"m = NXTMotor('AC', 'Power', 60);" 	of	"val1 = GetLight(SENSOR_1);"
			}else if(lineI.contains("=")){
				usedMethod = "assignment";
				
				String[] delen = lineI.split("=");
				String deel1 = delen[0].trim();
				String deel2 = delen[1].trim();
				
				//mA.Power = 20;
				if(deel1.contains(".Power")){
					setPower(deel1, deel2);
				//vector
				}else if(deel1.contains("(")){
					vector(deel1, deel2);
				//variabele	
				}else{
					v.add(deel1, deel2);
				}
				
				
			//motor: sendToNXT
			}else if(lineI.contains("SendToNXT")){
				usedMethod = "sendToNXT";
				
				sendToNXT(lineI);
			
			
			//motor: stop
			}else if(lineI.contains("Stop")){
				usedMethod = "Stop";
				stop(lineI);
				
				
				
				
			/*	
			//motor: stop brake
			}else if(lineI.contains("Stop") && lineI.contains("brake")){
				usedMethod = "Stop brake";
				stopBrake(lineI);
			//motor: stop brake
			}else if(lineI.contains("Stop") && lineI.contains("off")){
				usedMethod = "Stop off";
				stopOff(lineI);
			*/	
				
			//break	
			}else if(lineI.contains("break")){
				usedMethod = "break";
				breakFound = true;
				breakLine = i;
				
				System.out.println("break.voor: i = "+i);
				i = main.simulator.code.doBreak(i);
				//i = main.simulator.code.regelNummerEnd(outerloop+1);
				System.out.println("break.na: i = "+i);
				
			
			//pause
			}else if(lineI.contains("pause")){
				usedMethod = "pause";
				pause(lineI, i, end);
				return;
				
			//SwitchLamp
			}else if(lineI.contains("SwitchLamp")){
				usedMethod = "SwitchLamp";
				switchLamp(lineI);
				
				
			//light: openLight
			}else if(lineI.contains("OpenLight")){
				usedMethod = "OpenLight";
				openLight(lineI, i);
				
			//light: getLight
			}else if(lineI.contains("GetLight")){
				usedMethod = "GetLight";
				getLight(lineI);
			}
			//light & ultrasonic: closeSensor
			else if(lineI.contains("CloseSensor")){
				usedMethod = "CloseSensor";
				closeSensor(lineI, i);
			
			//ultrasonic: openUltrasonic
			}else if(lineI.contains("OpenUltrasonic")){
				usedMethod = "OpenUltrasonic";
				openUltrasonic(lineI, i);
				
			//ultrasonic: getUltrasonic
			}else if(lineI.contains("GetUltrasonic")){
				usedMethod = "GetUltrasonic";
				getUltrasonic(lineI);
			
			
				
			//display
			}else if(lineI.contains("display")){
				usedMethod = "display";
				display(lineI);
				
			//end
			}else if(lineI.contains("end")){
				usedMethod = "end";
			
			//unknown
			}else{
				usedMethod = "unknown";
			}
			
			if(i == main.simulator.code.getSize()-1){
				System.out.println("stopRun");
				stopRun = true;
			}
			
			System.out.println("\t" + i + " >"+lineI + "\n\tmethod: "+usedMethod);
		}
		
			
	}
	
	
	public boolean isNumeric(Scanner s){
		s.useDelimiter("");
		
		while(s.hasNext()){
			
			char c = s.next().charAt(0);
			if(!Character.isDigit(c)){
				return false;
			}
		}
		
		return true;
		
	}
	
	private void vector(String deel1, String deel2){
		//deel1 = "light_vec(i)"		deel2 = "GetLight(SENSOR_2)"
		
		int openen = deel1.indexOf('(');
		int sluiten = deel1.indexOf(')');
		
		String naam = deel1.substring(0, openen);
		
		String i = deel1.substring(openen+1, sluiten).trim();
		
		vector.add(naam, getValue(i), getValue(deel2));
		
		/*
		
		Scanner scanner = new Scanner(i);
		
		if(isNumeric(scanner)){
			//i = "5"
			int index = Integer.parseInt(i);
			vector.add(naam, index, getValue(deel2));
		}else{
			//i = "i"
			int indexLoops = loopSet.get(i);
			if(indexLoops != -1){
				vector.add(naam, indexLoops, getValue(deel2));
			}else{
				int index = getValue(v.getWaarde(i));
				vector.add(naam, index, getValue(deel2));
			}
			
			
			
			
		}
		
		scanner.close();
		*/
		
	}
	
	
	private void pause(String line, int i, int j){
		//pause(5000)
		int openen = line.indexOf('(');
		int sluiten = line.lastIndexOf(')');
		String waarde = line.substring(openen+1, sluiten);
		double value = Double.parseDouble(waarde);
		//pause = 0.5
		
		pause = value;
		pauseLine = i;
		pauseEnd = j;
		pauseFound = true;
		
	}
	
	
	private void switchLamp(String line){
		//line = "SwitchLamp(Motor_A, ‘on’);
		
		int lageStreepje = line.indexOf('_');
		char c = line.charAt(lageStreepje+1);
		//c = 'A'
		System.out.println(c);
		
		if(main.init.getRobot().getActuator((char)(c+32))==null){
			return;
		}
		
		if(line.contains("on")){
			main.init.robot.open(c+"");
			
			
		}else{
			main.init.robot.close(c+"");
		}
		
		
		
	}
	
	
	private int ifStatement(int i, String line){
		boolean statement = berekenBoolean(line);
		
		if(statement){
			return i++;
		}else{
			return main.simulator.code.regelEnd(i, main.simulator.code.numberOfSpace(i));
			
		}
		
	}
	
	
	private void nxtMotor(String waarde){
		//waarde = 			-->NXTMotor('AC', 'Power', 60)
		
		Scanner waardeScanner = new Scanner(waarde.substring(8));
		waardeScanner.useDelimiter("");
		
		String letter = "";
		String naamSensoren = "";
		do{
			letter = waardeScanner.next();
			if(letter.equals("(") || letter.equals(")") || letter.equals("'") || letter.equals(" ")){
			
			}else if(letter.equals(",")){
				break;
			}else{
				naamSensoren += letter;
			}
		}while(waardeScanner.hasNext());
			
		
		
		//waardeScanner heeft nu	--> 'Power', 60)	(met spatie)	
		do{
			letter = waardeScanner.next();
		}while(!letter.equals(","));
		
		
		int snelheid = 0;
		
		//waardeScanner heeft nu	--> 60)	(met spatie)
		do{
			letter = waardeScanner.next();
			if(letter.equals(" ")){
				
			}else if(letter.equals(")")){
				break;	
			}else if(Character.isDigit(letter.charAt(0))){
				int x = Character.getNumericValue(letter.charAt(0));
				if(snelheid==0){
					snelheid=x;
				}else{
					snelheid = snelheid*10+x;
				}
			}
		}while(waardeScanner.hasNext());
		
		//System.out.println("naamSensoren >"+naamSensoren);
		//System.out.println("snelheid >"+snelheid);
		waardeScanner.close();
		
		activeerMotoren(naamSensoren, snelheid);
	
	}
	
	private void activeerMotoren(String naamSensoren, int snelheid){
		//naamSensoren -->AC		snelheid=10
		//System.out.println("activeerMotoren("+naamSensoren+","+snelheid+")");
		for(int i=0; i<naamSensoren.length(); i++){
			char c = naamSensoren.charAt(i);
			
			this.main.init.robot.setMotor(c, snelheid);
		}
		
	}
	
	public void sendToNXT(String s){
		s.trim();
		//m.SendToNXT();
		Scanner scanner = new Scanner(s);
		scanner.useDelimiter("");
		
		String naam = "";
		String letter = "";
		do{
			if(!letter.equals("") && Character.isLetter(letter.charAt(0))){
				naam += letter;	
			}
			
			letter = scanner.next();

		}while(!letter.equals("."));
		//naam = "m"
		
		scanner.close();
		
		
		//waarde = 			"NXTMotor('AC', 'Power', 60);"
		String waarde = v.getWaarde(naam);
		nxtMotor(waarde);
		
	}
	
	private void stopBrake(String line){
		line = line.trim();
		//line = "mA.Stop('brake');"	
		
		Scanner s = new Scanner(line);
		s.useDelimiter("");
		
		String character = "";
		String var = "";
		
		do{
			var+=character;
			character = s.next();
		}while(!character.equals("."));
		
		//var = "mA"
		
		s.close();
		
		//sensor = 		"NXTMotor('A', 'Power', 20);"
		String sensor = v.getWaarde(var);
		int index = sensor.indexOf("'");
		
		
		
		//scanner heeft 		"A', 'Power', 20);"
		Scanner scanner = new Scanner(sensor.substring(index+1));
		scanner.useDelimiter("");
		
		
		
		String temp = "";
		String sensoren = "";
		
		do{
			temp = scanner.next();
			if(temp.equals("A") || temp.equals("B") || temp.equals("C")){
				sensoren += temp;
			}
			
		}while(!temp.equals("'"));
		
		//sensoren = "AB"
		s.close();
		
		
		for(int i=0; i<sensoren.length(); i++){
			main.init.robot.setMotor(sensoren.charAt(i), 0);
		}
	}
	
	
	private void stopOff(String line){
		line = line.trim();
		//line = "mA.Stop('off')"	
		
		Scanner s = new Scanner(line);
		s.useDelimiter("");
		
		String character = "";
		String var = "";
		
		do{
			var+=character;
			character = s.next();
		}while(!character.equals("."));
		
		//var = "mA"
		
		s.close();
		
		//sensor = 		"NXTMotor('A', 'Power', 20);"
		String sensor = v.getWaarde(var);
		int index = sensor.indexOf("'");
		
		
		
		//scanner heeft 		"A', 'Power', 20);"
		Scanner scanner = new Scanner(sensor.substring(index+1));
		scanner.useDelimiter("");
		
		
		
		String temp = "";
		String sensoren = "";
		
		do{
			temp = scanner.next();
			if(temp.equals("A") || temp.equals("B") || temp.equals("C")){
				sensoren += temp;
			}
			
		}while(!temp.equals("'"));
		
		//sensoren = "AB"
		s.close();
		
		
		for(int i=0; i<sensoren.length(); i++){
			main.init.robot.stopMotor(sensoren.charAt(i));
		}
		
		
	}
	
	private void stop(String line){
		line = line.trim();
		//line = "mA.Stop('off')"	
		
		Scanner s = new Scanner(line);
		s.useDelimiter("");
		
		String character = "";
		String var = "";
		
		do{
			var+=character;
			character = s.next();
		}while(!character.equals("."));
		
		//var = "mA"
		
		
		s.close();
		
		
		//var = "mA"
		
		v.setPower(var, 0);
		
		//sensor = 		"NXTMotor('A', 'Power', 20);"
		String sensor = v.getWaarde(var);
		int index = sensor.indexOf("'");
		
		
		
		//scanner heeft 		"A', 'Power', 20);"
		Scanner scanner = new Scanner(sensor.substring(index+1));
		scanner.useDelimiter("");
		
		
		
		String temp = "";
		String sensoren = "";
		
		do{
			temp = scanner.next();
			if(temp.equals("A") || temp.equals("B") || temp.equals("C")){
				sensoren += temp;
			}
			
		}while(!temp.equals("'"));
		
		//sensoren = "AB"
		s.close();
		
		activeerMotoren(sensoren, 0);
		
		
		
	}
		
		
	/*
	private void stop(String line){
		//#stop
		line = line.trim();
		//line = "mA.Stop('off')"	
		
		Scanner s = new Scanner(line);
		s.useDelimiter("");
		
		String character = "";
		String var = "";
		
		do{
			var+=character;
			character = s.next();
		}while(!character.equals("."));
		
		//var = "mA"
		
		s.close();
		
		//sensor = 		"NXTMotor('A', 'Power', 20);"
		String sensor = v.getWaarde(var);
		int index = sensor.indexOf("'");
		
		
		
		//scanner heeft 		"A', 'Power', 20);"
		Scanner scanner = new Scanner(sensor.substring(index+1));
		scanner.useDelimiter("");
		
		
		
		String temp = "";
		String sensoren = "";
		
		do{
			temp = scanner.next();
			if(temp.equals("A") || temp.equals("B") || temp.equals("C")){
				sensoren += temp;
			}
			
		}while(!temp.equals("'"));
		
		//sensoren = "AB"
		s.close();
		
		activeerMotoren(sensoren, 0);
		
		////////////for loop moet in commentaar
		for(int i=0; i<sensoren.length(); i++){
			System.out.println("main.init.robot.setMotor("+sensoren.charAt(i)+", 0);");
			
			main.init.robot.setMotor(sensoren.charAt(i), 0);
			main.init.robot.stopMotor(sensoren.charAt(i));
		}
		
		
	}
	
	*/
	
	
	
	
	/*
	private Object getObject(String s){
		//NXTMotor('AC', 'Power', 60);
		if(s.contains("NXTMotor")){
			
			Scanner scanner = new Scanner(s.substring(9));
			scanner.useDelimiter(",");
			
			String[] deel = new String[3];
			for(int i = 0; i < deel.length; i++){
				deel[i] = scanner.next();
			}
			
			//'AC'
			Scanner s1 = new Scanner(deel[0]);
			s1.useDelimiter("");
			deel[0] = "";
			
			while(s1.hasNext()){
				char c = s1.next().charAt(0);
				if(c == '\''){
					
				}else{
					deel[0] += c;
				}
			}
			
			
			//'Power'
			Scanner s2 = new Scanner(deel[1]);
			
			//System.out.println("\t*****\tdeel[2]=" + deel[2] + "\t*****");
			
			//60 met misschien ')'
			Scanner s3 = new Scanner(deel[2]);
			s1.useDelimiter("");
			deel[2] = "";
			
			while(s3.hasNext()){
				char c = s3.next().charAt(0);
				//60
				if(Character.isDigit(c)){
					deel[2] += c;
				}else{
					
				}
			}
			int speed = Integer.parseInt(deel[2]);
			
			
			Scanner last = new Scanner(deel[0]);
			last.useDelimiter("");
			
			Variabele temp = new Variabele(3);
			while(last.hasNext()){
				String ABC = last.next();
				if(ABC.charAt(0) == 'A'){
					temp.add("A", new Motor(speed));
					
				}else if(ABC.charAt(0) == 'B'){
					temp.add("B", new Motor(speed));
					
				}else if(ABC.charAt(0) == 'C'){
					temp.add("C", new Motor(speed));
				}
			
			}
			
			 
			return temp;
			
		}else{
			return null;
		}
		
		
		
		
	}
	*/
	
	private void openLight(String line, int lineNumber){
		//OpenLight(SENSOR_4, ‘active’);		or ‘ACTIVE’
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter("");
		
		String cijfer;
		
		do{
			cijfer = scanner.next();
		}while(!Character.isDigit(cijfer.charAt(0)));
		
		scanner.close();
		
		this.main.init.robot.open(cijfer);
		if(!(cijfer.equals("1") || cijfer.equals("2") || cijfer.equals("3") || cijfer.equals("4"))){
			main.simulator.outputPanel.print("Line "+lineNumber+": Sensor "+cijfer+" is not connected with a Light sensor.\n");
		}
		
		
	}
	
	private int getLight(String line){
		//GetLight(SENSOR_4))
		
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter("");
		
		String cijfer;
		do{
			cijfer = scanner.next();
		}while(!Character.isDigit(cijfer.charAt(0)));
		
		scanner.close();
		
		
		
		
		if(this.main.init.robot.getSensor(cijfer.charAt(0)).getType().equals("LightL")){
			return valueLightL;
		}else{
			return valueLightR;
		}
		
		
		
		
	}
	
	
	
	private void closeSensor(String line, int lineNumber){
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter("");
		
		String cijfer;
		
		do{
			cijfer = scanner.next();
		}while(!Character.isDigit(cijfer.charAt(0)));
		
		scanner.close();
		
		this.main.init.robot.close(cijfer);
		if(!(cijfer.equals("1") || cijfer.equals("2") || cijfer.equals("3") || cijfer.equals("4"))){
			main.simulator.outputPanel.print("Line "+lineNumber+": Sensor "+cijfer+" is not connected with a Light sensor.\n");
		}
		
		
	}
	
	private void openUltrasonic(String line, int lineNumber){
		//OpenUltrasonic(SENSOR_3);
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter("");
		
		String cijfer;
		
		do{
			cijfer = scanner.next();
		}while(!Character.isDigit(cijfer.charAt(0)));
		
		scanner.close();
		
		
		this.main.init.robot.open(cijfer);
		if(!(cijfer.equals("1") || cijfer.equals("2") || cijfer.equals("3") || cijfer.equals("4"))){
			main.simulator.outputPanel.print("Line "+lineNumber+": Sensor "+cijfer+" is not connected with a Ultrasonic sensor.\n");
		}
		
	}
	
	private int getUltrasonic(String line){
		//return main.simulator.graphicsPanel.gd.ultraDistance;
		return valueUltra;
	}
	
	
	
	private int getTellerStop(String line){
		//String line = "for i=1:10";
		int dubbelePunt = line.indexOf(':');
		int eind = line.length();
		return Integer.parseInt(line.substring(dubbelePunt+1, eind).trim());
	}
	
	private int getTellerStart(String line){
		//String line = "for i=1:10";
		int is = line.indexOf('=');
		int dubbelePunt = line.indexOf(':');
		return Integer.parseInt(line.substring(is+1, dubbelePunt).trim());
	}
	
	private String getTeller(String line){
		//String line = "for i=1:10";
		int r = line.indexOf('r');
		int is = line.indexOf('=');
		return line.substring(r+1, is).trim();
	}
	
	public int getWaardeTeller(String teller, int tellerStart){
		//teller = "i"
		
		if(v.has(teller)){
			v.increaseWaarde(teller);
			return Integer.parseInt(v.getWaarde(teller));
		}else{
			v.add(teller, tellerStart+"");
			return tellerStart;
		}
		
	}
	
	
	
	
	public boolean forLoop(Loop loop){
		//start = 0, end = 2, line = "for i=1:10";
		//teller = "i", tellerStart = 1, tellerStop = 10
		
		String line = loop.line;
		String teller = getTeller(line);
		int tellerStart = getTellerStart(line);
		int tellerStop = getTellerStop(line);
		
		//als i nieuw is dan i=1		als i al bestaat dan i=2
		int i = getWaardeTeller(teller, tellerStart);
		System.out.println(i+"<="+tellerStop);
		if(i<=tellerStop){
			System.out.println("for is true");
			read(loop.start+1, loop.end-1);
			System.out.println();
			if(breakFound){
				loopSet.verwijderLoops(breakLine);
				breakFound = false;
				breakLine = -1;
			}else{
				loopSet.push(loop);
			}
			
			return true;
		}else{
			System.out.println("for is false");
			read(loop.end+1, main.simulator.code.getSize()-1);
			return false;
		}
	}
	
	
	public boolean whileLoop(Loop loop){
		if(berekenBoolean(loop.line)){
			System.out.println("while is true");
			read(loop.start+1, loop.end-1);
			
			if(breakFound){
				loopSet.verwijderLoops(breakLine);
				breakFound = false;
				breakLine = -1;
			}else{
				loopSet.push(loop);
			}
			
			
			return true;
		}else{
			System.out.println("while is false");
			read(loop.end+1, main.simulator.code.getSize()-1);
			return false;	
		}
	}
	
	private boolean berekenBoolean(String line){
		//line = "while(GetUltrasonic(SENSOR_1) > minDistance)"		or		"if (val1 > 450 && val2 > 400)"
		
		String expressie = line.substring(line.indexOf("(") + 1, (line.length()-1));
		//expressie = "GetUltrasonic(SENSOR_1) > minDistance"
		
		//true or false
		if(expressie.contains("true")){
			return true;
		}else if(expressie.contains("false")){
			return false;
		}
		
		if(line.contains("&&")){
			String[] delen = expressie.split("&&");
			String deel1 = delen[0].trim();
			String deel2 = delen[1].trim();
			
			boolean d1 = berekenBoolean(deel1);
			boolean d2 = berekenBoolean(deel2);
			//System.out.println("#9. d1="+d1+"\td2="+d2);
			
			return d1 && d2;
		}
		
		
		
		//< or = or >
		if(expressie.contains("<")){
			String[] delen = expressie.split("<");
			String deel1 = delen[0].trim();
			String deel2 = delen[1].trim();
			
			return expressie1(deel1, deel2);
			
		}else if(expressie.contains(">")){
			String[] delen = expressie.split(">");
			String deel1 = delen[0].trim();
			String deel2 = delen[1].trim();
			
			return expressie2(deel1, deel2);
			
		}else if(expressie.contains("==")){
			String[] delen = expressie.split("==");
			String deel1 = delen[0].trim();
			String deel2 = delen[1].trim();
			
			return expressie3(deel1, deel2);
		}
		
		//fout
		return false;
		
	}
	
	
	private boolean expressie1(String deel1, String deel2){
		
		int d1 = getValue(deel1);
		int d2 = getValue(deel2);
		return d1 < d2;
		
	}
	
	private boolean expressie2(String deel1, String deel2){
		//deel1 = "val1", deel2 = "450"
		
		int d1 = getValue(deel1);
		int d2 = getValue(deel2);
		
		return d1 > d2;
	}
	
	
	private boolean expressie3(String deel1, String deel2){
		int d1 = getValue(deel1);
		int d2 = getValue(deel2);
		
		return d1 == d2;
	}
	
	private void display(String line){
		//display(GetUltrasonic(SENSOR_1));			display(vector(j))
		int openen = line.indexOf('(');
		int sluiten = line.lastIndexOf(')');
		String var = line.substring(openen+1, sluiten);
		
		main.simulator.print(getValue(var)+"\n");
	}
	
	private int getValue(String deel){
		//System.out.println("# jaaaa: "+deel);
		//deel = "minDistance", "GetUltrasonic(SENSOR_1)"
		//deel = "val1"
		if(deel.contains("GetLight")){
			return getLight(deel);
			
		}else if(deel.contains("GetUltrasonic")){
			return getUltrasonic(deel);
			
		}else if(containsAllDigits(deel)){
			return Integer.parseInt(deel);
			
		}else if(deel.contains("(") && deel.contains(")")){
		
			//deel = "light_vec(i)"
			int openen = deel.indexOf('(');
			int sluiten = deel.indexOf(')');
			String naam = deel.substring(0, openen);
			//System.out.println("# foooo: "+deel.substring(openen+1, sluiten).trim());
			return getValue(deel.substring(openen+1, sluiten).trim());
			//int index = Integer.parseInt(deel.substring(openen+1, sluiten).trim());
			//return vector.getWaarde(naam, index);
		
		}else if(v.has(deel)){
		
			//v.getWaarde(deel) = "GetLight(SENSOR_1)"
			return getValue(v.getWaarde(deel));
			
			
			//return Integer.parseInt(v.getWaarde(deel));
		}else if(loopSet.get(deel) != -1){
			return loopSet.get(deel);
		
		}else{
			return -1;
		}
		
		
		
		
	}
	
	private boolean containsAllDigits(String line){
		
		for(int i=0; i<line.length(); i++){
			if(!Character.isDigit(line.charAt(i))){
				return false;
			}
		}
		
		return true;
	}
	
	
	private void setPower(String deel1, String deel2){
		//#power
		//deel1 ="mA.Power"		deel2 = "20"	
		
		Scanner s = new Scanner(deel1);
		s.useDelimiter("");
		
		String character = "";
		String var = "";
		
		do{
			var+=character;
			character = s.next();
		}while(!character.equals("."));
		
		s.close();
		
		//var = "mA"
		
		v.setPower(var, Integer.parseInt(deel2));
		
	}
	
	
	
	
	public void ververs(){
		vector = new Vector();
		
		loopSet = new LoopSet(main.simulator.code.getSize());
		
		stopRun = false;
		
		v = new Variabele();
		
		valueUltra = main.simulator.graphicsPanel.gd.ultraDistance;
		valueLightL = main.simulator.graphicsPanel.gd.lightL;
		valueLightR = main.simulator.graphicsPanel.gd.lightR;
		
		pause = 0.0;
		pauseLine = -1;
		pauseEnd = -1;
		pauseFound = false;
		
		regelRunning = -1;
		
		breakFound = false;
		breakLine = -1;
	}
	
	
}
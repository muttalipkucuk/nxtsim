package simulator;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GraphicsData {
	
	public static double rotation = 0.;
	
	public static final int KOLOMMEN = 9, RIJEN = 12;
	public final int CEL = 64, WIDTH = KOLOMMEN*CEL, HEIGHT = RIJEN*CEL;
	
	public static int[] x = new int[KOLOMMEN+1];
	public static int[] y = new int[RIJEN+1];
	public static int rijClicked, kolomClicked;
	public static Image[][] images;		//rij, kolom
	public static String[][] imagesURL;	//rij, kolom
	public static Image robot;
	//public static final double ROTATION_RIGHT_UNDER = 90. + 60.2551187030577;//29.7448812969422
	//public static final double ROTATION_LEFT_UNDER = 90. + 90.;
	public static final int LENGTH_DIAGONAL = 137;
	public static int rotationX;
	public static int rotationY;
	
	//public static int pointX;
	//public static int pointY;
	public static int pointX2;
	public static int pointY2;
	
	public static final int ROBOT_WIDTH = 68;
	public static final int ROBOT_HEIGHT = 119;
	public static final int POINTX_MARGIN = 1*ROBOT_WIDTH/2;		//34
	//public static final int POINTY_MARGIN = 1*ROBOT_HEIGHT;		//119
	
	public static final int STEP_SIZE = 5;		//10 cm is 2 px
	public static final int MINIMAL_STEP = 3;
	public static final int MOVE = 5;
	public static final double ROTATION_STEP = 8.;
	
	public static final double[][][] DIAGONAL_LIGHT = new double[2][3][3];	//left/right, kolom, rij
	public static final double[][][] DEGREES_LIGHT = new double[2][3][3];	//left(-1)/right, kolom, rij
	public int[][][] lightX;	//left/right, a/b/c/..
	public int[][][] lightY;
	public Color[][][] lightColors;		
	public Color lightLeft, lightRight;
	public int lightL, lightR;		//als matlab waarde. 0 = white, 800 = black 
	public static final int MATLAB_WHITE = 800, MATLAB_BLACK = 0;
	//public int[] ultraX;
	//public int[] ultraY;
	public double[] DIAGONAL_ULTRA = new double[5];
	public double[] DEGREES_ULTRA = new double[5];
	
	public boolean lightIsOn;
	public boolean ultraIsOn;
	public Color laserOutside = new Color(252, 60, 64);
	public Color laserInside = new Color(255, 254, 199);
	public final int ULTRA_MARGIN = 1;		//eigenlijk 92
	public final int ULTRA_MAX_RANGE = 255;		//tussen 5 en 255 cm
	public boolean ultraSees;
	public int ultraDistance;
	public static final Color colorKubusCirkel = new Color(119, 119, 119);
	public static final Color colorMuur = new Color(172, 79, 40);
	public static final Color colorMuurHoek = new Color(189, 127, 100);
	
	public int[] ultraStartX;
	public int[] ultraStartY;
	
	public int[] ultraRangeX;
	public int[] ultraRangeY;
	
	public Main main;
	
	public void printData2(){
		System.out.println();
		System.out.println("***** robot = "+main.init.robot.getDigitsLED());
		System.out.print("A = ");
		if(main.init.robot!= null && main.init.robot.getA().isOn()){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
		
		System.out.print("B = ");
		if(main.init.robot!= null && main.init.robot.getB().isOn()){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
		
		System.out.print("C = ");
		if(main.init.robot!= null && main.init.robot.getC().isOn()){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
		
		
		
		System.out.println("*****");
		System.out.println();
		
	}
	
	public void printData(){
		System.out.println("\n******");
		System.out.println("pointX =\t"+pointX2);
		System.out.println("pointY =\t"+pointY2);
		System.out.println("rotation =\t"+rotation);
		System.out.println("ultraDistance =\t"+ultraDistance);
		System.out.println("lightL =\t"+lightL);
		System.out.println("lightR =\t"+lightR);
		for(int i=0; i<2; i++){
			
			for(int j=0; j<3; j++){
				for(int k=0; k<3; k++){
					if(i==0){
						System.out.println("left ["+j+"]["+k+"]\t("+lightX[0][j][k]+","+lightY[0][j][k]+")");
					}else{
						System.out.println("right ["+j+"]["+k+"]\t("+lightX[1][j][k]+","+lightY[1][j][k]+")");
					}
					
					String temp = getRGB(getColor2(lightX[0][j][k], lightY[0][j][k]))+")";
					System.out.println("\tnot real\t==> "+getRGB(lightColors[i][j][k]));
					System.out.println("\treal\t==> "+temp);
				}	
			}
		}
		System.out.println("******\n");
	}
	
	
	private Color getColor2(int x, int y){
		if(x<0 || y<0 || x>=WIDTH || y>=HEIGHT){
			//System.out.println("("+x+","+y+") "+"foo1");
			return Color.white;
		}
		try {
			int kolom = x/CEL;
			int rij = y/CEL;
			String url = imagesURL[rij][kolom];
			//System.out.println(" url = "+url);
			if(url!=null){
				//Image i = ImageIO.read(new File(url));
				int x1 = x%CEL;
				int y1 = y%CEL;
				BufferedImage i2 = ImageIO.read(this.getClass().getResource(url));
				if(isTransparent(i2, x1, y1)){
					//System.out.println("("+x+","+y+") "+"foo2");
					return Color.white;
				}
				//System.out.println("("+x+","+y+") "+"foo3");
				return new Color(i2.getRGB(x1, y1));
			}else{
				//System.out.println("("+x+","+y+") "+"foo4");
				return Color.white;
			}
		} catch (IOException e) {
			System.out.println("GraphicsData:\tImage not found");
			e.printStackTrace();
		}
		//System.out.println("("+x+","+y+") "+"foo5");
		return null;
	}

	
	public GraphicsData(Main main){
		this.main = main;
		init();
	}
	
	private void init(){
		rotation = 0.; 
		robot = null;
		
		rotationX = rotationY = -1;
		pointX2 = pointY2 = -100;
		
		images = new Image[RIJEN][KOLOMMEN];
		imagesURL = new String[RIJEN][KOLOMMEN];
		
		rijClicked = kolomClicked = -1;
		
		maakCoordinaten();
		
		lightX = new int[2][3][3];
		lightY = new int[2][3][3];
		lightColors = new Color[2][3][3];
		lightLeft = lightRight = Color.white;
		lightL = lightR = MATLAB_WHITE;
		lightIsOn = true;
		//maakConstantenLight();
		maakConstantenLight2();
		
		//ultraX = new int[5];
		//ultraY = new int[5];
		ultraStartX = new int[5];
		ultraStartY = new int[5];
		ultraRangeX = new int[5];
		ultraRangeY = new int[5];
		ultraIsOn = true;
		ultraSees = false;
		ultraDistance = -1;
		
		//maakConstantenUltra();
		maakConstantenUltra2();
		
		refreshSensors();
		
	}
	
	private void maakConstantenLight2(){
		/*
		double[][][] DIAGONAL_LIGHT = new double[2][3][3];
		double[][][] DEGREES_LIGHT = new double[2][3][3];
		*/
		
		double[] y = new double[3];
		y[0]=1;
		y[1]=9;
		y[2]=18;
		
		double[] x1 = new double[3];
		x1[0]=21;
		x1[1]=16;
		x1[2]=10;
		
		double[] x2 = new double[3];
		x2[0]=11;
		x2[1]=16;
		x2[2]=22;
		
		for(int rij=0; rij<3; rij++){
			for(int kolom=0; kolom<3; kolom++){
				DIAGONAL_LIGHT[0][rij][kolom] = Math.sqrt(Math.pow(y[rij],2) + Math.pow(x1[kolom],2) );
				DEGREES_LIGHT[0][rij][kolom] = 270. + -1.*Math.toDegrees( Math.atan(y[rij] / x1[kolom]) );
				
				DIAGONAL_LIGHT[1][rij][kolom] = Math.sqrt(Math.pow(y[rij],2) + Math.pow(x2[kolom],2) );
				DEGREES_LIGHT[1][rij][kolom] = 90. + Math.toDegrees( Math.atan(y[rij] / x2[kolom]) );
				
				lightX[0][rij][kolom] = lightY[0][rij][kolom] = lightX[1][rij][kolom] = lightY[1][rij][kolom] = -50;
			}
		}
		
		/*
		System.out.println("\tLEFT");
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				
				System.out.println("("+i+","+j+") met y = " + y[i] + " en x = " + x1[j]);
				System.out.println("> DIAGONAL_LIGHT[0]["+i+"]["+j+"] = " + DIAGONAL_LIGHT[0][i][j]);
				System.out.println("> DEGREES_LIGHT[0]["+i+"]["+j+"] = " + DEGREES_LIGHT[0][i][j]);
				
				System.out.println();
			}
		}
		System.out.println("_________________________________\n\n\tRIGHT");
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				
				System.out.println("("+i+","+j+") met y = " + y[i] + " en x = " + x2[j]);
				System.out.println("> DIAGONAL_LIGHT[1]["+i+"]["+j+"] = " + DIAGONAL_LIGHT[1][i][j]);
				System.out.println("> DEGREES_LIGHT[1]["+i+"]["+j+"] = " + DEGREES_LIGHT[1][i][j]);
				
				System.out.println();
			}
		}
		*/
	}
	
	private void maakConstantenLight(){
		double[] ov = new double[3];
		ov[0]=21;
		ov[1]=16;
		ov[2]=10;
		
		double[] an = new double[3];
		an[0]=117;
		an[1]=109;
		an[2]=100;
		
		
		for(int rij=0; rij<3; rij++){
			for(int kolom=0; kolom<3; kolom++){
				DIAGONAL_LIGHT[0][kolom][rij] = Math.sqrt(Math.pow(ov[kolom],2) + Math.pow(an[rij],2) );
				DEGREES_LIGHT[0][kolom][rij] = -1.*Math.toDegrees( Math.atan(ov[kolom] / an[rij]) );
				
				
				DIAGONAL_LIGHT[1][kolom][rij] = Math.sqrt(Math.pow(ov[2-kolom],2) + Math.pow(an[rij],2) );
				DEGREES_LIGHT[1][kolom][rij] = Math.toDegrees( Math.atan(ov[2-kolom] / an[rij]) );
				
				lightX[0][kolom][rij] = lightY[0][kolom][rij] = lightX[1][kolom][rij] = lightY[1][kolom][rij] = -50;
				
				
			}
		}
		
		/*
		System.out.println("\tLEFT");
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				char c = (char) (97+i*3+j);
				System.out.println("("+i+","+j+") met " + ov[j] + " " + an[i]);
				System.out.println("> DIAGONAL_LIGHT[0]["+j+"]["+i+"] = " + DIAGONAL_LIGHT[0][j][i]);
				System.out.println("> DEGREES_LIGHT[0]["+j+"]["+i+"] = " + DEGREES_LIGHT[0][j][i]);
				
				System.out.println();
				//System.out.println(c + " " + hoogte[i] + " " + breedte[j] + "\n> diagonal:\t" + DIAGONAL_LIGHT[0][i][j] + "\n> degrees:\t" + DEGREES_LIGHT[0][i][j]);
			}
		}
		System.out.println("\n_________________________________\n\tRIGHT");
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				char c = (char) (97+i*3+j);
				System.out.println("("+i+","+j+") met " + ov[2-j] + " " + an[i]);
				System.out.println("> DIAGONAL_LIGHT[1]["+j+"]["+i+"] = " + DIAGONAL_LIGHT[1][j][i]);
				System.out.println("> DEGREES_LIGHT[1]["+j+"]["+i+"] = " + DEGREES_LIGHT[1][j][i]);
				
				System.out.println();
				//System.out.println(c + " " + hoogte[i] + " " + breedte[j] + "\n> diagonal:\t" + DIAGONAL_LIGHT[0][i][j] + "\n> degrees:\t" + DEGREES_LIGHT[0][i][j]);
			}
		}
		*/
		
		
	}

	private void maakConstantenUltra2(){
		
		DIAGONAL_ULTRA[0] = 34;
		DIAGONAL_ULTRA[1] = 17;
		DIAGONAL_ULTRA[2] = 0;
		DIAGONAL_ULTRA[3] = 17;
		DIAGONAL_ULTRA[4] = 34;
		
		DEGREES_ULTRA[0] = -90.;
		DEGREES_ULTRA[1] = -90.;
		DEGREES_ULTRA[2] = 0;
		DEGREES_ULTRA[3] = 90.;
		DEGREES_ULTRA[4] = 90.;
		
		/*
		int[] x = new int[5];
		x[0] = 0;
		x[1] = 17;
		x[2] = 34;
		x[3] = 51;
		x[4] = 68;
		
		System.out.println("\tULTRA");
		for(int i=0; i<5; i++){
			System.out.println(i+".\t"+x[i]);
			System.out.println("> DIAGONAL_ULTRA["+i+"] = "+DIAGONAL_ULTRA[i]);
			System.out.println("> DEGREES_ULTRA["+i+"] = "+DEGREES_ULTRA[i]);
			System.out.println();
		}
		*/
		
	}
	
	private void maakConstantenUltra(){

		int an = 119-92;
		
		int[] x = new int[5];
		x[0] = 0;
		x[1] = 17;
		x[2] = 34;
		x[3] = 51;
		x[4] = 68;
						
		//double[] DIAGONAL_ULTRA = new double[5];
		//double[] DEGREES_ULTRA = new double[5];
		
		for(int i=0; i<5; i++){
			double ov = x[i] - 34.;
			DIAGONAL_ULTRA[i] = Math.sqrt(Math.pow(ov, 2) + Math.pow(an, 2));
			DEGREES_ULTRA[i] =  Math.toDegrees( Math.atan(ov / an) );
			
			ultraStartX[i] = ultraStartY[i] = -50;
		}
		
		
		/*
		System.out.println("\tULTRA");
		for(int i=0; i<5; i++){
			System.out.println(i+".\t"+x[i]);
			System.out.println("> DIAGONAL_ULTRA["+i+"] = "+DIAGONAL_ULTRA[i]);
			System.out.println("> DEGREES_ULTRA["+i+"] = "+DEGREES_ULTRA[i]);
			System.out.println();
		}
		*/
	}
	
	private void maakCoordinaten(){
		int x0=0, y0=0, xi=x0, yi=y0;
		
		for(int i = 0; i < x.length; i++){
			x[i] = xi; 
			xi+=CEL;
		}
		
		for(int i = 0; i < y.length; i++){
			y[i] = yi; 
			yi+=CEL;
		}
	}

	/*
	public void printUltra(){
		System.out.println("point = ("+pointX+","+pointY + ")\trotation = "+rotation);
		for(int i=0; i<5; i++){
			System.out.println("ultra["+i+"] = ("+ultraX[i]+","+ultraY[i]+")");
		}
		
		for(int i=0; i<5; i++){
			System.out.println("ultraStart["+i+"] = ("+ultraStartX[i]+","+ultraStartY[i]+")");
		}
		
		
		
		for(int i=0; i<5; i++){
			System.out.println("ultraRange["+i+"] = ("+ultraRangeX[i]+","+ultraRangeY[i]+")   <--");
		}
		
		System.out.println("ultraSees = " + ultraSees);
		System.out.println("ultraDistance = "+ultraDistance);
		
		System.out.println();
		
	}
	*/
	
	private void moveRight(){
		double degree = 90. + rotation;
		
		double cos = cos(degree);
		double x = MOVE * cos;
		double sin = sin(degree);
		double y = MOVE * sin;
		
		
		pointX2 += round(x);
		pointY2 -= round(y);
		
	}
	
	private void moveLeft(){
		double degree = 270. + rotation;
		
		double cos = cos(degree);
		double x = MOVE * cos;
		double sin = sin(degree);
		double y = MOVE * sin;
		
		
		pointX2 += round(x);
		pointY2 -= round(y);
		
		
	}
	
	public void moveStep(int left, int right){
		//System.out.println("# moveStep("+left+","+right+")");
		//left = 20, right = 10
		int step;
		if(right<left){
			step = round(right / STEP_SIZE);
		}else{
			step = round(left / STEP_SIZE);
		}
		if(left == 0 || right == 0){
			step = MINIMAL_STEP;
		}
		
		if(left==0){
			moveLeft();
		}
		
		if(right==0){
			moveRight();
		}
		
		System.out.println("step = "+step);
		
		double x = step * cos(rotation);
		double y = step * sin(rotation);
		pointX2 += round(x);
		pointY2 -= round(y);
		
		refreshSensors();
		
	}

	public void berekenLight(){
		for(int rij=0; rij<3; rij++){
			for(int kolom=0; kolom<3; kolom++){
				
				
				
				//left: number i
				double cos = cos(rotation + DEGREES_LIGHT[0][rij][kolom]);
				double x = DIAGONAL_LIGHT[0][rij][kolom] * cos;
				double sin = sin(rotation + DEGREES_LIGHT[0][rij][kolom]);
				double y = DIAGONAL_LIGHT[0][rij][kolom] * sin;
				lightX[0][rij][kolom] = pointX2 + round(x);
				lightY[0][rij][kolom] = pointY2 - round(y);
				
				/*
				System.out.println("\tleft:\t<"+rij+","+kolom+">");
				System.out.println("DEGRE\t\t=\t"+DEGREES_LIGHT[0][rij][kolom]);
				System.out.println("DIAG\t\t=\t"+DIAGONAL_LIGHT[0][rij][kolom]);
				System.out.println("cos\t\t=\t"+cos);
				System.out.println("x\t\t=\t"+x);
				System.out.println("sin\t\t=\t"+sin);
				System.out.println("y\t\t=\t"+y);
				System.out.println("lightX[0]["+rij+"]["+kolom+"]\t=\t"+lightX[0][rij][kolom]);
				System.out.println("lightY[0]["+rij+"]["+kolom+"]\t=\t"+lightY[0][rij][kolom]);
				*/
				
				//right: number i
				cos = cos(rotation + DEGREES_LIGHT[1][rij][kolom]);
				x = DIAGONAL_LIGHT[1][rij][kolom] * cos;
				sin = sin(rotation + DEGREES_LIGHT[1][rij][kolom]);
				y = DIAGONAL_LIGHT[1][rij][kolom] * sin;
				lightX[1][rij][kolom] = pointX2 + round(x);
				lightY[1][rij][kolom] = pointY2 - round(y);
				
			}
		}
		
	}

	public void berekenUltraStart(){
		
		for(int i=0; i<5; i++){
			double cos = cos(rotation + DEGREES_ULTRA[i]);
			double x = DIAGONAL_ULTRA[i] * cos;
			double sin = sin(rotation + DEGREES_ULTRA[i]);
			double y = DIAGONAL_ULTRA[i] * sin;
			ultraStartX[i] = pointX2 + round(x);
			ultraStartY[i] = pointY2 - round(y);
		
			/*
			System.out.println("degr =\t"+DEGREES_ULTRA[i]);
			System.out.println("diag =\t"+DIAGONAL_ULTRA[i]);
			System.out.println("i =\t"+i);
			System.out.println("cos =\t"+cos);
			System.out.println("x =\t"+x);
			System.out.println("sin =\t"+sin);
			System.out.println("y =\t"+y);
			System.out.println("startX =\t"+ultraStartX[i]);
			System.out.println("startY =\t"+ultraStartY[i]);
			*/
			
			
			//cos = cos(rotation);
			//x = ULTRA_MARGIN * cos;
			//sin = sin(rotation);
			//y = ULTRA_MARGIN * sin;
			//ultraStartX[i] = ultraX[i] + round(x);
			//ultraStartY[i] = ultraY[i] - round(y);
			
		}
		
		
	}

	public void wisLight(){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				lightX[0][i][j] = lightY[0][i][j] = lightX[1][i][j] = lightY[1][i][j] = -1;
			}
		}
	}
	
	public void wisUltra(){
		for(int i=0; i<5; i++){
			ultraStartX[i] = ultraStartY[i] = -1;
		}
	}

	private int round(double d){
	    double dAbs = Math.abs(d);
	    int i = (int) dAbs;
	    double result = dAbs - (double) i;
	    if(result<0.5){
	        return d<0 ? -i : i;            
	    }else{
	        return d<0 ? -(i+1) : i+1;          
	    }
	}

	private double degreeJAVAToMath(double d){
		//System.out.println(">>> JAVA (" + d +  ")  <=>  Math (" + (90.-d) + ") <<<");
		return (90. - d);
	}

	private double cos(double d){
		return Math.cos(Math.toRadians(degreeJAVAToMath(d)));
	}

	private double sin(double d){
		return Math.sin(Math.toRadians(degreeJAVAToMath(d)));
	}

	public static double getDegree(int dif){
		dif /= 10;
		return Math.toDegrees( Math.atan( ( dif*2 ) / 68.) );
	}

	public void rotate(int speedLeft, int speedRight, double r){
		rotation += r;
	}

	
	public boolean isTransparent(BufferedImage img, int x, int y ) {
		  int pixel = img.getRGB(x,y);
		  if( (pixel>>24) == 0x00){
		      return true;
		  }
		  return false;
	}
	
	private Color getColor(int x, int y){
		
		if(x<0 || y<0 || x>=WIDTH || y>=HEIGHT){
			//System.out.println("foo1");
			return Color.white;
		}
		
		try {
			int kolom = x/CEL;
			int rij = y/CEL;
			String url = imagesURL[rij][kolom];
			//System.out.println(" url = "+url);
			if(url!=null){
				//Image i = ImageIO.read(new File(url));
				int x1 = x%CEL;
				int y1 = y%CEL;
				BufferedImage i2 = ImageIO.read(this.getClass().getResource(url));
				
				
				if(isTransparent(i2, x1, y1)){
					//System.out.println("("+x+","+y+") "+"foo2");
					return Color.white;
				}
				
				//System.out.println("("+x+","+y+") "+"foo3");
				return new Color(i2.getRGB(x1, y1));
				
				
			}else{
				//System.out.println("("+x+","+y+") "+"foo4");
				return Color.white;
			}
			
		} catch (IOException e) {
			//System.out.println("GraphicsData:\tImage not found");
			e.printStackTrace();
		}
		
		//System.out.println("("+x+","+y+") "+"foo5");
		return null;
	}

	
	private int getLightMATLAB(Color c){
		return round((0.21*c.getRed() + 0.72*c.getGreen() + 0.07*c.getBlue()) / 255 * 800);
		
	}
	
	private void updateLightL(){
		for(int rij=0; rij<3; rij++){
			for(int kolom=0; kolom<3; kolom++){
				
				if(!lightColors[0][rij][kolom].equals(Color.white)){
					lightL = getLightMATLAB(lightColors[0][rij][kolom]);
					return;
				}
			}
		}
	}
	
	private void updateLightR(){
		for(int rij=0; rij<3; rij++){
			for(int kolom=0; kolom<3; kolom++){
				
				if(!lightColors[1][rij][kolom].equals(Color.white)){
					lightR = getLightMATLAB(lightColors[1][rij][kolom]);
					return;
				}
			}
		}
	}
	
	
	
	public void updateLight(){
		
		for(int rij=0; rij<3; rij++){
			for(int kolom=0; kolom<3; kolom++){
				
				lightColors[0][rij][kolom] = getColor(lightX[0][rij][kolom], lightY[0][rij][kolom]);
				lightColors[1][rij][kolom] = getColor(lightX[1][rij][kolom], lightY[1][rij][kolom]);
				
				
			}
		}
		
		
		lightL = lightR = MATLAB_WHITE;
		
		updateLightL();
		updateLightR();
		
		
		
		
		
		
		/*
		int blackL = 0, whiteL = 0, blackR = 0, whiteR = 0;
		
		for(int rij=0; rij<3; rij++){
			for(int kolom=0; kolom<3; kolom++){
				//System.out.print("[0]["+kolom+"]["+rij+"] = ");
				//System.out.println("left:\trij="+rij+"\tkolom="+kolom);
				lightColors[0][rij][kolom] = getColor(lightX[0][rij][kolom], lightY[0][rij][kolom]);
				//System.out.println("right:\trij="+rij+"\tkolom="+kolom);
				lightColors[1][rij][kolom] = getColor(lightX[1][rij][kolom], lightY[1][rij][kolom]);
				
				
				
				Color cL = lightColors[0][rij][kolom];
				//System.out.println(cL);
				//black
				if(cL.getRed()==0 && cL.getGreen()==0 && cL.getBlue()==0){
					blackL++;
				//white
				}else if(cL.getRed()==255 && cL.getGreen()==255 && cL.getBlue()==255){
					whiteL++;
				}else{
					//System.out.println("updateLightColors (L):\tUnknown color: Color("+cL.getRed()+","+cL.getGreen()+","+cL.getBlue()+")");
				}
				
				Color cR = lightColors[1][rij][kolom];
				//black
				if(cR.getRed()==0 && cR.getGreen()==0 && cR.getBlue()==0){
					blackR++;
				//white
				}else if(cR.getRed()==255 && cR.getGreen()==255 && cR.getBlue()==255){
					whiteR++;
				}else{
					//System.out.println("updateLightColors (R):\tUnknown color: Color("+cR.getRed()+","+cR.getGreen()+","+cR.getBlue()+")");
				}
				
			}
		}
		
		//LEFT
		if(whiteL>blackL){
			this.lightLeft = Color.white;
			this.lightL = MATLAB_WHITE;
		//whiteL == blackL		==>		black
		}else{
			this.lightLeft = Color.black;
			this.lightL = MATLAB_BLACK;
		}
		//RIGHT
		if(whiteR>blackR){
			this.lightRight = Color.white;
			this.lightR = MATLAB_WHITE;
		//whiteL == blackL		==>		black
		}else{
			this.lightRight = Color.black;
			this.lightR = MATLAB_BLACK;
		}


		//System.out.println("left = "+this.lightL+"\twhiteL = "+whiteL+"\tblackL = "+blackL);
		//System.out.println("right = "+this.lightR+"\twhiteR = "+whiteR+"\tblackR = "+blackR);
		*/
		
	}
	
	private String getRGB(Color c){
		return (c.getRed()+","+c.getGreen() + "," + c.getBlue());
	}
	
	public void berekenUltraRange(){
		
		for(int i=0; i<5; i++){
			double cos = cos(rotation);
			double x = (ultraDistance*6.4) * cos;
			double sin = sin(rotation);
			double y = (ultraDistance*6.4) * sin;
			ultraRangeX[i] = ultraStartX[i] + round(x);
			ultraRangeY[i] = ultraStartY[i] - round(y);
			
		}
	}
	
	public boolean ultraSees(){
		for(int i = 0; i<5; i++){
			Color c = getColor(ultraRangeX[i], ultraRangeY[i]);
			//System.out.println("\t"+ultraRangeX[i]+","+ultraRangeY[i]+" = "+getRGB(c));
			if(!(Color.BLACK.equals(c) || Color.LIGHT_GRAY.equals(c) || Color.WHITE.equals(c))){
			//if(colorKubusCirkel.equals(c) || colorMuur.equals(c) || colorMuurHoek.equals(c)){	
				//System.out.println("There is a object found!\tultraDistance = "+ultraDistance);
				return true;
			}
		}
		return false;
	}
	
	private void updateUltra(){
		
		ultraDistance = 0;
		berekenUltraRange();
		
		while(ultraDistance<ULTRA_MAX_RANGE){
			
			if(ultraSees()){
				ultraSees = true;
				return;
			}else{
				ultraDistance++;
				berekenUltraRange();	
			}
		}
		
		ultraSees = false;
	}
	
	public void refreshSensors(){
		
		berekenLight();
		berekenUltraStart();
		berekenUltraRange();
		updateLight();
		updateUltra();
		
		if(main.simulator.run!=null){
			main.simulator.run.valueUltra = ultraDistance;
			main.simulator.run.valueLightL = lightL;
			main.simulator.run.valueLightR = lightR;
		}
		
	}
	
	public boolean outOfScreen(){
		return (pointX2<0 || pointX2>CEL*KOLOMMEN || pointY2<0 || pointY2>CEL*RIJEN);
	}

	public void clearImages(){
		
		for(int i=0; i<RIJEN; i++){
			for(int j=0; j<KOLOMMEN; j++){
				images[i][j] = null;
				imagesURL[i][j] = null;
			}
		}
		
		robot = null;
		main.simulator.graphicsPanel.repaint();
		
	}
	
}

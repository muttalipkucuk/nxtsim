package simulator;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;


public class Init {
	public static final int CEL = 64;
	
	public Main main;
	
	private JPanel left, right;
	private JPanel emptyPanel;
	
	
	public static Robot robot;
	
	
	private ImageIcon[][] images;
	private ImageIcon imageRobot;
	
	private JIconButton[][] buttons;
	private JButton ok, ok2;
	
	
	private boolean cMotorL, cMotorR, cLightL, cLightR, cUltra;
	
	
	private boolean connectedA, connectedB, connectedC, connected1, connected2, connected3, connected4;
	
	private int clicksLED;
	//private final int MAX_LED_CLICKS = 3;
	private final int MINIMAL_CLICKS_LED_TO_OKE = 1;
	
	public Init(Main main){
		this.main = main;
		
		robot = new Robot(main);
		
		cMotorL = cMotorR = cLightL = cLightR = cUltra = true;
		connectedA = connectedB = connectedC = connected1 = connected2 = connected3 = connected4 = false;
		clicksLED = 0;
		
		main.frame.getContentPane().removeAll();
		main.frame.setSize(1024, 768);				///Main.WIDTH en Main.HEIGHT kan gebruikt worden!!
		main.frame.setLayout(new GridLayout(1,2));
		
		
		setImages();
		setButtons();
		
		fillLeft();
		fillRight();
		fillEmptyPanel();
		
		activeerButtons();
		
		
		main.frame.add(left);
		main.frame.add(right);
		main.frame.setVisible(true);
	}
	
	private void initImagesButtons(){
		//A, B, C, USB
		for(int i = 0; i < 4; i++){
			try{
				String path;
				if(i==0){
					path = "A";
				}else if(i==1){
					path = "B";
				}else if(i==2){
					path = "C";
				}else{
					path = "USB";
				}
				path = "/images/init/icon/" + path + ".png";
				images[1][i] = new ImageIcon(this.getClass().getResource(path));
			}catch(NullPointerException e){
				System.out.println("Image not found: A, B, C or USB");
			}
		}
		
		//1, 2, 3, 4
		for(int i = 0; i < 4; i++){
			try{
				String path;
				if(i==0){
					path = "1";
				}else if(i==1){
					path = "2";
				}else if(i==2){
					path = "3";
				}else{
					path = "4";
				}
				path = "/images/init/icon/" + path + ".png";
				images[2][i] = new ImageIcon(this.getClass().getResource(path));
				}catch(NullPointerException e){
				System.out.println("Image not found: 1, 2, 3 or 4");
			}
		}
	}
	
	private void setImages(){
		imageRobot = new ImageIcon(this.getClass().getResource("/images/init/icon/robot.png"));
		
		images = new ImageIcon[4][4];
		
		//ACTUATORS: empty				SENSORS: empty
		for(int i = 0; i < 4; i++){
			try{
				String path = "/images/init/icon/empty1x1.5.png";
				images[0][i] = new ImageIcon(this.getClass().getResource(path));
				images[3][i] = new ImageIcon(this.getClass().getResource(path));
			}catch(NullPointerException e){
				System.out.println("Image not found: Empty (1x1.5)");
			}
		}
		
		
		//A, B, C, USB
		for(int i = 0; i < 4; i++){
			try{
				String path;
				if(i==0){
					path = "A";
				}else if(i==1){
					path = "B";
				}else if(i==2){
					path = "C";
				}else{
					path = "USB";
				}
				path = "/images/init/icon/" + path + ".png";
				images[1][i] = new ImageIcon(this.getClass().getResource(path));
			}catch(NullPointerException e){
				System.out.println("Image not found: A, B, C or USB");
			}
		}
		
		//1, 2, 3, 4
		for(int i = 0; i < 4; i++){
			try{
				String path;
				if(i==0){
					path = "1";
				}else if(i==1){
					path = "2";
				}else if(i==2){
					path = "3";
				}else{
					path = "4";
				}
				path = "/images/init/icon/" + path + ".png";
				images[2][i] = new ImageIcon(this.getClass().getResource(path));
				}catch(NullPointerException e){
				System.out.println("Image not found: 1, 2, 3 or 4");
			}
		}
	}
	
	private void setButtons(){
		buttons = new JIconButton[2][4];
		
		//A, B, C, USB			1, 2, 3, 4
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 4; j++){
				JIconButton cel = new JIconButton();
				cel.setSize(1*CEL, (int) 0.5*CEL);
				cel.setBackground(Color.white);
				cel.setOpaque(true);
				cel.setIcon(images[i+1][j]);
				buttons[i][j] = cel;
			}
		}
		
		ok = new JButton();
		try{
			String path = "/images/init/icon/ok.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			ok.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: OK");
		}
		ok.setText("OK");
		ok.setFont(new Font("Helvetica", Font.PLAIN, 30));
		ok.setHorizontalAlignment(SwingConstants.CENTER);
		ok.setForeground(main.ORANGE);
		ok.setSize(10*CEL, 4*CEL);
		
		ok.setEnabled(clicksLED>=MINIMAL_CLICKS_LED_TO_OKE || (!cMotorL && !cMotorR));
		
		ok2 = new JButton();
		try{
			String path = "/images/init/icon/ok.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			ok2.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: OK");
		}
		ok2.setText("OK");
		ok2.setFont(new Font("Helvetica", Font.PLAIN, 30));
		ok2.setHorizontalAlignment(SwingConstants.CENTER);
		ok2.setForeground(main.ORANGE);
		ok2.setSize(10*CEL, 4*CEL);
	
		ok2.setEnabled(clicksLED>=MINIMAL_CLICKS_LED_TO_OKE || (!cMotorL && !cMotorR));
		
	}
	
	private void fillEmptyPanel(){
		JPanel empty = new JPanel(new BorderLayout());
		empty.setSize(8*CEL, 12*CEL);
		empty.setBackground(main.ORANGE);
		
		
		
		
		JLabel picture = new JLabel();
		//String message2 = "Left and right are defined as follows.";
		//picture.setText(message2);
		picture.setFont(new Font("Helvetica", Font.PLAIN, 14));
		picture.setHorizontalAlignment(SwingConstants.CENTER);
		picture.setForeground(Color.white);
		picture.setIcon(new ImageIcon(this.getClass().getResource("/images/init/icon/robotlr.png")));
		empty.add(picture, BorderLayout.PAGE_START);
		
		
		//#
		String message = "<html><center><b>HINT:</b> Click on <b>A, B, C, 1, 2, 3</b> or <b>4</b> to make a connection with a sensor/actuator. "
				+ "Left and right are defined as shown at the top." 
				+ "Click on <b>OK</b> if your robot is initialized. </center></html>";
		JLabel text = new JLabel();
		text.setText(message);
		text.setFont(new Font("Helvetica", Font.PLAIN, 30));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setForeground(Color.white);
		empty.add(text, BorderLayout.CENTER);
		
		
		
		
		
		
		empty.add(ok2, BorderLayout.PAGE_END);
		
		this.emptyPanel = empty;
	}
	
	private void fillLeft(){
		JPanel left = new JPanel();
		left.setSize(8*CEL, 12*CEL);
		left.setBackground(Color.white);
		
		//main
		JPanel main = new JPanel(new BorderLayout());
		main.setSize(4*CEL, 10*CEL);
		main.setBackground(Color.white);
		
		
		
		//TOP
		JPanel top = new JPanel(new BorderLayout());
		top.setSize(4*CEL, 2*CEL);
		top.setBackground(Color.white);
		top.setOpaque(true);
		
		//actuators: connected images
		JPanel actuators = new JPanel(new GridLayout(1,4));
		actuators.setSize(4*CEL, (int) 1.5*CEL);
		
		for(int i = 0; i < 4; i++){
			JLabel cel = new JLabel();
			cel.setSize(1*CEL, (int) 1.5*CEL);
			cel.setIcon(images[0][i]);
			cel.setBackground(Color.white);
			cel.setOpaque(true);
			actuators.add(cel);
		}
		top.add(actuators, BorderLayout.NORTH);
		
		//A, B, C and USB
		JPanel abcusb = new JPanel(new GridLayout(1,4));
		abcusb.setSize(4*CEL, (int) 0.5*CEL);
		for(int i = 0; i < 4; i++){
			abcusb.add(buttons[0][i]);
		}
		top.add(abcusb, BorderLayout.SOUTH);
		
		main.add(top, BorderLayout.NORTH);
		
		
		
		//CENTER
		JLabel center = new JLabel();
		center.setSize(4*CEL, 6*CEL);
		center.setIcon(imageRobot);
		main.add(center, BorderLayout.CENTER);
		
		
		
		//BOTTOM
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setSize(4*CEL, 2*CEL);
		bottom.setBackground(Color.white);
		bottom.setOpaque(true);
		
		//1, 2, 3 and 4
		JPanel port1234 = new JPanel(new GridLayout(1,4));
		port1234.setSize(4*CEL, (int) 0.5*CEL);
		port1234.setBackground(Color.white);
		port1234.setOpaque(true);
		
		for(int i = 0; i < 4; i++){
			port1234.add(buttons[1][i]);
		}
		bottom.add(port1234, BorderLayout.NORTH);
		
		//sensors: connected images
		JPanel sensors = new JPanel(new GridLayout(1,4));
		sensors.setSize(4*CEL, (int) 1.5*CEL);
		
		for(int i = 0; i < 4; i++){
			JLabel cel = new JLabel();
			cel.setSize(1*CEL,(int) 1.5*CEL);
			cel.setIcon(images[3][i]);
			cel.setBackground(Color.white);
			cel.setOpaque(true);
			sensors.add(cel);
		}
		bottom.add(sensors, BorderLayout.SOUTH);
		
		main.add(bottom, BorderLayout.SOUTH);
		
		left.add(main);
		this.left = left;
	}

	private void fillRight(){
		
		JPanel right = new JPanel(new BorderLayout());
		right.setSize(8*CEL, 12*CEL);
		right.setBackground(main.ORANGE);
		
		JLabel picture = new JLabel();
		//String message2 = "Left and right are defined as follows.";
		//picture.setText(message2);
		picture.setFont(new Font("Helvetica", Font.PLAIN, 14));
		picture.setHorizontalAlignment(SwingConstants.CENTER);
		picture.setForeground(Color.white);
		picture.setIcon(new ImageIcon(this.getClass().getResource("/images/init/icon/robotlr.png")));
		right.add(picture, BorderLayout.PAGE_START);
		
		String message = "<html><center><b>HINT:</b> Click on <b>A, B, C, 1, 2, 3</b> or <b>4</b> to make a connection with a sensor/actuator. "
				+ "Left and right are defined as shown at the top." 
				+ "Click on <b>OK</b> if your robot is initialized. </center></html>";
		JLabel text = new JLabel();
		text.setText(message);
		text.setFont(new Font("Helvetica", Font.PLAIN, 30));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setForeground(Color.white);
		right.add(text, BorderLayout.CENTER);
		
		right.add(ok, BorderLayout.PAGE_END);
		
		this.right = right;
		
		//#
		/*
		
		
		JPanel right = new JPanel(new BorderLayout());
		right.setSize(8*CEL, 12*CEL);
		right.setBackground(main.ORANGE);
		
		String message = "<html><center><b>HINT:</b> Click on <b>A, B, C, 1, 2, 3</b> or <b>4</b> to make a connection with a sensor/actuator. "
				+ "Click on <b>OK</b> if your robot is initialized.</center></html>";
		JLabel text = new JLabel();
		text.setText(message);
		text.setFont(new Font("Helvetica", Font.PLAIN, 30));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setForeground(Color.white);
		right.add(text, BorderLayout.CENTER);
		
		right.add(ok, BorderLayout.PAGE_END);
		
		this.right = right;*/
		
		
	}
	/*
	private void vraagteken(int rij, int kolom){
		System.out.println("vraagteken("+rij+","+kolom+")");
		System.out.println("\tvoor: clickPreviousRij = "+clickPreviousRij);
		System.out.println("\tvoor: clickPreviousKolom = "+clickPreviousKolom);
		System.out.println("\tvoor: clickRij = "+clickRij);
		System.out.println("\tvoor: clickKolom = "+clickKolom);
		
		if(clickPreviousRij != -1 && clickPreviousKolom != -1){
			images[clickPreviousRij][clickPreviousKolom] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
		}
		clickPreviousRij = clickRij;
		clickPreviousKolom = clickKolom;
		clickRij = rij;
		clickKolom = kolom;
		
		System.out.println("\tna: clickPreviousRij = "+clickPreviousRij);
		System.out.println("\tna: clickPreviousKolom = "+clickPreviousKolom);
		System.out.println("\tna: clickRij = "+clickRij);
		System.out.println("\tna: clickKolom = "+clickKolom);
		
		ImageIcon im = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5?.png"));
		images[clickRij][clickKolom] = im;
		
		
		
		
		
		//images[rij][kolom] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5?.png"));
		fillLeft();
		
	}
	*/
	
	private void kleurImage(int rij, int kolom){
		//##
		//A, B, C, USB
		for(int i = 0; i < 4; i++){
			try{
				String path;
				if(i==0){
					path = "A";
				}else if(i==1){
					path = "B";
				}else if(i==2){
					path = "C";
				}else{
					path = "USB";
				}
				if(rij==1 && kolom==i){
					path += "c";
				}
				path = "/images/init/icon/" + path + ".png";
				images[1][i] = new ImageIcon(this.getClass().getResource(path));
			}catch(NullPointerException e){
				System.out.println("Image not found: A, B, C or USB");
			}
		}
		
		//1, 2, 3, 4
		for(int i = 0; i < 4; i++){
			try{
				String path;
				if(i==0){
					path = "1";
				}else if(i==1){
					path = "2";
				}else if(i==2){
					path = "3";
				}else{
					path = "4";
				}
				if(rij==2 && kolom==i){
					path += "c";
				}
				
				path = "/images/init/icon/" + path + ".png";
				images[2][i] = new ImageIcon(this.getClass().getResource(path));
				}catch(NullPointerException e){
				System.out.println("Image not found: 1, 2, 3 or 4");
			}
		}
		
		//A, B, C, USB			1, 2, 3, 4
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 4; j++){
				JIconButton cel = new JIconButton();
				cel.setSize(1*CEL, (int) 0.5*CEL);
				cel.setBackground(Color.white);
				cel.setOpaque(true);
				cel.setIcon(images[i+1][j]);
				buttons[i][j] = cel;
			}
		}
		
		//images[i][j] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
		fillLeft();
	}
	
	
	private void activeerButtons(){
		buttons[0][0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                System.out.println("Init:\tClick on buttonA");
                
                //#####
                kleurImage(1,0);
                
    			
    			kiesActuator("A");
				
			}
        });  
		
		buttons[0][1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\tClick on buttonB");
				kleurImage(1,1);
    			
                kiesActuator("B");
			}
        });  
		
		buttons[0][2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\tClick on buttonC");
				kleurImage(1,2);
    			
                kiesActuator("C");
			}
        });  
		
	
		
		buttons[1][0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\tClick on button1");
				kleurImage(2,0);
    			
                kiesSensor("1");
			}
        });  
		
		buttons[1][1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\tClick on button2");
				kleurImage(2,1);
    			
                kiesSensor("2");
			}
        });  
		
		buttons[1][2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\tClick on button3");
				kleurImage(2,2);
    			
                kiesSensor("3");
			}
        });  
		
		buttons[1][3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\tClick on button4");
				kleurImage(2,3);
    			
                kiesSensor("4");
			}
        });  
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\tClick on ok");
                quit();
			}
        });  
		
		ok2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\tClick on ok2");
                quit();
			}
        });  
		
	}
	
	private void quit(){
		main.init = this;
		main.simulator = new Simulator(main);
		/*
		if(Main.simulator==null){
			Main.simulator = new Simulator();
		}else{
			Main.simulator.refresh();
		}
		*/
		return;
	}
	
	
	private void printConnections(){
		
		System.out.println("connectedA = "+connectedA);
		System.out.println("connectedB = "+connectedB);
		System.out.println("connectedC = "+connectedC);
		System.out.println("connected1 = "+connected1);
		System.out.println("connected2 = "+connected2);
		System.out.println("connected3 = "+connected3);
		System.out.println("connected4 = "+connected4);
		System.out.println();
		System.out.println("cMotorL = "+cMotorL);
		System.out.println("cMotorR = "+cMotorR);
		System.out.println("clicksLED = "+clicksLED);
		System.out.println("cLightL = "+cLightL);
		System.out.println("cLightR = "+cLightR);
		System.out.println("cUltra = "+cUltra);
		System.out.println();
		
		
		
		
	}
	
	private void disconnect(String s, String button){
		if(s.equals("A")){
			connectedA = false;
		}else if(s.equals("B")){
			connectedB = false;
		}else if(s.equals("C")){
			connectedC = false;
		}else if(s.equals("1")){
			connected1 = false;
		}else if(s.equals("2")){
			connected2 = false;
		}else if(s.equals("3")){
			connected3 = false;
		}else if(s.equals("4")){
			connected4 = false;
		}
		
		
		String element = "/init/connected/led.png";
		if(button.contains(element)){
			clicksLED--;
		}
		
		element = "/init/connected/wheelL.png";
		if(button.contains(element)){
			cMotorL = true;
		}
		
		element = "/init/connected/wheelR.png";
		if(button.contains(element)){
			cMotorR = true;
		}
		
		element = "/init/connected/lightL.png";
		if(button.contains(element)){
			cLightL = true;
		}
		
		element = "/init/connected/lightR.png";
		if(button.contains(element)){
			cLightR = true;
		}
		
		
		element = "/init/connected/ultrasonic.png";
		if(button.contains(element)){
			cUltra = true;
		}
		
		
		//printConnections();
		ok.setEnabled(clicksLED>=MINIMAL_CLICKS_LED_TO_OKE || (!cMotorL && !cMotorR));
		ok2.setEnabled(clicksLED>=MINIMAL_CLICKS_LED_TO_OKE || (!cMotorL && !cMotorR));
		
		
		
	}
	
	private void connect(String s, String keuze){
		if(s.equals("A")){
			connectedA = true;
		}else if(s.equals("B")){
			connectedB = true;
		}else if(s.equals("C")){
			connectedC = true;
		}else if(s.equals("1")){
			connected1 = true;
		}else if(s.equals("2")){
			connected2 = true;
		}else if(s.equals("3")){
			connected3 = true;
		}else if(s.equals("4")){
			connected4 = true;
		}
		
		
		
		if(keuze.equals("motorR")){
			cMotorR = false;
		}
		
		if(keuze.equals("motorL")){
			cMotorL = false;
		}
		
		if(keuze.equals("LED")){
			clicksLED++;
		}
		
		if(keuze.equals("lightL")){
			cLightL = false;
		}
		
		if(keuze.equals("lightR")){
			cLightR = false;
		}
		
		if(keuze.equals("ultra")){
			cUltra = false;
		}
		
		//printConnections();
		ok.setEnabled(clicksLED>=MINIMAL_CLICKS_LED_TO_OKE || (!cMotorL && !cMotorR));
		ok2.setEnabled(clicksLED>=MINIMAL_CLICKS_LED_TO_OKE || (!cMotorL && !cMotorR));
		
	}
	
	private void kiesActuator(final String ABC){
		Color bg = main.ORANGE;
		
		JPanel main = new JPanel(new GridLayout(4, 1));
		main.setBackground(bg);
		
		//motor right
		JPanel top = new JPanel(new BorderLayout());
		top.setBackground(bg);
		JButton keuze1 = new JButton();
		String text1 = "<html><center>MOTOR (RIGHT)</center></html>";
		keuze1.setText(text1);
		keuze1.setFont(new Font("Helvetica", Font.BOLD, 36));
		try{
			String path = "/images/init/icon/wheelR.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			keuze1.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: motorR");
		}
		keuze1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\t\tClick on motor (right)");
				
				keuzeMotorR(ABC);
                updateRightEmpty();
				
			}
        });  
		
		
		keuze1.setEnabled(cMotorR && clicksLED==0);
		top.add(keuze1, BorderLayout.CENTER);
		main.add(top);
		
		
		//motor left
		JPanel middle = new JPanel(new BorderLayout());
		middle.setBackground(bg);
		JButton keuze2 = new JButton();
		String text2 = "<html><center>MOTOR (LEFT)</center></html>";
		keuze2.setText(text2);
		keuze2.setFont(new Font("Helvetica", Font.BOLD, 36));
		try{
			String path = "/images/init/icon/wheelL.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			keuze2.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: motorL");
		}
		keuze2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\t\tClick on motor (left)");
				keuzeMotorL(ABC);
                updateRightEmpty();
				
			}
        });  
		keuze2.setEnabled(cMotorL && clicksLED==0);
		middle.add(keuze2, BorderLayout.CENTER);
		main.add(middle);
		
		
		
		
		
		//LED
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setBackground(bg);
		JButton keuze3 = new JButton();
		String text3 = "<html><center>LED</center></html>";
		keuze3.setText(text3);
		keuze3.setFont(new Font("Helvetica", Font.BOLD, 36));
		try{
			String path = "/images/init/icon/led.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			keuze3.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: LED");
		}
		keuze3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\t\tClick on LED");
				keuzeLED(ABC);
                updateRightEmpty();
			}
        });  
		keuze3.setEnabled(cMotorL && cMotorR);
		bottom.add(keuze3, BorderLayout.CENTER);
		main.add(bottom);
		
		//delete
		JPanel bottom2 = new JPanel(new BorderLayout());
		bottom2.setBackground(bg);
		JButton keuze4 = new JButton();
		String text4 = "<html><center>DELETE</center></html>";
		keuze4.setText(text4);
		keuze4.setFont(new Font("Helvetica", Font.BOLD, 36));
		try{
			String path = "/images/init/icon/delete.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			keuze4.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: Delete");
		}
		keuze4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\t\tClick on delete");
                keuzeDelete(ABC);
                updateRightEmpty();
			}
        });  
		bottom2.add(keuze4, BorderLayout.CENTER);
		main.add(bottom2);
		
		//update right
		JPanel right = new JPanel(new BorderLayout());
		right.setSize(8*CEL, 12*CEL);
		right.setBackground(Color.blue);
		right.setOpaque(true);
		
		right.add(main);
		updateRight(main);
	}
	
	private void keuzeMotorR(String ABC){
		//###
		kleurImage(0,0);
						

		connect(ABC, "motorR");
		
		updateRightEmpty();
		
		if(ABC.equals("A")){
			disconnect(ABC, images[0][0].toString());
			images[0][0] = new ImageIcon(this.getClass().getResource("/images/init/connected/wheelR.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setA(new MotorR(robot));
			
			
		}else if(ABC.equals("B")){
			disconnect(ABC, images[0][1].toString());
			images[0][1] = new ImageIcon(this.getClass().getResource("/images/init/connected/wheelR.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setB(new MotorR(robot));
			
		}else if(ABC.equals("C")){
			disconnect(ABC, images[0][2].toString());
			images[0][2] = new ImageIcon(this.getClass().getResource("/images/init/connected/wheelR.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setC(new MotorR(robot));
			
		}else{
			System.out.println("ERROR: Onbekende actuator-input (geen A, B of C)");
		}
		
	}
	
	
	private void keuzeMotorL(String ABC){
		//###
		kleurImage(0,0);
						

		connect(ABC, "motorL");
		
		updateRightEmpty();
		
		if(ABC.equals("A")){
			disconnect(ABC, images[0][0].toString());
			images[0][0] = new ImageIcon(this.getClass().getResource("/images/init/connected/wheelL.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setA(new MotorL(robot));
			
			
		}else if(ABC.equals("B")){
			disconnect(ABC, images[0][1].toString());
			images[0][1] = new ImageIcon(this.getClass().getResource("/images/init/connected/wheelL.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setB(new MotorL(robot));
			
		}else if(ABC.equals("C")){
			disconnect(ABC, images[0][2].toString());
			images[0][2] = new ImageIcon(this.getClass().getResource("/images/init/connected/wheelL.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setC(new MotorL(robot));
			
		}else{
			System.out.println("ERROR: Onbekende actuator-input (geen A, B of C)");
		}
		
	}
	

	private void keuzeLED(String ABC){
		
		//###
		kleurImage(0,0);
						

		connect(ABC, "LED");
		
		updateRightEmpty();
		
		updateRight(emptyPanel);
		if(ABC.equals("A")){
			disconnect(ABC, images[0][0].toString());
			images[0][0] = new ImageIcon(this.getClass().getResource("/images/init/connected/led.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setA(new LED(robot));
			
		}else if(ABC.equals("B")){
			disconnect(ABC, images[0][1].toString());
			images[0][1] = new ImageIcon(this.getClass().getResource("/images/init/connected/led.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setB(new LED(robot));
			
		}else if(ABC.equals("C")){
			disconnect(ABC, images[0][2].toString());
			images[0][2] = new ImageIcon(this.getClass().getResource("/images/init/connected/led.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setC(new LED(robot));
			
		}else{
			System.out.println("ERROR: Onbekende actuator-input (geen A, B of C)");
		}
	}
	
	private void keuzeDelete(String ABC){
		//###
		kleurImage(0,0);
						
		updateRightEmpty();
		
		updateRight(emptyPanel);
		if(ABC.equals("A")){
			disconnect(ABC, images[0][0].toString());
			
			images[0][0] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setA(null);
			
		}else if(ABC.equals("B")){
			disconnect(ABC, images[0][1].toString());
			
			
			images[0][1] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setB(null);
			
		}else if(ABC.equals("C")){
			disconnect(ABC, images[0][2].toString());
			
			images[0][2] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.setC(null);
			
		}else if(ABC.equals("1")){
			disconnect(ABC, images[3][0].toString());
			
			
			images[3][0] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set1(null);
			
		}else if(ABC.equals("2")){
			disconnect(ABC, images[3][1].toString());
			
			images[3][1] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set2(null);
			
		}else if(ABC.equals("3")){
			disconnect(ABC, images[3][2].toString());
			
			
			images[3][2] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set3(null);
			
		}else if(ABC.equals("4")){
			disconnect(ABC, images[3][3].toString());
			
			
			images[3][3] = new ImageIcon(this.getClass().getResource("/images/init/icon/empty1x1.5.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set4(null);
			
		}else{
			System.out.println("ERROR: Onbekende actuator-input (geen A, B, C, 1, 2, 3 of 4)");
		}
	}
	
	private void updateRight(JPanel right){
		this.right = right;
		main.frame.getContentPane().removeAll();
		main.frame.getContentPane().add(this.left);
		main.frame.getContentPane().add(right);
		main.frame.setVisible(true);
	}
	
	private void updateRightEmpty(){
		this.right = emptyPanel;
		main.frame.getContentPane().removeAll();
		main.frame.getContentPane().add(this.left);
		main.frame.getContentPane().add(emptyPanel);
		main.frame.setVisible(true);
	}
	
	private void updateLeft(JPanel left){
		this.left = left;
		main.frame.getContentPane().removeAll();
		main.frame.getContentPane().add(left);
		main.frame.getContentPane().add(this.right);
		main.frame.setVisible(true);
	}
	
	private void kiesSensor(final String s){
		Color bg = main.ORANGE;
		
		
		
		
		JPanel main = new JPanel(new GridLayout(4, 1));
		main.setBackground(bg);
		
		

		//light (right)
		JPanel panel1 = new JPanel(new BorderLayout());
		panel1.setBackground(bg);
		JButton keuze1 = new JButton();
		String text1 = "<html><center>LIGHT SENSOR (RIGHT)</center></html>";
		keuze1.setText(text1);
		keuze1.setFont(new Font("Helvetica", Font.BOLD, 36));
		try{
			String path = "/images/init/icon/lightR.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			keuze1.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: lightR");
		}
		keuze1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\t\tClick on lightR");
                keuzeLightR(s);
                updateRightEmpty();
			}
        });  
		
		keuze1.setEnabled(cLightR);
		panel1.add(keuze1, BorderLayout.CENTER);
		main.add(panel1);
		
		
		
		
		
		//light (left)
		JPanel panel2 = new JPanel(new BorderLayout());
		panel2.setBackground(bg);
		JButton keuze2 = new JButton();
		String text2 = "<html><center>LIGHT SENSOR (LEFT)</center></html>";
		keuze2.setText(text2);
		keuze2.setFont(new Font("Helvetica", Font.BOLD, 36));
		try{
			String path = "/images/init/icon/lightL.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			keuze2.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: lightL");
		}
		keuze2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\t\tClick on lightL");
                keuzeLightL(s);
                updateRightEmpty();
			}
        });  
		keuze2.setEnabled(cLightL);
		panel2.add(keuze2, BorderLayout.CENTER);
		main.add(panel2);
		
		
		
		
		
		
		
		//ultrasonic
		JPanel panel3 = new JPanel(new BorderLayout());
		panel3.setBackground(bg);
		JButton keuze3 = new JButton();
		String text3 = "<html><center>ULTRASONIC SENSOR</center></html>";
		keuze3.setText(text3);
		keuze3.setFont(new Font("Helvetica", Font.BOLD, 36));
		try{
			String path = "/images/init/icon/ultrasonic.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			keuze3.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: ultrasonic");
		}
		keuze3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\t\tClick on ultrasonic");
                keuzeUltrasonic(s);
                updateRightEmpty();	
			}
        });  
		keuze3.setEnabled(cUltra);
		panel3.add(keuze3, BorderLayout.CENTER);
		main.add(panel3);
		
		
		
		
		
		
		
		
		//delete
		JPanel panel4 = new JPanel(new BorderLayout());
		panel4.setBackground(bg);
		JButton keuze4 = new JButton();
		String text4 = "<html><center>DELETE</center></html>";
		keuze4.setText(text4);
		keuze4.setFont(new Font("Helvetica", Font.BOLD, 36));
		try{
			String path = "/images/init/icon/delete.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			keuze4.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: Delete");
		}
		keuze4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				System.out.println("Init:\t\tClick on delete");
                keuzeDelete(s);
                updateRightEmpty();
			}
        });  
		panel4.add(keuze4, BorderLayout.CENTER);
		main.add(panel4);
		
		//update right
		JPanel right = new JPanel(new BorderLayout());
		right.setSize(8*CEL, 12*CEL);
		right.setBackground(Color.blue);
		right.setOpaque(true);
		
		right.add(main);
		updateRight(main);
	}
	
	private void keuzeUltrasonic(String s){
		//###
		kleurImage(0,0);

		connect(s, "ultra");
		
		updateRightEmpty();
		
		updateRight(emptyPanel);
		if(s.equals("1")){
			disconnect(s, images[3][0].toString());
			
			images[3][0] = new ImageIcon(this.getClass().getResource("/images/init/connected/ultrasonic.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set1(new Ultrasonic(robot));
			
		}else if(s.equals("2")){
			disconnect(s, images[3][1].toString());
			
			images[3][1] = new ImageIcon(this.getClass().getResource("/images/init/connected/ultrasonic.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set2(new Ultrasonic(robot));
			
		}else if(s.equals("3")){
			disconnect(s, images[3][2].toString());
			
			images[3][2] = new ImageIcon(this.getClass().getResource("/images/init/connected/ultrasonic.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set3(new Ultrasonic(robot));
			
		}else if(s.equals("4")){
			disconnect(s, images[3][3].toString());
			
			images[3][3] = new ImageIcon(this.getClass().getResource("/images/init/connected/ultrasonic.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set4(new Ultrasonic(robot));
			
		}else{
			System.out.println("ERROR: Onbekende actuator-input (geen 1, 2, 3 of 4)");
		}
	}
	
	

	private void keuzeLightR(String s){
		//###
		kleurImage(0,0);
		
		connect(s, "lightR");
		
		updateRightEmpty();
		
		updateRight(emptyPanel);
		if(s.equals("1")){
			disconnect(s, images[3][0].toString());
			
			images[3][0] = new ImageIcon(this.getClass().getResource("/images/init/connected/lightR.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set1(new LightR(robot));
			
		}else if(s.equals("2")){
			disconnect(s, images[3][1].toString());
			
			images[3][1] = new ImageIcon(this.getClass().getResource("/images/init/connected/lightR.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set2(new LightR(robot));
			
		}else if(s.equals("3")){
			disconnect(s, images[3][2].toString());
			
			images[3][2] = new ImageIcon(this.getClass().getResource("/images/init/connected/lightR.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set3(new LightR(robot));
			
		}else if(s.equals("4")){
			disconnect(s, images[3][3].toString());
			
			images[3][3] = new ImageIcon(this.getClass().getResource("/images/init/connected/lightR.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set4(new LightR(robot));
			
		}else{
			System.out.println("ERROR: Onbekende actuator-input (geen 1, 2, 3 of 4)");
		}
	}
	
	
	

	private void keuzeLightL(String s){
		//###
		kleurImage(0,0);
						

		connect(s, "lightL");
		updateRightEmpty();
		
		updateRight(emptyPanel);
		if(s.equals("1")){
			disconnect(s, images[3][0].toString());
			
			images[3][0] = new ImageIcon(this.getClass().getResource("/images/init/connected/lightL.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set1(new LightL(robot));
			
		}else if(s.equals("2")){
			disconnect(s, images[3][1].toString());
			
			images[3][1] = new ImageIcon(this.getClass().getResource("/images/init/connected/lightL.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set2(new LightL(robot));
			
		}else if(s.equals("3")){
			disconnect(s, images[3][2].toString());
			
			images[3][2] = new ImageIcon(this.getClass().getResource("/images/init/connected/lightL.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set3(new LightL(robot));
			
		}else if(s.equals("4")){
			disconnect(s, images[3][3].toString());
			
			images[3][3] = new ImageIcon(this.getClass().getResource("/images/init/connected/lightL.png"));
			fillLeft();
			fillRight();
			activeerButtons();
			main.frame.add(left);
			main.frame.add(right);
			main.frame.setVisible(true);
			
			robot.set4(new LightL(robot));
			
		}else{
			System.out.println("ERROR: Onbekende actuator-input (geen 1, 2, 3 of 4)");
		}
	}
	
	
	
	
	public Robot getRobot(){
		return robot;
	}
	
	private static final class JIconButton extends JButton{
        private static final long serialVersionUID = 7274140930080397481L;

        public JIconButton(){
            super(UIManager.getIcon("OptionPane.informationIcon"));
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder());
            
        }
    }
	
	public boolean hasLED(){
		for(int i=0; i<3; i++){
			//System.out.println("Character.toChars(97+i)[0] = " + Character.toChars(97+i)[0]);
			Actuator a = robot.getActuator(Character.toChars(97+i)[0]);
			if(a!=null && a.getType().equals("LED")){
				return true;
			}
		}
		return false;
	}
	
}

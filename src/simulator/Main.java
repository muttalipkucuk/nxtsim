package simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Main {
	
	public static final int WIDTH = 1024, HEIGHT = 768 + 2*MenuBar.HEIGHT;
	//public final static Color ORANGE = new Color(68, 33, 14);
	public final static Color ORANGE = new Color(232, 131, 56);
	
	
	public static JFrame frame;
	
	public static Init init;
	public static Simulator simulator;
	
	public String title = "NXTSim - Lego Mindstorms NXT 2.0 Simulator";
	public static boolean endDetected;
	
	public Main(){
		
		init();
	}
	
	
	public void init(){
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle(title);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setResizable(false);
		frame.pack();
		//frame.setVisible(true);
		
		init = new Init(this);
			
	}
	
	
	public void start(){
		
		/*
		boolean running = true;
		while(running){
			System.out.print("");
			if(Main.simulator.run != null){
				while(Main.simulator.graphicsPanel.gd.pointX>0 && Main.simulator.graphicsPanel.gd.pointY>0){
		        	Main.simulator.graphicsPanel.drive();
		        }
				running = false;
			}
		}
		*/
		
		/*
		while(true){
			System.out.print("");
			if(Main.simulator.run != null){
				//System.out.println("run");
				while(Main.init.robot.isOn){
						System.out.println("> run");
					Main.simulator.run.run();
						System.out.println("> drive");
					Main.simulator.graphicsPanel.drive();
						System.out.println("> repaint");
					Main.simulator.outputPanel.repaint();
					
				}
			}
		}
		*/
		
		/*
		while(true){
			System.out.print("");
			if(Main.simulator.run != null){
				Main.simulator.run.run();
			}
		}
		*/
		
		//System.out.println("Program ends");
	}
	
	
	/*
	public static void run(){
		for(int i=0; i<10; i++){
			System.out.println("run i="+i);
			Main.simulator.graphicsPanel.drive();
		}
		
		
	}
	*/
	
	
	public static void main(String[] args) {
		

		
		Runnable r1 = new Runnable() {
			public void run() {
				endDetected = false;
				
				boolean runCode = true;
				while(runCode){
					System.out.print("");
					if(simulator!=null && simulator.menuBar.runIsClicked){
						System.out.println("###1");
						//simulator.graphicsPanel.gd.printData();
						//simulator.graphicsPanel.gd.printData2();
						simulator.performStep();
						//simulator.refresh();
						
						try {
							double seconds = 0.5;
							System.out.println("Main:\twait "+seconds+" seconds... ");
							Thread.sleep((int) (seconds*1000));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
						
						if(simulator.run !=null){
							System.out.println("###2");
							if(simulator.run.pauseFound){
								System.out.println("###3");
								try {
									System.out.println("Main:\tpause "+simulator.run.pause+" milliseconds... ");
									Thread.sleep((int) (simulator.run.pause+0.5));
									simulator.run.pauseFound = false;
									simulator.run.read(simulator.run.pauseLine+1, simulator.run.pauseEnd);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}else{
								System.out.println("###4");
								if(!simulator.run.loopSet.isEmpty()){
									Loop loop = simulator.run.loopSet.pop();
									System.out.println("###5");
									if(loop.type.equals("for")){
										simulator.run.forLoop(loop);
									}else{
										simulator.run.whileLoop(loop);
									}
								}else{
									System.out.println("###6");
									System.out.println("# stopRun is true");
									//runCode = false;
									
									if(!endDetected){
										System.out.println("prrrr");
										endDetected = true;
										simulator.outputPanel.setMidden("Running the MATLAB-file is completed.\n");
										//simulator.print("Reading the MATLAB-file is completed.\n");
									}
									
									if(simulator.graphicsPanel.gd.outOfScreen()){
										
										//System.out.println("# outOfScreen");
										//runCode=false;
									}
								}	
								
							}
						}
						
					}
					
					
				}
				
				System.out.println("Thread 1 is stopped.");
				
	         }
	     };
	     
	     new Thread(r1).start();
	     
	     
	     
	     
	     
	     Runnable r2 = new Runnable() {
				public void run() {
					new Main();
					
		         }
		     };
		
	     new Thread(r2).start();
	    
	     
	     
		
		
	}
	
	
	public void ververs(){
		endDetected = false;
	}
	
	
}
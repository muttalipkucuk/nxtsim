package simulator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

public class Simulator{
	
	public static final int WIDTH = 1024, HEIGHT = 768 + 2*MenuBar.HEIGHT;
	
	//public static JFrame frame;
	
	public static MenuBar menuBar;
	public static ToolsPanel toolsPanel;
	public static DeletePanel deletePanel;
	
	//public static WereldPanel wereldPanel;
	public static GraphicsPanel graphicsPanel;
	
	public static CodePanel codePanel;
	public static OutputPanel outputPanel;
	
	public static Code code;
	
	public static Run run;
	
	public Main main;
	
	
	
	
	
	public Simulator(Main main){
		this.main = main;
		
		init();
		placePanels();
		
	}	
	
	private void placePanels(){
		this.main.frame.getContentPane().removeAll();
		this.main.frame.setTitle("Lego Mindstorms Simulator");
		this.main.frame.setSize(WIDTH, HEIGHT);
		this.main.frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.main.frame.setLayout(new BorderLayout());
		this.main.frame.setBackground(Color.white);
		
		
		
		this.main.frame.add(menuBar, BorderLayout.PAGE_START);
		
		JPanel main = new JPanel(new BorderLayout());
		
		JPanel west = new JPanel(new BorderLayout());
		west.add(toolsPanel, BorderLayout.PAGE_START);
		west.add(deletePanel, BorderLayout.PAGE_END);
		main.add(west, BorderLayout.WEST);
		
		main.setOpaque(false);
		//WereldPanel wereldPanel = new WereldPanel();
		main.add(graphicsPanel, BorderLayout.CENTER);
		
		JPanel textPanel = new JPanel(new GridLayout(2,1));
		textPanel.add(codePanel);
		textPanel.add(outputPanel);
		main.add(textPanel, BorderLayout.EAST);
		//JScrollPane s = new JScrollPane(codePanel);
		
		this.main.frame.add(main, BorderLayout.PAGE_END);
		this.main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.main.frame.setVisible(true);
        //this.frame = frame;
	}
	
	private void init(){
		menuBar = new MenuBar(this.main);
		
		toolsPanel = new ToolsPanel(this.main);
		deletePanel = new DeletePanel(this.main);
		
		graphicsPanel = new GraphicsPanel(this.main);
		
		codePanel = new CodePanel(this.main);
		outputPanel = new OutputPanel(this.main);
		
	}
	
	
	public void print(String s){
		outputPanel.print(s);
		outputPanel.refresh();
		//refresh();
	}
	
	public void refresh(){
		
		
		codePanel.refresh();
        menuBar.refresh();
		outputPanel.refresh();
		placePanels();
	}
	
	
	
	
	public void performStep(){

		if(!main.init.hasLED()){
			if(main.init.robot.motorLeft.isOn() || main.init.robot.motorRight.isOn()){
				graphicsPanel.drive();
				graphicsPanel.gd.refreshSensors();
				
			}
			
		}else{
			
			try {
				//For example, digits = "101"
				String digits = main.init.robot.getDigitsLED();
				String path = "/Users/Kucuk/Documents/eclipse/workspace/Lego Mindstorms Simulator/res/images/wereld/led"+digits+".png";
				main.simulator.graphicsPanel.gd.robot = ImageIO.read(new File(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
		}
		graphicsPanel.repaint();
		main.simulator.outputPanel.verversWaardenSensoren();
		
	}
	
	
}
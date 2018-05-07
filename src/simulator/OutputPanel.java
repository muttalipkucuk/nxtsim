package simulator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class OutputPanel extends JTextPane{
	
	public final int KOLOMMEN = 5, RIJEN = 6;
	public final int CEL = 64, WIDTH = KOLOMMEN*CEL, HEIGHT = RIJEN*CEL;
	
	String text;
	
	public Main main;
	
	public String begin = 
			"\n" +
			"\t\tOUTPUT\n" +
			"\n" +
			this.main.init.robot.toString();
	
	public String midden = "";
	public String midden2 = "";
	public String eind = "";
	
	public OutputPanel(Main main){
		this.main = main;
		
		removeAll();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		Font font = new Font("Helvetica", Font.PLAIN, 10);
		setFont(font);
		
		text = begin + midden2 + midden + eind;
		setText(text);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
		setEditable(false);
		setVisible(true);
	}
	
	public void print(String s){
		eind += s;
		text = begin + midden2 + midden + eind;
		setText(text);
		
	}
	
	public void regelRunning(int i){
		midden = "Running line "+i+"...\n";
		
		text = begin + midden2 + midden + eind;
		setText(text);
		
	}
	
	
	
	
	public void setMidden(String s){
		midden = s;
		text = begin + midden2 + midden + eind;
		setText(text);
		
	}
	
	
	public void refresh(){
		removeAll();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		Font font = new Font("Helvetica", Font.PLAIN, 10);
		setFont(font);
		setText(text);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
		setEditable(false);
		setVisible(true);
		
	}
	
	public void ververs(){
		eind = "";
	}
	
	public void verversWaardenSensoren(){
		midden2 = "";
		
		
		midden2 += "Value of light sensor (left):\t";
		if(main.init.robot.hasLightL()){
			midden2 += main.simulator.graphicsPanel.gd.lightL + "\n";
		}else{
			midden2 += "-\n";
		}
		
		midden2 += "Value of light sensor (right):\t";
		if(main.init.robot.hasLightR()){
			midden2 += main.simulator.graphicsPanel.gd.lightR + "\n";
		}else{
			midden2 += "-\n";
		}
		
		midden2 += "Value of ultrasonic sensor:\t";
		if(main.init.robot.hasUltra()){
			midden2 += main.simulator.graphicsPanel.gd.ultraDistance + "\n";
		}else{
			midden2 += "-\n";
		}
		
		/*if(!midden2.equals("")){
			midden2 += "\n";
		}*/
		
		text = begin + midden2 + midden + eind;
		setText(text);
		
	}
	
	
}

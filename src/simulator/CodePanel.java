package simulator;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CodePanel extends JTextPane{
	
	public final int KOLOMMEN = 5, RIJEN = 6;
	public final int CEL = 64, WIDTH = KOLOMMEN*CEL, HEIGHT = RIJEN*CEL;
	
	public Main main;
	
	public CodePanel(final Main main){
		this.main = main;
		init();
		editText();
		
		addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseClicked(MouseEvent e) {
            	System.out.println("CodePanel:\tmouseClicked\t" + e.getX() + "," + e.getY());
            	
            	main.simulator.code = new FilePanel(main).getCode();
	            main.simulator.refresh();
            }
            
        });
	}
	
	private void init(){
		removeAll();
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		Font font = new Font("Courier", Font.PLAIN, 10);
		setFont(font);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setBackground(Color.black);
		setForeground(Color.white);
		setEditable(false);
		setVisible(true);
	}

	private void editText(){
		if(this.main.simulator.code == null){
			String text = 
					"\n" +
					"\t\tCODE\n" +
					"\n";
			text += "\tNO MATLAB-FILE IMPORTED YET.\n" +
					"\tCLICK ON FILE > IMPORT MATLAB-FILE.";
			setText(text);
		}else{
			String text = 
					"\n" +
					"\t\tCODE\n" +
					"\n";
			text += this.main.simulator.code.toString();
			setText(text);
		}
	}
	
	public void refresh(){
		init();
		editText();
	}
}

package simulator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
 
public class ScrollingTextArea extends JFrame {
 
	JTextArea txt = new JTextArea();
	JScrollPane scrolltxt = new JScrollPane(txt);
 
	public ScrollingTextArea() {
 
		setLayout(null);
 
		scrolltxt.setBounds(3, 3, 300, 200);
		add(scrolltxt);		
	}
 
 
	public static void main(String[] args) {
 
		ScrollingTextArea sta = new ScrollingTextArea();
		sta.setSize(313,233);
		sta.setTitle("Scrolling JTextArea with JScrollPane");
		sta.show();		
	}
 
}
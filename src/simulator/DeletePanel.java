package simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DeletePanel extends JPanel{
	
	private final int CEL = 64, RIJEN = 2, KOLOMMEN = 2;
	private final int WIDTH = KOLOMMEN*CEL, HEIGHT = RIJEN*CEL; 
	
	public static boolean isClicked;
	
	public Main main;
	
	public JButton del;
	
	public DeletePanel(final Main main){
		this.main = main;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Main.ORANGE);
		
		isClicked = false;
		
		del = new JButton();
		del.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		//cellen[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		try{
			String path = "/images/tools/delete.png";
			ImageIcon image = new ImageIcon(this.getClass().getResource(path));
			del.setIcon(image);
		}catch(NullPointerException e){
			System.out.println("Image not found: /images/tools/delete.png");
		}
		
		
		
		
		del.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
				/*
				//##
				int index = getIndex(main.simulator.toolsPanel.selectedURL);
				System.out.println("main.simulator.toolsPanel.tempURL = "+main.simulator.toolsPanel.tempURL);
				System.out.println("main.simulator.toolsPanel.selectedURL = "+main.simulator.toolsPanel.selectedURL);
				System.out.println("index = "+index);
				
				if(index!=-1){
					main.simulator.toolsPanel.cellen[index].setBorder(new JButton().getBorder());
				}
				del.setBorder(BorderFactory.createLoweredBevelBorder());
				*/
				
				boolean toolsWasClicked = main.simulator.toolsPanel.isClicked;
				
				
				
				isClicked = true;
				main.simulator.toolsPanel.isClicked = false;
				
				//##
				main.simulator.toolsPanel.previousURL = main.simulator.toolsPanel.selectedURL;
				main.simulator.toolsPanel.selectedURL = null;
				
				
				int indexVorige = getIndex(main.simulator.toolsPanel.previousURL);
				
				System.out.println("indexVorige = "+indexVorige);
				System.out.println("toolsWasClicked = "+toolsWasClicked);
				
				
				
				if(indexVorige!=-1 && toolsWasClicked){
					
					main.simulator.toolsPanel.cellen[indexVorige].setBorder(new JButton().getBorder());
				}
				
				del.setBorder(BorderFactory.createLoweredBevelBorder());
				
				
				
				System.out.println("DeletePanel:\tYou clicked the delete button");
			}
        }); 
		
		add(del, BorderLayout.CENTER );
		
		setVisible(true);
		
	}
	
	
	private int getIndex(String selectedURL){
		
		
		if(selectedURL!=null){
			for(int i=0; i<20; i++){
				if(selectedURL.contains(main.simulator.toolsPanel.names[i])){
					return i;
				}
			}
		}
		return -1;
	}
	
	
	
}

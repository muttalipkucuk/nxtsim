package simulator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolsPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final int KOLOMMEN = 2, RIJEN = 10;
	public final int CEL = 64, WIDTH = KOLOMMEN*CEL, HEIGHT = RIJEN*CEL;
	
	
	public static ImageIcon selected;
	public static String selectedURL;
	public static String previousURL;
	public static boolean isClicked;
	
	public String[] names = new String[20];
	
	public Main main;
	
	public JButton[] cellen;
	
	public ToolsPanel(Main main){
		this.main = main;
		setLayout(new GridLayout(RIJEN,KOLOMMEN));
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(Main.ORANGE);
		
		isClicked = false;
		
		initImages();
		maakCellen();
		setVisible(true);
	}
	
	
	
	
	private void maakCellen(){
		cellen = new JButton[KOLOMMEN*RIJEN];
		for(int i = 0; i < cellen.length; i++){
			cellen[i] = new JButton();
			cellen[i].setPreferredSize(new Dimension(CEL, CEL));
			//cellen[i].setBorder(BorderFactory.createLineBorder(Color.black, 3));
			
			try{
				String path = "/images/tools/" + names[i] + ".png";
				
				ImageIcon image = new ImageIcon(this.getClass().getResource(path));

				cellen[i].setIcon(image);
				
				
			}catch(NullPointerException e){
				System.out.println("Image not found: names[" + i + "]");
			}
			
			final String tempURL = "/images/wereld/" + names[i] + ".png";
			
			
			final int j = i;
			
			cellen[j].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
	                
					//Execute when button is pressed
					try{
						
						boolean deleteWasClicked = main.simulator.deletePanel.isClicked;
						
						
						
						isClicked = true;
						main.simulator.deletePanel.isClicked = false;

						//##
						previousURL = selectedURL;
						selectedURL = tempURL;
						
						int indexVorige = getIndex(previousURL);
						
						System.out.println("indexVorige = "+indexVorige);
						System.out.println("deleteWasClicked = "+deleteWasClicked);
						
						
						
						if(indexVorige!=-1 && !deleteWasClicked){
							
							cellen[indexVorige].setBorder(new JButton().getBorder());
						}else if(deleteWasClicked){
							
							main.simulator.deletePanel.del.setBorder(new JButton().getBorder());
							
						}
						//cellen[j].setBorder(BorderFactory.createLoweredBevelBorder());
						
						//cellen[j].setBorder(BorderFactory.createBevelBorder(1));
						
						cellen[j].setBorder(BorderFactory.createEtchedBorder());
						
						
						
						main.simulator.toolsPanel.selected = new ImageIcon(this.getClass().getResource(tempURL));
						
					}catch(NullPointerException e2){
						System.out.println("Image not found: " + tempURL);
					}
					System.out.println("ToolsPanel:\tYou clicked cellen["+j+"]\t"+tempURL);
				}
				
	        });  
			
			add(cellen[i]);
			
		}
		
	}
	
	private int getIndex(String selectedURL){
		
			
		if(selectedURL!=null){
			for(int i=0; i<20; i++){
				if(selectedURL.contains(names[i])){
					return i;
				}
			}
		}
		return -1;
	}
	
	
	
	private void initImages(){
		
		for(int i=0; i<16; i++){
			
			switch (i) {
        	//rij 1
        	case 0:  names[i] = "lijn1";
        			 break;
            case 1:  names[i] = "lijn2";
                     break;
            case 2:  names[i] = "bocht1";
                     break;
            case 3:  names[i] = "bocht2";
                     break;
                     
            //rij 2         
            case 4:  names[i] = "bocht3";
                     break;
            case 5:  names[i] = "bocht4";
                     break;
            case 6:  names[i] = "einde1";
                     break;
            case 7:  names[i] = "einde2";
                     break;
                     
            //rij 3         
            case 8:  names[i] = "einde3";
                     break;
            case 9:  names[i] = "einde4";
                     break;
        	case 10: names[i] = "kubus1";
        			 break;
            case 11: names[i] = "kubus2";
                     break;
                     
            //rij 4         
            case 12: names[i] = "cirkel1";
                     break;
            case 13: names[i] = "cirkel2";
                     break;
            case 14: names[i] = "muur1";
                     break;
            case 15: names[i] = "muur2";
                     break;
			}
		}
		
		String led = "";
		if(main.init.hasLED()){
			led = "led";
		}
		
		for(int i=0; i<4; i++){
			names[16+i] = "robot"+ led + (i+1);
			//names[16+i] = "robot" + (i+1);
			//names[16+i] = "robotled" + (i+1);
		}
		
		
		
		
	}
	
	
	
}

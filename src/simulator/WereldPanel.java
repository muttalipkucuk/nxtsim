package simulator;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class WereldPanel extends JPanel{
	
	public final int KOLOMMEN = 9, RIJEN = 12;
	public final int CEL = 64, WIDTH = 9*CEL, HEIGHT = 12*CEL;
	
	public Color backgroundColor;
	
	public Main main;
	
	public WereldPanel(Main main){
		this.main = main;
		
		setLayout(new GridLayout(RIJEN,KOLOMMEN));
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		//setBorder(BorderFactory.createLineBorder(Color.black, 3));
		
		backgroundColor = Color.white;
		setBackground(backgroundColor);
		
		maakCellen();
		setVisible(true);
	}
	
	private void maakCellen(){
		JButton[][] cellen = new JButton[RIJEN][KOLOMMEN];
		
		for(int i = 0; i<RIJEN; i++){
			for(int j = 0; j<KOLOMMEN; j++){
				
				cellen[i][j] = new JButton();
				cellen[i][j].setPreferredSize(new Dimension(CEL, CEL));
				cellen[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				
				final int i2 = i;
				final int j2 = j;
				final JButton temp = cellen[i][j];
				
				cellen[i2][j2].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
		                //Execute when button is pressed
						System.out.println("\tWereldPanel:\tYou clicked cellen["+i2+","+j2+"]");
						temp.setIcon(main.simulator.toolsPanel.selected);
					}
					
		        });  
				
				cellen[i2][j2].addMouseListener(new MouseListener(){
					
					@Override
					public void mouseClicked(MouseEvent e) {
						int xMargin = j2 * 64;
						int yMargin = i2 * 64;
								
						int x=e.getX() + xMargin;
					    int y=e.getY() + yMargin;
					    System.out.println("\tWereldPanel:\tCoordinaat<"+x+","+y+">");
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

				});
				
				
				
				
				
				add(cellen[i][j]);
				
			}
		}
		
		
		
		
		
		
		/**
		
		
		
		//oud
		JButton[] cellen = new JButton[KOLOMMEN*RIJEN];
		
		for (int i = 0; i < cellen.length; i++){
			cellen[i] = new JButton();
			cellen[i].setPreferredSize(new Dimension(CEL, CEL));
			//cellen[i].setBackground(Color.red);
			//cellen[i].setOpaque(true);
			cellen[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			
			//cellen[i].setIcon(Simulator.selected);
			
			final int j = i;
			final JButton temp = cellen[i];
			cellen[j].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
	                //Execute when button is pressed
					System.out.println("\tWereldPanel:\tYou clicked cellen["+j+"]");
					temp.setIcon(ToolsPanel.selected);
				}
				
				
				
				
	        });  
			
			cellen[j].addMouseListener(new MouseListener(){
				
				

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					
					int xMargin = (j%KOLOMMEN) * 64;
					
					int yMargin = 0;
					int temp=j;
					
					do{
						temp-=KOLOMMEN;
						
					}while(temp>0);
					
					
					// TODO Auto-generated method stub
					int x=e.getX();
				    int y=e.getY();
				    System.out.println(x+","+y);//these co-ords are relative to the component
				}
				
				
				
			});
			
			
			add(cellen[i]);
			
			
		}
		
		*/
		
	}
	/**
	public void paint(Graphics g){
		
		Graphics2D g2 = (Graphics2D) g;

	    Image img1 = Toolkit.getDefaultToolkit().getImage("/images/wereld/robot_ultrasonic.png");
		   
	}
	*/
	
}

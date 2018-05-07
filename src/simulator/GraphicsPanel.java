package simulator;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel{
	
	public static final int TIME_TO_SLEEP = 1000;
	
	long animDuration = 10000;
	long currentTime = System.nanoTime() / 1000000;
	long startTime = currentTime;
	long elapsedTime = currentTime - startTime;
	
	//String res = "res/";
	String res = "";
	
	public static GraphicsData gd;
	
	public Main main;
	
	public GraphicsPanel(Main main){
		
		this.main = main;
		
		gd = new GraphicsData(main);
		
		setPreferredSize(new Dimension(gd.WIDTH,gd.HEIGHT));
		setOpaque(false);
		maakClickable();
		
		
		setVisible(true);
	}
	
	public GraphicsPanel(Main main, GraphicsData gd){
		this.main = main;
		this.gd = gd;
		
		setPreferredSize(new Dimension(gd.WIDTH,gd.HEIGHT));
		setOpaque(false);
		
		maakClickable();
		
		setVisible(true);
		
	}
	
	private void maakClickable(){
		addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	addImage((int) e.getX(), (int) e.getY());
            	
            	System.out.println("GraphicsPanel:\tmouseClicked\t" + e.getX() + "," + e.getY());
            	repaint();
            }
        });
	}
	
	@Override
	public void paint(Graphics g) {
		System.out.println("paint...");
		
		//gd.printData();
		Graphics2D g2d = (Graphics2D) g;
		
		

				
		//PIJL NAAR LINKS - 10 CM - PIJL NAAR RECHTS
		try {
			String path = "/images/wereld/arrow_to_left.png";
			Image arrow_to_left = ImageIO.read(this.getClass().getResource(path));
			path = "/images/wereld/arrow_to_right.png";		
			Image arrow_to_right = ImageIO.read(this.getClass().getResource(path));
			g2d.drawImage(arrow_to_left, 4*gd.CEL, 11*gd.CEL+54, null);
			g2d.drawImage(arrow_to_right, 4*gd.CEL+44, 11*gd.CEL+54, null);
			
			Font font = new Font("Arial", Font.BOLD, 8);
			g2d.setColor(Color.BLACK);
			g2d.setFont(font);
			g2d.drawString("10 cm", (int) (4.5*gd.CEL-10), (int) (11.5*gd.CEL+30));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//MAAK RASTER
		g2d.setColor(Color.LIGHT_GRAY);
		//verticale lijnen
		for(int i=0; i<gd.y.length; i++){
			g2d.drawLine(gd.x[0], gd.y[i], gd.x[gd.x.length-1], gd.y[i]);
		}
		//horizontale lijnen
		for(int i=0; i<gd.x.length; i++){
			g2d.drawLine(gd.x[i], gd.y[0], gd.x[i], gd.y[gd.y.length-1]);
		}
		
		//TEKEN PLATTEGROND
		for(int i=0; i<gd.RIJEN; i++){
			for(int j=0; j<gd.KOLOMMEN; j++){
				Image im = gd.images[i][j];
				if(im != null){
					//main.simulator.print("im!=null at ("+i+","+j+")\n");
					g2d.drawImage(im, j*gd.CEL, i*gd.CEL, null);
				}
			}
		}
		
		
		//TEKEN ROBOT
		if(gd.robot!=null){
			//System.out.println("paint:\tr="+gd.rotation+"°, ("+gd.pointX+","+gd.pointY+") leftup: ("+(gd.pointX-gd.POINTX_MARGIN)+","+(gd.pointY-gd.POINTY_MARGIN)+")");
			AffineTransform backup = g2d.getTransform();
			AffineTransform trans = new AffineTransform();
			trans.rotate(Math.toRadians(gd.rotation), gd.pointX2, gd.pointY2); // the points to rotate around (the center in my example, your left side for your problem)
			g2d.transform(trans);
			g2d.drawImage(gd.robot, gd.pointX2-gd.POINTX_MARGIN, gd.pointY2, null);
			//g2d.drawImage(gd.robot, gd.pointX-gd.POINTX_MARGIN, gd.pointY-gd.POINTY_MARGIN, null);
			g2d.setTransform(backup); // restore previous transform
			gd.refreshSensors();
			
		}
		
		//System.out.println("url issss " + gd.imagesURL[0][0]);
		
		
		
		
	}
	
	public void addImage(int x, int y){
		
		gd.kolomClicked = x/gd.CEL;
		gd.rijClicked = y/gd.CEL;
		
		if(main.simulator.toolsPanel.isClicked){
			//main.simulator.print("#ToolsPanel is clicked "+x+","+y+" ='"+main.simulator.toolsPanel.selectedURL+"'\n");
			
			if(main.simulator.toolsPanel.selectedURL.contains("robot")){		//robot is clicked
				//main.simulator.print("#robot\n");
				//String path = "/Users/Kucuk/Documents/eclipse/workspace/Lego Mindstorms Simulator/res" + main.simulator.toolsPanel.selectedURL;
				
				//String path = "/Users/Kucuk/Documents/eclipse/workspace/Lego Mindstorms Simulator/res/images/wereld/robot1.png";
				String path = res+"/images/wereld/robot1.png";
				
				if(main.simulator.toolsPanel.selectedURL.contains("led")){
					//For example, digits = "101"
					String digits = main.init.robot.getDigitsLED();
					//main.simulator.toolsPanel.selectedURL.lastIndexOf("d");
					System.out.println("digits="+digits);
					path = res+"/images/wereld/led000.png";
					
				}
				
				
				
				try {
					
					gd.robot = ImageIO.read(this.getClass().getResource(path));
					gd.rotation = (Integer.parseInt(main.simulator.toolsPanel.selectedURL.charAt(main.simulator.toolsPanel.selectedURL.length()-5)+"")-1)*90.;
					gd.pointX2 = x;
					gd.pointY2 = y;
					gd.refreshSensors();
					//main.simulator.print("#zz:"+path+"\n");
					
					//System.out.println("# rotation = "+gd.rotation+"\turl="+main.simulator.toolsPanel.selectedURL);
					System.out.println("GraphicsPanel:\trobot is placed on ("+gd.pointX2+","+gd.pointY2+") after click on ("+x+","+y+")");
				} catch (IOException e) {
					//main.simulator.print("ERROR: Image not found:\n" + path);
					System.out.println("ERROR: Image not found:" + path);
					e.printStackTrace();
				}
				
				
			}else{		//voeg toe aan matrix van plattegrond
				//main.simulator.print("#plattegrond\n");
				//String path = "/Users/Kucuk/Documents/eclipse/workspace/Lego Mindstorms Simulator/res" + main.simulator.toolsPanel.selectedURL;
				String path = res + main.simulator.toolsPanel.selectedURL;
				try {
					
					//main.simulator.print("begin path="+path+"\n");
					
					//gd.images[gd.rijClicked][gd.kolomClicked] = ImageIO.read(new File(path));
					//ImageIcon image = new ImageIcon(this.getClass().getResource(path));
					//path = this.getClass().getResource(path).toString().substring(9);
					//path = path.substring(0, 36) + path.substring(37, path.length());

					//main.simulator.print("#zz:"+path+"\n");
					gd.images[gd.rijClicked][gd.kolomClicked] = ImageIO.read(this.getClass().getResource(path));
					gd.imagesURL[gd.rijClicked][gd.kolomClicked] = path;
					
					//main.simulator.print("end\n");
					System.out.println("GraphicsPanel:\timages["+gd.rijClicked+"]["+gd.kolomClicked+"] is changed by click<" + x + "," + y+">");
				} catch (IOException e) {
					//main.simulator.print("ERROR: Image not found:\n" + path);
					System.out.println("ERROR: Image not found:" + path);
					e.printStackTrace();
				}
			}
			
			
		}else if(main.simulator.deletePanel.isClicked){
			
			//main.simulator.print("#DeletePanel is clicked "+x+","+y+"\n");
			
			if(gd.robot!=null){		//verwijder robot
				int xmin = gd.pointX2, xmax = gd.pointX2 + gd.robot.getWidth(null);
				int ymin = gd.pointY2, ymax = gd.pointY2 + gd.robot.getHeight(null);
						
				if(x >= xmin && x <= xmax && y >= ymin && y <= ymax){	//clicked op robot
					gd.robot=null;
					gd.pointX2 = gd.pointY2 = -1;
					gd.wisLight();
					gd.wisUltra();
					
				}else{
					gd.images[gd.rijClicked][gd.kolomClicked] = null;
					gd.imagesURL[gd.rijClicked][gd.kolomClicked] = null;
					return;
				}
			}else{		//verwijder tool uit images
				gd.images[gd.rijClicked][gd.kolomClicked] = null;
				gd.imagesURL[gd.rijClicked][gd.kolomClicked] = null;
			}
			
		}
		
		//main.simulator.print("#"+printImages());
		
	}
	
	public String printImages(){
		String temp = "";
		for(int i=0; i<gd.KOLOMMEN; i++){
			for(int j=0; j<gd.RIJEN; j++){
				
				if(gd.images[j][i]!=null){
					temp+=j+","+i+": "+gd.imagesURL[j][i]+"\n";
				}
				
				
			}
		}
		
		return temp;
	}
	
	
	
	public void drive(){
		
		int speedLeft = main.init.robot.motorLeft.getSpeed(), speedRight = main.init.robot.motorRight.getSpeed();
		//System.out.println("speedLeft = "+speedLeft+"\tspeedRight = "+speedRight);
		if(!(speedLeft==0 && speedRight==0)){
			gd.moveStep(speedLeft, speedRight);
			
			double r = gd.getDegree(speedLeft-speedRight)*gd.ROTATION_STEP;
			//System.out.println("gd.degree = "+gd.getDegree(speedLeft-speedRight)+"\tmet r.step = "+r);
			
			gd.rotate(speedLeft, speedRight, r);
			
		}else{
			//System.out.println("speedLeft and speedRight zijn beide 0, dus no moving");
		}
		
		
	}
	
}

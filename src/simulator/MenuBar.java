package simulator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar{
	
	public static int WIDTH = Simulator.WIDTH, HEIGHT = 22;
	JMenu file;
		//JMenuItem init;
		JMenuItem clear;
		JMenuItem importMatlab;
		JMenuItem quit;
	//JMenu edit;
		//JMenuItem undo;
	JMenu run;
		JMenuItem run2;
		JMenuItem abort;
	
	public Main main;
		
	public boolean runIsClicked;	
	
	
	public MenuBar(Main main){
		this.main = main;
		runIsClicked = false;
		
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		init();	
		updateItems();
		remove();
		addItems();
		
	}
	
	//makes items
	private void init(){
		file = new JMenu("File");
		/*
		init = new JMenuItem("Back to initialize");
		init.setIcon(new ImageIcon(this.getClass().getResource("/images/menu/init.png")));
		init.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("MenuBar:\t" + e.getActionCommand());
				
				Main.start();
				//Main.init = new Init(Simulator.frame);
                
			}
        });
        */
		
		clear = new JMenuItem("Clear environment");
		clear.setIcon(new ImageIcon(this.getClass().getResource("/images/menu/clear.png")));
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                System.out.println("MenuBar:\t" + e.getActionCommand());
                main.simulator.graphicsPanel.gd.clearImages();
                
			}
        });
        
		
		importMatlab = new JMenuItem("Import MATLAB-file");
		importMatlab.setIcon(new ImageIcon(this.getClass().getResource("/images/menu/import.png")));
		importMatlab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                System.out.println("MenuBar:\t" + e.getActionCommand());
                
                //Simulator.codePanel = new CodePanel(new FilePanel().getCode());
                //Main.simulator.refresh();
                
                main.simulator.code = new FilePanel(main).getCode();
                main.simulator.codePanel.refresh();
                main.simulator.refresh();
                
                
                
			}
        });
		
		quit = new JMenuItem("Quit");
		quit.setIcon(new ImageIcon(this.getClass().getResource("/images/menu/quit.png")));
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                System.out.println("MenuBar:\t" + e.getActionCommand());
                
                main.frame.dispose();
                
			}
        });
		
	/*
	
	edit = new JMenu("Edit");
		undo = new JMenuItem("Undo");
		undo.setIcon(new ImageIcon(this.getClass().getResource("/images/menu/undo.png")));
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                System.out.println("MenuBar:\t" + e.getActionCommand());
			}
        });
	*/
	
	run = new JMenu("Run");
		run2 = new JMenuItem("Run");
		run2.setIcon(new ImageIcon(this.getClass().getResource("/images/menu/play.png")));
		run2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                System.out.println("MenuBar:\t" + e.getActionCommand());
                
                runIsClicked = true;
                main.simulator.menuBar.refresh();
                main.simulator.run = new Run(main);
                
			}
        });
	
	
	
	
	abort = new JMenuItem("Abort");
	abort.setIcon(new ImageIcon(this.getClass().getResource("/images/menu/abort.png")));
	abort.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
            System.out.println("MenuBar:\t" + e.getActionCommand());
            main.simulator.outputPanel.ververs();
            main.simulator.run.ververs();
            main.ververs();
            runIsClicked = false;
            main.simulator.menuBar.refresh();
            //main.simulator.graphicsPanel.gd.zetStop;
            
            
		}
    });
	}
	
	
	//updates items
	private void updateItems(){
		
		if(main.simulator.code==null){
			run2.setEnabled(false);
			
			
		}else{
			run2.setEnabled(true);
			
		}
		
		if(run2.isEnabled() && runIsClicked){
			abort.setEnabled(true);
		}else{
			abort.setEnabled(false);
		}
		
	}
	
	//remove all
	private void remove(){
		
		file.removeAll();
		//edit.removeAll();
		run.removeAll();
		removeAll();
	}
	
	//adds items to menu
	private void addItems(){
		//file.add(init);

		//file.addSeparator();
		file.add(clear);	
		file.add(importMatlab);
		file.addSeparator();
		file.add(quit);
		
		//edit.add(undo);
		
		run.add(run2);
		run.addSeparator();
		run.add(abort);
		
		add(file);
		//add(edit);
		add(run);
		
	}
	
	public void refresh(){
		updateItems();
		remove();
		addItems();
	}
	
	
}



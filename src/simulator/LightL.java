package simulator;

public class LightL implements Sensor{
	
	public Robot robot;
	
	public LightL(Robot robot){
		this.robot = robot;
		//Main.simulator.graphicsPanel.gd.lightIsOn = false;
	}
	
	
	public String getType(){
		return "LightL";
	}
	
	public void open() {
		//Main.simulator.graphicsPanel.gd.lightIsOn = true;
	}
	
	public void close() {
		//Main.simulator.graphicsPanel.gd.lightIsOn = false;
	}
	
	public int getValue() {
		return robot.main.simulator.graphicsPanel.gd.lightL;
	}
}

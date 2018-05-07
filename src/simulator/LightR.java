package simulator;

public class LightR implements Sensor{
	
	public Robot robot;
	
	public LightR(Robot robot){
		this.robot = robot;
		//Main.simulator.graphicsPanel.gd.lightIsOn = false;
	}
	
	
	public String getType(){
		return "LightR";
	}
	
	public void open() {
		//Main.simulator.graphicsPanel.gd.lightIsOn = true;
	}
	
	public void close() {
		//Main.simulator.graphicsPanel.gd.lightIsOn = false;
	}
	
	public int getValue() {
		//Main.simulator.graphicsPanel.gd.lig
		return robot.main.simulator.graphicsPanel.gd.lightR;
	}
}

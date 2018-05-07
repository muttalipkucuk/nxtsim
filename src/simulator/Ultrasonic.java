package simulator;

public class Ultrasonic implements Sensor{
	
	private boolean power;
	
	
	public Robot robot;
	
	public Ultrasonic(Robot robot){
		
		this.robot = robot;
		
		power = false;
	}
	
	public String getType() {
		return "Ultrasonic";
	}

	public void open() {
		power = true;
	}

	public void close() {
		power = false;
	}
	
	public int getValue() {
		return robot.main.simulator.graphicsPanel.gd.ultraDistance;
	}
	
}

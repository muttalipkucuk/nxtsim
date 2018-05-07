package simulator;

import java.awt.Color;

public class LED implements Actuator{
	
	private boolean power;
	
	Robot robot;
	
	public LED(Robot robot){
		this.robot = robot;
		
		this.power = false;
	}
	
	public String getType(){
		return "LED";
	}

	public void open() {
		power = true;
	}
	
	public void close() {
		power = false;
	}
	
	

	@Override
	public boolean isOn() {
		return power;
	}
	
}

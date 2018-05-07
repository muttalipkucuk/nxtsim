package simulator;

public class MotorL implements Actuator{
	
	private int speed;
	private boolean power;
	
	public Robot robot;
	
	public MotorL(Robot robot){
		this.robot = robot;
		
		speed = 0;
		power = false;
	}
	
	public MotorL(int speed){
		this.speed = speed;
		power = false;
	}
	
	public String getType() {
		return "MotorLeft";
	}

	@Override
	public void open() {
		power = true;
		
	}

	@Override
	public void close() {
		power = false;
		
	}
	
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public int getSpeed(){
		if(power){
			return speed;
		}else{
			return 0;
		}
		
	}

	@Override
	public boolean isOn() {
		return power;
	}
}

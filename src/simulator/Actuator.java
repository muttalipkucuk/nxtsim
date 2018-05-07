package simulator;

public interface Actuator {
	
	public String getType();
	public void open();
	public void close();
	public boolean isOn();
}

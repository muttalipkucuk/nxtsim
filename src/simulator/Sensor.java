package simulator;

public interface Sensor {
	
	public String getType();
	
	public void open();
	public void close();
	public int getValue();
}

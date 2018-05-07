package simulator;

public class Loop {
	
	public String type;
	public String line;
	public int start;
	public int end;
	public int i;
	
	public Loop(String type, String line, int start, int end){
		this.type = type;
		this.line = line;
		this.start = start;
		this.end = end;
		this.i = i;
	}
	
	public String toString(){
		return (type+", "+start+", "+end);
	}
	
	public void increase(){
		i++;
	}
	
	public String getVariabele(){
		//line = "for i=1:5"
		int r = line.indexOf('r');
		int is = line.indexOf('=');
		return line.substring(r+1, is).trim();
	}
	
}
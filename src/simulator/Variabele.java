package simulator;


public class Variabele {
	
	private int aantalElementen;
	
	private String[] naam;
	private String[] waarde;
	
	public Variabele(){
		aantalElementen = 0;
		naam = new String[10];
		waarde = new String[10];
		
	}
	
	public Variabele(int i){
		aantalElementen = 0;
		naam = new String[i];
		waarde = new String[i];
	}
	
	public void add(String s, String o){
		if(!has(s)){
			naam[aantalElementen] = s;
			waarde[aantalElementen] = o;
			aantalElementen++;
		}
	}
	
	
	public boolean has(String s){
		//System.out.println("has("+s+")");
		for(int i = 0; i < aantalElementen; i++){
			//System.out.println("naam["+i+"].equals("+s+") = " +naam[i].equals(s));
			if(naam[i].equals(s)){
				return true;
			}
		}
		//System.out.println("Variabele:\tThere is no variabele with name = \"" + s + "\"");
		return false;
		
	}
	
	public String getWaarde(String s){
		for(int i = 0; i < aantalElementen; i++){
			if(naam[i].equals(s)){
				return waarde[i];
			}
		}
		System.out.println("Variabele:\tThere is no variabele with name = \"" + s + "\"");
		return null;
	}
	
	public void setWaarde(String naam, String waarde){
		
		for(int i=0; i<aantalElementen; i++){
			if(this.naam[i].equals(naam)){
				this.waarde[i] = waarde;
				return;
			}
		}
		
	}
	
	
	public void increaseWaarde(String s){
		int waarde = Integer.parseInt(getWaarde(s));
		waarde++;
		setWaarde(s, waarde+"");
		
	}
	
	public void print(){
		System.out.println("Variabele:\tDit zijn alle variabeles:");
		for(int i = 0; i < aantalElementen; i++){
			System.out.println(naam[i] + "\t=\t" + waarde[i]);
		}
	}
	
	
	public void setPower(String var, int snelheid){
		//var = "mA"			snelheid = 20
		//waarde = "NXTMotor('A', 'Power', 20);"
		String waarde = getWaarde(var);
		//22
		int laatsteKomma = (waarde.lastIndexOf(",")+1);
		//" 20)"
		String voor = waarde.substring(0, laatsteKomma);
		String na = ""+snelheid+")";
		
		
		setWaarde(var, voor+na);
		 
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
}

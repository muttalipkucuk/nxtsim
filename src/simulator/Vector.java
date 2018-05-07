package simulator;

public class Vector {
	
	public String[] naam;
	public int[][] waarde;
	
	public int aantalNamen;
	public static final int MAX_AANTAL_NAMEN = 10;
	public static final int MAX_AANTAL_WAARDES = 50;
	
	
	public Vector(){
		naam = new String[MAX_AANTAL_NAMEN];
		waarde = new int[MAX_AANTAL_NAMEN][MAX_AANTAL_WAARDES];
		aantalNamen = 0;
	}
	
	public void add(String naam, int i, int waarde){
		int index = index(naam);
		
		
		if(index != -1){
			this.waarde[index][i] = waarde;
		}else{
			this.naam[aantalNamen] = naam;
			this.waarde[aantalNamen][i] = waarde;
			aantalNamen++;
			
		}
		
		
		
	}
	
	public boolean has(String naam){
		return index(naam) != -1;
	}
	
	
	public int getWaarde(String naam, int i){
		
		int index = index(naam);
		return waarde[index][i];
		
	}
	
	
	public int index(String naam){
		for(int i=0; i<aantalNamen; i++){
			if(this.naam[i].equals(naam)){
				return i;
			}
		}
		return -1;
	}
	
	
}
